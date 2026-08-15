package hauveli.hexoncommand

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.common.lib.HexAttributes
import at.petrak.hexcasting.common.lib.HexSounds
import at.petrak.hexcasting.common.msgs.MsgClearSpiralPatternsS2C
import at.petrak.hexcasting.common.msgs.MsgOpenSpellGuiS2C
import at.petrak.hexcasting.xplat.IXplatAbstractions
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.Commands.literal
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand

class HexOnCommand : ModInitializer {

	fun handleCastingGridPacket(player: ServerPlayer) {
		if (player.getAttributeValue(HexAttributes.FEEBLE_MIND) > 0.0) return

		val level = player.serverLevel()
		val hand = InteractionHand.MAIN_HAND

		if (player.isShiftKeyDown) {
			if (level.isClientSide) {
				player.playSound(HexSounds.STAFF_RESET.value(), 1f, 1f)
			}
			IXplatAbstractions.INSTANCE.clearCastingData(player)
			val packet = MsgClearSpiralPatternsS2C(player.uuid)
			IXplatAbstractions.INSTANCE.sendPacketToPlayer(player, packet)
			IXplatAbstractions.INSTANCE.sendPacketTracking(player, packet)
		}

		if (!level.isClientSide) {
			val vm = IXplatAbstractions.INSTANCE.getStaffcastVM(player, hand)
			val patterns = IXplatAbstractions.INSTANCE.getPatternsSavedInUi(player)
			val stack = vm.image.stack
			val ravenmind: Iota? = vm.image.ravenmind().orElse(null)

			IXplatAbstractions.INSTANCE.sendPacketToPlayer(
				player,
				MsgOpenSpellGuiS2C(hand, patterns, stack, ravenmind, 0)
			)
		}
	}

	override fun onInitialize() {
		CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
			dispatcher.register(
				literal("hexcastinggui").executes { context ->
					val player = context.source.player

					if (player != null &&
						player.getAttributeValue(HexOnCommandAttributes.COMMAND_PERMISSION) > 0.0
					) {
						handleCastingGridPacket(player)
					}

					1
				}
			)
		}
	}
}
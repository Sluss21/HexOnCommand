package hauveli.hexoncommand.mixin;

import hauveli.hexoncommand.HexOnCommandAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerAttributesMixin {

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void hexoncommand$addAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        AttributeSupplier.Builder builder = cir.getReturnValue();
        builder.add(HexOnCommandAttributes.COMMAND_PERMISSION);
        cir.setReturnValue(builder);
    }
}
package hauveli.hexoncommand.mixin;

// homework copied from https://github.com/FallingColors/HexMod/blob/532fe9a60138544112e096812c7aefb78b3d7364/Fabric/src/main/java/at/petrak/hexcasting/fabric/mixin/FabricPlayerMixin.java
import hauveli.hexoncommand.HexOnCommandAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class FabricPlayerMixin extends LivingEntity {

    protected FabricPlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void hex$addAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        AttributeSupplier.Builder out = cir.getReturnValue();
        out.add(HexOnCommandAttributes.COMMAND_PERMISSION);

        // ⭐ REQUIRED: return the modified builder
        cir.setReturnValue(out);
    }
}

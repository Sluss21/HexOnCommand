package hauveli.hexoncommand

import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.RangedAttribute

object HexOnCommandAttributes {

    const val MODID = "hexoncommand"
    const val ATTR_NAME = "command_permission"

    lateinit var COMMAND_PERMISSION: Holder<Attribute>

    init {
        val id = ResourceLocation.tryParse("$MODID:$ATTR_NAME")!!

        val attribute = RangedAttribute(
            "attribute.$MODID.$ATTR_NAME",
            0.0,
            0.0,
            1.0
        ).setSyncable(true)

        COMMAND_PERMISSION = Registry.registerForHolder(
            BuiltInRegistries.ATTRIBUTE,
            id,
            attribute
        )
    }
}

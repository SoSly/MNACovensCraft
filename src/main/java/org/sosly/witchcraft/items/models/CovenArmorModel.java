package org.sosly.witchcraft.items.models;

import net.minecraft.resources.ResourceLocation;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.items.armor.CovenArmorItem;
import software.bernie.geckolib.model.GeoModel;

public class CovenArmorModel extends GeoModel<CovenArmorItem> {
    public CovenArmorModel() {
    }

    public ResourceLocation getModelResource(CovenArmorItem object) {
        return new ResourceLocation(Witchcraft.MOD_ID, "geo/coven_armor.geo.json");
    }

    public ResourceLocation getTextureResource(CovenArmorItem object) {
        return new ResourceLocation(Witchcraft.MOD_ID, "textures/item/coven_armor.png");
    }

    public ResourceLocation getAnimationResource(CovenArmorItem animatable) {
        // todo: get some animations maybe
        return new ResourceLocation("mna", "animations/none.anim.json");
    }
}

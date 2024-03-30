package org.sosly.witchcraft.items.renderers;

import org.sosly.witchcraft.items.armor.CovenArmorItem;
import org.sosly.witchcraft.items.models.CovenArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CovenArmorRenderer extends GeoArmorRenderer<CovenArmorItem> {
    public CovenArmorRenderer() {
        super(new CovenArmorModel());
    }
}

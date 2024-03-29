package org.sosly.covenscraft.items.renderers;

import org.sosly.covenscraft.items.armor.CovenArmorItem;
import org.sosly.covenscraft.items.models.CovenArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CovenArmorRenderer extends GeoArmorRenderer<CovenArmorItem> {
    public CovenArmorRenderer() {
        super(new CovenArmorModel());
    }
}

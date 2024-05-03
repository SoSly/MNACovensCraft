package org.sosly.witchcraft.gui.screens;

import com.mna.api.ManaAndArtificeMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

abstract class AbstractPlayerInventoryScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    ResourceLocation InventoryTexture = new ResourceLocation(ManaAndArtificeMod.ID, "textures/gui/standalone_player_inventory.png");

    public AbstractPlayerInventoryScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    void renderPlayerInventory(GuiGraphics graphics, int centerX, int centerY) {
        graphics.blit(InventoryTexture, centerX - 88, centerY - 45, 0.0F, 0.0F, 176, 90, 176, 90);
    }
}

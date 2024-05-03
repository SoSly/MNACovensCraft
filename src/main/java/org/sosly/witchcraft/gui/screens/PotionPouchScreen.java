package org.sosly.witchcraft.gui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.gui.containers.PotionPouchContainer;

public class PotionPouchScreen extends AbstractPlayerInventoryScreen<PotionPouchContainer> {
    public static final ResourceLocation InventoryTexture = new ResourceLocation(Witchcraft.MOD_ID, "textures/gui/potion_pouch.png");

    public PotionPouchScreen(PotionPouchContainer menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        this.renderBackground(graphics);
        int xPos = this.leftPos + (this.imageWidth / 2);
        int yPos = (this.topPos + this.imageHeight) - 90;
        this.renderPlayerInventory(graphics, xPos, yPos);

        yPos -= 72;
        graphics.blit(InventoryTexture, xPos - 96, yPos, 0, 118, 150, 32);
    }

    @Override
    public void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderTooltip(graphics, mouseX - this.leftPos, mouseY - this.topPos);
    }
}

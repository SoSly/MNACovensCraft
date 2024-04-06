package org.sosly.witchcraft.items.sympathy;

import com.mna.api.items.ChargeableItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class AntiSympathyCharmItem extends ChargeableItem {
    public AntiSympathyCharmItem() {
        super(new Properties().stacksTo(1), 1000);
    }

    @Override
    protected boolean tickEffect(ItemStack itemStack, Player player, Level level, int i, float v, boolean b) {
        return false;
    }

    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.mnaw.antisympathy_charm.flavor").withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}

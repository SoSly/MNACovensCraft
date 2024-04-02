package org.sosly.witchcraft.items.armor;

import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.ITieredItem;
import com.mna.items.armor.IBrokenArmorReplaceable;
import com.mna.items.armor.ISetItem;
import com.mna.items.base.IManaRepairable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.factions.FactionRegistry;
import org.sosly.witchcraft.items.renderers.CovenArmorRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class CovenArmorItem extends ArmorItem implements GeoItem, ISetItem, ITieredItem<CovenArmorItem>, IFactionSpecific, IBrokenArmorReplaceable<CovenArmorItem>, IManaRepairable {
    private int tier = -1;
    private static final ResourceLocation COVEN_SET_BONUS = new ResourceLocation(Witchcraft.MOD_ID, "coven_armor_set_bonus");
    private AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);

    public CovenArmorItem(ArmorMaterial material, Type slot, Properties builder) {
        super(material, slot, builder.rarity(Rarity.EPIC));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animCache;
    }

    @Override
    public void applySetBonus(LivingEntity entity, EquipmentSlot... setSlots) {
        // todo
    }

    @Override
    public void setCachedTier(int i) {
        tier = i;
    }

    @Override
    public int getCachedTier() {
        return tier;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return super.damageItem(stack, amount, entity, onBroken);
    }


    @Override
    public IFaction getFaction() {
        return FactionRegistry.COVEN;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> defaultModel) {
                if (renderer == null) {
                    renderer = new CovenArmorRenderer();
                }

                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, defaultModel);
                return renderer;
            }
        });
    }

    @Override
    public int itemsForSetBonus() {
        return 4;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public ResourceLocation getSetIdentifier() {
        return COVEN_SET_BONUS;
    }
}

package org.sosly.witchcraft.items;

import com.mna.api.items.MACreativeTabs;
import com.mna.items.armor.MAArmorMaterial;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.sosly.witchcraft.Witchcraft;
import org.sosly.witchcraft.items.armor.CovenArmorItem;
import org.sosly.witchcraft.items.sympathy.AntiSympathyCharmItem;
import org.sosly.witchcraft.items.sympathy.BloodyNeedleItem;

@Mod.EventBusSubscriber(modid= Witchcraft.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Witchcraft.MOD_ID);
    public static final RegistryObject<Item> ANTISYMPATHY_CHARM = ITEMS.register("antisympathy_charm", AntiSympathyCharmItem::new);
    public static final RegistryObject<Item> COVEN_ARMOR_CHEST = ITEMS.register("coven_armor_chest", () -> new CovenArmorItem(
            MAArmorMaterial.INFUSED_SILK, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> COVEN_ARMOR_LEGGINGS = ITEMS.register("coven_armor_leggings", () -> new CovenArmorItem(
            MAArmorMaterial.INFUSED_SILK, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> COVEN_ARMOR_HEAD = ITEMS.register("coven_armor_helmet", () -> new CovenArmorItem(
            MAArmorMaterial.INFUSED_SILK, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> COVEN_ARMOR_BOOTS = ITEMS.register("coven_armor_boots", () -> new CovenArmorItem(
            MAArmorMaterial.INFUSED_SILK, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> BLOODY_NEEDLE = ITEMS.register("bloody_needle", BloodyNeedleItem::new);

    @SubscribeEvent
    public static void FillCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == MACreativeTabs.GENERAL) {
            ITEMS.getEntries().stream().map(RegistryObject::get).forEach(event::accept);
        }
    }
}

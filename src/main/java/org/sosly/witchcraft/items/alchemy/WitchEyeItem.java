package org.sosly.witchcraft.items.alchemy;

import com.mysticalchemy.init.RecipeInit;
import com.mysticalchemy.recipe.PotionIngredientRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.sosly.witchcraft.items.ItemRegistry;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Optional;

public class WitchEyeItem extends Item {
    public WitchEyeItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    public static boolean hasWitchEye(Player player) {
        Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(player).resolve();
        if (curios.isEmpty()) {
            return false;
        }

        Optional<SlotResult> result = curios.get().findFirstCurio(ItemRegistry.WITCH_EYE.get());
        return result.isPresent();
    }

    public static void revealAlchemicalProperties(Level level, ItemStack item, List<Component> tooltips) {
        if (!ModList.get().isLoaded("mysticalchemy")) {
            return;
        }

        RecipeManager recipes = level.getRecipeManager();
        Optional<PotionIngredientRecipe> recipe = recipes.getRecipeFor(RecipeInit.POTION_RECIPE_TYPE.get(), createDummyCraftingInventory(item), level);
        if (recipe.isEmpty()) {
            return;
        }

        tooltips.add(Component.translatable("item.mnaw.witch_eye/tooltip")
                .withStyle(ChatFormatting.LIGHT_PURPLE)
                .withStyle(ChatFormatting.ITALIC));

        recipe.get().getEffects().forEach((effect, value) -> {
            MutableComponent component = Component.literal("  - ");
            component.append(effect.getDisplayName());
            component.append(": ");
            component.append(String.valueOf(value));
            component.withStyle(ChatFormatting.LIGHT_PURPLE);
            tooltips.add(component);
        });
    }

    private static CraftingContainer createDummyCraftingInventory(ItemStack stack) {
        CraftingContainer craftinginventory = new TransientCraftingContainer(new AbstractContainerMenu((MenuType)null, -1) {
            public boolean stillValid(Player playerIn) {
                return false;
            }

            public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
                return ItemStack.EMPTY;
            }
        }, 1, 1);
        craftinginventory.setItem(0, stack);
        return craftinginventory;
    }
}

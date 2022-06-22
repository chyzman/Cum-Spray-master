package net.immortaldevs.cumspray;


import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CumRecipe extends SpecialCraftingRecipe {
    public CumRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        boolean victim = false;
        boolean spray = false;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;

            if (stack.isOf(CumSprayItems.CUM_SPRAY)) {
                if (spray) {
                    if (victim) return false;
                    victim = true;
                }
                spray = true;
            } else {
                if (victim) return false;
                if (stack.getNbt() != null && stack.getNbt().getBoolean("cum_coated")) return false;
                victim = true;
            }
        }

        return victim && spray;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        boolean doublecum = false;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;
            if (stack.isOf(CumSprayItems.CUM_SPRAY)) {
                if (!doublecum) {
                    doublecum = true;
                    continue;
                }
            }

            ItemStack victim = stack.copy();
            victim.setCount(1);
            victim.getOrCreateNbt().putBoolean("cum_coated", true);

            return victim;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CumSprayRecipeSerialisers.CUM;
    }
}

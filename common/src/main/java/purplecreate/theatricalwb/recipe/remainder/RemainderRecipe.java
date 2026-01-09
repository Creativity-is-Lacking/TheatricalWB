package purplecreate.theatricalwb.recipe.remainder;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import purplecreate.theatricalwb.registry.WRecipeSerializers;

public class RemainderRecipe extends ShapelessRecipe {
  final NonNullList<ItemStack> remainders;

  public RemainderRecipe(ResourceLocation id, String group, CraftingBookCategory category, ItemStack result, NonNullList<ItemStack> remainder, NonNullList<Ingredient> ingredients) {
    super(id, group, category, result, ingredients);
    this.remainders = remainder;
  }

  @Override
  public boolean isSpecial() {
    return true;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return WRecipeSerializers.REMAINDER;
  }

  public NonNullList<ItemStack> getRemainders() {
    return remainders;
  }

  @Override
  public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
    NonNullList<ItemStack> result = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
    int usableSlotsWithItems = 0;
    int remainderIndex = 0;

    for (int i = 0; i < result.size(); i++) {
      ItemStack item = inv.getItem(i);
      if (!item.isEmpty() && !item.getItem().hasCraftingRemainingItem())
        usableSlotsWithItems++;
    }

    for (int i = 0; i < result.size(); i++) {
      ItemStack item = inv.getItem(i);
      if (item.getItem().hasCraftingRemainingItem()) {
        result.set(i, new ItemStack(item.getItem().getCraftingRemainingItem()));
      } else if (!item.isEmpty() || usableSlotsWithItems < remainders.size()) {
        result.set(i, remainders.get(remainderIndex++).copy());
        if (remainderIndex >= remainders.size()) break;
      }
    }

    return result;
  }

  @Override
  public boolean canCraftInDimensions(int width, int height) {
    return super.canCraftInDimensions(width, height) && width * height >= remainders.size();
  }
}

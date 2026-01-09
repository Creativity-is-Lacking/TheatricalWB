package purplecreate.theatricalwb.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;
import purplecreate.theatricalwb.recipe.remainder.RemainderRecipe;

import java.util.ArrayList;
import java.util.List;

import static purplecreate.theatricalwb.util.RecipeHelper.getResult;

public class RemainderCategoryExtension implements ICraftingCategoryExtension {
  final RemainderRecipe recipe;

  public RemainderCategoryExtension(RemainderRecipe recipe) {
    this.recipe = recipe;
  }

  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
    List<List<ItemStack>> inputs = new ArrayList<>();
    for (Ingredient ingredient : recipe.getIngredients()) {
      List<ItemStack> items = List.of(ingredient.getItems());
      inputs.add(items);
    }
    ItemStack resultItem = getResult(recipe);

    int width = getWidth();
    int height = getHeight();

    craftingGridHelper.createAndSetOutputs(builder, List.of(resultItem))
      .addItemStacks(recipe.getRemainders());

    craftingGridHelper.createAndSetInputs(builder, inputs, width, height);
  }

  @Nullable
  @Override
  public ResourceLocation getRegistryName() {
    return recipe.getId();
  }
}

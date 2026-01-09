package purplecreate.theatricalwb.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import purplecreate.theatricalwb.recipe.remainder.RemainderRecipe;
import purplecreate.theatricalwb.recipe.remainder.RemainderRecipeSerializer;
import purplecreate.theatricalwb.recipe.workbench.WorkbenchRecipe;
import purplecreate.theatricalwb.recipe.workbench.WorkbenchRecipeSerializer;

public class WRecipeSerializers {
  public static final RecipeSerializer<RemainderRecipe> REMAINDER = register("remainder", new RemainderRecipeSerializer());
  public static final RecipeSerializer<WorkbenchRecipe> WORKBENCH = register("workbench", new WorkbenchRecipeSerializer());

  @ExpectPlatform
  private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String path, S recipeSerializer) {
    throw new AssertionError();
  }

  public static void register() {}
}

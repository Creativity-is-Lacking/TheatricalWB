package purplecreate.theatricalwb.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import purplecreate.theatricalwb.recipe.workbench.WorkbenchRecipe;

public class WRecipeTypes {
  public static final RecipeType<WorkbenchRecipe> WORKBENCH = register("workbench");

  @ExpectPlatform
  private static <T extends Recipe<?>> RecipeType<T> register(String path) {
    throw new AssertionError();
  }

  public static void register() {}
}

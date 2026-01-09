package purplecreate.theatricalwb.registry.forge;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;
import purplecreate.theatricalwb.Workbench;

public class WRecipeTypesImpl {
  public static <T extends Recipe<?>> RecipeType<T> register(String path) {
    RecipeType<T> recipeType = new RecipeType<T>() {
      @Override
      public String toString() {
        return path;
      }
    };

    ForgeRegistries.RECIPE_TYPES.register(Workbench.rl(path), recipeType);
    return recipeType;
  }
}

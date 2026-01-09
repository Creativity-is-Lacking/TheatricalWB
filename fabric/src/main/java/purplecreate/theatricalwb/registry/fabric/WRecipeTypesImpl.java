package purplecreate.theatricalwb.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import purplecreate.theatricalwb.Workbench;

public class WRecipeTypesImpl {
  public static <T extends Recipe<?>> RecipeType<T> register(String path) {
    return Registry.register(BuiltInRegistries.RECIPE_TYPE, Workbench.rl(path), new RecipeType<T>() {
      @Override
      public String toString() {
        return path;
      }
    });
  }
}

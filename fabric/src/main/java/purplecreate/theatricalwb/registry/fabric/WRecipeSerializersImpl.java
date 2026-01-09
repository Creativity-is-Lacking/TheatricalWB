package purplecreate.theatricalwb.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import purplecreate.theatricalwb.Workbench;

public class WRecipeSerializersImpl {
  public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String path, S recipeSerializer) {
    return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Workbench.rl(path), recipeSerializer);
  }
}

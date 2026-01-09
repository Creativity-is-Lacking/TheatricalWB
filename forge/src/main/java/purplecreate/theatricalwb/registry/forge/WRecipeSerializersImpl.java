package purplecreate.theatricalwb.registry.forge;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import purplecreate.theatricalwb.Workbench;

public class WRecipeSerializersImpl {
  public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String path, S recipeSerializer) {
    ForgeRegistries.RECIPE_SERIALIZERS.register(Workbench.rl(path), recipeSerializer);
    return recipeSerializer;
  }
}

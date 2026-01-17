package purplecreate.theatricalwb.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import dev.architectury.injectables.annotations.ExpectPlatform;

public class RecipeHelper {
  @ExpectPlatform
  public static ItemStack getResult(Recipe<?> recipe) {
    throw new AssertionError();
  }
}

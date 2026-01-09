package purplecreate.theatricalwb.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeHelper {
  public static ItemStack getResult(Recipe<?> recipe) {
    ClientLevel level = Minecraft.getInstance().level;
    if (level == null)
      return ItemStack.EMPTY;
    return recipe.getResultItem(level.registryAccess());
  }
}

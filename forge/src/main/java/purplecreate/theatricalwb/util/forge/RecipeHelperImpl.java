package purplecreate.theatricalwb.util.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.server.ServerLifecycleHooks;

public class RecipeHelperImpl {
  public static ItemStack getResult(Recipe<?> recipe) {
     if(ServerLifecycleHooks.getCurrentServer() == null){
       return ItemStack.EMPTY;
     } else {
       ServerLevel serverLevel = ServerLifecycleHooks.getCurrentServer().getAllLevels().iterator().next();
       return recipe.getResultItem(serverLevel.registryAccess());
     }
  }
}

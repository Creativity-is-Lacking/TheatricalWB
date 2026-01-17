package purplecreate.theatricalwb.util.fabric;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import purplecreate.theatricalwb.fabric.WorkbenchImpl;

public class RecipeHelperImpl {
  public static ItemStack getResult(Recipe<?> recipe) {
     if(WorkbenchImpl.currentServer == null){
       return ItemStack.EMPTY;
     } else {
       ServerLevel serverLevel = WorkbenchImpl.currentServer.getAllLevels().iterator().next();
       return recipe.getResultItem(serverLevel.registryAccess());
     }
  }
}

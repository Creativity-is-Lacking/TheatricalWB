package purplecreate.theatricalwb.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import purplecreate.theatricalwb.Workbench;
import purplecreate.theatricalwb.recipe.remainder.RemainderRecipe;
import purplecreate.theatricalwb.registry.WBlocks;
import purplecreate.theatricalwb.registry.WRecipeTypes;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
  @Override
  public ResourceLocation getPluginUid() {
    return Workbench.rl("jei");
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration) {
    IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
    registration.addRecipeCategories(new WorkbenchRecipeCategory(guiHelper));
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration) {
    RecipeManager recipeManager = Minecraft.getInstance().getConnection().getRecipeManager();
    registration.addRecipes(JeiRecipeTypes.WORKBENCH, recipeManager.getAllRecipesFor(WRecipeTypes.WORKBENCH));
  }

  @Override
  public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
    registration.getCraftingCategory().addCategoryExtension(RemainderRecipe.class, RemainderCategoryExtension::new);
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
    registration.addRecipeCatalyst(WBlocks.WORKBENCH, JeiRecipeTypes.WORKBENCH);
  }
}

package purplecreate.theatricalwb.compat.jei;

import mezz.jei.api.recipe.RecipeType;
import purplecreate.theatricalwb.Workbench;
import purplecreate.theatricalwb.recipe.workbench.WorkbenchRecipe;

public class JeiRecipeTypes {
  public static final RecipeType<WorkbenchRecipe> WORKBENCH = RecipeType.create(Workbench.ID, "workbench", WorkbenchRecipe.class);
}

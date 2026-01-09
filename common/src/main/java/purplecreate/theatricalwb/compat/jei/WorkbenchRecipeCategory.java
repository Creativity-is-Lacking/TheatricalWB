package purplecreate.theatricalwb.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import purplecreate.theatricalwb.block.workbench.WorkbenchBlock;
import purplecreate.theatricalwb.recipe.workbench.WorkbenchRecipe;
import purplecreate.theatricalwb.registry.WBlocks;

import static purplecreate.theatricalwb.util.RecipeHelper.getResult;

public class WorkbenchRecipeCategory extends AbstractRecipeCategory<WorkbenchRecipe> {
  final IGuiHelper guiHelper;

  public WorkbenchRecipeCategory(IGuiHelper guiHelper) {
    super(
      JeiRecipeTypes.WORKBENCH,
      WorkbenchBlock.TITLE_COMPONENT,
      guiHelper.createDrawableItemLike(WBlocks.WORKBENCH),
      98,
      54
    );
    this.guiHelper = guiHelper;
  }

  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, WorkbenchRecipe recipe, IFocusGroup focuses) {
    builder
      .addOutputSlot(77, 19)
      .setOutputSlotBackground()
      .addItemStack(getResult(recipe));

    for (int i = 0; i < 6; i++) {
      IRecipeSlotBuilder slot = builder
        .addInputSlot(1 + 18 * (i % 2), 1 + 18 * (i / 2))
        .setStandardSlotBackground();

      if (i < recipe.getIngredients().size()) {
        slot.addIngredients(recipe.getIngredients().get(i));
      }
    }
  }

  @Override
  public void createRecipeExtras(IRecipeExtrasBuilder builder, WorkbenchRecipe recipe, IFocusGroup focuses) {
    builder.addRecipeArrow().setPosition(43, 19);
  }

  @Override
  public void draw(WorkbenchRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
    super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
  }
}

package purplecreate.theatricalwb.block.workbench;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import purplecreate.theatricalwb.Workbench;
import purplecreate.theatricalwb.block.workbench.WorkbenchMenu.RecipeCollection;

public class WorkbenchScreen extends AbstractContainerScreen<WorkbenchMenu> {
  private static final ResourceLocation BACKGROUND = Workbench.rl("textures/gui/workbench.png");
  private float time = 0;
  private int scrollOffset = 0;

  public WorkbenchScreen(WorkbenchMenu menu, Inventory playerInventory, Component title) {
    super(menu, playerInventory, title);
    titleLabelY--;
    inventoryLabelY++;
  }

  private RecipeCollection getCurrentRecipe() {
    int i = menu.getSelectedRecipe();
    return i < 0 || i >= menu.getRecipes().size()
      ? null
      : menu.getRecipes().get(i);
  }

  @Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    time += partialTick;
    super.render(guiGraphics, mouseX, mouseY, partialTick);
    renderButtons(guiGraphics, mouseX, mouseY);
    renderGhostSlots(guiGraphics);
    renderTooltip(guiGraphics, mouseX, mouseY);
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
    renderBackground(guiGraphics);
    guiGraphics.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight);
  }

  @Override
  protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    super.renderTooltip(guiGraphics, mouseX, mouseY);

    for (int i = scrollOffset; i < menu.getRecipes().size() && i < scrollOffset + 12; i++) {
      int r = i - scrollOffset;
      int px = leftPos + 8 + r % 4 * 16;
      int py = topPos + 15 + r / 4 * 18;
      double dx = mouseX - px;
      double dy = mouseY - py;

      if (dx >= 0.0 && dy >= 0.0 && dx < 16.0 && dy < 18.0) {
        guiGraphics.renderTooltip(
          minecraft.font,
          new ItemStack(menu.getRecipes().get(i).item),
          mouseX,
          mouseY
        );
      }
    }

    RecipeCollection collection = getCurrentRecipe();
    for (int i = 0; i < 6; i++) {
      ItemStack item = menu.getItemInInputSlot(i);
      int px = leftPos + 103 + i % 2 * 18;
      int py = topPos + 16 + i / 2 * 18;
      double dx = mouseX - px;
      double dy = mouseY - py;

      if (item.isEmpty() && collection != null) {
        item = collection.getGhostItemForSlot(i, time);
      }

      if (!item.isEmpty() && dx >= 0.0 && dy >= 0.0 && dx < 18.0 && dy < 18.0) {
        guiGraphics.renderTooltip(minecraft.font, item, mouseX, mouseY);
      }
    }
  }

  private void renderButtons(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    for (int i = scrollOffset; i < menu.getRecipes().size() && i < scrollOffset + 12; i++) {
      ItemStack result = new ItemStack(menu.getRecipes().get(i).item);

      int r = i - scrollOffset;
      int px = leftPos + 8 + r % 4 * 16;
      int py = topPos + 15 + r / 4 * 18;
      int imageOffset = 166;

      if (i == menu.getSelectedRecipe()) {
        imageOffset += 18;
      } else if (mouseX >= px && mouseY >= py && mouseX < px + 16 && mouseY < py + 18) {
        imageOffset += 36;
      }

      guiGraphics.blit(BACKGROUND, px, py, 0, imageOffset, 16, 18);
      guiGraphics.renderFakeItem(result, px, py + 1);
    }

    // scrollbar
    double currentPage = ((double)scrollOffset / 12);
    double totalPages = Math.ceil((double)menu.getRecipes().size() / 12) - 1;

    guiGraphics.blit(
      BACKGROUND,
      leftPos + 75,
      topPos + 15 + (int)((currentPage / totalPages) * 39),
      176 + (menu.getRecipes().size() > 12 ? 0 : 12),
      0,
      12,
      15
    );
  }

  private void renderGhostSlots(GuiGraphics guiGraphics) {
    RecipeCollection collection = getCurrentRecipe();
    if (collection == null) return;

    for (int i = 0; i < 6; i++) {
      if (!menu.getItemInInputSlot(i).isEmpty()) continue;

      int px = leftPos + 103 + i % 2 * 18;
      int py = topPos + 16 + i / 2 * 18;

      ItemStack item = collection.getGhostItemForSlot(i, time);

      if (!item.isEmpty()) {
        guiGraphics.fill(px, py, px + 16, py + 16, 0x30_FF0000);
        guiGraphics.renderFakeItem(item, px, py);
        guiGraphics.fill(RenderType.guiGhostRecipeOverlay(), px, py, px + 16, py + 16, 0x30_FFFFFF);
        guiGraphics.renderItemDecorations(minecraft.font, item, px, py);
      }
    }
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int button) {
    for (int i = scrollOffset; i < menu.getRecipes().size() && i < scrollOffset + 12; i++) {
      int r = i - scrollOffset;
      int px = leftPos + 8 + r % 4 * 16;
      int py = topPos + 15 + r / 4 * 18;
      double dx = mouseX - px;
      double dy = mouseY - py;

      if (dx >= 0.0 && dy >= 0.0 && dx < 16.0 && dy < 18.0 && menu.clickMenuButton(minecraft.player, i)) {
        minecraft.gameMode.handleInventoryButtonClick(menu.containerId, i);
        return true;
      }
    }

    return super.mouseClicked(mouseX, mouseY, button);
  }

  @Override
  public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
    int nextOffset = scrollOffset - ((int)Math.round(delta) * 12);
    if (nextOffset >= 0 && nextOffset < menu.getRecipes().size()) {
      scrollOffset = nextOffset;
    }

    return true;
  }
}

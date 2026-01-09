package purplecreate.theatricalwb.block.workbench;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import purplecreate.theatricalwb.mixininterfaces.IIngredient;
import purplecreate.theatricalwb.recipe.workbench.WorkbenchRecipe;
import purplecreate.theatricalwb.registry.WBlocks;
import purplecreate.theatricalwb.registry.WMenus;
import purplecreate.theatricalwb.registry.WRecipeTypes;

import java.util.*;

import static purplecreate.theatricalwb.util.RecipeHelper.getResult;

public class WorkbenchMenu extends AbstractContainerMenu {
  private ContainerLevelAccess access;
  private final Slot resultSlot;
  private final Container inputContainer;
  private final ResultContainer resultContainer;
  private final DataSlot selectedRecipe;
  private final List<RecipeCollection> recipes = new ArrayList<>();

  public WorkbenchMenu(MenuType<WorkbenchMenu> type, int id, Inventory playerInventory) {
    super(type, id);

    selectedRecipe = DataSlot.standalone();
    resultContainer = new ResultContainer();
    inputContainer = new SimpleContainer(6);

    resultSlot = addSlot(new Slot(resultContainer, 0, 148, 34) {
      @Override
      public boolean mayPlace(ItemStack stack) {
        return false;
      }

      @Override
      public void onTake(Player player, ItemStack stack) {
        stack.onCraftedBy(player.level(), player, stack.getCount());
        WorkbenchMenu.this.craft(player.level());
        super.onTake(player, stack);
      }
    });

    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 2; x++) {
        int i = x + y * 2;

        addSlot(new Slot(inputContainer, i, 103 + x * 18, 16 + y * 18) {
          @Override
          public boolean mayPlace(ItemStack stack) {
            RecipeCollection collection = getCurrentRecipe();
            if (collection == null) return false;
            return collection.isPermittedForSlot(i, stack);
          }
        });
      }
    }

    for (int y = 0; y < 3; y++) {
      for (int x = 0; x < 9; x++) {
        addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
      }
    }

    for (int i = 0; i < 9; i++) {
      addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
    }

    setupRecipeList(playerInventory.player.level());
  }

  public static WorkbenchMenu create(int id, Inventory playerInventory, ContainerLevelAccess access) {
    WorkbenchMenu menu = new WorkbenchMenu(WMenus.WORKBENCH.get(), id, playerInventory);
    menu.access = access;
    return menu;
  }

  private RecipeCollection getCurrentRecipe() {
    int i = selectedRecipe.get();
    return i < 0 || i >= recipes.size()
      ? null
      : recipes.get(i);
  }

  public int getSelectedRecipe() {
    return selectedRecipe.get();
  }

  public List<RecipeCollection> getRecipes() {
    return recipes;
  }

  public ItemStack getItemInInputSlot(int slot) {
    return inputContainer.getItem(slot);
  }

  private void setupRecipeList(Level level) {
    Map<Item, RecipeCollection> map = new HashMap<>();

    recipes.clear();
    selectedRecipe.set(-1);

    for (WorkbenchRecipe recipe : level.getRecipeManager().getAllRecipesFor(WRecipeTypes.WORKBENCH)) {
      // this fixes a stupid issue :)
      for (Ingredient v : recipe.getIngredients()) {
        if (v instanceof IIngredient ingredient) {
          ingredient.wb$resetCache();
        }
      }

      Item item = getResult(recipe).getItem();
      map.compute(item, (k, v) -> {
        RecipeCollection collection = v == null ? new RecipeCollection(item) : v;
        collection.add(recipe);
        return collection;
      });
    }

    recipes.addAll(map.values());
    recipes.sort(Comparator.comparing(RecipeCollection::getId));
  }

  private void setupResultSlot(Level level) {
    if (level.isClientSide) return;

    RecipeCollection collection = getCurrentRecipe();
    WorkbenchRecipe recipe = collection != null
      ? collection.filter(inputContainer, level).orElse(null)
      : null;

    if (recipe == null) {
      resultSlot.set(ItemStack.EMPTY);
      return;
    }

    boolean canCraft = true;
    ItemStack item = recipe.assemble(inputContainer, level.registryAccess());

    for (int i = 0; i < recipe.getIngredients().size(); i++) {
      Ingredient ingredient = recipe.getIngredients().get(i);
      ItemStack slotStack = inputContainer.getItem(i);

      for (ItemStack stack : ingredient.getItems()) {
        if (!stack.is(slotStack.getItem()) || slotStack.getCount() < stack.getCount()) {
          canCraft = false;
          break;
        }
      }

      if (!canCraft) break;
    }

    if (canCraft && item.isItemEnabled(level.enabledFeatures())) {
      resultContainer.setRecipeUsed(recipe);
      resultSlot.set(item);
    } else {
      resultSlot.set(ItemStack.EMPTY);
    }
  }

  private void craft(Level level) {
    RecipeCollection collection = getCurrentRecipe();
    WorkbenchRecipe recipe = collection != null
      ? collection.filter(inputContainer, level).orElse(null)
      : null;

    if (recipe != null) {
      for (int i = 0; i < recipe.getIngredients().size(); i++) {
        Ingredient ingredient = recipe.getIngredients().get(i);
        ItemStack slotStack = inputContainer.getItem(i);

        for (ItemStack stack : ingredient.getItems()) {
          if (stack.is(slotStack.getItem())) {
            inputContainer.removeItem(i, stack.getCount());
            break;
          }
        }
      }

      broadcastChanges();
    }
  }

  private void handleRecipeChange(Player player) {
    clearContainer(player, inputContainer);
    setupResultSlot(player.level());
    super.broadcastChanges();
  }

  @Override
  public void broadcastChanges() {
    if (access != null) { // on the client access might be null
      access.execute((level, blockPos) -> {
        setupResultSlot(level);
      });
    }
    super.broadcastChanges();
  }

  @Override
  public boolean clickMenuButton(Player player, int id) {
    if (id != selectedRecipe.get() && id >= 0 && id < recipes.size()) {
      selectedRecipe.set(id);
      handleRecipeChange(player);
      return true;
    }

    return false;
  }

  @Override
  public ItemStack quickMoveStack(Player player, int index) {
    RecipeCollection collection = getCurrentRecipe();
    Slot slot = slots.get(index);

    if (!slot.hasItem()) {
      return ItemStack.EMPTY;
    }

    ItemStack slotStack = slot.getItem();
    ItemStack stack = slotStack.copy();

    if (index == 0 /* result slot */) {
      slotStack.getItem().onCraftedBy(slotStack, player.level(), player);
      if (!moveItemStackTo(slotStack, 6, slots.size(), true)) {
        return ItemStack.EMPTY;
      }
      slot.onQuickCraft(slotStack, stack);
    } else if (index <= 6 /* input slots */) {
      if (!moveItemStackTo(slotStack, 6, slots.size(), true)) {
        return ItemStack.EMPTY;
      }
    }

    if (collection != null) {
      for (int i = 0; i < 6; i++) {
        if (
          collection.isPermittedForSlot(i, slotStack)
            && !moveItemStackTo(slotStack, i + 1, i + 2, false)
        ) {
          return ItemStack.EMPTY;
        }
      }
    }

    if (slotStack.isEmpty()) {
      slot.setByPlayer(ItemStack.EMPTY);
    }

    slot.setChanged();

    if(slotStack.getCount() == stack.getCount())
    {
      return ItemStack.EMPTY;
    }

    slot.onTake(player, slotStack);
    broadcastChanges();

    return stack;
  }

  @Override
  public boolean stillValid(Player player) {
    return stillValid(access, player, WBlocks.WORKBENCH.get());
  }

  @Override
  public void removed(Player player) {
    super.removed(player);
    clearContainer(player, inputContainer);
  }

  public static class RecipeCollection extends ArrayList<WorkbenchRecipe> {
    public final Item item;

    public RecipeCollection(Item item) {
      super();
      this.item = item;
    }

    public RecipeCollection(Item item, List<WorkbenchRecipe> recipes) {
      super(recipes);
      this.item = item;
    }

    public ResourceLocation getId() {
      return BuiltInRegistries.ITEM.getKey(item);
    }

    public ItemStack getGhostItemForSlot(int slot, float time) {
      WorkbenchRecipe recipe = this.get(Mth.floor(time / 30.0F) % this.size());

      if (slot >= recipe.getIngredients().size()) return ItemStack.EMPTY;

      ItemStack[] items = recipe.getIngredients().get(slot).getItems();
      return items.length == 0
        ? ItemStack.EMPTY
        : items[Mth.floor(time / 30.0F) % items.length];
    }

    public boolean isPermittedForSlot(int slot, ItemStack slotStack) {
      for (WorkbenchRecipe recipe : this) {
        if (slot >= recipe.getIngredients().size()) continue;

        for (ItemStack stack : recipe.getIngredients().get(slot).getItems()) {
          if (stack.is(slotStack.getItem())) return true;
        }
      }

      return false;
    }

    public <C extends Container> Optional<WorkbenchRecipe> filter(C container, Level level) {
      return this.stream().filter((recipe) -> recipe.matches(container, level)).findFirst();
    }
  }
}

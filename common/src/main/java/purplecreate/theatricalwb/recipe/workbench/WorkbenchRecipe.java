package purplecreate.theatricalwb.recipe.workbench;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import purplecreate.theatricalwb.registry.WRecipeSerializers;
import purplecreate.theatricalwb.registry.WRecipeTypes;

public class WorkbenchRecipe implements Recipe<Container> {
  final ResourceLocation id;
  final NonNullList<Ingredient> materials;
  final ItemStack result;

  public WorkbenchRecipe(ResourceLocation id, NonNullList<Ingredient> materials, ItemStack result) {
    this.id = id;
    this.materials = materials;
    this.result = result;
  }

  @Override
  public ResourceLocation getId() {
    return id;
  }

  @Override
  public RecipeType<?> getType() {
    return WRecipeTypes.WORKBENCH;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return WRecipeSerializers.WORKBENCH;
  }

  @Override
  public boolean matches(Container container, Level level) {
    for (int i = 0; i < container.getContainerSize(); i++) {
      ItemStack slotStack = container.getItem(i);

      if (i >= materials.size()) {
        if (slotStack.isEmpty()) continue;
        return false;
      }

      Ingredient ingredient = materials.get(i);
      boolean valid = false;

      for (ItemStack stack : ingredient.getItems()) {
        if (stack.is(slotStack.getItem()) && slotStack.getCount() >= stack.getCount()) {
          valid = true;
        }
      }

      if (!valid) return false;
    }

    return true;
  }

  @Override
  public ItemStack assemble(Container container, RegistryAccess registryAccess) {
    return result.copy();
  }

  @Override
  public boolean canCraftInDimensions(int width, int height) {
    return true;
  }

  @Override
  public NonNullList<Ingredient> getIngredients() {
    return materials;
  }

  @Override
  public ItemStack getResultItem(RegistryAccess registryAccess) {
    return result;
  }
}

package purplecreate.theatricalwb.recipe.workbench;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class WorkbenchRecipeSerializer implements RecipeSerializer<WorkbenchRecipe> {
  @Override
  public WorkbenchRecipe fromJson(ResourceLocation id, JsonObject json) {
    JsonArray jsonMaterials = json.getAsJsonArray("materials");

    NonNullList<Ingredient> materials = NonNullList.withSize(jsonMaterials.size(), Ingredient.EMPTY);
    ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

    if (jsonMaterials.size() > 6) {
      throw new JsonParseException("Too many ingredients. Max is 6.");
    }

    for (int i = 0; i < jsonMaterials.size(); ++i) {
      Ingredient ingredient = Ingredient.fromJson(jsonMaterials.get(i), false);
      materials.set(i, ingredient);

      if (ingredient.getItems().length == 0) {
        throw new JsonParseException("Encountered empty ingredient. Probably a missing tag.");
      }
    }

    return new WorkbenchRecipe(id, materials, result);
  }

  @Override
  public void toNetwork(FriendlyByteBuf buffer, WorkbenchRecipe recipe) {
    buffer.writeVarInt(recipe.materials.size());
    for (Ingredient ingredient : recipe.materials) {
      ingredient.toNetwork(buffer);
    }
    buffer.writeItem(recipe.result);
  }

  @Override
  public WorkbenchRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
    int materialsSize = buffer.readVarInt();
    NonNullList<Ingredient> materials = NonNullList.withSize(materialsSize, Ingredient.EMPTY);
    for (int i = 0; i < materialsSize; i++) {
      materials.set(i, Ingredient.fromNetwork(buffer));
    }
    ItemStack result = buffer.readItem();

    return new WorkbenchRecipe(id, materials, result);
  }
}

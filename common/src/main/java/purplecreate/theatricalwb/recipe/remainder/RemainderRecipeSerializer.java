package purplecreate.theatricalwb.recipe.remainder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import static purplecreate.theatricalwb.util.RecipeHelper.getResult;

public class RemainderRecipeSerializer implements RecipeSerializer<RemainderRecipe> {
  @Override
  public RemainderRecipe fromJson(ResourceLocation id, JsonObject json) {
    ShapelessRecipe shapelessRecipe = SHAPELESS_RECIPE.fromJson(id, json);

    NonNullList<ItemStack> remainders = NonNullList.create();
    for (JsonElement object : json.getAsJsonArray("remainders")) {
      remainders.add(ShapedRecipe.itemStackFromJson(object.getAsJsonObject()));
    }

    return new RemainderRecipe(
      shapelessRecipe.getId(),
      shapelessRecipe.getGroup(),
      shapelessRecipe.category(),
      getResult(shapelessRecipe),
      remainders,
      shapelessRecipe.getIngredients()
    );
  }

  @Override
  public void toNetwork(FriendlyByteBuf buffer, RemainderRecipe recipe) {
    SHAPELESS_RECIPE.toNetwork(buffer, recipe);

    buffer.writeVarInt(recipe.remainders.size());
    for (ItemStack item : recipe.remainders) {
      buffer.writeItem(item);
    }
  }

  @Override
  public RemainderRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
    ShapelessRecipe shapelessRecipe = SHAPELESS_RECIPE.fromNetwork(id, buffer);

    int size = buffer.readVarInt();
    NonNullList<ItemStack> remainders = NonNullList.withSize(size, ItemStack.EMPTY);
    for (int i = 0; i < size; i++) {
      remainders.set(i, buffer.readItem());
    }

    return new RemainderRecipe(
      shapelessRecipe.getId(),
      shapelessRecipe.getGroup(),
      shapelessRecipe.category(),
      getResult(shapelessRecipe),
      remainders,
      shapelessRecipe.getIngredients()
    );
  }
}

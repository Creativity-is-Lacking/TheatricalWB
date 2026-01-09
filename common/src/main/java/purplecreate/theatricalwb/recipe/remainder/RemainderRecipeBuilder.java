package purplecreate.theatricalwb.recipe.remainder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import purplecreate.theatricalwb.registry.WRecipeSerializers;

import java.util.List;
import java.util.function.Consumer;

public class RemainderRecipeBuilder extends ShapelessRecipeBuilder {
  private final List<ItemStack> remainders = Lists.newArrayList();

  public RemainderRecipeBuilder(RecipeCategory category, ItemLike result, int count) {
    super(category, result, count);
  }

  public static RemainderRecipeBuilder shapeless(RecipeCategory category, ItemLike result) {
    return new RemainderRecipeBuilder(category, result, 1);
  }

  public static RemainderRecipeBuilder shapeless(RecipeCategory category, ItemLike result, int count) {
    return new RemainderRecipeBuilder(category, result, count);
  }

  public RemainderRecipeBuilder remainder(ItemLike item) {
    return remainder(item, 1);
  }

  public RemainderRecipeBuilder remainder(ItemLike item, int count) {
    remainders.add(new ItemStack(item, count));
    return this;
  }

  @Override
  public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
    super.save(finishedRecipe -> finishedRecipeConsumer.accept(new Result(finishedRecipe, remainders)), recipeId);
  }

  public static class Result implements FinishedRecipe {
    final FinishedRecipe shapeless;
    final List<ItemStack> remainders;

    public Result(FinishedRecipe shapeless, List<ItemStack> remainders) {
      this.shapeless = shapeless;
      this.remainders = remainders;
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
      shapeless.serializeRecipeData(json);

      JsonArray jsonRemainders = new JsonArray();
      for (ItemStack item : remainders) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("item", BuiltInRegistries.ITEM.getKey(item.getItem()).toString());
        if (item.getCount() > 1) {
          jsonObject.addProperty("count", item.getCount());
        }
        jsonRemainders.add(jsonObject);
      }
      json.add("remainders", jsonRemainders);
    }

    @Override
    public ResourceLocation getId() {
      return shapeless.getId();
    }

    @Override
    public RecipeSerializer<?> getType() {
      return WRecipeSerializers.REMAINDER;
    }

    @Override
    public @Nullable JsonObject serializeAdvancement() {
      return shapeless.serializeAdvancement();
    }

    @Override
    public @Nullable ResourceLocation getAdvancementId() {
      return shapeless.getAdvancementId();
    }
  }
}

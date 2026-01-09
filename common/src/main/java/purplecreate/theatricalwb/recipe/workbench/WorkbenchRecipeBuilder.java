package purplecreate.theatricalwb.recipe.workbench;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import purplecreate.theatricalwb.registry.WRecipeSerializers;
import purplecreate.theatricalwb.util.IngredientWithCount;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class WorkbenchRecipeBuilder<T extends WorkbenchRecipeBuilder<T>> implements RecipeBuilder {
  protected final Item result;
  protected final int count;
  protected final List<Ingredient> materials = new ArrayList<>();

  public WorkbenchRecipeBuilder(ItemLike result, int count) {
    this.result = result.asItem();
    this.count = count;
  }

  public static <T extends WorkbenchRecipeBuilder<T>> WorkbenchRecipeBuilder<T> workbench(ItemLike result) {
    return new WorkbenchRecipeBuilder<>(result, 1);
  }

  public static <T extends WorkbenchRecipeBuilder<T>> WorkbenchRecipeBuilder<T> workbench(ItemLike result, int count) {
    return new WorkbenchRecipeBuilder<>(result, count);
  }

  public T requires(TagKey<Item> tag) {
    return requires(Ingredient.of(tag));
  }

  public T requires(TagKey<Item> tag, int quantity) {
    return requires(
      new Ingredient(
        Stream.of(new IngredientWithCount.TagValue(tag, quantity))
      )
    );
  }

  public T requires(ItemLike item) {
    return requires(item, 1);
  }

  public T requires(ItemLike item, int quantity) {
    return requires(
      new Ingredient(
        Stream.of(new IngredientWithCount.ItemValue(item, quantity))
      )
    );
  }

  public T requires(Ingredient ingredient) {
    materials.add(ingredient);
    return (T)this;
  }

  @Override
  public T unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
    return (T)this;
  }

  @Override
  public T group(@Nullable String groupName) {
    return (T)this;
  }

  @Override
  public Item getResult() {
    return result;
  }

  @Override
  public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
    finishedRecipeConsumer.accept(new Result(recipeId, materials, result, count));
  }

  public static class Result implements FinishedRecipe {
    final ResourceLocation id;
    final List<Ingredient> materials;
    final Item result;
    final int count;

    public Result(ResourceLocation id, List<Ingredient> materials, Item result, int count) {
      this.id = id;
      this.materials = materials;
      this.result = result;
      this.count = count;
    }

    @Override
    public void serializeRecipeData(JsonObject json) {
      JsonArray materialsJson = new JsonArray();
      for (Ingredient ingredient : materials) {
        materialsJson.add(ingredient.toJson());
      }
      json.add("materials", materialsJson);

      JsonObject resultJson = new JsonObject();
      resultJson.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
      resultJson.addProperty("count", count);
      json.add("result", resultJson);
    }

    @Override
    public ResourceLocation getId() {
      return id;
    }

    @Override
    public RecipeSerializer<?> getType() {
      return WRecipeSerializers.WORKBENCH;
    }

    @Override
    public @Nullable JsonObject serializeAdvancement() {
      return null;
    }

    @Override
    public @Nullable ResourceLocation getAdvancementId() {
      return null;
    }
  }
}

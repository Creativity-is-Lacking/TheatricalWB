package purplecreate.theatricalwb.util;

import com.google.gson.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import purplecreate.theatricalwb.Workbench;

import java.util.*;

public class IngredientWithCount {
  public static final String countKey = Workbench.ID + ":count";

  public static Ingredient.Value valueFromJson(JsonObject json) {
    int count = json.has(countKey) ? json.get(countKey).getAsInt() : 1;

    if (json.has("item") && json.has("tag")) {
      throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
    } else if (json.has("item")) {
      Item item = ShapedRecipe.itemFromJson(json);
      return new ItemValue(item, count);
    } else if (json.has("tag")) {
      ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(json, "tag"));
      TagKey<Item> tagKey = TagKey.create(Registries.ITEM, resourceLocation);
      return new TagValue(tagKey, count);
    } else {
      throw new JsonParseException("An ingredient entry needs either a tag or an item");
    }
  }

  public static class TagValue implements Ingredient.Value {
    private final TagKey<Item> tag;
    private final int count;

    public TagValue(TagKey<Item> tag) {
      this(tag, 1);
    }

    public TagValue(TagKey<Item> tag, int count) {
      this.tag = tag;
      this.count = count;
    }

    @Override
    public Collection<ItemStack> getItems() {
      List<ItemStack> list = new ArrayList<>();

      for (Holder<Item> itemHolder : BuiltInRegistries.ITEM.getTagOrEmpty(tag)) {
        list.add(new ItemStack(itemHolder, count));
      }

      if (Platform.get() == Platform.FORGE && list.isEmpty()) {
        list.add(
          new ItemStack(Blocks.BARRIER)
            .setHoverName(Component.literal("Empty Tag: " + tag.location()))
        );
      }

      return list;
    }

    @Override
    public JsonObject serialize() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("tag", tag.location().toString());
      if (count > 1) {
        jsonObject.addProperty(countKey, count);
      }
      return jsonObject;
    }
  }

  public static class ItemValue implements Ingredient.Value {
    private final Item item;
    private final int count;

    public ItemValue(ItemLike item) {
      this(item, 1);
    }

    public ItemValue(ItemLike item, int count) {
      this.item = item.asItem();
      this.count = count;
    }

    public Collection<ItemStack> getItems() {
      return Collections.singleton(new ItemStack(this.item, count));
    }

    public JsonObject serialize() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("item", BuiltInRegistries.ITEM.getKey(item).toString());
      if (count > 1) {
        jsonObject.addProperty(countKey, count);
      }
      return jsonObject;
    }
  }
}

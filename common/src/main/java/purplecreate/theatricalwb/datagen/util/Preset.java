package purplecreate.theatricalwb.datagen.util;

import com.mojang.datafixers.util.Pair;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.imabad.theatrical.blockentities.light.BaseDMXConsumerLightBlockEntity;
import dev.imabad.theatrical.blocks.light.BaseLightBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.ItemLike;
import purplecreate.theatricalwb.recipe.remainder.RemainderRecipeBuilder;
import purplecreate.theatricalwb.recipe.workbench.WorkbenchRecipeBuilder;
import purplecreate.theatricalwb.registry.WBlocks;
import purplecreate.theatricalwb.registry.WItems;
import purplecreate.theatricalwb.datagen.util.OtherRegistries.MinecraftItems;
import purplecreate.theatricalwb.datagen.util.OtherRegistries.TheatricalItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static purplecreate.theatricalwb.datagen.util.Helpers.accept;
import static purplecreate.theatricalwb.datagen.util.Helpers.unlockedByItem;

public class Preset extends WorkbenchRecipeBuilder<Preset> {
  private final List<Pair<String, RecipeBuilder>> extraRecipes = new ArrayList<>();
  private int bulbCount;

  public Preset(ItemLike result, int count) {
    super(result, count);
  }

  public Preset(ItemLike result, int count, int bulbCount) {
    this(result, count);
    this.bulbCount = bulbCount;
  }

  public static void rotate(ItemLike vertical, ItemLike horizontal, String id, RegistrateRecipeProvider provider) {
    accept(
      ShapelessRecipeBuilder
        .shapeless(RecipeCategory.MISC, horizontal)
        .unlockedBy("has_item", unlockedByItem(WBlocks.WORKBENCH))
        .requires(vertical),
      provider,
      "h_" + id
    );

    accept(
      ShapelessRecipeBuilder
        .shapeless(RecipeCategory.MISC, vertical)
        .unlockedBy("has_item", unlockedByItem(WBlocks.WORKBENCH))
        .requires(horizontal),
      provider,
      "v_" + id
    );
  }

  private static Preset abstractLight(ItemLike itemLike, int bulbCount) {
    if (!(itemLike.asItem() instanceof BlockItem item)) {
      throw new AssertionError("Item is not an instance of BlockItem");
    }

    if (!(item.getBlock() instanceof BaseLightBlock block)) {
      throw new AssertionError("Block is not an instance of BaseLightBlock");
    }

    if (!(block.newBlockEntity(BlockPos.ZERO, block.defaultBlockState()) instanceof BaseDMXConsumerLightBlockEntity be)) {
      throw new AssertionError("BlockEntity is not an instance of BaseDMXConsumerLightBlockEntity");
    }

    return new Preset(item, 1, bulbCount)
      .requires(MinecraftItems.IRON_INGOT, 4 + Math.round(bulbCount / 2f))
      .requires(MinecraftItems.REDSTONE, Math.round(be.getChannelCount() / 2f));
  }

  public static Preset incLight(ItemLike item) {
    return incLight(item, 1);
  }
  public static Preset incLight(ItemLike item, int bulbCount) {
    return abstractLight(item, bulbCount).requires(WItems.INCANDESCENT_BULB, bulbCount);
  }

  public static Preset ledLight(ItemLike item) {
    return ledLight(item, 1);
  }
  public static Preset ledLight(ItemLike item, int bulbCount) {
    return abstractLight(item, bulbCount).requires(WItems.LED_BULB, bulbCount);
  }

  public static Preset ledBar(ItemLike item) {
    return ledBar(item, 1);
  }
  public static Preset ledBar(ItemLike item, int bulbCount) {
    return new Preset(item, 1, bulbCount)
      .requires(TheatricalItems.PIPE.get())
      .requires(MinecraftItems.REDSTONE)
      .requires(WItems.LED_BULB, bulbCount);
  }

  public static Preset movingLedLight(ItemLike item) {
    return movingLedLight(item, 1);
  }
  public static Preset movingLedLight(ItemLike item, int bulbCount) {
    return ledLight(item, bulbCount).requires(WItems.MOTOR);
  }

  public static Preset movingLedBar(ItemLike item) {
    return movingLedBar(item, 1);
  }
  public static Preset movingLedBar(ItemLike item, int bulbCount) {
    return ledBar(item, bulbCount).requires(WItems.MOTOR);
  }

  public static Preset waterFeature(ItemLike item, int tankCount, int motorCount) {
    return new Preset(item, 1)
      .requires(MinecraftItems.IRON_INGOT, 4)
      .requires(MinecraftItems.BARREL, tankCount)
      .requires(TheatricalItems.PIPE.get())
      .requires(WItems.MOTOR, motorCount);
  }

  public Preset gel(Map<GelColor, ItemLike> gelMap) {
    for (Map.Entry<GelColor, ItemLike> entry : gelMap.entrySet()) {
      extraRecipes.add(Pair.of(
        result.asItem() + "_and_" + entry.getKey().name + "_gel",
        RemainderRecipeBuilder
          .shapeless(RecipeCategory.MISC, result)
          .remainder(entry.getKey().glassPane, bulbCount)
          .unlockedBy("has_item", unlockedByItem(WBlocks.WORKBENCH))
          .requires(entry.getValue())
      ));

      extraRecipes.add(Pair.of(
        null,
        ShapelessRecipeBuilder
          .shapeless(RecipeCategory.MISC, entry.getValue())
          .unlockedBy("has_item", unlockedByItem(WBlocks.WORKBENCH))
          .requires(result)
          .requires(entry.getKey().glassPane, bulbCount)
      ));
    }

    return this;
  }

  private void saveExtra(RegistrateRecipeProvider provider) {
    for (Pair<String, RecipeBuilder> entry : extraRecipes) {
      String id = entry.getFirst();
      RecipeBuilder builder = entry.getSecond();

      if (id == null) accept(builder, provider);
      else accept(builder, provider, id);
    }
  }

  public void save(RegistrateRecipeProvider provider) {
    accept(this, provider);
    saveExtra(provider);
  }
  public void save(RegistrateRecipeProvider provider, String id) {
    accept(this, provider, id);
    saveExtra(provider);
  }
}

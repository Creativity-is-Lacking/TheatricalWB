package purplecreate.theatricalwb.registry;

import com.tterrag.registrate.providers.ProviderType;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import purplecreate.theatricalwb.Workbench;

import java.util.Arrays;

import static purplecreate.theatricalwb.Workbench.REGISTRATE;

public class WTags {
  public static final TagKey<Item> IRON_RODS = itemMerged("iron_rods", "c:rods/iron", "c:iron_rods", "forge:rods/iron");
  public static final TagKey<Block> MINEABLE_AXE = block("mineable/axe");

  private static TagKey<Item> item(String rl) {
    return item(new ResourceLocation(rl));
  }
  private static TagKey<Item> item(ResourceLocation rl) {
    return TagKey.create(Registries.ITEM, rl);
  }

  private static TagKey<Block> block(String rl) {
    return block(new ResourceLocation(rl));
  }
  private static TagKey<Block> block(ResourceLocation rl) {
    return TagKey.create(Registries.BLOCK, rl);
  }

  private static TagKey<Item> itemMerged(String name, String... tags) {
    return itemMerged(
      name,
      Arrays
        .stream(tags)
        .map(ResourceLocation::new)
        .toArray(ResourceLocation[]::new)
    );
  }
  private static TagKey<Item> itemMerged(String name, ResourceLocation... tags) {
    TagKey<Item> mergedTag = item(Workbench.rl("mergedtag/" + name));

    REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, (provider) -> {
      FabricTagProvider<Item>.FabricTagBuilder builder = provider.addTag(mergedTag);
      for (ResourceLocation tag : tags) {
        builder.addOptionalTag(tag);
      }
    });

    return mergedTag;
  }

  public static void register() {}
}

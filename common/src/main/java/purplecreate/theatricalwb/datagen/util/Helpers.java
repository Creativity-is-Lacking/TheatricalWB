package purplecreate.theatricalwb.datagen.util;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.world.level.ItemLike;
import purplecreate.theatricalwb.Workbench;

import static com.tterrag.registrate.providers.RegistrateRecipeProvider.inventoryTrigger;

public class Helpers {
  public static InventoryChangeTrigger.TriggerInstance unlockedByItem(ItemLike item) {
    return inventoryTrigger(
      ItemPredicate.Builder
        .item()
        .of(item)
        .build()
    );
  }

  public static void accept(RecipeBuilder builder, RegistrateRecipeProvider provider) {
    String itemPath = BuiltInRegistries.ITEM.getKey(builder.getResult()).getPath();
    accept(builder, provider, itemPath);
  }

  public static void accept(RecipeBuilder builder, RegistrateRecipeProvider provider, String id) {
    String itemNamespace = BuiltInRegistries.ITEM.getKey(builder.getResult()).getNamespace();
    builder.save(provider, (Workbench.ID.equals(itemNamespace) ? "" : itemNamespace + "/") + id);
  }
}

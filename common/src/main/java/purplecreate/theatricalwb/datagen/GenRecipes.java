package purplecreate.theatricalwb.datagen;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import purplecreate.theatricalwb.datagen.util.Preset;
import purplecreate.theatricalwb.recipe.workbench.WorkbenchRecipeBuilder;
import purplecreate.theatricalwb.registry.WBlocks;
import purplecreate.theatricalwb.registry.WItems;
import purplecreate.theatricalwb.util.IngredientWithCount;
import purplecreate.theatricalwb.datagen.util.OtherRegistries.MinecraftItems;
import purplecreate.theatricalwb.datagen.util.OtherRegistries.TheatricalItems;
import purplecreate.theatricalwb.datagen.util.OtherRegistries.ExtraLightsItems;
import purplecreate.theatricalwb.registry.WTags;

import java.util.stream.Stream;

import static purplecreate.theatricalwb.datagen.util.Helpers.accept;
import static purplecreate.theatricalwb.datagen.util.Helpers.unlockedByItem;
import static purplecreate.theatricalwb.datagen.util.RecipeGroups.*;

public class GenRecipes {
  public static void generator(RegistrateRecipeProvider provider) {
    // workbench
    accept(
      ShapedRecipeBuilder
        .shaped(RecipeCategory.DECORATIONS, WBlocks.WORKBENCH)
        .unlockedBy("has_item", unlockedByItem(MinecraftItems.CRAFTING_TABLE))
        .define('I', MinecraftItems.IRON_NUGGET)
        .define('B', MinecraftItems.BLACK_DYE)
        .define('C', MinecraftItems.CRAFTING_TABLE)
        .pattern("IBI")
        .pattern("BCB")
        .pattern("IBI"),
      provider
    );

    // workbench ingredients
    accept(
      ShapedRecipeBuilder
        .shaped(RecipeCategory.MISC, WItems.LED_BULB)
        .unlockedBy("has_item", unlockedByItem(WBlocks.WORKBENCH))
        .define('U', MinecraftItems.GLASS_BOTTLE)
        .define('R', MinecraftItems.RED_DYE)
        .define('G', MinecraftItems.GREEN_DYE)
        .define('B', MinecraftItems.BLUE_DYE)
        .define('.', MinecraftItems.GLOWSTONE_DUST)
        .pattern(" U ")
        .pattern("RGB")
        .pattern(" . "),
      provider
    );

    accept(
      ShapedRecipeBuilder
        .shaped(RecipeCategory.MISC, WItems.INCANDESCENT_BULB)
        .unlockedBy("has_item", unlockedByItem(WBlocks.WORKBENCH))
        .define('U', MinecraftItems.GLASS_BOTTLE)
        .define('.', MinecraftItems.GLOWSTONE_DUST)
        .pattern("U")
        .pattern("."),
      provider
    );

    accept(
      ShapedRecipeBuilder
        .shaped(RecipeCategory.MISC, WItems.LASER_DIODE)
        .unlockedBy("has_item", unlockedByItem(WBlocks.WORKBENCH))
        .define('-', MinecraftItems.GLASS_PANE)
        .define('C', MinecraftItems.COPPER_INGOT)
        .define('G', MinecraftItems.GLOWSTONE)
        .pattern("-")
        .pattern("C")
        .pattern("G"),
      provider
    );

    accept(
      ShapedRecipeBuilder
        .shaped(RecipeCategory.MISC, WItems.MOTOR, 2)
        .unlockedBy("has_item", unlockedByItem(WBlocks.WORKBENCH))
        .define('C', MinecraftItems.COPPER_INGOT)
        .define('I', MinecraftItems.IRON_INGOT)
        .pattern("CIC"),
      provider
    );

    accept(
      ShapelessRecipeBuilder
        .shapeless(RecipeCategory.MISC, WItems.MIRROR)
        .unlockedBy("has_item", unlockedByItem(WBlocks.WORKBENCH))
        .requires(MinecraftItems.IRON_INGOT)
        .requires(MinecraftItems.GLASS_PANE),
      provider
    );

    // theatrical
    Preset.ledLight(TheatricalItems.LED_FRESNEL.get()).save(provider);
    Preset.ledLight(TheatricalItems.LED_PANEL.get()).save(provider);
    Preset.movingLedLight(TheatricalItems.MOVING_LIGHT.get()).save(provider);
    Preset.movingLedLight(TheatricalItems.MOVING_WASH.get()).save(provider);

    accept(
      WorkbenchRecipeBuilder
        .workbench(TheatricalItems.PIPE.get(), 2)
        .requires(MinecraftItems.IRON_INGOT),
      provider,
      "pipe_with_iron_ingot"
    );

    accept(
      WorkbenchRecipeBuilder
        .workbench(TheatricalItems.PIPE.get())
        .requires(WTags.IRON_RODS),
      provider,
      "pipe_with_iron_rod"
    );

    accept(
      WorkbenchRecipeBuilder
        .workbench(TheatricalItems.TRUSS.get())
        .requires(new Ingredient(Stream.of(
          new IngredientWithCount.ItemValue(TheatricalItems.PIPE.get(), 6),
          new IngredientWithCount.TagValue(WTags.IRON_RODS, 6)
        ))),
      provider
    );

    accept(
      WorkbenchRecipeBuilder
        .workbench(TheatricalItems.REDSTONE_INTERFACE.get())
        .requires(MinecraftItems.IRON_INGOT, 4)
        .requires(MinecraftItems.REDSTONE),
      provider
    );

    accept(
      WorkbenchRecipeBuilder
        .workbench(TheatricalItems.TANK_TRAP.get())
        .requires(MinecraftItems.IRON_INGOT, 2),
      provider
    );

    accept(
      WorkbenchRecipeBuilder
        .workbench(TheatricalItems.BASIC_LIGHTING_DESK.get())
        .requires(MinecraftItems.STONE_BUTTON, 13)
        .requires(MinecraftItems.IRON_INGOT, 4)
        .requires(MinecraftItems.REDSTONE)
        .requires(MinecraftItems.BLUE_DYE)
        .requires(MinecraftItems.GRAY_DYE),
      provider
    );

    accept(
      WorkbenchRecipeBuilder
        .workbench(TheatricalItems.CONFIGURATION_CARD.get())
        .requires(MinecraftItems.PAPER)
        .requires(MinecraftItems.IRON_NUGGET),
      provider
    );

    // extra lights
    Preset.movingLedLight(ExtraLightsItems.MOVING_VL2C.get()).save(provider);
    Preset.ledBar(ExtraLightsItems.RGB_BAR.get(), 9).save(provider);
    Preset.ledBar(ExtraLightsItems.VERTICAL_BAR.get(), 9).save(provider);
    Preset.ledBar(ExtraLightsItems.MOVING_BAR.get(), 8).save(provider);
    Preset.movingLedLight(ExtraLightsItems.MOVING_BEAM.get()).save(provider);
    Preset.ledLight(ExtraLightsItems.LED_FOUNTAIN.get()).save(provider);
    Preset.ledLight(ExtraLightsItems.LED_PANEL_2.get()).save(provider);
    Preset.movingLedLight(ExtraLightsItems.MOVING_VL6.get()).save(provider);
    Preset.ledLight(ExtraLightsItems.PAR_LED.get()).save(provider);
    Preset.ledLight(ExtraLightsItems.BLINDER.get(), 8).save(provider);
    Preset.ledLight(ExtraLightsItems.BLINDER2X2.get(), 4).save(provider);
    Preset.incLight(ExtraLightsItems.BLINDER2X2WARM.get(), 4).save(provider);
    Preset.incLight(ExtraLightsItems.BLINDER_WARM.get(), 8).save(provider);
    Preset.ledLight(ExtraLightsItems.STROBE.get()).save(provider);
    Preset.incLight(ExtraLightsItems.WHITE_STROBE.get()).save(provider);
    Preset.ledLight(ExtraLightsItems.TRUSS_3LIGHTS.get(), 9).requires(TheatricalItems.TRUSS.get(), 4).save(provider);
    Preset.movingLedLight(ExtraLightsItems.BEAM_7R.get()).save(provider);
    Preset.movingLedLight(ExtraLightsItems.MAC_VIP.get()).save(provider);
    Preset.movingLedLight(ExtraLightsItems.SHARPLUS.get()).save(provider);
    Preset.movingLedLight(ExtraLightsItems.MOVING500.get()).save(provider);
    Preset.movingLedLight(ExtraLightsItems.ROBITSPOT.get()).save(provider);
    Preset.movingLedLight(ExtraLightsItems.VERVESPOT.get()).save(provider);
    Preset.movingLedLight(ExtraLightsItems.SEARCHLIGHT.get()).save(provider);
    Preset.movingLedLight(ExtraLightsItems.WASHLIGHT.get()).save(provider);
    Preset.movingLedLight(ExtraLightsItems.MINIWASH.get()).save(provider);
    Preset.movingLedLight(ExtraLightsItems.ATOMICTILT.get()).save(provider);
    Preset.movingLedLight(ExtraLightsItems.VL6000.get()).save(provider);
    Preset.movingLedLight(ExtraLightsItems.WASHLED.get()).save(provider);
    Preset.ledLight(ExtraLightsItems.SOURCE_FOUR.get()).save(provider);
    Preset.incLight(ExtraLightsItems.SOURCE_FOUR_warm.get()).save(provider);
    Preset.ledLight(ExtraLightsItems.PARSCROLLER.get()).save(provider);
    Preset.incLight(ExtraLightsItems.PAR1000.get()).gel(PAR1000_WITH_GEL).save(provider);
    Preset.incLight(ExtraLightsItems.x8PAR_WARM.get(), 8).requires(TheatricalItems.PIPE.get()).gel(PAR64x8_WITH_GEL).save(provider);
    Preset.incLight(ExtraLightsItems.PAR56_WARM.get()).gel(PAR56_WITH_GEL).save(provider);
    Preset.incLight(ExtraLightsItems.A2X2PAR64_WARM.get(), 4).requires(TheatricalItems.TRUSS.get()).gel(PAR64x4_WITH_GEL).save(provider);
    Preset.movingLedLight(ExtraLightsItems.FOLLOWSPOT.get()).save(provider);
    Preset.ledLight(ExtraLightsItems.BIGSCROLLER.get(), 18).save(provider);
    Preset.ledLight(ExtraLightsItems.HORIZONTALSCROLLER.get(), 8).save(provider);
    Preset.ledLight(ExtraLightsItems.VERTICALSCROLLER.get(), 8).save(provider);
    Preset.waterFeature(ExtraLightsItems.MOVING_JET.get(), 1, 2).save(provider);
    Preset.waterFeature(ExtraLightsItems.WATER_JET_THIN.get(), 1, 1).save(provider);
    Preset.waterFeature(ExtraLightsItems.WATER_JET_SPREAD.get(), 1, 1).save(provider);
    Preset.waterFeature(ExtraLightsItems.WATER_JET_BIG.get(), 1, 1).save(provider);
    Preset.waterFeature(ExtraLightsItems.WATER_JET_CENTRAL.get(), 1, 1).save(provider);
    Preset.waterFeature(ExtraLightsItems.SPINNER.get(), 1, 1).save(provider);
    Preset.waterFeature(ExtraLightsItems.ORGANPIPES.get(), 2, 1).save(provider);
    Preset.waterFeature(ExtraLightsItems.ORGANPIPES_INV.get(), 2, 1).save(provider);

    Preset.rotate(
      ExtraLightsItems.VERTICAL_BAR.get(),
      ExtraLightsItems.RGB_BAR.get(),
      "rgb_bar_rotated",
      provider
    );
    Preset.rotate(
      ExtraLightsItems.VERTICALSCROLLER.get(),
      ExtraLightsItems.HORIZONTALSCROLLER.get(),
      "scroller_rotated",
      provider
    );

    accept(
      WorkbenchRecipeBuilder
        .workbench(ExtraLightsItems.MOVING_SCAN.get())
        .requires(MinecraftItems.IRON_INGOT, 4)
        .requires(WItems.LED_BULB)
        .requires(WItems.MOTOR)
        .requires(WItems.MIRROR),
      provider
    );

    accept(
      WorkbenchRecipeBuilder
        .workbench(ExtraLightsItems.LASER.get())
        .requires(MinecraftItems.IRON_INGOT, 4)
        .requires(MinecraftItems.REDSTONE)
        .requires(WItems.LASER_DIODE)
        .requires(WItems.MOTOR)
        .requires(WItems.MIRROR),
      provider
    );

    accept(
      WorkbenchRecipeBuilder
        .workbench(ExtraLightsItems.LASER_MIRROR.get())
        .requires(MinecraftItems.IRON_INGOT, 2)
        .requires(MinecraftItems.REDSTONE)
        .requires(WItems.LASER_DIODE)
        .requires(WItems.MIRROR),
      provider
    );
  }
}

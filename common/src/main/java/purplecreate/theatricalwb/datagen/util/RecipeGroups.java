package purplecreate.theatricalwb.datagen.util;

import net.minecraft.world.level.ItemLike;
import purplecreate.theatricalwb.datagen.util.OtherRegistries.ExtraLightsItems;

import java.util.Map;

public class RecipeGroups {
  public static final Map<GelColor, ItemLike> PAR1000_WITH_GEL = Map.of(
    GelColor.RED, ExtraLightsItems.PAR1000_RED.get(),
    GelColor.BLUE, ExtraLightsItems.PAR1000_BLUE.get(),
    GelColor.GREEN, ExtraLightsItems.PAR1000_GREEN.get(),
    GelColor.MAGENTA, ExtraLightsItems.PAR1000_MAGENTA.get(),
    GelColor.YELLOW, ExtraLightsItems.PAR1000_AMBER.get(),
    GelColor.ORANGE, ExtraLightsItems.PAR1000_ORANGE.get(),
    GelColor.PURPLE, ExtraLightsItems.PAR1000_PURPLE.get(),
    GelColor.LIGHT_BLUE, ExtraLightsItems.PAR1000_LIGHTBLUE.get(),
    GelColor.WHITE, ExtraLightsItems.PAR1000_WHITE.get()
  );

  public static final Map<GelColor, ItemLike> PAR64x8_WITH_GEL = Map.of(
    GelColor.RED, ExtraLightsItems.x8PAR_RED.get(),
    GelColor.BLUE, ExtraLightsItems.x8PAR_BLUE.get(),
    GelColor.GREEN, ExtraLightsItems.x8PAR_GREEN.get(),
    GelColor.MAGENTA, ExtraLightsItems.x8PAR_MAGENTA.get(),
    GelColor.YELLOW, ExtraLightsItems.x8PAR_YELLOW.get(),
    GelColor.ORANGE, ExtraLightsItems.x8PAR_ORANGE.get(),
    GelColor.PURPLE, ExtraLightsItems.x8PAR_PURPLE.get(),
    GelColor.LIGHT_BLUE, ExtraLightsItems.x8PAR_LIGHTBLUE.get(),
    GelColor.WHITE, ExtraLightsItems.x8PAR_WHITE.get()
  );

  public static final Map<GelColor, ItemLike> PAR56_WITH_GEL = Map.of(
    GelColor.RED, ExtraLightsItems.PAR56_RED.get(),
    GelColor.BLUE, ExtraLightsItems.PAR56_BLUE.get(),
    GelColor.GREEN, ExtraLightsItems.PAR56_GREEN.get(),
    GelColor.MAGENTA, ExtraLightsItems.PAR56_MAGENTA.get(),
    GelColor.YELLOW, ExtraLightsItems.PAR56_YELLOW.get(),
    GelColor.ORANGE, ExtraLightsItems.PAR56_ORANGE.get(),
    GelColor.PURPLE, ExtraLightsItems.PAR56_PURPLE.get(),
    GelColor.LIGHT_BLUE, ExtraLightsItems.PAR56_LIGHTBLUE.get(),
    GelColor.WHITE, ExtraLightsItems.PAR56_WHITE.get()
  );

  public static final Map<GelColor, ItemLike> PAR64x4_WITH_GEL = Map.of(
    GelColor.RED, ExtraLightsItems.A2X2PAR64_RED.get(),
    GelColor.BLUE, ExtraLightsItems.A2X2PAR64_BLUE.get(),
    GelColor.GREEN, ExtraLightsItems.A2X2PAR64_GREEN.get(),
    GelColor.MAGENTA, ExtraLightsItems.A2X2PAR64_MAGENTA.get(),
    GelColor.YELLOW, ExtraLightsItems.A2X2PAR64_YELLOW.get(),
    GelColor.ORANGE, ExtraLightsItems.A2X2PAR64_ORANGE.get(),
    GelColor.PURPLE, ExtraLightsItems.A2X2PAR64_PURPLE.get(),
    GelColor.LIGHT_BLUE, ExtraLightsItems.A2X2PAR64_LIGHTBLUE.get(),
    GelColor.WHITE, ExtraLightsItems.A2X2PAR64_WHITE.get()
  );
}

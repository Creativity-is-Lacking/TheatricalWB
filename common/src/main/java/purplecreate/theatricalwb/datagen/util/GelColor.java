package purplecreate.theatricalwb.datagen.util;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public enum GelColor {
  WHITE("white", DyeColor.WHITE, Items.WHITE_STAINED_GLASS_PANE),
  ORANGE("orange", DyeColor.ORANGE, Items.ORANGE_STAINED_GLASS_PANE),
  MAGENTA("magenta", DyeColor.MAGENTA, Items.MAGENTA_STAINED_GLASS_PANE),
  LIGHT_BLUE("light_blue", DyeColor.LIGHT_BLUE, Items.LIGHT_BLUE_STAINED_GLASS_PANE),
  YELLOW("yellow", DyeColor.YELLOW, Items.YELLOW_STAINED_GLASS_PANE),
  LIME("lime", DyeColor.LIME, Items.LIME_STAINED_GLASS_PANE),
  PINK("pink", DyeColor.PINK, Items.PINK_STAINED_GLASS_PANE),
  GRAY("gray", DyeColor.GRAY, Items.GRAY_STAINED_GLASS_PANE),
  LIGHT_GRAY("light_gray", DyeColor.LIGHT_GRAY, Items.LIGHT_GRAY_STAINED_GLASS_PANE),
  CYAN("cyan", DyeColor.CYAN, Items.CYAN_STAINED_GLASS_PANE),
  PURPLE("purple", DyeColor.PURPLE, Items.PURPLE_STAINED_GLASS_PANE),
  BLUE("blue", DyeColor.BLUE, Items.BLUE_STAINED_GLASS_PANE),
  BROWN("brown", DyeColor.BROWN, Items.BROWN_STAINED_GLASS_PANE),
  GREEN("green", DyeColor.GREEN, Items.GREEN_STAINED_GLASS_PANE),
  RED("red", DyeColor.RED, Items.RED_STAINED_GLASS_PANE),
  BLACK("black", DyeColor.BLACK, Items.BLACK_STAINED_GLASS_PANE);

  public final String name;
  public final DyeColor dyeColor;
  public final Item glassPane;

  GelColor(String name, DyeColor dyeColor, Item glassPane) {
    this.name = name;
    this.dyeColor = dyeColor;
    this.glassPane = glassPane;
  }
}

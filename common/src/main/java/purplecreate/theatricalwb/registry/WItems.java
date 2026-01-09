package purplecreate.theatricalwb.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import static purplecreate.theatricalwb.Workbench.REGISTRATE;

public class WItems {
  static {
    REGISTRATE.defaultCreativeTab(CreativeModeTabs.INGREDIENTS);
  }

  public static final ItemEntry<Item> LED_BULB =
    REGISTRATE.item("led_bulb", Item::new)
      .lang("LED Bulb")
      .register();

  public static final ItemEntry<Item> INCANDESCENT_BULB =
    REGISTRATE.item("incandescent_bulb", Item::new)
      .lang("Incandescent Bulb")
      .register();

  public static final ItemEntry<Item> LASER_DIODE =
    REGISTRATE.item("laser_diode", Item::new)
      .lang("Laser Diode")
      .register();

  public static final ItemEntry<Item> MOTOR =
    REGISTRATE.item("motor", Item::new)
      .lang("Motor")
      .register();

  public static final ItemEntry<Item> MIRROR =
    REGISTRATE.item("mirror", Item::new)
      .lang("Mirror")
      .register();

  public static void register() {}
}

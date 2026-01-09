package purplecreate.theatricalwb.util.forge;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.MenuConstructor;
import purplecreate.theatricalwb.util.Platform;

public class PlatformImpl {
  public static Platform get() {
    return Platform.FORGE;
  }

  public static MenuProvider menu(MenuConstructor constructor, Component title) {
    return new SimpleMenuProvider(constructor, title);
  }
}

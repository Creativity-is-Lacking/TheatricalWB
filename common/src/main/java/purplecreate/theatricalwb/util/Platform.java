package purplecreate.theatricalwb.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.MenuConstructor;

public enum Platform {
  FABRIC,
  FORGE;

  @ExpectPlatform
  public static Platform get() {
    throw new AssertionError();
  }

  @ExpectPlatform
  public static MenuProvider menu(MenuConstructor constructor, Component title) {
    throw new AssertionError();
  }
}

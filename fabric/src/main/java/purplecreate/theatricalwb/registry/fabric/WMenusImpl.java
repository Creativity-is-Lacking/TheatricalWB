package purplecreate.theatricalwb.registry.fabric;

import com.tterrag.registrate.builders.MenuBuilder;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import purplecreate.theatricalwb.registry.WMenus;

import static purplecreate.theatricalwb.Workbench.REGISTRATE;

public class WMenusImpl {
  public static <T extends AbstractContainerMenu, SC extends Screen & MenuAccess<T>> MenuEntry<T> create(String name, String langValue, MenuBuilder.MenuFactory<T> factory, NonNullSupplier<MenuBuilder.ScreenFactory<T, SC>> screenFactory) {
    return REGISTRATE.menu(name, factory, screenFactory).lang(WMenus::getLangKey, langValue).register();
  }
}

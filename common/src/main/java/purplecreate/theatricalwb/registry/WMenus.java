package purplecreate.theatricalwb.registry;

import com.tterrag.registrate.builders.MenuBuilder.ScreenFactory;
import com.tterrag.registrate.builders.MenuBuilder.MenuFactory;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import purplecreate.theatricalwb.block.workbench.WorkbenchMenu;
import purplecreate.theatricalwb.block.workbench.WorkbenchScreen;

public class WMenus {
  public static final MenuEntry<WorkbenchMenu> WORKBENCH = create(
    "workbench",
    "Theatrical Workbench",
    WorkbenchMenu::new,
    () -> WorkbenchScreen::new
  );

  @ExpectPlatform
  public static <T extends AbstractContainerMenu, SC extends Screen & MenuAccess<T>> MenuEntry<T> create(String name, String langValue, MenuFactory<T> factory, NonNullSupplier<ScreenFactory<T, SC>> screenFactory) {
    throw new AssertionError();
  }

  public static String getLangKey(MenuType<?> menu) {
    ResourceLocation rl = BuiltInRegistries.MENU.getKey(menu);
    return "menu." + rl.getNamespace() + "." + rl.getPath();
  }

  public static void register() {}
}

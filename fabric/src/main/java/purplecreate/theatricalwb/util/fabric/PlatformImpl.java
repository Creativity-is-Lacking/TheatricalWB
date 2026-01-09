package purplecreate.theatricalwb.util.fabric;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuConstructor;
import org.jetbrains.annotations.Nullable;
import purplecreate.theatricalwb.util.Platform;

public class PlatformImpl {
  public static Platform get() {
    return Platform.FABRIC;
  }

  public static MenuProvider menu(MenuConstructor constructor, Component title) {
    return new ExtendedScreenHandlerFactory() {
      @Override
      public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buffer) {
      }

      @Override
      public Component getDisplayName() {
        return title;
      }

      @Override
      public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return constructor.createMenu(i, inventory, player);
      }
    };
  }
}

package purplecreate.theatricalwb.fabric;

import com.tterrag.registrate.fabric.EnvExecutor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import purplecreate.theatricalwb.Workbench;

public class WorkbenchImpl implements ModInitializer {
  @Override
  public void onInitialize() {
    Workbench.init();
    Workbench.REGISTRATE.register();
    Workbench.commonSetup();

    EnvExecutor.runWhenOn(EnvType.CLIENT, () -> Workbench::clientSetup);
    ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarted);
  }

  public static MinecraftServer currentServer = null;

  private void onServerStarted(MinecraftServer minecraftServer) {
    currentServer = minecraftServer;
  }
}

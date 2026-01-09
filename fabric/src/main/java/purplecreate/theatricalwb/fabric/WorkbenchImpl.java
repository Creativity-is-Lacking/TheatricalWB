package purplecreate.theatricalwb.fabric;

import com.tterrag.registrate.fabric.EnvExecutor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import purplecreate.theatricalwb.Workbench;

public class WorkbenchImpl implements ModInitializer {
  @Override
  public void onInitialize() {
    Workbench.init();
    Workbench.REGISTRATE.register();
    Workbench.commonSetup();

    EnvExecutor.runWhenOn(EnvType.CLIENT, () -> Workbench::clientSetup);
  }
}

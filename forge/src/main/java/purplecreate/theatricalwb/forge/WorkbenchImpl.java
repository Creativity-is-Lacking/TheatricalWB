package purplecreate.theatricalwb.forge;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import purplecreate.theatricalwb.Workbench;

@Mod(Workbench.ID)
public class WorkbenchImpl {
  public WorkbenchImpl() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    Workbench.init();
    modEventBus.addListener(this::commonSetup);
    modEventBus.addListener(this::clientSetup);
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    event.enqueueWork(Workbench::commonSetup);
  }

  private void clientSetup(final FMLClientSetupEvent event) {
    event.enqueueWork(Workbench::clientSetup);
  }
}

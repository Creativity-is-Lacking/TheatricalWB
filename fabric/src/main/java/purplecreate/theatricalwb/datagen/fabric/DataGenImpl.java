package purplecreate.theatricalwb.datagen.fabric;

import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import purplecreate.theatricalwb.datagen.DataGen;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static purplecreate.theatricalwb.Workbench.REGISTRATE;

public class DataGenImpl implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator gen) {
    Path resources = Paths.get(System.getProperty(ExistingFileHelper.EXISTING_RESOURCES));
    ExistingFileHelper helper = new ExistingFileHelper(
      Set.of(resources), Set.of("create"), false, null, null
    );
    FabricDataGenerator.Pack pack = gen.createPack();
    REGISTRATE.setupDatagen(pack, helper);
    DataGen.generate();
  }
}

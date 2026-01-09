package purplecreate.theatricalwb.datagen;

import com.tterrag.registrate.providers.ProviderType;

import static purplecreate.theatricalwb.Workbench.REGISTRATE;

public class DataGen {
  public static void generate() {
    REGISTRATE.addDataGenerator(ProviderType.RECIPE, GenRecipes::generator);
  }
}

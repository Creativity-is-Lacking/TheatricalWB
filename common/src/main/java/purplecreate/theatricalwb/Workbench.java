package purplecreate.theatricalwb;

import com.tterrag.registrate.Registrate;
import net.minecraft.network.chat.Component;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import purplecreate.theatricalwb.registry.*;

public class Workbench {
  public static final String ID = "theatricalwb";
  public static final Registrate REGISTRATE = Registrate.create(ID);
  public static final Logger LOGGER = LogUtils.getLogger();

  public static void init() {
    WTags.register();
    WRecipeTypes.register();
    WRecipeSerializers.register();
    WMenus.register();
    WItems.register();
    WBlocks.register();
  }

  public static void commonSetup() {
  }

  public static void clientSetup() {
  }

  public static MutableComponent translatable(String path, Object... o) {
    return Component.translatable(ID + "." + path, o);
  }

  public static ResourceLocation rl(String path) {
    return new ResourceLocation(ID, path);
  }
}

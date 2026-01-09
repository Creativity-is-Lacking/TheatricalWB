package purplecreate.theatricalwb.registry.forge;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.level.block.Block;

public class WBlocksImpl {
  public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> horizontal(String path) {
    return (ctx, p) -> {
      throw new AssertionError();
    };
  }
}

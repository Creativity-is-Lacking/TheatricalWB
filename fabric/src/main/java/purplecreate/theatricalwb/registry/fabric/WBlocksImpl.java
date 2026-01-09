package purplecreate.theatricalwb.registry.fabric;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.level.block.Block;

public class WBlocksImpl {
  public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> horizontal(String path) {
    return (ctx, p) -> p.horizontalBlock(ctx.getEntry(), p.models().getExistingFile(p.modLoc(path)));
  }
}

package purplecreate.theatricalwb.registry;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.imabad.theatrical.Theatrical;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import purplecreate.theatricalwb.block.workbench.WorkbenchBlock;

import static purplecreate.theatricalwb.Workbench.REGISTRATE;

public class WBlocks {
  static {
    REGISTRATE.defaultCreativeTab(Theatrical.TAB.getKey());
  }

  public static final BlockEntry<WorkbenchBlock> WORKBENCH =
    REGISTRATE.block("workbench", WorkbenchBlock::new)
      .initialProperties(() -> Blocks.OAK_PLANKS)
      .blockstate(horizontal("block/workbench"))
      .tag(WTags.MINEABLE_AXE)
      .lang("Theatrical Workbench")
      .item()
      .build()
      .register();

  @ExpectPlatform
  private static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> horizontal(String path) {
    throw new AssertionError();
  }

  public static void register() {}
}

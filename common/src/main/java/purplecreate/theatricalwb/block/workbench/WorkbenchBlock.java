package purplecreate.theatricalwb.block.workbench;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import purplecreate.theatricalwb.util.Platform;

public class WorkbenchBlock extends Block {
  public static final Component TITLE_COMPONENT = Component.translatable("menu.theatricalwb.workbench");
  public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

  public WorkbenchBlock(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (level.isClientSide) {
      return InteractionResult.SUCCESS;
    } else {
      player.openMenu(getMenuProvider(state, level, pos));
      return InteractionResult.CONSUME;
    }
  }

  @Override
  public @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
    return Platform.menu(
      (i, inventory, player) -> WorkbenchMenu.create(i, inventory, ContainerLevelAccess.create(level, pos)),
      TITLE_COMPONENT
    );
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(FACING);
  }

  @Override
  public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
    BlockState state = super.getStateForPlacement(context);
    if (state == null) return null;
    return state.setValue(FACING, context.getHorizontalDirection());
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    switch (state.getValue(FACING)) {
      case NORTH, SOUTH -> {
        return box(1, 1.5, 4, 15, 12.5, 12);
      }
      default -> {
        return box(4, 1.5, 1, 12, 12.5, 15);
      }
    }
  }
}

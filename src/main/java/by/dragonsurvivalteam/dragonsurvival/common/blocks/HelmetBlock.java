package by.dragonsurvivalteam.dragonsurvival.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HelmetBlock extends Block{
	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
	protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);

	public HelmetBlock(Properties properties){
		super(properties.requiresCorrectToolForDrops().strength(3, 40));
		registerDefaultState(getStateDefinition().any().setValue(ROTATION, 0));
	}

	@Override
	public boolean isPathfindable(BlockState p_196266_1_, BlockGetter p_196266_2_, BlockPos p_196266_3_, PathComputationType p_196266_4_){
		return false;
	}

	public BlockState rotate(BlockState blockState, Rotation rotation){
		return blockState.setValue(ROTATION, rotation.rotate(blockState.getValue(ROTATION), 16));
	}

	public BlockState mirror(BlockState blockState, Mirror mirror){
		return blockState.setValue(ROTATION, mirror.mirror(blockState.getValue(ROTATION), 16));
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_){
		return SHAPE;
	}

	public BlockState getStateForPlacement(BlockPlaceContext useContext){

		return this.defaultBlockState().setValue(ROTATION, Mth.floor((double)(useContext.getRotation() * 16.0F / 360.0F) + 0.5D) & 15);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder){
		stateBuilder.add(ROTATION);
	}
}
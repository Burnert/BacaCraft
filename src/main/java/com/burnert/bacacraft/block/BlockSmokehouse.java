package com.burnert.bacacraft.block;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.core.block.BlockTileCore;
import com.burnert.bacacraft.core.registry.BacaCraftBlockRegistry;
import com.burnert.bacacraft.BacaCraftCreativeTabs;
import com.burnert.bacacraft.tile.TileEntitySmokehouse;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockSmokehouse extends BlockTileCore {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool BURNING = PropertyBool.create("burning");
	public static final AxisAlignedBB SMOKEHOUSE_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 2.0, 1.0);

	public BlockSmokehouse() {
		super("smokehouse", Material.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));
		this.setSound(SoundType.WOOD);
		this.setHardness(1.8f);
		this.setResistance(3.5f);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(BacaCraftCreativeTabs.MAIN);
	}

	public static boolean isBlockTopObstructed(World worldIn, BlockPos pos) {
		return !worldIn.getBlockState(pos.up()).getBlock().equals(Blocks.AIR);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(BacaCraftBlockRegistry.SMOKEHOUSE);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return Block.FULL_BLOCK_AABB;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntitySmokehouse tileEntitySmokehouse = (TileEntitySmokehouse) worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, tileEntitySmokehouse);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("incomplete-switch")
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.getValue(BURNING) && !isBlockTopObstructed(worldIn, pos)) {
			double x = (double)pos.getX() + 0.4D + rand.nextDouble() * 0.2D;
			double y = (double)(pos.getY() + 1);
			double z = (double)pos.getZ() + 0.4D + rand.nextDouble() * 0.2D;
			double ySpeed = 0.02D + rand.nextDouble() * 0.06D;
			if (rand.nextDouble() < 0.1D) {
				worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0.0D, ySpeed, 0.0D);
		}
	}

	/**
	 * Called when the block is right clicked by a player.
	 */
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		BacaCraft.LOGGER.info("Blockstate - facing:" + state.getValue(FACING) + "; burning:" + state.getValue(BURNING));
		if (worldIn.isRemote) {
			return true;
		}
		else {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof TileEntitySmokehouse) {
				playerIn.openGui(BacaCraft.instance, BacaCraft.GUI_SMOKEHOUSE, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		}
	}

	public static void setState(boolean burning, World worldIn, BlockPos pos) {
		IBlockState blockState = worldIn.getBlockState(pos);
		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if (burning) {
			worldIn.setBlockState(
					pos,
					BacaCraftBlockRegistry.SMOKEHOUSE.getDefaultState()
							.withProperty(FACING, blockState.getValue(FACING))
							.withProperty(BURNING, true),
					3);
		}
		else {
			worldIn.setBlockState(
					pos,
					BacaCraftBlockRegistry.SMOKEHOUSE.getDefaultState()
							.withProperty(FACING, blockState.getValue(FACING))
							.withProperty(BURNING, false),
					3);
		}

		if (tileEntity != null) {
			tileEntity.validate();
			worldIn.setTileEntity(pos, tileEntity);
		}
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySmokehouse();
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileEntitySmokehouse) {
				((TileEntitySmokehouse)tileentity).setCustomInventoryName(stack.getDisplayName());
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getHorizontal(meta & 3);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}
		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(BURNING, (meta & 4) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex() | (state.getValue(BURNING) ? 4 : 0);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, BURNING);
	}
}

package com.burnert.bacacraft.block;

import com.burnert.bacacraft.BacaCraft;
import com.burnert.bacacraft.BacaCraftCreativeTabs;
import com.burnert.bacacraft.core.registry.BacaCraftItemRegistry;
import com.burnert.bacacraft.core.tile.TileEntityCore;
import com.burnert.bacacraft.item.ItemBlockContraption;
import com.burnert.bacacraft.tile.TileEntityContraption;
import com.burnert.bacacraft.tile.TileEntityCooker;
import com.burnert.bacacraft.tile.TileEntitySmokehouse;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockContraption extends BlockTileBase {

	public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public static ItemBlockContraption itemBlockContraption;

	private static ItemStack[] itemContraptionReferences;

	public BlockContraption() {
		super("contraption", Material.WOOD);
		this.setDefaultTileType(TYPE, Type.SMOKEHOUSE);
		this.setSound(SoundType.WOOD);
		this.setHardness(1.8f);
		this.setResistance(3.5f);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(BacaCraftCreativeTabs.MAIN);
	}

	public static ItemStack getItemFromType(Type type) {
		return itemContraptionReferences[type.metadata];
	}

	public static Type getTypeFromMeta(int meta) {
		return Type.values()[meta];
	}

	public static TileEntityContraption tileFromWorld(IBlockAccess world, BlockPos pos) {
		return (TileEntityContraption) world.getTileEntity(pos);
	}

	// BlockCore:

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		for (Type type : Type.values()) {
			BacaCraft.proxy.registerItemRenderer(itemBlockContraption, type.metadata, "inventory", type.getName().toLowerCase());
		}
	}

	@Override
	protected void registerItemBlock() {
		itemBlockContraption = new ItemBlockContraption(this);
		itemBlockContraption.setRegistryName(this.getRegistryName());
		BacaCraftItemRegistry.register(itemBlockContraption);
	}

	// End of BlockCore

	// ITileEntityProvider:

	@Nullable
	@Override
	public TileEntityCore createNewTileEntityCore(World worldIn, int meta) {
		switch (meta) {
			case 0:
				return new TileEntitySmokehouse();
			case 1:
				return new TileEntityCooker();
			default:
				return null;
		}
	}

	// End of ITileEntityProvider

	// Block:

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE, FACING, ACTIVE);
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return true;
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public Material getMaterial(IBlockState state) {
		return state.getValue(TYPE).getMaterial();
	}

	@Nullable
	@Override
	public String getHarvestTool(IBlockState state) {
		return state.getValue(TYPE).getHarvestTool();
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (Type type : Type.values()) {
			int meta = type.metadata;
			items.add(itemBlockContraption.withDefaultTag(new ItemStack(this, 1, meta)));
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, Type.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).getMetadata();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE).getMetadata();
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		TileEntityContraption entity = tileFromWorld(worldIn, pos);
		if (entity != null) {
			EnumFacing facing = placer.getHorizontalFacing().getOpposite();
			entity.setFacing(facing);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityContraption entity = tileFromWorld(worldIn, pos);
		if (entity != null) {
			entity.setActive(!entity.isActive());
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
		Type type = state.getValue(TYPE);
		switch (type) {
			case COOKER:
				return SoundType.STONE;
		}
		return super.getSoundType(state, world, pos, entity);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		// TODO: Implement particles
	}

	// End of Block

	public enum Type implements IStringSerializable {
		SMOKEHOUSE(0, "smokehouse"),
		COOKER(1, "cooker", Material.ROCK, "pickaxe");

		private final int metadata;
		private final String name;
		private final String harvestTool;
		private final Material material;

		Type(int meta, String name) {
			this(meta, name, Material.WOOD, "axe");
		}

		Type(int meta, String name, Material mat, String harvestTool) {
			this.material = mat;
			this.harvestTool = harvestTool;
			this.metadata = meta;
			this.name = name;
		}

		public int getMetadata() {
			return metadata;
		}

		public Material getMaterial() {
			return material;
		}

		public String getHarvestTool() {
			return harvestTool;
		}

		@Override
		public String getName() {
			return name;
		}
	}
}

package space.impact.api.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import space.impact.api.Main;

import java.util.List;

import static space.impact.impactapi.BuildConfigKt.MODID;

public class BlockHint extends Block {
	private static final IIcon[] hint = new IIcon[16];
	
	public BlockHint() {
		super(Material.iron);
		setBlockName("impactapi.blockhint");
		setCreativeTab(Main.creativeTab);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		for (int i = 1; i <= hint.length; i++) {
			hint[i - 1] = icon.registerIcon(MODID+ ":" + i);
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return hint[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess w, int x, int y, int z, int side) {
		int meta = w.getBlockMetadata(x, y, z);
		return getIcon(side, meta);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		for (int i = 0; i <= 15; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
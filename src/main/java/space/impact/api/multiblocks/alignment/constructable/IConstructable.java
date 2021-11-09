package space.impact.api.multiblocks.alignment.constructable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

/**
 * @author Tec on 24.03.2017.
 */
public interface IConstructable {
	void construct(ItemStack stackSize, boolean hintsOnly);
	
	@SideOnly(Side.CLIENT)
	String[] getStructureDescription(ItemStack stackSize);
}
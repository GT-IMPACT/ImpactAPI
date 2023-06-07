package space.impact.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import space.impact.api.Main;
import space.impact.api.multiblocks.alignment.constructable.ConstructableUtility;

import java.util.List;

import static net.minecraft.util.EnumChatFormatting.RED;
import static net.minecraft.util.StatCollector.translateToLocal;
import static space.impact.impactapi.BuildConfigKt.MODID;

public class ItemConstructableTrigger extends Item {
	public ItemConstructableTrigger() {
		setUnlocalizedName("impactapi.constructableTrigger");
		setTextureName(MODID + ":itemConstructableTrigger");
		setCreativeTab(Main.creativeTab);
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		return ConstructableUtility.handle(stack, player, world, x, y, z, side);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack aStack, EntityPlayer ep, List aList, boolean boo) {
		aList.add(translateToLocal("item.impactapi.constructableTrigger.desc.0"));        // Triggers Constructable Interface
		aList.add(RED + translateToLocal("item.impactapi.constructableTrigger.desc.1")); // Shows multiblock construction details,
		aList.add(RED + translateToLocal("item.impactapi.constructableTrigger.desc.2")); // just Use on a multiblock controller.
		aList.add(RED + translateToLocal("item.impactapi.constructableTrigger.desc.3")); // (Sneak Use in creative to build)
		aList.add(RED + translateToLocal("item.impactapi.constructableTrigger.desc.4")); // Quantity affects tier/mode/type
	}
}
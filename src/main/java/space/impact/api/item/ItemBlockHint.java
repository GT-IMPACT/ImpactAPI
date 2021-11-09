package space.impact.api.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

import static net.minecraft.util.StatCollector.translateToLocal;

public class ItemBlockHint extends ItemBlock {
	
	public ItemBlockHint(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean f3) {
		// Подсказки
		if (f3) {
			list.add("F3 Enabled. Its Debug Information");
			list.add("Unlocalized Name: " + stack.getUnlocalizedName());
			list.add("Meta / Damage: " + stack.getItemDamage());
		}
		list.add(translateToLocal("impactapi.blockhint.desc.0"));
	}
	
	public int getMetadata(int aMeta) {
		return aMeta;
	}
	
	public String getUnlocalizedName(ItemStack aStack) {
		return this.field_150939_a.getUnlocalizedName() + "." + getDamage(aStack);
	}
}
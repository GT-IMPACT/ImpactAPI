package space.impact.api.proxy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class CommonProxy {
	
	public void hintParticleTinted(World w, int x, int y, int z, IIcon[] icons, short[] RGBa) {}
	
	public void hintParticleTinted(World w, int x, int y, int z, Block block, int meta, short[] RGBa) {}
	
	public void hintParticle(World w, int x, int y, int z, IIcon[] icons) {}
	
	public void hintParticle(World w, int x, int y, int z, Block block, int meta) {}
	
	public EntityPlayer getCurrentPlayer() {
		return null;
	}
	
	public boolean isCurrentPlayer(EntityPlayer player) {
		return false;
	}
	
	public void addClientSideChatMessages(String... messages) {
	}
	
	public void startHinting(World w) {}
	
	public void endHinting(World w) {}
	
	public void preInit(FMLPreInitializationEvent e) {}
}
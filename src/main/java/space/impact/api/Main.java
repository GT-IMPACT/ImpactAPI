package space.impact.api;

import space.impact.api.net.AlignmentMessage;
import space.impact.api.block.BlockHint;
import space.impact.api.item.ItemBlockHint;
import space.impact.api.item.ItemConstructableTrigger;
import space.impact.api.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import static space.impact.impactapi.BuildConfigKt.*;

@Mod(modid = MODID, name = MODNAME, version = VERSION, dependencies = "required-after:Forge@[10.13.4.1614,);")
public class Main {

	@SidedProxy(serverSide = "space.impact.api.proxy.CommonProxy", clientSide = "space.impact.api.proxy.ClientProxy")
	static CommonProxy proxy;
	static SimpleNetworkWrapper net = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	static {
		net.registerMessage(AlignmentMessage.ServerHandler.class, AlignmentMessage.AlignmentQuery.class, 0, Side.SERVER);
		net.registerMessage(AlignmentMessage.ClientHandler.class, AlignmentMessage.AlignmentData.class, 1, Side.CLIENT);
	}
	
	static Block blockHint;
	static Item itemBlockHint;
	static Item itemConstructableTrigger;
	public static final CreativeTabs creativeTab = new CreativeTabs("impactapi") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return ImpactAPI.getItemBlockHint();
		}
	};

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		ConfigurationHandler.INSTANCE.init(e.getSuggestedConfigurationFile());
		GameRegistry.registerBlock(blockHint = new BlockHint(), ItemBlockHint.class, "blockhint");
		itemBlockHint = ItemBlock.getItemFromBlock(ImpactAPI.getBlockHint());
		GameRegistry.registerItem(itemConstructableTrigger = new ItemConstructableTrigger(), itemConstructableTrigger.getUnlocalizedName());
		proxy.preInit(e);
	}

	public static void addClientSideChatMessages(String... messages) {
		proxy.addClientSideChatMessages(messages);
	}

	public static EntityPlayer getCurrentPlayer() {
		return proxy.getCurrentPlayer();
	}

	public static boolean isCurrentPlayer(EntityPlayer player) {
		return proxy.isCurrentPlayer(player);
	}
}

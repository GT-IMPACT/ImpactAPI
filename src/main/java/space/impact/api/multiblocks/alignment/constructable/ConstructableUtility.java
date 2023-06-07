package space.impact.api.multiblocks.alignment.constructable;

import cpw.mods.fml.common.Loader;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.ImpactAPI;
import space.impact.api.Main;
import space.impact.api.multiblocks.alignment.IAlignment;
import space.impact.api.multiblocks.alignment.enumerable.ExtendedFacing;

public class ConstructableUtility {
	
	private ConstructableUtility() {
	}
	
	public static boolean handle(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide) {
		ImpactAPI.startHinting(aWorld);
		boolean ret = handle0(aStack, aPlayer, aWorld, aX, aY, aZ, aSide);
		ImpactAPI.endHinting(aWorld);
		return ret;
	}
	
	private static boolean handle0(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide) {
		TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
		if (tTileEntity == null || aPlayer instanceof FakePlayer) {
			return aPlayer instanceof EntityPlayerMP;
		}
		if (aPlayer instanceof EntityPlayerMP) {
			if (aPlayer.isSneaking() && aPlayer.capabilities.isCreativeMode) {
				if (Loader.isModLoaded("gregtech")) {
					if (tTileEntity instanceof IGregTechTileEntity) {
						IMetaTileEntity metaTE = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
						if (metaTE instanceof IConstructable) {
							((IConstructable) metaTE).construct(aStack, false);
						} else if (IMultiBlockInfoContainer.contains(metaTE.getClass())) {
							IMultiBlockInfoContainer<IMetaTileEntity> imb = IMultiBlockInfoContainer.get(metaTE.getClass());
							if (metaTE instanceof IAlignment) {
								imb.construct(aStack, false, metaTE, ((IAlignment) metaTE).getExtendedFacing());
							} else {
								imb.construct(aStack, false, metaTE,
										ExtendedFacing.of(ForgeDirection.getOrientation(((IGregTechTileEntity) tTileEntity).getFrontFacing()))
								);
							}
						}
					}
				} else if (tTileEntity instanceof IConstructable) {
					((IConstructable) tTileEntity).construct(aStack, false);
				} else if (IMultiBlockInfoContainer.contains(tTileEntity.getClass())) {
					IMultiBlockInfoContainer<TileEntity> imb = IMultiBlockInfoContainer.get(tTileEntity.getClass());
					if (tTileEntity instanceof IAlignment) {
						imb.construct(aStack, false, tTileEntity, ((IAlignment) tTileEntity).getExtendedFacing());
					} else {
						imb.construct(aStack, false, tTileEntity, ExtendedFacing.of(ForgeDirection.getOrientation(aSide)));
					}
				}
			}
			return true;
		} else if (Main.isCurrentPlayer(aPlayer)) {
			if (Loader.isModLoaded("gregtech")) {
			if (tTileEntity instanceof IGregTechTileEntity) {
				IMetaTileEntity metaTE = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
				if (metaTE instanceof IConstructable) {
					((IConstructable) metaTE).construct(aStack, true);
					Main.addClientSideChatMessages(((IConstructable) metaTE).getStructureDescription(aStack));
					return false;
				} else if (IMultiBlockInfoContainer.contains(metaTE.getClass())) {
					IMultiBlockInfoContainer<IMetaTileEntity> imb = IMultiBlockInfoContainer.get(metaTE.getClass());
					if (metaTE instanceof IAlignment) {
						imb.construct(aStack, true, metaTE, ((IAlignment) metaTE).getExtendedFacing());
					} else {
						imb.construct(aStack, true, metaTE,
								ExtendedFacing.of(ForgeDirection.getOrientation(((IGregTechTileEntity) tTileEntity).getFrontFacing()))
						);
					}
					Main.addClientSideChatMessages(IMultiBlockInfoContainer.get(metaTE.getClass()).getDescription(aStack));
					return false;
				}
			}
			} else if (tTileEntity instanceof IConstructable) {
				((IConstructable) tTileEntity).construct(aStack, true);
				Main.addClientSideChatMessages(((IConstructable) tTileEntity).getStructureDescription(aStack));
				return false;
			} else if (IMultiBlockInfoContainer.contains(tTileEntity.getClass())) {
				IMultiBlockInfoContainer<TileEntity> imb = IMultiBlockInfoContainer.get(tTileEntity.getClass());
				if (tTileEntity instanceof IAlignment) {
					imb.construct(aStack, true, tTileEntity, ((IAlignment) tTileEntity).getExtendedFacing());
				} else {
					imb.construct(aStack, true, tTileEntity, ExtendedFacing.of(ForgeDirection.getOrientation(aSide)));
				}
				Main.addClientSideChatMessages(IMultiBlockInfoContainer.get(tTileEntity.getClass()).getDescription(aStack));
				return false;
			}
		}
		return false;
	}
}

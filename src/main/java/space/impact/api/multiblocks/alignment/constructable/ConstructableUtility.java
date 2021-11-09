package space.impact.api.multiblocks.alignment.constructable;

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
			// Построить по частицам
			if (aPlayer.isSneaking() && aPlayer.capabilities.isCreativeMode) {
				if (tTileEntity instanceof IConstructableProvider) {
					IConstructable constructable = ((IConstructableProvider) tTileEntity).getConstructable();
					if (constructable != null) {
						constructable.construct(aStack, false);
					}
				} else if (tTileEntity instanceof IConstructable) {
					((IConstructable) tTileEntity).construct(aStack, false);
				} else if (IMultiBlockInfoContainer.contains(tTileEntity.getClass())) {
					IMultiBlockInfoContainer<TileEntity> iMultipleInfoContainer = IMultiBlockInfoContainer.get(tTileEntity.getClass());
					if (tTileEntity instanceof IAlignment) {
						iMultipleInfoContainer.construct(aStack, false, tTileEntity, ((IAlignment) tTileEntity).getExtendedFacing());
					} else {
						iMultipleInfoContainer.construct(aStack, false, tTileEntity, ExtendedFacing.of(ForgeDirection.getOrientation(aSide)));
					}
				}
			}
			return true;
			// частицы и текст на стороне клиента
		} else if (Main.isCurrentPlayer(aPlayer)) {
			if (tTileEntity instanceof IConstructableProvider) {
				IConstructable constructable = ((IConstructableProvider) tTileEntity).getConstructable();
				if (constructable != null) {
					constructable.construct(aStack, true);
					Main.addClientSideChatMessages(constructable.getStructureDescription(aStack));
				}
			} else if (tTileEntity instanceof IConstructable) {
				IConstructable constructable = (IConstructable) tTileEntity;
				constructable.construct(aStack, true);
				Main.addClientSideChatMessages(constructable.getStructureDescription(aStack));
				return false;
			} else if (IMultiBlockInfoContainer.contains(tTileEntity.getClass())) {
				IMultiBlockInfoContainer<TileEntity> iMultipleInfoContainer = IMultiBlockInfoContainer.get(tTileEntity.getClass());
				if (tTileEntity instanceof IAlignment) {
					iMultipleInfoContainer.construct(aStack, true, tTileEntity, ((IAlignment) tTileEntity).getExtendedFacing());
				} else {
					iMultipleInfoContainer.construct(aStack, true, tTileEntity, ExtendedFacing.of(ForgeDirection.getOrientation(aSide)));
				}
				Main.addClientSideChatMessages(IMultiBlockInfoContainer.get(tTileEntity.getClass()).getDescription(aStack));
				return false;
			}
		}
		return false;
	}
}

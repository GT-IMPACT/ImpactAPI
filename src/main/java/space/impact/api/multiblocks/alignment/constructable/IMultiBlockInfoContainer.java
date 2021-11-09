package space.impact.api.multiblocks.alignment.constructable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import space.impact.api.multiblocks.alignment.enumerable.ExtendedFacing;

import java.util.HashMap;

/**
 * Для реализации IConstructable на не собственных TileEntities
 * <p>
 * Создает мета класс
 */
public interface IMultiBlockInfoContainer<T> {
	
	HashMap<String, IMultiBlockInfoContainer<?>> MULTIBLOCK_MAP = new HashMap<>();
	
	/**
	 * Конкретного ограничения на фазу загрузки нет, но обычно ее не следует
	 * вызывать до того, как TileEntities будет должным образом зарегистрирован.
	 * <p>
	 * Рекомендация: загружать вместе с TileEntities, при регистрации.
	 */
	static <T extends TileEntity> void registerTileClass(Class<T> clazz, IMultiBlockInfoContainer<?> info) {
		MULTIBLOCK_MAP.put(clazz.getCanonicalName(), info);
	}
	
	static void registerTileClass(String CanonicalNameClass, IMultiBlockInfoContainer<?> info) {
		MULTIBLOCK_MAP.put(CanonicalNameClass, info);
	}
	
	@SuppressWarnings("unchecked")
	static <T> IMultiBlockInfoContainer<T> get(Class<?> tClass) {
		return (IMultiBlockInfoContainer<T>) MULTIBLOCK_MAP.get(tClass.getCanonicalName());
	}
	
	static boolean contains(Class<?> tClass) {
		return MULTIBLOCK_MAP.containsKey(tClass.getCanonicalName());
	}
	
	void construct(ItemStack stackSize, boolean hintsOnly, T tileEntity, ExtendedFacing aSide);
	
	@SideOnly(Side.CLIENT)
	String[] getDescription(ItemStack stackSize);
}
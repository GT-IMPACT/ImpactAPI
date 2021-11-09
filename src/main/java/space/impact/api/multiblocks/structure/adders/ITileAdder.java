package space.impact.api.multiblocks.structure.adders;

import net.minecraft.tileentity.TileEntity;

public interface ITileAdder<T> {
	/**
	 * Обратный вызов для добавления TileEntity, необходимо проверить, является ли TileEntity действительной (и добавить его)
	 *
	 * @param te TileEntity
	 * @return удалось kb добавить TileEntity (структура все еще актуальна)
	 */
	boolean apply(T t, TileEntity te);
}
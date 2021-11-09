package space.impact.api.multiblocks.structure;

import net.minecraft.world.World;

public interface ICustomBlockSetting {
	/**
	 * Установка блока по умолчанию вызывает World#setBlock(int x, int y, int z, Block block block, int meta, int updateType)
	 * <p>
	 * {@code world.setBlock(x,y,z,this/block,meta,2)}, где updateType 2 означает обновление освещения и прочего.
	 *
	 * @param world - текущий мир
	 * @param x - координата x
	 * @param y - координата y
	 * @param z - координата z
	 * @param meta - мета блока
	 */
	void setBlock(World world, int x, int y, int z, int meta);
}
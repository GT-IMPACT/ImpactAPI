package space.impact.api.multiblocks.structure.adders;


import net.minecraft.block.Block;

public interface IBlockAdder<T> {
	/**
	 * Обратный вызов при добавлении блока, необходимо проверить, действителен ли блок (и добавить его)
	 *
	 * @param block - добавить блок
	 * @param meta  - добавить мету блока
	 * @return удалось ли установить блок
	 */
	boolean apply(T t, Block block, int meta);
}

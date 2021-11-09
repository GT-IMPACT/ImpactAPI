package space.impact.api.multiblocks.structure;

import net.minecraft.block.Block;

/**
 * Функция для извлечения информации тире из блока.
 */
@FunctionalInterface
public interface ITierConverter<T> {
	T convert(Block block, int meta);
}
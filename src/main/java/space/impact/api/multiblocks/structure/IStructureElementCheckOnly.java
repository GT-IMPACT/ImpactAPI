package space.impact.api.multiblocks.structure;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Используйте {@link StructureUtility} для создания instance
 */

public interface IStructureElementCheckOnly<T> extends IStructureElement<T> {
	@Override
	default boolean placeBlock(T t, World world, int x, int y, int z, ItemStack trigger) {
		return false;
	}
	
	@Override
	default boolean spawnHint(T t, World world, int x, int y, int z, ItemStack trigger) {
		return false;
	}
}
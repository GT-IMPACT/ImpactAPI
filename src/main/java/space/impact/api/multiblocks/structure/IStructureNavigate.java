package space.impact.api.multiblocks.structure;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Используйте {@link StructureUtility} для создания instance
 */
interface IStructureNavigate<T> extends IStructureElement<T> {
	@Override
	default boolean check(T t, World world, int x, int y, int z) {
		return true;
	}

	@Override
	default boolean spawnHint(T t, World world, int x, int y, int z, ItemStack trigger) {
		return true;
	}

	@Override
	default boolean placeBlock(T t, World world, int x, int y, int z, ItemStack trigger) {
		return true;
	}

	default boolean isNavigating() {
		return true;
	}
}
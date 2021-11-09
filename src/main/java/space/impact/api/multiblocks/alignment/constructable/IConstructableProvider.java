package space.impact.api.multiblocks.alignment.constructable;

import javax.annotation.Nullable;

/**
 * Реализуйте этот интерфейс, если этот TileEntity МОЖЕТ быть построен
 */
public interface IConstructableProvider {
	/**
	 * @return null, если не может построить, и instance в противном случае.
	 */
	@Nullable
	IConstructable getConstructable();
}
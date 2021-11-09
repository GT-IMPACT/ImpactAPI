package space.impact.api.multiblocks.alignment.enumerable;

import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.util.Vec3Impl;

public enum Direction {
	
	DOWN(ForgeDirection.DOWN),
	UP(ForgeDirection.UP),
	NORTH(ForgeDirection.NORTH),
	SOUTH(ForgeDirection.SOUTH),
	WEST(ForgeDirection.WEST),
	EAST(ForgeDirection.EAST);
	
	public static final Direction[] VALUES = values();
	private final ForgeDirection forgeDirection;
	private final Vec3Impl axisVector;
	
	Direction(ForgeDirection forgeDirection) {
		this.forgeDirection = forgeDirection;
		axisVector          = new Vec3Impl(forgeDirection.offsetX, forgeDirection.offsetY, forgeDirection.offsetZ);
	}
	
	public static Vec3Impl getAxisVector(ForgeDirection forgeDirection) {
		return VALUES[forgeDirection.ordinal()].axisVector;
	}
	
	public ForgeDirection getForgeDirection() {
		return forgeDirection;
	}
	
	public Vec3Impl getAxisVector() {
		return axisVector;
	}
}
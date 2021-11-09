package space.impact.api.multiblocks.alignment;

import javax.annotation.Nullable;

public interface IAlignmentProvider {
	@Nullable
	IAlignment getAlignment();
}
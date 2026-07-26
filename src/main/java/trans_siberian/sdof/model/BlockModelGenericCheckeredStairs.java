package trans_siberian.sdof.model;

import org.jetbrains.annotations.NotNull;
import org.useless.dragonfly.models.block.StaticBlockModel;

import net.minecraft.client.render.block.model.generic.BlockModelGenericStairs;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicStairs;

public class BlockModelGenericCheckeredStairs<Stairs extends BlockLogicStairs> extends BlockModelGenericStairs<Stairs> {
	private @NotNull final StaticBlockModel staticModelNorthSouth;
	private @NotNull final StaticBlockModel staticModelWestEast;

	public BlockModelGenericCheckeredStairs(
		@NotNull final Block<Stairs> block,
		@NotNull final StaticBlockModel staticModelNorthSouth,
		@NotNull final StaticBlockModel staticModelWestEast
	) {
		super(block, staticModelNorthSouth);
		this.staticModelNorthSouth = staticModelNorthSouth;
		this.staticModelWestEast = staticModelWestEast;
	}

 	@Override
	public @NotNull StaticBlockModel getModelFromData(int data) {
		final int hRot = data & 0b0011;
		return switch (hRot) {
			case 0, 1 -> this.staticModelWestEast;
			case 2, 3 -> this.staticModelNorthSouth;

			default -> {
				assert false : "unreachable";
				yield null;
			}
		};
	}
}

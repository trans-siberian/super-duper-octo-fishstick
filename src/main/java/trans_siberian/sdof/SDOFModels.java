package trans_siberian.sdof;

import static trans_siberian.sdof.SDOF.LOGGER;
import static trans_siberian.sdof.SDOF.MOD_ID;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.render.block.model.*;
import trans_siberian.sdof.definition.BlockDefinition;
import trans_siberian.sdof.model.BlockModelGenericCheckeredStairs;

public class SDOFModels {
	public static boolean hasInit = false;

	public static void init(final @NotNull BlockModelDispatcher dispatcher) {
		// texture gooning

		// ACTUAL BLOCKS START!!!!!!

		assert !hasInit : "double initialization";
		assert SDOFBlocks.hasInit;
		hasInit = true;

		// checkered stairs uses a custom block model implementation, because
		// vanilla stairs just rotates the model; our checkered blocks' patterns are
		// mirrored on north/south faces, if we simply rotate the model,
		// the mirrored patterns will some times be on west/east as well :O
		dispatcher.addDispatch(new BlockModelGenericCheckeredStairs<>(
			SDOFBlocks.POLISHED_CHECKERED_TILES_STAIRS,
			BlockModelDispatcher.loadDataModel(MOD_ID + ":block/stairs/polished_checkered_tiles_stair/north_south").asModel(),
			BlockModelDispatcher.loadDataModel(MOD_ID + ":block/stairs/polished_checkered_tiles_stair/west_east").asModel()
		));

		// by having this here, we skip the need to manually add every models
		// line-by-line!
		for (final var block : SDOFBlocks.blocks) {
			// we can't do anything if they don't provide a model method :(
			if (!(block instanceof BlockDefinition.IModel model)) continue;

			// if they are already added to the dispatcher (e.g. checkered stairs),
			// we do not re-add them
			final var existing = dispatcher.getDispatch(block.block());
			if (existing != BlockModelDispatcher.modelEmpty) continue;

			final var blockModel = model.getModel();
			// stairs and slabs will never fail here, but other block could.
			// this is a reminder for us to add their .json models!
			if (blockModel == null) {
				LOGGER.warn("{} missing model json", block.block());
				continue;
			}
			dispatcher.addDispatch(blockModel);
		}
	}
}

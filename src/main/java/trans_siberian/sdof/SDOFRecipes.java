package trans_siberian.sdof;

import net.minecraft.core.block.*;
import net.minecraft.core.item.ItemStack;
import trans_siberian.sdof.definition.BlockDefinition;
import turniplabs.halplibe.helper.RecipeBuilder;

import static trans_siberian.sdof.SDOF.MOD_ID;

public class SDOFRecipes {
	private SDOFRecipes(){}

	public static boolean hasInit = false;

	public static void init(){
		assert !hasInit : "double initialization";
		assert SDOFBlocks.hasInit;
		hasInit = true;

		//smooth sandstone
		RecipeBuilder.Furnace(MOD_ID)
			.setInput(Blocks.SANDSTONE)
			.create("smooth_sandstone", new ItemStack(SDOFBlocks.SMOOTH_SANDSTONE, 1));

		//polished checkered tiles
		RecipeBuilder.Shaped(MOD_ID, "MB", "BM")
			.addInput('M', Blocks.SLAB_BRICK_MARBLE)
			.addInput('B', Blocks.SLAB_BASALT_POLISHED)
			.create("polished_checkered_tiles", new ItemStack(SDOFBlocks.POLISHED_CHECKERED_TILES, 2));

		final var workbenchGroup = RecipeBuilder.getRecipeGroup(MOD_ID, "workbench", null);

		// by having this here, we skip the need to manually add every recipes
		// line-by-line!
		// currently only stairs, slabs, bricks and tiles are handled here
		for (final var block : SDOFBlocks.blocks) {
			if (block instanceof BlockDefinition.IWorkbenchRecipe recipe &&
				// this makes it possible to skip the "default" recipes
				// (of, e.g. a specific brick) by creating them from above.
				workbenchGroup.getItem(block.nameKey) == null
			) {
				recipe.makeWorkbenchRecipe();
				continue;
			}
		}

		SDOF.LOGGER.info("SDOF recipes initialized");
	}
}

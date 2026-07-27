package trans_siberian.sdof;

import net.minecraft.core.block.*;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
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

		//compressed cobblestone
		RecipeBuilder.Shaped(MOD_ID, "###", "###", "###")
			.addInput('#', Blocks.COBBLE_STONE)
			.create("compressed_cobblestone", new ItemStack(SDOFBlocks.COMPRESSED_COBBLESTONE, 1));
		RecipeBuilder.Shaped(MOD_ID, "###")
			.addInput('#', SDOFBlocks.COMPRESSED_COBBLESTONE)
			.create("compressed_cobblestone_slab", new ItemStack(SDOFBlocks.COMPRESSED_COBBLESTONE_SLAB, 6));

		//compressed cobblestone mossy
		RecipeBuilder.Shaped(MOD_ID, "###", "###", "###")
			.addInput('#', Blocks.COBBLE_STONE_MOSSY)
			.create("compressed_cobblestone_mossy", new ItemStack(SDOFBlocks.COMPRESSED_COBBLESTONE_MOSSY, 1));
		RecipeBuilder.Shaped(MOD_ID, "###")
			.addInput('#', SDOFBlocks.COMPRESSED_COBBLESTONE_MOSSY)
			.create("compressed_cobblestone_mossy_slab", new ItemStack(SDOFBlocks.COMPRESSED_COBBLESTONE_MOSSY_SLAB, 6));

		//compressed polished stone
		RecipeBuilder.Shaped(MOD_ID, "###", "###", "###")
			.addInput('#', Blocks.STONE_POLISHED)
			.create("compressed_polished_stone", new ItemStack(SDOFBlocks.COMPRESSED_POLISHED_STONE, 1));
		RecipeBuilder.Shaped(MOD_ID, "###")
			.addInput('#', SDOFBlocks.COMPRESSED_POLISHED_STONE)
			.create("compressed_polished_stone_slab", new ItemStack(SDOFBlocks.COMPRESSED_POLISHED_STONE_SLAB, 6));

		//compressed polished stone tiles
		RecipeBuilder.Shaped(MOD_ID, "###", "###", "###")
			.addInput('#', SDOFBlocks.POLISHED_STONE_TILES)
			.create("compressed_polished_stone_tiles", new ItemStack(SDOFBlocks.COMPRESSED_POLISHED_STONE_TILES, 1));
		RecipeBuilder.Shaped(MOD_ID, "###")
			.addInput('#', SDOFBlocks.COMPRESSED_POLISHED_STONE_TILES)
			.create("compressed_polished_stone_tiles_slab", new ItemStack(SDOFBlocks.COMPRESSED_POLISHED_STONE_TILES_SLAB, 6));

		//compressed polished netherrack
		RecipeBuilder.Shaped(MOD_ID, "###", "###", "###")
			.addInput('#', Blocks.NETHERRACK_POLISHED)
			.create("compressed_polished_netherrack", new ItemStack(SDOFBlocks.COMPRESSED_POLISHED_NETHERRACK, 1));
		RecipeBuilder.Shaped(MOD_ID, "###")
			.addInput('#', SDOFBlocks.COMPRESSED_POLISHED_NETHERRACK)
			.create("compressed_polished_netherrack_slab", new ItemStack(SDOFBlocks.COMPRESSED_POLISHED_NETHERRACK_SLAB, 6));

		//shored gravel
		RecipeBuilder.Shaped(MOD_ID, "GS", "SG")
			.addInput('G', Blocks.GRAVEL)
			.addInput('S', Items.STICK)
			.create("shored_gravel", new ItemStack(SDOFBlocks.SHORED_GRAVEL, 2));
		RecipeBuilder.Shaped(MOD_ID, "#", "##", "###")
			.addInput('#', SDOFBlocks.SHORED_GRAVEL)
			.create("shored_gravel_stairs", new ItemStack(SDOFBlocks.SHORED_GRAVEL_STAIRS, 6));
		RecipeBuilder.Shaped(MOD_ID, "###")
			.addInput('#', SDOFBlocks.SHORED_GRAVEL)
			.create("shored_gravel_slab", new ItemStack(SDOFBlocks.SHORED_GRAVEL_SLAB, 6));

		//shored sand
		RecipeBuilder.Shaped(MOD_ID, "GS", "SG")
			.addInput('G', Blocks.SAND)
			.addInput('S', Items.STICK)
			.create("shored_sand", new ItemStack(SDOFBlocks.SHORED_SAND, 2));
		RecipeBuilder.Shaped(MOD_ID, "#", "##", "###")
			.addInput('#', SDOFBlocks.SHORED_SAND)
			.create("shored_sand_stairs", new ItemStack(SDOFBlocks.SHORED_SAND_STAIRS, 6));
		RecipeBuilder.Shaped(MOD_ID, "###")
			.addInput('#', SDOFBlocks.SHORED_SAND)
			.create("shored_sand_slab", new ItemStack(SDOFBlocks.SHORED_SAND_SLAB, 6));

		//iron plating
		RecipeBuilder.Shaped(MOD_ID, " # ", "# #", " # ")
			.addInput('#', Items.INGOT_IRON)
			.create("iron_plating", new ItemStack(SDOFBlocks.IRON_PLATING, 4));
		RecipeBuilder.Shaped(MOD_ID, "###")
			.addInput('#', SDOFBlocks.IRON_PLATING)
			.create("iron_plating_slab", new ItemStack(SDOFBlocks.IRON_PLATING_SLAB, 6));

		//steel plating
		RecipeBuilder.Shaped(MOD_ID, " # ", "# #", " # ")
			.addInput('#', Items.INGOT_STEEL)
			.create("steel_plating", new ItemStack(SDOFBlocks.STEEL_PLATING, 4));
		RecipeBuilder.Shaped(MOD_ID, "###")
			.addInput('#', SDOFBlocks.STEEL_PLATING)
			.create("steel_plating_slab", new ItemStack(SDOFBlocks.STEEL_PLATING_SLAB, 6));

		//small marble tiles
		RecipeBuilder.Shaped(MOD_ID, "##", "##")
			.addInput('#', SDOFBlocks.POLISHED_MARBLE_TILES)
			.create("small_marble_tiles", new ItemStack(SDOFBlocks.SMALL_MARBLE_TILES, 4));
		RecipeBuilder.Shaped(MOD_ID, "#", "##", "###")
			.addInput('#', SDOFBlocks.SMALL_MARBLE_TILES)
			.create("small_marble_tiles_stairs", new ItemStack(SDOFBlocks.SMALL_MARBLE_TILES_STAIRS, 6));
		RecipeBuilder.Shaped(MOD_ID, "###")
			.addInput('#', SDOFBlocks.SMALL_MARBLE_TILES)
			.create("small_marble_tiles_slab", new ItemStack(SDOFBlocks.SMALL_MARBLE_TILES_SLAB, 6));

		//small lapis tiles
		RecipeBuilder.Shaped(MOD_ID, "##", "##")
			.addInput('#', Blocks.BRICK_LAPIS)
			.create("small_lapis_tiles", new ItemStack(SDOFBlocks.SMALL_LAPIS_TILES, 4));
		RecipeBuilder.Shaped(MOD_ID, "#", "##", "###")
			.addInput('#', SDOFBlocks.SMALL_LAPIS_TILES)
			.create("small_lapis_tiles_stairs", new ItemStack(SDOFBlocks.SMALL_LAPIS_TILES_STAIRS, 6));
		RecipeBuilder.Shaped(MOD_ID, "###")
			.addInput('#', SDOFBlocks.SMALL_LAPIS_TILES)
			.create("small_lapis_tiles_slab", new ItemStack(SDOFBlocks.SMALL_LAPIS_TILES_SLAB, 6));

		//vent block
		RecipeBuilder.Shaped(MOD_ID, " C ", "CMC", " C ")
			.addInput('C', Blocks.COBBLE_STONE)
			.addInput('M', Blocks.MESH)
			.create("vent_block", new ItemStack(SDOFBlocks.VENT_BLOCK, 4));

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

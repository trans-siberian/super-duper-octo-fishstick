package trans_siberian.sdof;

import net.minecraft.core.block.*;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import trans_siberian.sdof.definition.BlockDefinition;
import trans_siberian.sdof.definition.BlockMetaDefinition;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryCategory;
import turniplabs.halplibe.helper.creativeInventory.CreativeInventoryPlacement;

import static trans_siberian.sdof.SDOF.MOD_ID;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

/*
 - Simple blocks using basic models
 - Rotatable blocks using block logic

*/
// implement BlockInitEntrypoint
public class SDOFBlocks {

	// we will create a couple extra functions

	public static int newBlockID() {
		return BlockDefinition.idIncr ++;
	}

	// this will allow us to initialize blocks in BlockExampleMod.java
	public static boolean hasInit = false;

	// ACTUAL BLOCKS START!!!!!!
	public static Block<BlockLogicStairs> STONE_STAIRS;
	public static Block<BlockLogicSlab> STONE_SLAB;

	public static Block<BlockLogicStairs> LIMESTONE_STAIRS;
	public static Block<BlockLogicSlab> LIMESTONE_SLAB;

	public static Block<BlockLogicStairs> GRANITE_STAIRS;
	public static Block<BlockLogicSlab> GRANITE_SLAB;

	public static Block<BlockLogicStairs> BASALT_STAIRS;
	public static Block<BlockLogicSlab> BASALT_SLAB;

	public static Block<BlockLogicStairs> PERMAFROST_STAIRS;
	public static Block<BlockLogicSlab> PERMAFROST_SLAB;

	public static Block<BlockLogicStairs> SLATE_STAIRS;
	public static Block<BlockLogicSlab> SLATE_SLAB;

	public static Block<BlockLogicStairs> MARBLE_STAIRS;
	public static Block<BlockLogicSlab> MARBLE_SLAB;


	//smooth sandstone
	public static Block<?> SMOOTH_SANDSTONE;
	public static Block<BlockLogicStairs> SMOOTH_SANDSTONE_STAIRS;
	public static Block<BlockLogicSlab> SMOOTH_SANDSTONE_SLAB;


	//polished limestone bricks
	public static Block<?> POLISHED_LIMESTONE_BRICKS;
	public static Block<BlockLogicStairs> POLISHED_LIMESTONE_BRICKS_STAIRS;
	public static Block<BlockLogicSlab> POLISHED_LIMESTONE_BRICKS_SLAB;


	//polished stone tiles
	public static Block<?> POLISHED_STONE_TILES;
	public static Block<BlockLogicStairs> POLISHED_STONE_TILES_STAIRS;
	public static Block<BlockLogicSlab> POLISHED_STONE_TILES_SLAB;


	//polished limestone tiles
	public static Block<?> POLISHED_LIMESTONE_TILES;
	public static Block<BlockLogicStairs> POLISHED_LIMESTONE_TILES_STAIRS;
	public static Block<BlockLogicSlab> POLISHED_LIMESTONE_TILES_SLAB;


	//polished granite tiles
	public static Block<?> POLISHED_GRANITE_TILES;
	public static Block<BlockLogicStairs> POLISHED_GRANITE_TILES_STAIRS;
	public static Block<BlockLogicSlab> POLISHED_GRANITE_TILES_SLAB;


	//polished basalt tiles
	public static Block<?> POLISHED_BASALT_TILES;
	public static Block<BlockLogicStairs> POLISHED_BASALT_TILES_STAIRS;
	public static Block<BlockLogicSlab> POLISHED_BASALT_TILES_SLAB;


	//polished marble tiles
	public static Block<?> POLISHED_MARBLE_TILES;
	public static Block<BlockLogicStairs> POLISHED_MARBLE_TILES_STAIRS;
	public static Block<BlockLogicSlab> POLISHED_MARBLE_TILES_SLAB;


	//polished checkered tiles
	public static Block<?> POLISHED_CHECKERED_TILES;
	public static Block<BlockLogicStairs> POLISHED_CHECKERED_TILES_STAIRS;
	public static Block<BlockLogicSlab> POLISHED_CHECKERED_TILES_SLAB;


	//compressed cobblestone
	public static Block<?> COMPRESSED_COBBLESTONE;
	public static Block<?> COMPRESSED_COBBLESTONE_CARVED;
	public static Block<BlockLogicSlab> COMPRESSED_COBBLESTONE_SLAB;


	//compressed mossy cobblestone
	public static Block<?> COMPRESSED_COBBLESTONE_MOSSY;
	public static Block<?> COMPRESSED_COBBLESTONE_MOSSY_CARVED;
	public static Block<BlockLogicSlab> COMPRESSED_COBBLESTONE_MOSSY_SLAB;

	//compressed polished stone
	public static Block<?> COMPRESSED_POLISHED_STONE;
	public static Block<?> COMPRESSED_POLISHED_STONE_CARVED;
	public static Block<BlockLogicSlab> COMPRESSED_POLISHED_STONE_SLAB;


	//compressed polished stone tiles
	public static Block<?> COMPRESSED_POLISHED_STONE_TILES;
	public static Block<BlockLogicSlab> COMPRESSED_POLISHED_STONE_TILES_SLAB;


	//compressed polished netherrack
	public static Block<?> COMPRESSED_POLISHED_NETHERRACK;
	public static Block<?> COMPRESSED_POLISHED_NETHERRACK_CARVED;
	public static Block<BlockLogicSlab> COMPRESSED_POLISHED_NETHERRACK_SLAB;


	//shored gravel
	public static Block<?> SHORED_GRAVEL;
	public static Block<BlockLogicStairs> SHORED_GRAVEL_STAIRS;
	public static Block<BlockLogicSlab> SHORED_GRAVEL_SLAB;


	//shored sand
	public static Block<?> SHORED_SAND;
	public static Block<BlockLogicStairs> SHORED_SAND_STAIRS;
	public static Block<BlockLogicSlab> SHORED_SAND_SLAB;


	//iron plating
	public static Block<?> IRON_PLATING;
	public static Block<BlockLogicSlab> IRON_PLATING_SLAB;


	//steel plating
	public static Block<?> STEEL_PLATING;
	public static Block<BlockLogicSlab> STEEL_PLATING_SLAB;


	//small marble tiles
	public static Block<?> SMALL_MARBLE_TILES;
	public static Block<BlockLogicStairs> SMALL_MARBLE_TILES_STAIRS;
	public static Block<BlockLogicSlab> SMALL_MARBLE_TILES_SLAB;


	//small lapis tiles
	public static Block<?> SMALL_LAPIS_TILES;
	public static Block<BlockLogicStairs> SMALL_LAPIS_TILES_STAIRS;
	public static Block<BlockLogicSlab> SMALL_LAPIS_TILES_SLAB;


	//vent block
	public static Block<?> VENT_BLOCK;

	public static final List<BlockDefinition<?>> blocks = new ArrayList<>();

	// this method conveniently adds the definition to a list and then return the block
	// of the definition.
	// storing them to a centrallized list allow us to iterate through them and
	// automatically do fascinating things with them, without having to
	// manually do it one by one
	private static <Logic extends BlockLogic> @NotNull Block<Logic> make(
		final @NotNull BlockDefinition<Logic> definition
	) {
		blocks.add(definition);
		return definition.block();
	}

	// the fun part
	// control + click BlockBuilder for more detailed info
	// there is WAY more in the BlockBuilder class so definitely investigate
	public static void init() {

		////////////////////////////////
		/// ACTUAL BLOCKS START!!!!! ///
		////////////////////////////////

		assert !hasInit : "double initialization";
		hasInit = true;


		//////////////////////////////
		/// STONE STAIRS AND SLABS ///
		//////////////////////////////

		final var stones = new BlockMetaDefinition.WithMaterial(Materials.STONE, new BlockBuilder(MOD_ID)
			.setCreativeInventoryPlacement(new CreativeInventoryPlacement.Category(CreativeInventoryCategory.STONE))
			.setHardness(1.5F)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE)
			.setBlockSound(BlockSounds.STONE));

		// 		STONE_STAIRS                     = make(STONE_ISH.stairs("stone_stair", Blocks.STONE));
		STONE_SLAB                       = make(stones.slab("stone_slab", Blocks.STONE));
		LIMESTONE_STAIRS                 = make(stones.stairs("limestone_stair", Blocks.LIMESTONE));
		LIMESTONE_SLAB                   = make(stones.slab("limestone_slab", Blocks.LIMESTONE));
		GRANITE_STAIRS                   = make(stones.stairs("granite_stair", Blocks.GRANITE));
		GRANITE_SLAB                     = make(stones.slab("granite_slab", Blocks.GRANITE));
		BASALT_STAIRS                    = make(stones.stairs("basalt_stair", Blocks.BASALT));
		BASALT_SLAB                      = make(stones.slab("basalt_slab", Blocks.BASALT));
		PERMAFROST_STAIRS                = make(stones.stairs("permafrost_stair", Blocks.PERMAFROST));
		PERMAFROST_SLAB                  = make(stones.slab("permafrost_slab", Blocks.PERMAFROST));
		SLATE_STAIRS                     = make(stones.stairs("slate_stair", Blocks.SLATE));
		SLATE_SLAB                       = make(stones.slab("slate_slab", Blocks.SLATE));
		MARBLE_STAIRS                    = make(stones.stairs("marble_stair", Blocks.MARBLE));
		MARBLE_SLAB                      = make(stones.slab("marble_slab", Blocks.MARBLE));


		////////////////////////////////////////////////////
		/// SMOOTH SANDSTONE & POLISHED LIMESTONE BRICKS ///
		////////////////////////////////////////////////////

		// smooth sandstone
		SMOOTH_SANDSTONE                 = make(stones.simple("smooth_sandstone"));
		SMOOTH_SANDSTONE_STAIRS          = make(stones.stairs("smooth_sandstone_stair", SMOOTH_SANDSTONE));
		SMOOTH_SANDSTONE_SLAB            = make(stones.slab("smooth_sandstone_slab", SMOOTH_SANDSTONE));


		// polished limestone bricks
		POLISHED_LIMESTONE_BRICKS        = make(stones.bricks("polished_limestone_bricks", Blocks.LIMESTONE_POLISHED));
		POLISHED_LIMESTONE_BRICKS_STAIRS = make(stones.stairs("polished_limestone_bricks_stair", POLISHED_LIMESTONE_BRICKS));
		POLISHED_LIMESTONE_BRICKS_SLAB   = make(stones.slab("polished_limestone_bricks_slab", POLISHED_LIMESTONE_BRICKS));


		//////////////////////
		/// POLISHED TILES ///
		//////////////////////

		// polished stone tiles
		POLISHED_STONE_TILES             = make(stones.tiles("polished_stone_tiles", Blocks.SLAB_STONE_POLISHED));
		POLISHED_STONE_TILES_STAIRS      = make(stones.stairs("polished_stone_tiles_stair", POLISHED_STONE_TILES));
		POLISHED_STONE_TILES_SLAB        = make(stones.slab("polished_stone_tiles_slab", POLISHED_STONE_TILES));

		// polished limestone tiles
		POLISHED_LIMESTONE_TILES         = make(stones.tiles("polished_limestone_tiles", Blocks.SLAB_LIMESTONE_POLISHED));
		POLISHED_LIMESTONE_TILES_STAIRS  = make(stones.stairs("polished_limestone_tiles_stair", POLISHED_LIMESTONE_TILES));
		POLISHED_LIMESTONE_TILES_SLAB    = make(stones.slab("polished_limestone_tiles_slab", POLISHED_LIMESTONE_TILES));

		// polished granite tiles
		POLISHED_GRANITE_TILES           = make(stones.tiles("polished_granite_tiles", Blocks.SLAB_GRANITE_POLISHED));
		POLISHED_GRANITE_TILES_STAIRS    = make(stones.stairs("polished_granite_tiles_stair", POLISHED_GRANITE_TILES));
		POLISHED_GRANITE_TILES_SLAB      = make(stones.slab("polished_granite_tiles_slab", POLISHED_GRANITE_TILES));

		// polished basalt tiles
		POLISHED_BASALT_TILES            = make(stones.tiles("polished_basalt_tiles", Blocks.SLAB_BASALT_POLISHED));
		POLISHED_BASALT_TILES_STAIRS     = make(stones.stairs("polished_basalt_tiles_stair", POLISHED_BASALT_TILES));
		POLISHED_BASALT_TILES_SLAB       = make(stones.slab("polished_basalt_tiles_slab", POLISHED_BASALT_TILES));

		// polished marble tiles
		POLISHED_MARBLE_TILES            = make(stones.tiles("polished_marble_tiles", Blocks.SLAB_BRICK_MARBLE));
		POLISHED_MARBLE_TILES_STAIRS     = make(stones.stairs("polished_marble_tiles_stair", POLISHED_MARBLE_TILES));
		POLISHED_MARBLE_TILES_SLAB       = make(stones.slab("polished_marble_tiles_slab", POLISHED_MARBLE_TILES));

		// polished checkered tiles
		POLISHED_CHECKERED_TILES         = make(stones.simple("polished_checkered_tiles"));
		POLISHED_CHECKERED_TILES_STAIRS  = make(stones.stairs("polished_checkered_tiles_stair", POLISHED_CHECKERED_TILES));
		POLISHED_CHECKERED_TILES_SLAB    = make(stones.slab("polished_checkered_tiles_slab", POLISHED_CHECKERED_TILES));


		///////////////////
		/// COMPRESSEDS ///
		///////////////////

		final var compresseds = new BlockMetaDefinition.WithMaterial(Materials.STONE, stones.builder.clone()
			.setHardness(0.8F));

		// compressed cobblestone
		COMPRESSED_COBBLESTONE                = make(compresseds.simple("compressed_cobblestone"));
		COMPRESSED_COBBLESTONE_CARVED         = make(compresseds.simple("compressed_cobblestone_carved"));
		COMPRESSED_COBBLESTONE_SLAB           = make(compresseds.slab("compressed_cobblestone_slab", COMPRESSED_COBBLESTONE_CARVED));

		// compressed mossy cobblestone
		COMPRESSED_COBBLESTONE_MOSSY          = make(compresseds.simple("compressed_cobblestone_mossy"));
		COMPRESSED_COBBLESTONE_MOSSY_CARVED   = make(compresseds.simple("compressed_cobblestone_mossy_carved"));
		COMPRESSED_COBBLESTONE_MOSSY_SLAB     = make(compresseds.slab("compressed_cobblestone_mossy_slab", COMPRESSED_COBBLESTONE_MOSSY_CARVED));

		// compressed polished stone
		COMPRESSED_POLISHED_STONE             = make(compresseds.simple("compressed_polished_stone"));
		COMPRESSED_POLISHED_STONE_CARVED      = make(compresseds.simple("compressed_polished_stone_carved"));
		COMPRESSED_POLISHED_STONE_SLAB        = make(compresseds.slab("compressed_polished_stone_slab", COMPRESSED_POLISHED_STONE_CARVED));

		// compressed polished stone tiles
		COMPRESSED_POLISHED_STONE_TILES       = make(compresseds.simple("compressed_polished_stone_tiles"));
		COMPRESSED_POLISHED_STONE_TILES_SLAB  = make(compresseds.slab("compressed_polished_stone_tiles_slab", COMPRESSED_POLISHED_STONE_TILES));

		// compressed polished netherrack
		compresseds.builder.setHardness(0.8F);
		COMPRESSED_POLISHED_NETHERRACK        = make(compresseds.simple("compressed_polished_netherrack"));
		COMPRESSED_POLISHED_NETHERRACK_CARVED = make(compresseds.simple("compressed_polished_netherrack_carved"));
		COMPRESSED_POLISHED_NETHERRACK_SLAB   = make(compresseds.slab("compressed_polished_netherrack_slab", COMPRESSED_POLISHED_NETHERRACK_CARVED));


		///////////////
		/// SHOREDS ///
		///////////////

		final var shoreds = new BlockMetaDefinition.WithMaterial(Materials.DIRT, new BlockBuilder(MOD_ID)
			.setCreativeInventoryPlacement(new CreativeInventoryPlacement.Category(CreativeInventoryCategory.NATURAL))
			.setHardness(0.6F)
			.setTags(BlockTags.MINEABLE_BY_SHOVEL)
			.setBlockSound(BlockSounds.GRAVEL));

		// shored gravel
		SHORED_GRAVEL        = make(shoreds.simple("shored_gravel"));
		SHORED_GRAVEL_STAIRS = make(shoreds.stairs("shored_gravel_stair", SHORED_GRAVEL));
		SHORED_GRAVEL_SLAB   = make(shoreds.slab("shored_gravel_slab", SHORED_GRAVEL));

		// shored sand
		shoreds.builder.setHardness(0.5F).setBlockSound(BlockSounds.SAND);
		shoreds.material = Materials.SAND;

		SHORED_SAND          = make(shoreds.simple("shored_sand"));
		SHORED_SAND_STAIRS   = make(shoreds.stairs("shored_sand_stair", SHORED_SAND));
		SHORED_SAND_SLAB     = make(shoreds.slab("shored_sand_slab", SHORED_SAND));


		////////////////
		/// PLATINGS ///
		////////////////


		final var platings = new BlockMetaDefinition.WithMaterial(Materials.METAL, new BlockBuilder(MOD_ID)
			.setCreativeInventoryPlacement(new CreativeInventoryPlacement.Category(CreativeInventoryCategory.MISCELLANEOUS))
			.setHardness(1.5F)
			.setTags(BlockTags.MINEABLE_BY_PICKAXE)
			.setBlockSound(BlockSounds.METAL));

		//iron plating
		IRON_PLATING =       make(platings.simple("iron_plating"));
		IRON_PLATING_SLAB =  make(platings.slab("iron_plating_slab", IRON_PLATING));

		//steel plating
		STEEL_PLATING =      make(platings.simple("steel_plating"));
		STEEL_PLATING_SLAB = make(platings.slab("steel_plating_slab", STEEL_PLATING));


		///////////////////
		/// SMALL TILES ///
		///////////////////

		//small marble tiles
		SMALL_MARBLE_TILES =        make(stones.simple("small_marble_tiles"));
		SMALL_MARBLE_TILES_STAIRS = make(stones.stairs("small_marble_tiles_stair", SMALL_MARBLE_TILES));
		SMALL_MARBLE_TILES_SLAB =   make(stones.slab("small_marble_tiles_slab", SMALL_MARBLE_TILES));

		//small lapis tiles
		SMALL_LAPIS_TILES =        make(stones.simple("small_lapis_tiles"));
		SMALL_LAPIS_TILES_STAIRS = make(stones.stairs("small_lapis_tiles_stair", SMALL_LAPIS_TILES));
		SMALL_LAPIS_TILES_SLAB =   make(stones.slab("small_lapis_tiles_slab", SMALL_LAPIS_TILES));


		/////////////////////
		/// MISCELLANEOUS ///
		/////////////////////

		//vent block
		VENT_BLOCK = make(stones.simple("vent_block"));

		SDOF.LOGGER.info("SDOF blocks initialized");
	}
}

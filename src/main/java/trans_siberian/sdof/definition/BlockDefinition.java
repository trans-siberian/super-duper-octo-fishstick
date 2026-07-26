package trans_siberian.sdof.definition;

import static trans_siberian.sdof.SDOF.MOD_ID;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelSlab;
import net.minecraft.client.render.block.model.BlockModelStairs;
import net.minecraft.client.render.block.model.generic.BlockModelGeneric;
import net.minecraft.client.render.block.model.generic.BlockModelGenericSlab;
import net.minecraft.client.render.block.model.generic.BlockModelGenericStairs;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicSlab;
import net.minecraft.core.block.BlockLogicStairs;
import net.minecraft.core.block.BlockLogicSupplier;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.RecipeBuilder;

// This BlockDefinition thingy can be thought of a set of useful informations
// for automating tasks like building the block itself, recipes and models

// <Logic> can be "replaced" by classes like BlockLogicStairs, BlockLogicSlabs,
// or just BlockLogic itself, or BlockLogicOhMyGahhh
// it is called a "generic type parameter", or just "generic type"

public abstract class BlockDefinition<Logic extends BlockLogic> {
	public static int idIncr = 7000;
	protected static final NamespaceID MISSING_MODEL_ID = new NamespaceID("minecraft", "block/missing");

	// both a name and a key, how convenient
	// it is used for both the block's translation key, namespace id,
	// and path to its model in some cases
	public final @NotNull String nameKey;

	// this fellow builds the block
	protected final @NotNull BlockBuilder builder;

	// this field stores the block once it is built, for repeated access
	private @Nullable Block<Logic> block = null;

	protected BlockDefinition(
		final @NotNull BlockBuilder builder,
		final @NotNull String nameKey
	) {
		this.nameKey = nameKey;
		this.builder = builder;
	}

	// this method is abstract, and the class itself is also abstract.
	// we need to `extend` it to define actual implementations, like in
	// BlockDefinition.Stairs, this method constructs a BlockLogicStair...
	protected abstract Logic makeLogic(final @NotNull Block<Logic> block);

	public final @NotNull Block<Logic> block() {
		return (this.block != null)
			? this.block
			: (this.block = this.builder.build(this.nameKey, this.nameKey, idIncr++, this::makeLogic));
	}

	// this is an interface.
	// from the outside, we can use
	// `if (something instanceof IWorkbenchRecipe recipe) recipe.makeWorkbenchRecipe()`
	// to ask the actual implementation to make the recipe, without having to know
	// what the actual implementation is.
	// For stairs, slabs, bricks and tiles, for example, this automatically
	// makes their shaped recipe
	public interface IWorkbenchRecipe {
		void makeWorkbenchRecipe();
	}

	// similar to IWorkbenchRecipe, this thingy builds the model of the block
	public interface IModel<Logic extends BlockLogic> {
		@Nullable BlockModel<Logic> getModel();
	}

	// this is the first concrete implementation of our BlockDefinition :D
	public static class Simple extends BlockDefinition<BlockLogic> implements IModel<BlockLogic> {
		public final @NotNull Material mat;

		public Simple(
			final @NotNull BlockBuilder builder,
			final @NotNull String nameKey,
			final @NotNull Material mat
		) {
			super(builder, nameKey);
			this.mat = mat;
		}

		@Override
		protected BlockLogic makeLogic(@NotNull Block<BlockLogic> block) {
			return new BlockLogic(block, this.mat);
		}

		// To get the block model, we first try to load a json-model from
		// "sdof:block/<this block>", and if that json file does not exist, we
		// just give up -- at which point it is up to the outside to decide what
		// they do with it.
		//
		// Remember that this implementation just gives up. Some of the
		// following implementations tho are able to fallback to other ways to
		// build the model!
		//
		// Currently, we just make sure that all of our "simple blocks" have
		// a model json
		@Override
		public @Nullable BlockModel<BlockLogic> getModel() {
			final var jsonModel = BlockModelDispatcher.loadDataModel(MOD_ID + ":block/" + this.nameKey);
			if (!MISSING_MODEL_ID.equals(jsonModel.modelId())) return new BlockModelGeneric<>(this.block(), jsonModel);
			return null;
		}
	}

	// not used, but here in case we need custom block logic
	public static final class Custom<Logic extends BlockLogic> extends BlockDefinition<Logic> {
		private final @NotNull BlockLogicSupplier<Logic> logic;

		public Custom(
			final @NotNull BlockBuilder builder,
			final @NotNull String nameKey,
			final @NotNull BlockLogicSupplier<Logic> logic
		) {
			super(builder, nameKey);
			this.logic = logic;
		}

		@Override
		protected Logic makeLogic(@NotNull Block<Logic> block) {
			return this.logic.get(block);
		}
	}

	// this is a convenient super-type for all kinds of blocks that derives from
	// other blocks, i.e. stairs, slabs, bricks, and tiles
	public static abstract class Derived<Logic extends BlockLogic, BaseLogic extends BlockLogic> extends BlockDefinition<Logic> {
		public final @NotNull Block<? extends BaseLogic> base;

		public Derived(
			final @NotNull BlockBuilder builder,
			final @NotNull String nameKey,
			final @NotNull Block<? extends BaseLogic> base
		) {
			super(builder, nameKey);
			assert base != null : nameKey;
			this.base = base;
		}

		// Since our block in this case is derived, we provide a convenient method
		// for making a shaped crafting recipe of it, with all ingredients being
		// the base-block
		public void makeShapedWorkbenchRecipe(@NotNull String @NotNull... shape) {
			RecipeBuilder.Shaped(MOD_ID, shape).addInput('X', this.base).create(this.nameKey, this.block());
		}
	}

	// stairs, it derives from the derived block definition
	public static class Stairs extends Derived<BlockLogicStairs, BlockLogic> implements IWorkbenchRecipe, IModel<BlockLogicStairs> {
		public Stairs(
			final @NotNull BlockBuilder builder,
			final @NotNull String nameKey,
			final @NotNull Block<?> base
		) {
			super(builder, nameKey, base);
		}

		@Override
		protected BlockLogicStairs makeLogic(@NotNull Block<BlockLogicStairs> block) {
			return new BlockLogicStairs(block, this.base);
		}

		@Override
		public void makeWorkbenchRecipe() {
			this.makeShapedWorkbenchRecipe(
				"X  ",
				"XX ",
				"XXX"
			);
		}

		// here, as said above in BlockDefnition.Simple, we fall back to other
		// models if loading the stairs json fail, in this case it is
		// BlockModelStairs.
		// This makes it so that we don't have to write hundreads (well, potentialaly)
		// of jsons for every stairs and slabs, while also still allowing
		// asset pack authors (and ourselves) to override the models with json
		// as-if they were defined by one :3
		@Override
		public @Nullable BlockModel<BlockLogicStairs> getModel() {
			final var jsonModel = BlockModelDispatcher.loadDataModel(MOD_ID + ":block/stairs/" + this.nameKey);
			if (!MISSING_MODEL_ID.equals(jsonModel.modelId())) return new BlockModelGenericStairs<>(this.block(), jsonModel);

			// this can fail if our base block does not have a model yet assigned,
			// having this here saves us from otherwise having to guess which one of the stairs
			// is the bad boy >:(
			assert BlockModelDispatcher.getInstance().getDispatch(this.block().getLogic().modelBlock) != null
				: "modelBlock of a stair used by BlockModelStairs must not be unsassighed: " + this.block();

			return new BlockModelStairs<>(this.block());
		}
	}

	public static class Slab extends Derived<BlockLogicSlab, BlockLogic> implements IWorkbenchRecipe, IModel<BlockLogicSlab> {
		public Slab(
			final @NotNull BlockBuilder builder,
			final @NotNull String nameKey,
			final @NotNull Block<?> base
		) {
			super(builder, nameKey, base);
		}

		@Override
		protected BlockLogicSlab makeLogic(@NotNull Block<BlockLogicSlab> block) {
			return new BlockLogicSlab(block, this.base);
		}

		@Override
		public void makeWorkbenchRecipe() {
			this.makeShapedWorkbenchRecipe(
				"XXX"
			);
		}

		// similar to stairs, we fallback to BlockModelSlab
		@Override
		public @Nullable BlockModel<BlockLogicSlab> getModel() {
			final var jsonModel = this.getJsonModel();
			if (jsonModel != null) return jsonModel;

			assert BlockModelDispatcher.getInstance().getDispatch(this.block().getLogic().modelBlock) != null
				: "modelBlock of a stair used by BlockModelSlab must not be unsassighed: " + this.block();

			return new BlockModelSlab<>(this.block());
		}

		// slab's json models are by convention defined in a more complicated way,
		// requiring a json for both full, lower, and upper models.
		// we fail (return null) if any of which is not found
		private @Nullable BlockModelGenericSlab<BlockLogicSlab> getJsonModel() {
			final String full = "/full";
			final String lower = "/lower";
			final String upper = "/upper";

			final var sb = new StringBuilder();
			sb.append(MOD_ID);
			sb.append(":block/slab/");
			sb.append(this.nameKey);

			final var jsonModelFull = BlockModelDispatcher.loadDataModel(sb.append(full).toString());
			if (MISSING_MODEL_ID.equals(jsonModelFull.modelId())) return null;
			sb.setLength(sb.length() - full.length());

			final var jsonModelLower = BlockModelDispatcher.loadDataModel(sb.append(lower).toString());
			if (MISSING_MODEL_ID.equals(jsonModelLower.modelId())) return null;
			sb.setLength(sb.length() - lower.length());

			final var jsonModelUpper = BlockModelDispatcher.loadDataModel(sb.append(upper).toString());
			if (MISSING_MODEL_ID.equals(jsonModelUpper.modelId())) return null;

			return new BlockModelGenericSlab<>(this.block(), jsonModelLower, jsonModelUpper, jsonModelFull);
		}
	}

	// We use a unique definition for bricks, so that it's recipes can be automatically generated.
	public static class Bricks extends Derived<BlockLogic, BlockLogic> implements IWorkbenchRecipe, IModel<BlockLogic> {
		public Bricks(
			final @NotNull BlockBuilder builder,
			final @NotNull String nameKey,
			final @NotNull Block<?> base
		) {
			super(builder, nameKey, base);
		}

		@Override
		protected BlockLogic makeLogic(@NotNull Block<BlockLogic> block) {
			return new BlockLogic(block, this.base.getMaterial());
		}

		@Override
		public void makeWorkbenchRecipe() {
			this.makeShapedWorkbenchRecipe(
				"XX",
				"XX"
			);
		}

		@Override
		public @Nullable BlockModel<BlockLogic> getModel() {
			final var jsonModel = BlockModelDispatcher.loadDataModel(MOD_ID + ":block/bricks/" + this.nameKey);
			if (!MISSING_MODEL_ID.equals(jsonModel.modelId())) return new BlockModelGeneric<>(this.block(), jsonModel);
			return null;
		}
	}

	// Similar to bricks, we use a unique definition for tiles, so that it's recipes can be automatically generated.
	//
	// However, note that checkered tiles are not using this, because their recipe are slightly different
	// (well, checkered).
	//
	// We can add a custom CheckeredTiles definition in the future if we want to add more of those,
	// however currently we just manually specify their recipe in SDOFRecipes.init
	public static class Tiles extends Derived<BlockLogic, BlockLogicSlab> implements IWorkbenchRecipe, IModel<BlockLogic> {
		public Tiles(
			final @NotNull BlockBuilder builder,
			final @NotNull String nameKey,
			final @NotNull Block<? extends BlockLogicSlab> baseSlab
		) {
			super(builder, nameKey, baseSlab);
		}

		@Override
		protected BlockLogic makeLogic(@NotNull Block<BlockLogic> block) {
			return new BlockLogic(block, this.base.getLogic().modelBlock.getMaterial());
		}

		@Override
		public void makeWorkbenchRecipe() {
			this.makeShapedWorkbenchRecipe(
				"XX",
				"XX"
			);
		}

		@Override
		public @Nullable BlockModel<BlockLogic> getModel() {
			final var jsonModel = BlockModelDispatcher.loadDataModel(MOD_ID + ":block/tiles/" + this.nameKey);
			if (!MISSING_MODEL_ID.equals(jsonModel.modelId())) return new BlockModelGeneric<>(this.block(), jsonModel);
			return null;
		}
	}
}

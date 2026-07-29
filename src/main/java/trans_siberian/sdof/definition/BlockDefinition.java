package trans_siberian.sdof.definition;

import static trans_siberian.sdof.SDOF.LOGGER;
import static trans_siberian.sdof.SDOF.MOD_ID;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
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
	public sealed interface IModel<Logic extends BlockLogic> {
		@Environment(EnvType.CLIENT)
		default @Nullable BlockModel<Logic> getModel() {
			final var jsonModel = this.getModelFromJson();
			if (jsonModel != null) return jsonModel;
			return this.getModelFallback();
		}

		@NotNull Block<Logic> block();

		default @NotNull String modelPath() {
			assert this instanceof BlockDefinition;
			return ((BlockDefinition<?>)this).nameKey;
		}

		@Environment(EnvType.CLIENT)
		default @Nullable BlockModel<Logic> getModelFromJson() {
			final var jsonModel = BlockModelDispatcher.loadDataModel(MOD_ID + ":block/" + this.modelPath());
			if (!MISSING_MODEL_ID.equals(jsonModel.modelId())) return new BlockModelGeneric<>(this.block(), jsonModel);
			return null;
		}

		@Environment(EnvType.CLIENT)
		default @Nullable BlockModel<Logic> getModelFallback() {
			return null;
		}
	}

	// this is the first concrete implementation of our BlockDefinition :D
	public static non-sealed class Simple extends BlockDefinition<BlockLogic> implements IModel<BlockLogic> {
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
		public void makeShapedWorkbenchRecipe(final int count, final @NotNull String @NotNull... shape) {
			RecipeBuilder.Shaped(MOD_ID, shape).addInput('X', this.base).create(this.nameKey, new ItemStack(this.block(), count));
		}
	}

	// stairs, it derives from the derived block definition
	public static non-sealed class Stairs extends Derived<BlockLogicStairs, BlockLogic> implements IWorkbenchRecipe, IModel<BlockLogicStairs> {
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
			this.makeShapedWorkbenchRecipe(6,
				"X  ",
				"XX ",
				"XXX"
			);
		}

		@Environment(EnvType.CLIENT)
		@Override
		public @Nullable BlockModel<BlockLogicStairs> getModelFromJson() {
			final var jsonModel = BlockModelDispatcher.loadDataModel(MOD_ID + ":block/stairs/" + this.nameKey);
			if (!MISSING_MODEL_ID.equals(jsonModel.modelId())) return new BlockModelGenericStairs<>(this.block(), jsonModel);
			return null;
		}

		@Environment(EnvType.CLIENT)
		@Override
		public @Nullable BlockModel<BlockLogicStairs> getModelFallback() {
			// this can fail if our base block does not have a model yet assigned,
			// having this here saves us from otherwise having to guess which one of the stairs
			// is the bad boy >:(
			assert BlockModelDispatcher.getInstance().getDispatch(this.block().getLogic().modelBlock) != null
				: "modelBlock of a stair used by BlockModelStairs must not be unsassighed: " + this.block();

			return new BlockModelStairs<>(this.block());
		}
	}

	public static non-sealed class Slab extends Derived<BlockLogicSlab, BlockLogic> implements IWorkbenchRecipe, IModel<BlockLogicSlab> {
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
			this.makeShapedWorkbenchRecipe(6,
				"XXX"
			);
		}

		// slab's json models are by convention defined in a more complicated way,
		// requiring a json for both full, lower, and upper models.
		// we fail (return null) if any of which is not found
		@Environment(EnvType.CLIENT)
		@Override
		public @Nullable BlockModel<BlockLogicSlab> getModelFromJson() {
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

		@Environment(EnvType.CLIENT)
		@Override
		public @Nullable BlockModel<BlockLogicSlab> getModelFallback() {
			assert BlockModelDispatcher.getInstance().getDispatch(this.block().getLogic().modelBlock) != null
				: "modelBlock of a stair used by BlockModelSlab must not be unsassighed: " + this.block();

			return new BlockModelSlab<>(this.block());
		}
	}

	// We use a unique definition for bricks, so that it's recipes can be automatically generated.
	public static non-sealed class Bricks extends Derived<BlockLogic, BlockLogic> implements IWorkbenchRecipe, IModel<BlockLogic> {
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
			this.makeShapedWorkbenchRecipe(4,
				"XX",
				"XX"
			);
		}

		@Override
		public @NotNull String modelPath() {
			return "bricks/" + this.nameKey;
		}
	}

	// Similar to bricks, we use a unique definition for tiles, so that it's recipes can be automatically generated.
	//
	// However, note that checkered tiles are not using this, because their recipe are slightly different
	// (well, checkered).
	//
	// We can add a custom CheckeredTiles definition in the future if we want to add more of those,
	// however currently we just manually specify their recipe in SDOFRecipes.init
	public static non-sealed class Tiles extends Derived<BlockLogic, BlockLogicSlab> implements IWorkbenchRecipe, IModel<BlockLogic> {
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
			this.makeShapedWorkbenchRecipe(2,
				"XX",
				"XX"
			);
		}


		@Override
		public @NotNull String modelPath() {
			return "tiles/" + this.nameKey;
		}
	}

	public static non-sealed class SmallTiles extends Derived<BlockLogic, BlockLogic> implements IWorkbenchRecipe, IModel<BlockLogic> {
		public SmallTiles(
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
			this.makeShapedWorkbenchRecipe(4,
				"XX",
				"XX"
			);
		}
	}

	public static non-sealed class Compressed extends Derived<BlockLogic, BlockLogic> implements IWorkbenchRecipe, IModel<BlockLogic> {
		public Compressed(
			final @NotNull BlockBuilder builder,
			final @NotNull String nameKey,
			final @NotNull Block<? extends BlockLogic> base
		) {
			super(builder, nameKey, base);
		}

		@Override
		protected BlockLogic makeLogic(@NotNull Block<BlockLogic> block) {
			return new BlockLogic(block, this.base.getMaterial());
		}


		@Override
		public void makeWorkbenchRecipe() {
			this.makeShapedWorkbenchRecipe(1,
				"XXX",
				"XXX",
				"XXX"
			);
		}
	}

	public static non-sealed class Shored extends BlockDefinition<BlockLogic> implements IWorkbenchRecipe, IModel<BlockLogic> {
		public final Block<? extends BlockLogic> meat;

		public Shored(
			final @NotNull BlockBuilder builder,
			final @NotNull String nameKey,
			final @NotNull Block<? extends BlockLogic> meat
		) {
			super(builder, nameKey);
			this.meat = meat;
		}

		@Override
		protected BlockLogic makeLogic(@NotNull Block<BlockLogic> block) {
			return new BlockLogic(block, this.meat.getMaterial());
		}

		@Override
		public void makeWorkbenchRecipe() {
			RecipeBuilder.Shaped(MOD_ID)
				.setShape(
					"MS",
					"SM")
				.addInput('M', this.meat)
				.addInput('S', Items.STICK)
				.create(this.nameKey, new ItemStack(this.block(), 2));
		}
	}

	public static non-sealed class Plating extends BlockDefinition<BlockLogic> implements IWorkbenchRecipe, IModel<BlockLogic> {
		public final Supplier<IItemConvertible> ingredient;

		public Plating(
			final @NotNull BlockBuilder builder,
			final @NotNull String nameKey,
			final @NotNull Supplier<IItemConvertible> ingredient
		) {
			super(builder, nameKey);
			this.ingredient = ingredient;
		}

		@Override
		protected BlockLogic makeLogic(@NotNull Block<BlockLogic> block) {
			return new BlockLogic(block, Materials.METAL);
		}

		@Override
		public void makeWorkbenchRecipe() {
			RecipeBuilder.Shaped(MOD_ID)
				.setShape(
					" # ",
					"# #",
					" # ")
				.addInput('#', this.ingredient.get())
				.create(this.nameKey, new ItemStack(this.block(), 4));
		}
	}
}

package trans_siberian.sdof.definition;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicSlab;
import net.minecraft.core.block.BlockLogicSupplier;
import net.minecraft.core.block.material.Material;
import turniplabs.halplibe.helper.BlockBuilder;

// This is a convenience type, containing a reused BlockBuilder, and using it to
// instantiate BlockDefinitions (hence the name)

public class BlockMetaDefinition {
	public final @NotNull BlockBuilder builder;
	public BlockMetaDefinition(@NotNull BlockBuilder builder) {
		this.builder = builder;
	}

	public @NotNull BlockDefinition.Simple simple(final @NotNull String nameKey, @NotNull Material material) {
		return new BlockDefinition.Simple(this.builder, nameKey, material);
	}

	public @NotNull <Logic extends BlockLogic> BlockDefinition.Custom<Logic> custom(
		final @NotNull String nameKey,
		final @NotNull BlockLogicSupplier<Logic> logic
	) {
		return new BlockDefinition.Custom<>(this.builder, nameKey, logic);
	}

	public @NotNull BlockDefinition.Stairs stairs(
		final @NotNull String nameKey,
		final @NotNull Block<?> base
	) {
		return new BlockDefinition.Stairs(this.builder, nameKey, base);
	}

	public @NotNull BlockDefinition.Slab slab(
		final @NotNull String nameKey,
		final @NotNull Block<?> base
	) {
		return new BlockDefinition.Slab(this.builder, nameKey, base);
	}

	public @NotNull BlockDefinition.Bricks bricks(
		final @NotNull String nameKey,
		final @NotNull Block<?> base
	) {
		return new BlockDefinition.Bricks(builder, nameKey, base);
	}

	public @NotNull BlockDefinition.Tiles tiles(
		final @NotNull String nameKey,
		final @NotNull Block<? extends BlockLogicSlab> base
	) {
		return new BlockDefinition.Tiles(builder, nameKey, base);
	}


	public static class WithMaterial extends BlockMetaDefinition {
		public @NotNull Material material;
		public WithMaterial(@NotNull Material material, @NotNull BlockBuilder builder) {
			super(builder);
			this.material = material;
		}

		public @NotNull BlockDefinition.Simple simple(final @NotNull String nameKey) {
			return new BlockDefinition.Simple(this.builder, nameKey, this.material);
		}
	}

}

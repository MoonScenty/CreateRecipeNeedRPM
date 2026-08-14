package me.moonscenty.createrecipeneedrpm.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RPMCrushingRecipeSerializer
        implements RecipeSerializer<RPMCrushingRecipe> {

    private static final MapCodec<RPMCrushingRecipe> CODEC =
            RPMProcessingRecipeParams.CODEC.xmap(
                    RPMCrushingRecipe::new,
                    RPMCrushingRecipe::getRPMParams
            );

    private static final StreamCodec<
            RegistryFriendlyByteBuf,
            RPMCrushingRecipe
            > STREAM_CODEC =
            RPMProcessingRecipeParams.STREAM_CODEC.map(
                    RPMCrushingRecipe::new,
                    RPMCrushingRecipe::getRPMParams
            );

    @Override
    public MapCodec<RPMCrushingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<
            RegistryFriendlyByteBuf,
            RPMCrushingRecipe
            > streamCodec() {
        return STREAM_CODEC;
    }
}
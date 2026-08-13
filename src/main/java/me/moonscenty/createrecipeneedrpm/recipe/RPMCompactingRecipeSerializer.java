package me.moonscenty.createrecipeneedrpm.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RPMCompactingRecipeSerializer
        implements RecipeSerializer<RPMCompactingRecipe> {

    private static final MapCodec<RPMCompactingRecipe> CODEC =
            RPMProcessingRecipeParams.CODEC.xmap(
                    RPMCompactingRecipe::new,
                    RPMCompactingRecipe::getRPMParams
            );

    private static final StreamCodec<RegistryFriendlyByteBuf, RPMCompactingRecipe>
            STREAM_CODEC =
            RPMProcessingRecipeParams.STREAM_CODEC.map(
                    RPMCompactingRecipe::new,
                    RPMCompactingRecipe::getRPMParams
            );

    @Override
    public MapCodec<RPMCompactingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, RPMCompactingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
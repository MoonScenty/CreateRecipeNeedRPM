package me.moonscenty.createrecipeneedrpm.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeParams;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public class RPMProcessingRecipeParams extends ProcessingRecipeParams {

    private static final Codec<Float> MIN_RPM_CODEC =
            Codec.FLOAT.validate(RPMProcessingRecipeParams::validateMinRPM);

    public static final MapCodec<RPMProcessingRecipeParams> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(

                    codec(RPMProcessingRecipeParams::new)
                            .forGetter(Function.identity()),

                    MIN_RPM_CODEC
                            .fieldOf("min_rpm")
                            .forGetter(RPMProcessingRecipeParams::getMinRPM)

            ).apply(instance, (params, minRPM) -> {
                params.minRPM = minRPM;
                return params;
            }));

    public static final StreamCodec<RegistryFriendlyByteBuf, RPMProcessingRecipeParams>
            STREAM_CODEC =
            streamCodec(RPMProcessingRecipeParams::new);

    private float minRPM;

    public RPMProcessingRecipeParams() {
        super();
    }

    public float getMinRPM() {
        return minRPM;
    }

    private static DataResult<Float> validateMinRPM(float minRPM) {
        if (!Float.isFinite(minRPM)) {
            return DataResult.error(() ->
                    "min_rpm must be a finite number"
            );
        }

        if (minRPM < 0.0F) {
            return DataResult.error(() ->
                    "min_rpm must be greater than or equal to 0"
            );
        }

        return DataResult.success(minRPM);
    }

    private static float requireValidMinRPM(float minRPM) {
        if (!Float.isFinite(minRPM)) {
            throw new IllegalArgumentException(
                    "min_rpm must be a finite number"
            );
        }

        if (minRPM < 0.0F) {
            throw new IllegalArgumentException(
                    "min_rpm must be greater than or equal to 0"
            );
        }

        return minRPM;
    }

    @Override
    protected void encode(RegistryFriendlyByteBuf buffer) {
        super.encode(buffer);
        ByteBufCodecs.FLOAT.encode(buffer, minRPM);
    }

    @Override
    protected void decode(RegistryFriendlyByteBuf buffer) {
        super.decode(buffer);
        minRPM = requireValidMinRPM(
                ByteBufCodecs.FLOAT.decode(buffer)
        );
    }
}

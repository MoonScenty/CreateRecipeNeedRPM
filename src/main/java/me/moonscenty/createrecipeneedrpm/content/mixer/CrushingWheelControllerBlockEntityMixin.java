package me.moonscenty.createrecipeneedrpm.mixin;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import me.moonscenty.createrecipeneedrpm.content.crusher.RPMCrushingWheelHelper;
import me.moonscenty.createrecipeneedrpm.recipe.RPMCrushingRecipeFinder;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(CrushingWheelControllerBlockEntity.class)
public abstract class CrushingWheelControllerBlockEntityMixin {

    @Inject(
            method = "findRecipe",
            at = @At("HEAD"),
            cancellable = true
    )
    private void createrecipeneedrpm$findRPMCrushingRecipe(
            CallbackInfoReturnable<
                    Optional<
                            RecipeHolder<
                                    StandardProcessingRecipe<RecipeWrapper>
                                    >
                            >
                    > cir
    ) {
        CrushingWheelControllerBlockEntity self =
                (CrushingWheelControllerBlockEntity) (Object) this;

        if (self.getLevel() == null) {
            return;
        }

        // 일반 Create Crushing Wheel Controller면 건드리지 않음
        if (RPMCrushingWheelHelper.findWheel(
                self.getLevel(),
                self.getBlockPos()
        ).isEmpty()) {
            return;
        }

        float rpm =
                RPMCrushingWheelHelper.getRPM(
                        self.getLevel(),
                        self.getBlockPos()
                );

        RecipeWrapper wrapper =
                new RecipeWrapper(self.inventory);

        cir.setReturnValue(
                RPMCrushingRecipeFinder.find(
                        wrapper,
                        self.getLevel(),
                        rpm
                )
        );
    }
}
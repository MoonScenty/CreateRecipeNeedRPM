package me.moonscenty.createrecipeneedrpm.integration.jei;

import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import me.moonscenty.createrecipeneedrpm.CreateRecipeNeedRPM;
import me.moonscenty.createrecipeneedrpm.registry.ModBlocks;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class CreateRecipeNeedRPMJEI implements IModPlugin {

    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    CreateRecipeNeedRPM.MOD_ID,
                    "jei_plugin"
            );

    private CreateRecipeCategory<AbstractCrushingRecipe> rpmMilling;
    private CreateRecipeCategory<PressingRecipe> rpmPressing;

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(
            IRecipeCategoryRegistration registration
    ) {
        rpmMilling =
                new CreateRecipeCategory.Builder<>(
                        AbstractCrushingRecipe.class
                )
                        .addTypedRecipes(ModRecipeTypes.RPM_MILLING)
                        .catalyst(ModBlocks.RPM_MILLSTONE::get)
                        .itemIcon(ModBlocks.RPM_MILLSTONE.get())
                        .emptyBackground(177, 70)
                        .build(
                                ResourceLocation.fromNamespaceAndPath(
                                        CreateRecipeNeedRPM.MOD_ID,
                                        "rpm_milling"
                                ),
                                RPMMillingCategory::new
                        );

        rpmPressing =
                new CreateRecipeCategory.Builder<>(
                        PressingRecipe.class
                )
                        .addTypedRecipes(ModRecipeTypes.RPM_PRESSING)
                        .catalyst(ModBlocks.RPM_MECHANICAL_PRESS::get)
                        .doubleItemIcon(
                                ModBlocks.RPM_MECHANICAL_PRESS.get(),
                                AllItems.IRON_SHEET.get()
                        )
                        .emptyBackground(177, 75)
                        .build(
                                ResourceLocation.fromNamespaceAndPath(
                                        CreateRecipeNeedRPM.MOD_ID,
                                        "rpm_pressing"
                                ),
                                RPMPressingCategory::new
                        );

        registration.addRecipeCategories(
                rpmMilling,
                rpmPressing
        );
    }

    @Override
    public void registerRecipes(
            IRecipeRegistration registration
    ) {
        rpmMilling.registerRecipes(registration);
        rpmPressing.registerRecipes(registration);
    }

    @Override
    public void registerRecipeCatalysts(
            IRecipeCatalystRegistration registration
    ) {
        rpmMilling.registerCatalysts(registration);
        rpmPressing.registerCatalysts(registration);
    }
}
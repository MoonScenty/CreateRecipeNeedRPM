package me.moonscenty.createrecipeneedrpm.integration.jei;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import me.moonscenty.createrecipeneedrpm.CreateRecipeNeedRPM;
import me.moonscenty.createrecipeneedrpm.registry.ModBlocks;
import me.moonscenty.createrecipeneedrpm.registry.ModItems;
import me.moonscenty.createrecipeneedrpm.registry.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

@JeiPlugin
public class CreateRecipeNeedRPMJEI implements IModPlugin {

    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(
                    CreateRecipeNeedRPM.MOD_ID,
                    "jei_plugin"
            );

    private static final mezz.jei.api.recipe.RecipeType<RecipeHolder<BasinRecipe>>
            CREATE_AUTOMATIC_PACKING =
            mezz.jei.api.recipe.RecipeType.createRecipeHolderType(
                    Create.asResource("automatic_packing")
            );
    private static final mezz.jei.api.recipe.RecipeType<RecipeHolder<BasinRecipe>>
            CREATE_AUTOMATIC_SHAPELESS =
            mezz.jei.api.recipe.RecipeType.createRecipeHolderType(
                    Create.asResource("automatic_shapeless")
            );

    private static final mezz.jei.api.recipe.RecipeType<RecipeHolder<BasinRecipe>>
            CREATE_AUTOMATIC_BREWING =
            mezz.jei.api.recipe.RecipeType.createRecipeHolderType(
                    Create.asResource("automatic_brewing")
            );
    private CreateRecipeCategory<AbstractCrushingRecipe> rpmMilling;
    private CreateRecipeCategory<PressingRecipe> rpmPressing;
    private CreateRecipeCategory<BasinRecipe> rpmCompacting;
    private CreateRecipeCategory<BasinRecipe> rpmMixing;
    private CreateRecipeCategory<AbstractCrushingRecipe> rpmCrushing;

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
        rpmCompacting =
                new CreateRecipeCategory.Builder<>(
                        BasinRecipe.class
                )
                        .addTypedRecipes(ModRecipeTypes.RPM_COMPACTING)
                        .catalyst(ModBlocks.RPM_MECHANICAL_PRESS::get)
                        .catalyst(AllBlocks.BASIN::get)
                        .doubleItemIcon(
                                ModBlocks.RPM_MECHANICAL_PRESS.get(),
                                AllBlocks.BASIN.get()
                        )
                        .emptyBackground(177, 103)
                        .build(
                                ResourceLocation.fromNamespaceAndPath(
                                        CreateRecipeNeedRPM.MOD_ID,
                                        "rpm_compacting"
                                ),
                                RPMCompactingCategory::new
                        );
        rpmMixing =
                new CreateRecipeCategory.Builder<>(
                        BasinRecipe.class
                )
                        .addTypedRecipes(ModRecipeTypes.RPM_MIXING)
                        .catalyst(ModBlocks.RPM_MECHANICAL_MIXER::get)
                        .catalyst(AllBlocks.BASIN::get)
                        .doubleItemIcon(
                                ModBlocks.RPM_MECHANICAL_MIXER.get(),
                                AllBlocks.BASIN.get()
                        )
                        .emptyBackground(177, 103)
                        .build(
                                ResourceLocation.fromNamespaceAndPath(
                                        CreateRecipeNeedRPM.MOD_ID,
                                        "rpm_mixing"
                                ),
                                RPMMixingCategory::new
                        );
        rpmCrushing =
                new CreateRecipeCategory.Builder<>(
                        AbstractCrushingRecipe.class
                )
                        .addTypedRecipes(
                                ModRecipeTypes.RPM_CRUSHING
                        )
                        .catalyst(
                                ModBlocks.RPM_CRUSHING_WHEEL::get
                        )
                        .doubleItemIcon(
                                ModBlocks.RPM_CRUSHING_WHEEL.get(),
                                AllItems.CRUSHED_GOLD.get()
                        )
                        .emptyBackground(
                                177,
                                100
                        )
                        .build(
                                ResourceLocation.fromNamespaceAndPath(
                                        CreateRecipeNeedRPM.MOD_ID,
                                        "rpm_crushing"
                                ),
                                RPMCrushingCategory::new
                        );
        registration.addRecipeCategories(
                rpmMilling,
                rpmPressing,
                rpmCompacting,
                rpmMixing,
                rpmCrushing
        );
    }

    @Override
    public void registerRecipes(
            IRecipeRegistration registration
    ) {
        rpmMilling.registerRecipes(registration);
        rpmPressing.registerRecipes(registration);
        rpmCompacting.registerRecipes(registration);
        rpmMixing.registerRecipes(registration);
        rpmCrushing.registerRecipes(registration);
    }

    @Override
    public void registerRecipeCatalysts(
            IRecipeCatalystRegistration registration
    ) {
        rpmMilling.registerCatalysts(registration);
        rpmPressing.registerCatalysts(registration);
        rpmCompacting.registerCatalysts(registration);
        registration.addRecipeCatalyst(
                new ItemStack(ModItems.RPM_MECHANICAL_PRESS.get()),
                CREATE_AUTOMATIC_PACKING
        );

        rpmMixing.registerCatalysts(registration);
        registration.addRecipeCatalyst(
                new ItemStack(ModItems.RPM_MECHANICAL_MIXER.get()),
                CREATE_AUTOMATIC_SHAPELESS
        );

        registration.addRecipeCatalyst(
                new ItemStack(ModItems.RPM_MECHANICAL_MIXER.get()),
                CREATE_AUTOMATIC_BREWING
        );
        rpmCrushing.registerCatalysts(registration);
    }
}
package me.moonscenty.createrecipeneedrpm.integration.jei;

import com.simibubi.create.compat.jei.category.BasinCategory;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.compat.jei.category.animations.AnimatedPress;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import me.moonscenty.createrecipeneedrpm.recipe.RPMCompactingRecipe;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class RPMCompactingCategory extends BasinCategory implements RPMCategoryHelper{

    private final AnimatedPress press = new AnimatedPress(true);
    private final AnimatedBlazeBurner heater = new AnimatedBlazeBurner();

    public RPMCompactingCategory(CreateRecipeCategory.Info<BasinRecipe> info) {
        super(info, true);
    }

    @Override
    public void draw(
            BasinRecipe recipe,
            IRecipeSlotsView recipeSlotsView,
            GuiGraphics graphics,
            double mouseX,
            double mouseY
    ) {
        // Create 기본 Basin JEI
        super.draw(recipe, recipeSlotsView, graphics, mouseX, mouseY);

        // Create PackingCategory.standard와 동일한 렌더링
        HeatCondition requiredHeat = recipe.getRequiredHeat();

        if (requiredHeat != HeatCondition.NONE) {
            heater.withHeat(requiredHeat.visualizeAsBlazeBurner())
                    .draw(
                            graphics,
                            getBackground().getWidth() / 2 + 3,
                            55
                    );
        }

        press.draw(
                graphics,
                getBackground().getWidth() / 2 + 3,
                34
        );

        // 우리 모드 추가 부분
        if (recipe instanceof RPMCompactingRecipe rpmRecipe) {

            drawRPM(
                    graphics,
                    rpmRecipe.getMinRPM(),
                    5,
                    6
            );
        }
    }

}
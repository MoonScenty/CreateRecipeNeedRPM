package me.moonscenty.createrecipeneedrpm.integration.jei.sequenced;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.animations.AnimatedPress;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import me.moonscenty.createrecipeneedrpm.integration.jei.RPMCategoryHelper;
import me.moonscenty.createrecipeneedrpm.recipe.RPMPressingRecipe;
import net.minecraft.client.gui.GuiGraphics;

public class RPMAssemblyPressing
        extends SequencedAssemblySubCategory
        implements RPMCategoryHelper {

    private final AnimatedPress press;

    public RPMAssemblyPressing() {
        // 기본 Create Pressing은 25인데
        // "128 RPM" 텍스트까지 들어가므로 조금 넓힘
        super(45);

        press = new AnimatedPress(false);
    }

    @Override
    public void draw(
            SequencedRecipe<?> recipe,
            GuiGraphics graphics,
            double mouseX,
            double mouseY,
            int index
    ) {
        PoseStack ms = graphics.pose();

        press.offset = index;

        ms.pushPose();
        ms.translate(-5, 50, 0);
        ms.scale(.6f, .6f, .6f);

        press.draw(
                graphics,
                getWidth() / 2,
                0
        );

        ms.popPose();

        if (!(recipe.getRecipe()
                instanceof RPMPressingRecipe rpmRecipe)) {
            return;
        }

        drawRPMStacked(
                graphics,
                rpmRecipe.getMinRPM(),
                getWidth() / 2,
                11
        );
    }
}
package me.moonscenty.createrecipeneedrpm.client;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.content.kinetics.millstone.MillstoneRenderer;
import com.simibubi.create.content.kinetics.press.MechanicalPressRenderer;
import com.simibubi.create.content.kinetics.press.PressVisual;
import com.simibubi.create.foundation.item.KineticStats;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import me.moonscenty.createrecipeneedrpm.CreateRecipeNeedRPM;
import me.moonscenty.createrecipeneedrpm.integration.ponder.CreateRecipeNeedRPMPonderPlugin;
import me.moonscenty.createrecipeneedrpm.registry.ModBlockEntities;
import me.moonscenty.createrecipeneedrpm.registry.ModItems;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(
        modid = CreateRecipeNeedRPM.MOD_ID,
        value = Dist.CLIENT
)
public final class ClientEvents {

    private ClientEvents() {
    }

    @SubscribeEvent
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntities.RPM_MILLSTONE.get(),
                context -> (BlockEntityRenderer) new MillstoneRenderer(context)
        );

        event.registerBlockEntityRenderer(
                ModBlockEntities.RPM_MECHANICAL_PRESS.get(),
                context ->
                        (BlockEntityRenderer)
                                new MechanicalPressRenderer(context)
        );
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {

        PonderIndex.addPlugin(
                new CreateRecipeNeedRPMPonderPlugin()
        );

        event.enqueueWork(() -> {
            SimpleBlockEntityVisualizer
                    .builder(ModBlockEntities.RPM_MILLSTONE.get())
                    .factory(
                            SingleAxisRotatingVisual.of(
                                    AllPartialModels.MILLSTONE_COG
                            )
                    )
                    .skipVanillaRender(blockEntity -> true)
                    .apply();

            SimpleBlockEntityVisualizer
                    .builder(ModBlockEntities.RPM_MECHANICAL_PRESS.get())
                    .factory(
                            (context, blockEntity, partialTick) ->
                                    new PressVisual(
                                            context,
                                            blockEntity,
                                            partialTick
                                    )
                    )
                    .skipVanillaRender(blockEntity -> false)
                    .apply();
        });
    }

    @SubscribeEvent
    public static void addItemTooltip(ItemTooltipEvent event) {

        Item item = event.getItemStack().getItem();

        if (item != ModItems.RPM_MILLSTONE.get()
                && item != ModItems.RPM_MECHANICAL_PRESS.get()) {
            return;
        }

        // Creative 검색 인덱스 생성 등의 상황에서는
        // 툴팁 이벤트에 Player가 없을 수 있음.
        if (event.getEntity() == null) {
            return;
        }

        KineticStats kineticStats =
                KineticStats.create(item);

        if (kineticStats != null) {
            kineticStats.modify(event);
        }
    }
}
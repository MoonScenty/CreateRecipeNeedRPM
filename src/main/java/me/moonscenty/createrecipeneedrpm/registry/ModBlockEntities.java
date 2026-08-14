package me.moonscenty.createrecipeneedrpm.registry;

import me.moonscenty.createrecipeneedrpm.CreateRecipeNeedRPM;
import me.moonscenty.createrecipeneedrpm.content.crusher.RPMCrushingWheelBlockEntity;
import me.moonscenty.createrecipeneedrpm.content.millstone.RPMMillstoneBlockEntity;
import me.moonscenty.createrecipeneedrpm.content.press.RPMMechanicalPressBlockEntity;
import me.moonscenty.createrecipeneedrpm.content.mixer.RPMMechanicalMixerBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public final class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(
                    Registries.BLOCK_ENTITY_TYPE,
                    CreateRecipeNeedRPM.MOD_ID
            );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RPMMillstoneBlockEntity>>
            RPM_MILLSTONE =
            BLOCK_ENTITY_TYPES.register(
                    "rpm_millstone",
                    () -> BlockEntityType.Builder.of(
                            RPMMillstoneBlockEntity::new,
                            ModBlocks.RPM_MILLSTONE.get()
                    ).build(null)
            );
    public static final DeferredHolder<
            BlockEntityType<?>,
            BlockEntityType<RPMMechanicalPressBlockEntity>
            > RPM_MECHANICAL_PRESS =
            BLOCK_ENTITY_TYPES.register(
                    "rpm_mechanical_press",
                    () -> BlockEntityType.Builder.of(
                            RPMMechanicalPressBlockEntity::new,
                            ModBlocks.RPM_MECHANICAL_PRESS.get()
                    ).build(null)
            );

    public static final DeferredHolder<
            BlockEntityType<?>,
            BlockEntityType<RPMMechanicalMixerBlockEntity>
            > RPM_MECHANICAL_MIXER =
            BLOCK_ENTITY_TYPES.register(
                    "rpm_mechanical_mixer",
                    () -> BlockEntityType.Builder
                            .of(
                                    RPMMechanicalMixerBlockEntity::new,
                                    ModBlocks.RPM_MECHANICAL_MIXER.get()
                            )
                            .build(null)
            );
    public static final DeferredHolder<
            BlockEntityType<?>,
            BlockEntityType<RPMCrushingWheelBlockEntity>
            > RPM_CRUSHING_WHEEL =
            BLOCK_ENTITY_TYPES.register(
                    "rpm_crushing_wheel",
                    () -> BlockEntityType.Builder
                            .of(
                                    RPMCrushingWheelBlockEntity::new,
                                    ModBlocks.RPM_CRUSHING_WHEEL.get()
                            )
                            .build(null)
            );

    private ModBlockEntities() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(ModBlockEntities::registerCapabilities);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                RPM_MILLSTONE.get(),
                (blockEntity, context) -> blockEntity.capability
        );
    }
}
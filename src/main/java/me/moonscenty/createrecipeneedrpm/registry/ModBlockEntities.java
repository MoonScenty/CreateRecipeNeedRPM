package me.moonscenty.createrecipeneedrpm.registry;

import me.moonscenty.createrecipeneedrpm.CreateRecipeNeedRPM;
import me.moonscenty.createrecipeneedrpm.content.millstone.RPMMillstoneBlockEntity;
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
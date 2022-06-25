package net.immortaldevs.cumspray;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.immortaldevs.cumspray.util.CUMmandHandler;
import net.minecraft.client.sound.Sound;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import static net.immortaldevs.cumspray.CUMponentInit.CUM_COATED;

public final class CumSpray implements ModInitializer {
    public static final String CUMSPRAY = "cumspray";

    @Override
    public void onInitialize() {
        CumSprayItems.init();
        CumSprayRecipeSerialisers.init();
        CumSpraySoundEvents.init();
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            ItemStack stackInHand = player.getStackInHand(hand);
            if (stackInHand.isOf(Items.SPONGE)) {
                if (entity.getComponent(CUM_COATED).cum_coated) {
                    entity.getComponent(CUM_COATED).cum_coated = false;
                    CUM_COATED.sync(entity);
                    world.playSound(null, entity.getBlockPos(), SoundEvents.BLOCK_WOOL_HIT, SoundCategory.PLAYERS, 1, 1);
                    return ActionResult.SUCCESS;
                }
            }
            if (stackInHand.isOf(Items.GLASS_BOTTLE)) {
                world.playSound(null, entity.getBlockPos(), CumSpraySoundEvents.ENTITY_WANDERING_TRADER_EJACULATE, SoundCategory.PLAYERS, 1, 1);
                if (!player.isCreative()) {
                    stackInHand.decrement(1);
                    player.giveItemStack(new ItemStack(CumSprayItems.CUM_SPRAY));
                }
                return ActionResult.SUCCESS;
            }
            return ActionResult.FAIL;
        });
        CUMmandHandler.register();
    }

    public static Identifier id(String path) {
        return new Identifier(CUMSPRAY, path);
    }
}

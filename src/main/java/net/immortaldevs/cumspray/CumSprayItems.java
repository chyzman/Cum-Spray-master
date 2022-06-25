package net.immortaldevs.cumspray;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;
import org.checkerframework.checker.units.qual.C;

import static net.immortaldevs.cumspray.CUMponentInit.CUM_COATED;
import static net.immortaldevs.cumspray.CumSpray.id;

public final class CumSprayItems {
    public static final Item CUM_SPRAY = new Item(new Item.Settings().group(ItemGroup.TOOLS).recipeRemainder(Items.GLASS_BOTTLE)) {
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            if (!user.world.isClient && !entity.getComponent(CUM_COATED).cum_coated) {
                entity.getComponent(CUM_COATED).cum_coated = true;
                user.getWorld().playSound(null, entity.getBlockPos(), SoundEvents.BLOCK_SLIME_BLOCK_BREAK, SoundCategory.PLAYERS, 1, 1);
                if (!user.isCreative()){
                    user.getMainHandStack().decrement(1);
                    user.giveItemStack(new ItemStack(Items.GLASS_BOTTLE));
                }
                CUM_COATED.sync(entity);
                return ActionResult.SUCCESS;
            }
            return ActionResult.FAIL;
        }
    };

    public static void init() {
        Registry.register(Registry.ITEM, id("cum_spray"), CUM_SPRAY);
    }
}

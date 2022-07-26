package me.roundaround.axolotlbuckets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.roundaround.axolotlbuckets.mixin.EntityBucketItemAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public final class AxolotlBucketsMod implements ClientModInitializer {
  public static final String MOD_ID = "axolotlbuckets";
  public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

  private static final MinecraftClient MINECRAFT = MinecraftClient.getInstance();

  @Override
  public void onInitializeClient() {
    ModelPredicateProviderRegistry.register(Items.AXOLOTL_BUCKET, new Identifier("variant"),
        (itemStack, world, holder, seed) -> {
          Item item = itemStack.getItem();
          if (!(item instanceof EntityBucketItem)) {
            return 0f;
          }

          Entity entity = ((EntityBucketItemAccessor) item).getEntityType().create(MINECRAFT.world);
          if (!(entity instanceof AxolotlEntity) || entity == null) {
            return 0f;
          }

          ((Bucketable) entity).copyDataFromNbt(itemStack.getOrCreateNbt());

          return ((AxolotlEntity) entity).getVariant().ordinal() / 10f;
        });

    ModelPredicateProviderRegistry.register(Items.AXOLOTL_BUCKET, new Identifier("baby"),
        (itemStack, world, holder, seed) -> {
          Item item = itemStack.getItem();
          if (!(item instanceof EntityBucketItem)) {
            return 0f;
          }

          Entity entity = ((EntityBucketItemAccessor) item).getEntityType().create(MINECRAFT.world);
          if (!(entity instanceof AxolotlEntity) || entity == null) {
            return 0f;
          }

          ((Bucketable) entity).copyDataFromNbt(itemStack.getOrCreateNbt());

          return ((LivingEntity) entity).isBaby() ? 1f : 0f;
        });

    FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent((container) -> {
      ResourceManagerHelper.registerBuiltinResourcePack(
          new Identifier(MOD_ID, "axolotl-buckets-small"),
          container,
          "Smaller Axolotl Buckets",
          ResourcePackActivationType.NORMAL);
    });
  }
}

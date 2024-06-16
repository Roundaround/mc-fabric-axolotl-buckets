package me.roundaround.axolotlbuckets;

import me.roundaround.axolotlbuckets.mixin.EntityBucketItemAccessor;
import me.roundaround.axolotlbuckets.mixin.ModelPredicateProviderRegistryAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class AxolotlBucketsMod implements ClientModInitializer {
  public static final String MOD_ID = "axolotlbuckets";
  public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

  private static final MinecraftClient MINECRAFT = MinecraftClient.getInstance();

  @Override
  public void onInitializeClient() {
    ModelPredicateProviderRegistryAccessor.invokeRegister(Items.AXOLOTL_BUCKET,
        Identifier.of("variant"),
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

    ModelPredicateProviderRegistryAccessor.invokeRegister(Items.AXOLOTL_BUCKET,
        Identifier.of("baby"),
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
      ResourceManagerHelper.registerBuiltinResourcePack(Identifier.of(MOD_ID,
              "axolotl-buckets-small"),
          container,
          Text.literal("Smaller Axolotl Buckets"),
          ResourcePackActivationType.NORMAL);
    });
  }
}

package me.roundaround.axolotlbuckets;

import me.roundaround.axolotlbuckets.mixin.EntityBucketItemAccessor;
import me.roundaround.axolotlbuckets.mixin.ModelPredicateProviderRegistryAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class AxolotlBucketsMod implements ClientModInitializer {
  public static final String MOD_ID = "axolotlbuckets";

  private static final MinecraftClient MINECRAFT = MinecraftClient.getInstance();

  @Override
  public void onInitializeClient() {
    ModelPredicateProviderRegistryAccessor.invokeRegister(Items.AXOLOTL_BUCKET, new Identifier("variant"),
        (itemStack, world, holder, seed) -> {
          AxolotlEntity axolotl = getAxolotlRef(itemStack, world, holder, seed);
          if (axolotl == null) {
            return 0f;
          }

          return axolotl.getVariant().ordinal() / 10f;
        }
    );

    ModelPredicateProviderRegistryAccessor.invokeRegister(Items.AXOLOTL_BUCKET, new Identifier("baby"),
        (itemStack, world, holder, seed) -> {
          AxolotlEntity axolotl = getAxolotlRef(itemStack, world, holder, seed);
          if (axolotl == null) {
            return 0f;
          }

          return axolotl.isBaby() ? 1f : 0f;
        }
    );

    FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent((container) -> {
      ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(MOD_ID, "axolotl-buckets-small"), container,
          Text.literal("Smaller Axolotl Buckets"), ResourcePackActivationType.NORMAL
      );
    });
  }

  private static AxolotlEntity getAxolotlRef(ItemStack itemStack, ClientWorld world, LivingEntity holder, int seed) {
    Item item = itemStack.getItem();
    if (!(item instanceof EntityBucketItem)) {
      return null;
    }

    Entity entity = ((EntityBucketItemAccessor) item).getEntityType().create(MINECRAFT.world);
    if (!(entity instanceof AxolotlEntity axolotl)) {
      return null;
    }

    NbtComponent component = itemStack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT);
    axolotl.copyDataFromNbt(component.copyNbt());
    return axolotl;
  }
}

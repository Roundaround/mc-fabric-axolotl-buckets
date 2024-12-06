package me.roundaround.axolotlbuckets;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

public class AxolotlBucketsMod implements ModInitializer {
  public static final String MOD_ID = "axolotlbuckets";
  public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

  private static final String NBT_VARIANT = "Variant";
  private static final String NBT_AGE = "Age";

  @Override
  public void onInitialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((entries) -> {
      HashMap<AxolotlEntity.Variant, Identifier> ids = getMavapiIds();

      entries.addAfter(
          Items.AXOLOTL_BUCKET,
          Stream.of(false, true).flatMap((baby) -> Arrays.stream(AxolotlEntity.Variant.values()).map((variant) -> {
            if (variant.equals(AxolotlEntity.Variant.LUCY) && !baby) {
              return null;
            }
            return getVariantStack(ids, variant, baby);
          })).filter(Objects::nonNull).toList()
      );
    });
  }

  private static HashMap<AxolotlEntity.Variant, Identifier> getMavapiIds() {
    if (!FabricLoader.getInstance().isModLoaded("mavapi")) {
      return null;
    }

    try {
      HashMap<AxolotlEntity.Variant, Identifier> ids = new HashMap<>();
      for (AxolotlEntity.Variant variant : AxolotlEntity.Variant.values()) {
        Class<?> axolotlTypeExtensionClass = variant.getClass();
        Object metadata = axolotlTypeExtensionClass.getMethod("mavapi$metadata").invoke(variant);
        Object id = metadata.getClass().getMethod("getId").invoke(metadata);
        ids.put(variant, (Identifier) id);
      }
      return ids;
    } catch (Exception e) {
      LOGGER.warn("Failed iterating mavapi variant metadata when trying to populate item group entries.", e);
    }

    return null;
  }

  private static ItemStack getVariantStack(
      HashMap<AxolotlEntity.Variant, Identifier> mavapiIds, AxolotlEntity.Variant variant, boolean baby
  ) {
    ItemStack stack = new ItemStack(Items.AXOLOTL_BUCKET);
    NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, stack, (nbt) -> {
      if (mavapiIds != null && mavapiIds.containsKey(variant)) {
        nbt.putString(NBT_VARIANT, mavapiIds.get(variant).toString());
      } else {
        nbt.putInt(NBT_VARIANT, variant.getId());
      }

      if (baby) {
        nbt.putInt(NBT_AGE, PassiveEntity.BABY_AGE);
      }
    });
    return stack;
  }
}

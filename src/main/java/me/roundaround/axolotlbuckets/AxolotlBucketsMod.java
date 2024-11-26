package me.roundaround.axolotlbuckets;

import me.roundaround.roundalib.util.BuiltinResourcePack;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class AxolotlBucketsMod implements ClientModInitializer {
  public static final String MOD_ID = "axolotlbuckets";

  @Override
  public void onInitializeClient() {
    ModelPredicateProviderRegistry.register(Items.AXOLOTL_BUCKET, Identifier.ofVanilla("variant"),
        (itemStack, world, holder, seed) -> getEntityNbtInt(itemStack, "Variant") / 10f
    );

    ModelPredicateProviderRegistry.register(Items.AXOLOTL_BUCKET, Identifier.ofVanilla("baby"),
        (itemStack, world, holder, seed) -> getEntityNbtInt(itemStack, "Age") >= 0 ? 1f : 0f
    );

    BuiltinResourcePack.register(MOD_ID, "axolotl-buckets-small", Text.translatable("axolotlbuckets.resource.smaller"));
  }

  private static int getEntityNbtInt(ItemStack stack, String key) {
    NbtCompound nbt = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT).copyNbt();
    return nbt.getInt(key);
  }
}

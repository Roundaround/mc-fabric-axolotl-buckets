package me.roundaround.axolotlbuckets.client;

import me.roundaround.axolotlbuckets.client.render.item.property.bool.BabyProperty;
import me.roundaround.axolotlbuckets.generated.Constants;
import me.roundaround.axolotlbuckets.roundalib.util.BuiltinResourcePack;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.text.Text;

public final class AxolotlBucketsClientMod implements ClientModInitializer {
  public static final String RESOURCE_PACK_ID = "axolotl-buckets-small";

  @Override
  public void onInitializeClient() {
    BabyProperty.register();

    BuiltinResourcePack.register(
        Constants.MOD_ID,
        RESOURCE_PACK_ID,
        Text.translatable("axolotlbuckets.resource.smaller")
    );
  }
}

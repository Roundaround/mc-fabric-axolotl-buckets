package me.roundaround.axolotlbuckets.client;

import me.roundaround.roundalib.util.BuiltinResourcePack;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class AxolotlBucketsClientMod implements ClientModInitializer {
  public static final String MOD_ID = "axolotlbuckets";
  public static final String RESOURCE_PACK_ID = "axolotl-buckets-small";

  @Override
  public void onInitializeClient() {
    SelectProperties.ID_MAPPER.put(Identifier.of(MOD_ID, "variant"), AxolotlVariantProperty.TYPE);
    BooleanProperties.ID_MAPPER.put(Identifier.of(MOD_ID, "baby"), BabyProperty.CODEC);

    BuiltinResourcePack.register(MOD_ID, RESOURCE_PACK_ID, Text.translatable("axolotlbuckets.resource.smaller"));
  }
}

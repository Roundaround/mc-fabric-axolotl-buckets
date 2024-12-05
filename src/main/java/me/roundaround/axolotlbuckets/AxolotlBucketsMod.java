package me.roundaround.axolotlbuckets;

import me.roundaround.roundalib.util.BuiltinResourcePack;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class AxolotlBucketsMod implements ClientModInitializer {
  public static final String MOD_ID = "axolotlbuckets";

  @Override
  public void onInitializeClient() {
    SelectProperties.ID_MAPPER.put(Identifier.ofVanilla("variant"), AxolotlVariantProperty.TYPE);
    BooleanProperties.ID_MAPPER.put(Identifier.ofVanilla("age"), BabyProperty.CODEC);

    BuiltinResourcePack.register(MOD_ID, "axolotl-buckets-small", Text.translatable("axolotlbuckets.resource.smaller"));
  }
}

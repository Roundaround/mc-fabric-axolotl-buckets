package me.roundaround.axolotlbuckets.client.data;

import me.roundaround.axolotlbuckets.client.AxolotlBucketsClientMod;
import me.roundaround.axolotlbuckets.generated.Constants;
import me.roundaround.gradle.api.annotation.Entrypoint;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.util.Identifier;

@Entrypoint("fabric-datagen")
public class AxolotlBucketsClientDataGenerator implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator generator) {
    final FabricDataGenerator.Pack pack = generator.createPack();
    pack.addProvider((FabricDataOutput output) -> new AxolotlBucketItemModelGenerator(output, false));

    final FabricDataGenerator.Pack resourcePack = generator.createBuiltinResourcePack(Identifier.of(
        Constants.MOD_ID,
        AxolotlBucketsClientMod.RESOURCE_PACK_ID
    ));
    resourcePack.addProvider((FabricDataOutput output) -> new AxolotlBucketItemModelGenerator(output, true));
  }
}

package me.roundaround.axolotlbuckets.client.data;

import me.roundaround.axolotlbuckets.client.AxolotlBucketsClientMod;
import me.roundaround.axolotlbuckets.client.AxolotlVariantProperty;
import me.roundaround.axolotlbuckets.client.BabyProperty;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.data.DataWriter;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class AxolotlBucketItemModelGenerator extends FabricModelProvider {
  private final boolean small;

  public AxolotlBucketItemModelGenerator(FabricDataOutput output, boolean small) {
    super(output);
    this.small = small;
  }

  @Override
  public CompletableFuture<?> run(DataWriter writer) {
    return super.run(writer);
  }

  @Override
  public void generateItemModels(ItemModelGenerator generator) {
    Item item = Items.AXOLOTL_BUCKET;
    Identifier modelId = ModelIds.getItemModelId(item);
    ItemModel.Unbaked unbaked = ItemModels.basic(modelId);

    Model model = Models.GENERATED;
    if (this.small) {
      model = new Model(Optional.of(Identifier.of(AxolotlBucketsClientMod.MOD_ID, "item/smaller_util")), Optional.empty(),
          TextureKey.LAYER0
      );
    }

    ArrayList<SelectItemModel.SwitchCase<AxolotlEntity.Variant>> adultSwitchCases = new ArrayList<>();
    ArrayList<SelectItemModel.SwitchCase<AxolotlEntity.Variant>> babySwitchCases = new ArrayList<>();

    for (AxolotlEntity.Variant variant : AxolotlEntity.Variant.values()) {
      adultSwitchCases.add(ItemModels.switchCase(variant,
          ItemModels.basic(generator.registerSubModel(item, "_" + variant.getName(), model))
      ));
      babySwitchCases.add(ItemModels.switchCase(variant,
          ItemModels.basic(generator.registerSubModel(item, "_" + variant.getName() + "_baby", model))
      ));
    }

    if (!this.small) {
      generator.output.accept(item, ItemModels.condition(new BabyProperty(),
          ItemModels.select(new AxolotlVariantProperty(), unbaked, babySwitchCases),
          ItemModels.select(new AxolotlVariantProperty(), unbaked, adultSwitchCases)
      ));
    }
  }

  @Override
  public void generateBlockStateModels(BlockStateModelGenerator generator) {
  }
}

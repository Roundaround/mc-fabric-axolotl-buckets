package me.roundaround.axolotlbuckets.data;

import me.roundaround.axolotlbuckets.AxolotlVariantProperty;
import me.roundaround.axolotlbuckets.BabyProperty;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class AxolotlBucketItemModelGenerator extends FabricModelProvider {
  public AxolotlBucketItemModelGenerator(FabricDataOutput output) {
    super(output);
  }

  @Override
  public void generateItemModels(ItemModelGenerator generator) {
    Item item = Items.AXOLOTL_BUCKET;
    Identifier modelId = ModelIds.getItemModelId(item);
    ItemModel.Unbaked unbaked = ItemModels.basic(modelId);

    ArrayList<SelectItemModel.SwitchCase<AxolotlEntity.Variant>> adultSwitchCases = new ArrayList<>();
    ArrayList<SelectItemModel.SwitchCase<AxolotlEntity.Variant>> babySwitchCases = new ArrayList<>();

    for (AxolotlEntity.Variant variant : AxolotlEntity.Variant.values()) {
      adultSwitchCases.add(ItemModels.switchCase(variant,
          ItemModels.basic(generator.registerSubModel(item, "_" + variant.getName(), Models.GENERATED))
      ));
      babySwitchCases.add(ItemModels.switchCase(variant,
          ItemModels.basic(generator.registerSubModel(item, "_" + variant.getName() + "_baby", Models.GENERATED))
      ));
    }

    generator.output.accept(item, ItemModels.condition(new BabyProperty(),
        ItemModels.select(new AxolotlVariantProperty(), unbaked, babySwitchCases),
        ItemModels.select(new AxolotlVariantProperty(), unbaked, adultSwitchCases)
    ));
  }

  @Override
  public void generateBlockStateModels(BlockStateModelGenerator generator) {
  }
}

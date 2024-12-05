package me.roundaround.axolotlbuckets;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.Arrays;
import java.util.Objects;

public record AxolotlVariantProperty() implements SelectProperty<AxolotlEntity.Variant> {
  private static final String NBT_VARIANT = "Variant";

  public static final Type<AxolotlVariantProperty, AxolotlEntity.Variant> TYPE = SelectProperty.Type.create(
      MapCodec.unit(new AxolotlVariantProperty()), AxolotlEntity.Variant.CODEC);

  @Override
  public AxolotlEntity.Variant getValue(
      ItemStack stack, ClientWorld world, LivingEntity user, int seed, ModelTransformationMode modelTransformationMode
  ) {
    NbtCompound nbt = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT).copyNbt();

    // Int type means vanilla
    if (nbt.contains(NBT_VARIANT, NbtElement.INT_TYPE)) {
      return AxolotlEntity.Variant.byId(nbt.getInt(NBT_VARIANT));
    }

    // String type means MoreAxolotlVariantsAPI (https://github.com/AkashiiKun/MoreAxolotlVariantsAPI-Common)
    if (nbt.contains(NBT_VARIANT, NbtElement.STRING_TYPE)) {
      String name = nbt.getString(NBT_VARIANT);
      return Arrays.stream(AxolotlEntity.Variant.values())
          .filter((variant) -> Objects.equals(variant.getName(), name))
          .findFirst()
          .orElse(AxolotlEntity.Variant.LUCY);
    }

    return null;
  }

  @Override
  public Type<? extends SelectProperty<AxolotlEntity.Variant>, AxolotlEntity.Variant> getType() {
    return TYPE;
  }
}

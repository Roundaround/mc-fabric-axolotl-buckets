package me.roundaround.axolotlbuckets.client.render.item.property.bool;

import com.mojang.serialization.MapCodec;
import me.roundaround.axolotlbuckets.AxolotlBucketsMod;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public record BabyProperty() implements BooleanProperty {
  private static final String NBT_AGE = "Age";

  public static final MapCodec<BabyProperty> CODEC = MapCodec.unit(new BabyProperty());

  @Override
  public boolean getValue(
      ItemStack stack, ClientWorld world, LivingEntity user, int seed, ModelTransformationMode modelTransformationMode
  ) {
    NbtCompound nbt = stack.getOrDefault(DataComponentTypes.BUCKET_ENTITY_DATA, NbtComponent.DEFAULT).copyNbt();
    return nbt.getInt(NBT_AGE) < 0;
  }

  @Override
  public MapCodec<? extends BooleanProperty> getCodec() {
    return CODEC;
  }

  public static void register() {
    BooleanProperties.ID_MAPPER.put(Identifier.of(AxolotlBucketsMod.MOD_ID, "baby"), CODEC);
  }
}

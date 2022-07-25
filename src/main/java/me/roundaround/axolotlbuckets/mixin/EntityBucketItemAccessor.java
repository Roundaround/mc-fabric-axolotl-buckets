package me.roundaround.axolotlbuckets.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.EntityType;
import net.minecraft.item.EntityBucketItem;

@Mixin(EntityBucketItem.class)
public interface EntityBucketItemAccessor {
  @Accessor
  public EntityType<?> getEntityType();
}

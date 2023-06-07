package me.roundaround.axolotlbuckets.mixin;

import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ModelPredicateProviderRegistry.class)
public interface ModelPredicateProviderRegistryAccessor {
  @Invoker("register")
  static void invokeRegister(
      Item item, Identifier identifier, ClampedModelPredicateProvider modelPredicateProvider) {
    throw new AssertionError();
  }
}

package purplecreate.theatricalwb.mixin;

import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import purplecreate.theatricalwb.mixininterfaces.IIngredient;
import purplecreate.theatricalwb.util.IngredientWithCount;

@Mixin(Ingredient.class)
public class IngredientMixin implements IIngredient {
  @Shadow @Nullable private ItemStack[] itemStacks;

  @Inject(method = "valueFromJson", at = @At("HEAD"), cancellable = true)
  private static void wb$valueFromJson(JsonObject json, CallbackInfoReturnable<Ingredient.Value> cir) {
    if (json.has(IngredientWithCount.countKey)) {
      cir.setReturnValue(IngredientWithCount.valueFromJson(json));
    }
  }

  @Override
  @Unique
  public void wb$resetCache() {
    itemStacks = null;
  }
}

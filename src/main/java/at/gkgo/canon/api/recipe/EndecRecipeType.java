package at.gkgo.canon.api.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;

@FunctionalInterface
public interface EndecRecipeType<R extends Recipe<?>> extends RecipeType<R>, EndecRecipeSerializer<R> {
}

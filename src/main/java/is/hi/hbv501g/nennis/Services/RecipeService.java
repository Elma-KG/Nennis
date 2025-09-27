package is.hi.hbv501g.nennis.Services;

import is.hi.hbv501g.nennis.dto.RecipeDto;
import is.hi.hbv501g.nennis.Api.RecipeFilters;
import java.util.List;
import java.util.UUID;

public interface RecipeService {
    List<RecipeDto> getAll(RecipeFilters filters);
    RecipeDto getRecipeById(UUID recipeId);
    List<RecipeDto> search(String query, RecipeFilters filters);
    RecipeDto addRecipe(RecipeDto dto);
    RecipeDto updateRecipe(UUID id, RecipeDto dto);
    void deleteRecipe(UUID recipeId);
}
package is.hi.hbv501g.nennis.Services;

import is.hi.hbv501g.nennis.dto.RecipeDTO;
import is.hi.hbv501g.nennis.api.RecipeFilters;
import java.util.List;
import java.util.UUID;

public interface RecipeService {
    List<RecipeDTO> getAll(RecipeFilters filters);
    RecipeDTO getRecipeById(UUID recipeId);
    List<RecipeDTO> search(String query, RecipeFilters filters);
    RecipeDTO addRecipe(RecipeDTO dto);
    RecipeDTO updateRecipe(UUID id, RecipeDTO dto);
    void deleteRecipe(UUID recipeId);
}
package is.hi.hbv501g.nennis.Services;

import is.hi.hbv501g.nennis.Api.RecipeDTO;
import is.hi.hbv501g.nennis.Api.RecipeFilters;
import is.hi.hbv501g.nennis.Persistence.Entities.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> findByName(String name);
    List<Recipe> findAll();
    Recipe findByID(long ID);
    List<Recipe> findByCategories(String categories);
    Recipe save(Recipe recipe);
    void delete(Long recipe);

    RecipeDTO update(Long id, RecipeDTO dto);

    List<RecipeDTO> search(String q, RecipeFilters filters);

    List<RecipeDTO> getAll(RecipeFilters filters);
}

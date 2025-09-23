package is.hi.hbv501g.nennis.Services;

import java.util.List;

public interface RecipeService {
    List<Recipe> findByName(String name);
    List<Recipe> findAll();
    Recipe findByID(long ID);
    List<Recipe> findByCategories(String categories);
    Recipe save(Recipe recipe);
    void delete(Recipe recipe);
}

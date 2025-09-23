package is.hi.hbv501g.nennis.Api;

import is.hi.hbv501g.nennis.Enums.Allergen;
import is.hi.hbv501g.nennis.Enums.Diet;

import java.util.Set;
import java.util.UUID;

public class RecipeDTO {
    public UUID recipeId;
    public String title;
    public String description;
    public Diet dietCode;
    public Set<Integer> tagIds;
    public Set<Allergen> allergens;
}

package is.hi.hbv501g.nennis.Api;

import is.hi.hbv501g.nennis.Enums.Allergen;
import is.hi.hbv501g.nennis.Enums.Diet;

import java.util.Set;

public class RecipeFilters {
    public String query;
    public Diet diet;
    public Set<Integer> tagIds;
    public Set<Allergen> excludeAllergens;
}

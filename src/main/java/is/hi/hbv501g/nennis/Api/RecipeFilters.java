package is.hi.hbv501g.nennis.Api;

import is.hi.hbv501g.nennis.Persistence.Entities.Allergen;
import is.hi.hbv501g.nennis.Persistence.Entities.Diet;

import java.util.Set;
import java.util.UUID;

public class RecipeFilters {
    public String query;
    public Diet diet;
    public Set<UUID> tagIds;
    public Set<Allergen> excludeAllergens;;
}

package is.hi.hbv501g.nennis.dto;

import is.hi.hbv501g.nennis.persistence.entities.Allergen;
import is.hi.hbv501g.nennis.persistence.entities.Diet;

import java.util.Set;
import java.util.UUID;

public class RecipeDto {
    public UUID recipeId;
    public String title;
    public String description;
    public Diet dietCode;
    public Set<Long> tagIds;
    public Set<Allergen> allergens;
}

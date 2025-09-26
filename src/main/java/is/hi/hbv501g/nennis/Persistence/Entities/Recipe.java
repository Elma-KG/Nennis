package is.hi.hbv501g.nennis.persistence.entities;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue
    private UUID recipeId;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Diet dietCode;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Allergen> allergens = new HashSet<>();

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Diet getDietCode() {
        return dietCode;
    }

    public void setDietCode(Diet dietCode) {
        this.dietCode = dietCode;
    }

    public Set<Allergen> getAllergens() {
        return allergens;
    }

    public void setAllergens(Set<Allergen> allergens) {
        this.allergens = allergens;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public UUID getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(UUID recipeId) {
        this.recipeId = recipeId;
    }
}

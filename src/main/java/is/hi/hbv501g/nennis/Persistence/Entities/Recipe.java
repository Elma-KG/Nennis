package is.hi.hbv501g.nennis.Persistence.Entities;

import is.hi.hbv501g.nennis.Enums.Allergen;
import is.hi.hbv501g.nennis.Enums.Diet;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @Column(name = "recipe_id", nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID recipeId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "diet_code", nullable = false)
    private Diet dietCode = Diet.NONE;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipe_tags",
            joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags = new HashSet<>();

    @ElementCollection(targetClass = Allergen.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "recipe_allergens", joinColumns = @JoinColumn(name = "recipe_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "allergen")
    private Set<Allergen> allergens = new HashSet<>();

    public Recipe() {}

    public Recipe(UUID recipeId, String title, String description, Diet dietCode, Set<Tag> tags, Set<Allergen> allergens) {
        this.recipeId = recipeId != null ? recipeId : UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.dietCode = dietCode == null ? Diet.NONE : dietCode;
        if (tags != null) this.tags = tags;
        if (allergens != null) this.allergens = allergens;
    }

    @PrePersist
    public void ensureId() {
        if (recipeId == null) recipeId = UUID.randomUUID();
    }


    public UUID getRecipeId() { return recipeId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Diet getDietCode() { return dietCode; }
    public Set<Allergen> getAllergens() { return allergens; }
    public Set<Tag> getTags() { return tags; }
    public void setRecipeId(UUID recipeId) { this.recipeId = recipeId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDietCode(Diet dietCode) { this.dietCode = (dietCode == null ? Diet.NONE : dietCode); }
    public void setAllergens(Set<Allergen> allergens) { this.allergens = (allergens == null ? new HashSet<>() : allergens); }
    public void setTags(Set<Tag> tags) { this.tags = (tags == null ? new HashSet<>() : tags); }


    public boolean hasTag(Tag tag) { return tag != null && tags.contains(tag); }
    public boolean fitsDiet(Diet diet) { return diet == null || diet == Diet.NONE || this.dietCode == diet; }
    public boolean isSafeFor(Set<Allergen> avoid) {
        if (avoid == null || avoid.isEmpty()) return true;
        for (Allergen a : avoid) {
            if (this.allergens.contains(a)) return false;
        }
        return true;
    }
}
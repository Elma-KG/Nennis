package is.hi.hbv501g.nennis.Persistence.Entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Map;

@Entity
@Table(name = "recipe")
public class RecipeService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    private String text;

    @Column(name = "rating")
    private int rating;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "vegetarian")
    private boolean vegetarian;

    @Column(name = "vegan")
    private boolean vegan;

    @Column(name = "dairy_free")
    private boolean dairyFree;

    @Column(name = "gluten_free")
    private boolean glutenFree;

    @Column(name = "servings")
    private int servings;

    @Column(name = "categories")
    private String categories;

    public RecipeService() {
    }

    public RecipeService(Long userId, Long id, String name, String text, int rating, String difficulty, boolean vegetarian,
                  boolean vegan, boolean dairyFree, boolean glutenFree, int servings, String categories,
                  Map<String, String> ingredients, String tempIngred, String tempAmount) {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.text = text;
        this.rating = rating;
        this.difficulty = difficulty;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.dairyFree = dairyFree;
        this.glutenFree = glutenFree;
        this.categories = categories;
    }

    @JsonCreator
    public RecipeService(@JsonProperty("userId") Long userId,
                  @JsonProperty("name") String name,
                  @JsonProperty("text") String text,
                  @JsonProperty("rating") int rating,
                  @JsonProperty("difficulty") String difficulty,
                  @JsonProperty("vegetarian") boolean vegetarian,
                  @JsonProperty("vegan") boolean vegan,
                  @JsonProperty("dairyFree") boolean dairyFree,
                  @JsonProperty("glutenFree") boolean glutenFree,
                  @JsonProperty("servings") int servings,
                  @JsonProperty("categories") String categories,
                  @JsonProperty("ingredients") Map<String, String> ingredients) {
        this.userId = userId;
        this.name = name;
        this.text = text;
        this.rating = rating;
        this.difficulty = difficulty;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.dairyFree = dairyFree;
        this.glutenFree = glutenFree;
        this.servings = servings;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public int getServings() {
        return servings;
    }

    public String getCategories() {
        return categories;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public void setDairyFree(boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}

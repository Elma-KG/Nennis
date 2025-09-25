package is.hi.hbv501g.nennis.Services.Implementation;

import is.hi.hbv501g.nennis.api.RecipeFilters;
import is.hi.hbv501g.nennis.Persistence.Entities.Allergen;
import is.hi.hbv501g.nennis.Persistence.Entities.Diet;
import is.hi.hbv501g.nennis.Persistence.Entities.Recipe;
import is.hi.hbv501g.nennis.Persistence.Entities.Tag;
import is.hi.hbv501g.nennis.Persistence.Repositories.RecipeRepository;
import is.hi.hbv501g.nennis.Services.RecipeService;
import is.hi.hbv501g.nennis.dto.RecipeDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeServiceImplementation implements RecipeService {

    private final RecipeRepository recipeRepo;

    @PersistenceContext
    private EntityManager em;

    public RecipeServiceImplementation(RecipeRepository recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    /**
     * Gets all recipes that match the given filters.
     * 
     * @param filters the filters to apply to the recipes. If null, no filters are applied.
     * @return a list of RecipeDTO objects that match the filters.
     */
    @Override
    public List<RecipeDTO> getAll(RecipeFilters filters) {
        if (filters == null)
            filters = new RecipeFilters();
        List<Recipe> all = recipeRepo.findAll(Sort.by(Sort.Direction.ASC, "title"));
        RecipeFilters finalFilters = filters;
        RecipeFilters finalFilters1 = filters;
        RecipeFilters finalFilters2 = filters;
        RecipeFilters finalFilters3 = filters;
        return all.stream()
                .filter(r -> matchesQuery(r, finalFilters.query))
                .filter(r -> matchesDiet(r, finalFilters1.diet))
                .filter(r -> matchesTags(r, finalFilters2.tagIds))
                .filter(r -> excludesAllergens(r, finalFilters3.excludeAllergens))
                .map(this::toDto)
                .toList();
    }



    @Override
    public RecipeDTO getRecipeById(UUID recipeId) {
        Recipe r = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found: " + recipeId));
        return toDto(r);
    }

    @Override
    public List<RecipeDTO> search(String query, RecipeFilters filters) {
        if (filters == null)
            filters = new RecipeFilters();
        filters.query = query;
        return getAll(filters);
    }

    @Override
    public RecipeDTO addRecipe(RecipeDTO dto) {
        Recipe r = new Recipe();
        copyFromDto(dto, r);
        Recipe saved = recipeRepo.save(r);
        return toDto(saved);
    }

    @Override
    public RecipeDTO updateRecipe(UUID id, RecipeDTO dto) {
        Recipe r = recipeRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found: " + id));
        copyFromDto(dto, r);
        Recipe saved = recipeRepo.save(r);
        return toDto(saved);
    }

    @Override
    public void deleteRecipe(UUID recipeId) {
        recipeRepo.deleteById(recipeId);
    }

    /**
     * Checks if the recipe matches the query
     * 
     * @param r
     * @param query
     * @return
     */
    private boolean matchesQuery(Recipe r, String query) {
        if (query == null || query.isBlank())
            return true;
        String q = query.toLowerCase(Locale.ROOT);
        String title = Optional.ofNullable(r.getTitle()).orElse("").toLowerCase(Locale.ROOT);
        String desc = Optional.ofNullable(r.getDescription()).orElse("").toLowerCase(Locale.ROOT);
        return title.contains(q) || desc.contains(q);
    }

    private boolean matchesDiet(Recipe r, Diet diet) {
        if (diet == null)
            return true;
        return diet.equals(r.getDietCode());
    }

    /**
     * Checks if the recipe matches the tags
     * 
     * @param r
     * @param requiredTagIds
     * @return
     */
    private boolean matchesTags(Recipe r, Set<UUID> requiredTagIds) {
        if (requiredTagIds == null || requiredTagIds.isEmpty())
            return true;
        Set<Long> recipeTagIds = Optional.ofNullable(r.getTags())
                .orElseGet(Set::of)
                .stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());
        return recipeTagIds.containsAll(requiredTagIds);
    }

    private boolean excludesAllergens(Recipe r, Set<Allergen> toExclude) {
        if (toExclude == null || toExclude.isEmpty())
            return true;
        Set<Allergen> allergens = Optional.ofNullable(r.getAllergens()).orElseGet(Set::of);
        for (var a : toExclude)
            if (allergens.contains(a))
                return false;
        return true;
    }

    /**
     * Converts a Recipe object into a RecipeDTO object.
     * 
     * @param r the Recipe object to convert.
     * @return the converted RecipeDTO object.
     */
    private RecipeDTO toDto(Recipe r) {
        RecipeDTO d = new RecipeDTO();
        d.recipeId = r.getRecipeId();
        d.title = r.getTitle();
        d.description = r.getDescription();
        d.dietCode = r.getDietCode();
        d.allergens = r.getAllergens();
        d.tagIds = Optional.ofNullable(r.getTags())
                .orElseGet(Set::of)
                .stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());
        return d;
    }

    /**
     * Copies the data from a RecipeDTO object into a Recipe object.
     *
     * @param d the RecipeDTO object to copy from.
     * @param r the Recipe object to copy into.
     */
    private void copyFromDto(RecipeDTO d, Recipe r) {
        if (d.title != null)
            r.setTitle(d.title);
        if (d.description != null)
            r.setDescription(d.description);
        if (d.dietCode != null)
            r.setDietCode(d.dietCode);
        if (d.allergens != null)
            r.setAllergens(d.allergens);
        if (d.tagIds != null) {
            Set<Tag> tags = d.tagIds.stream()
                    .map(id -> em.getReference(Tag.class, id))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            r.setTags(tags);
        }
    }
}

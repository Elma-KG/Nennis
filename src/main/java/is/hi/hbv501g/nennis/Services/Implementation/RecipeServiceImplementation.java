package is.hi.hbv501g.nennis.Services.Implementation;

import is.hi.hbv501g.nennis.Api.RecipeDTO;
import is.hi.hbv501g.nennis.Api.RecipeFilters;
import is.hi.hbv501g.nennis.Persistence.Entities.Recipe;
import is.hi.hbv501g.nennis.Persistence.Entities.Tag;
import is.hi.hbv501g.nennis.Persistence.Repositories.RecipeRepository;
import is.hi.hbv501g.nennis.Services.RecipeService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeServiceImplementation implements RecipeService {

    private final RecipeRepository recipeRepo;

    public RecipeServiceImplementation(RecipeRepository recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    @Override
    public List<RecipeDTO> getAll(RecipeFilters filters) {
        return recipeRepo.findAll(toSpec(filters)).stream().map(this::toDto).toList();
    }

    @Override
    public RecipeDTO getRecipeById(UUID recipeId) {
        return recipeRepo.findById(recipeId).map(this::toDto).orElseThrow();
    }

    @Override
    public List<RecipeDTO> search(String query, RecipeFilters filters) {
        if (filters == null) filters = new RecipeFilters();
        filters.query = query;
        return recipeRepo.findAll(toSpec(filters)).stream().map(this::toDto).toList();
    }

    @Override
    public RecipeDTO addRecipe(RecipeDTO dto) {
        Recipe saved = recipeRepo.save(toEntity(dto));
        return toDto(saved);
    }

    @Override
    public RecipeDTO updateRecipe(UUID id, RecipeDTO dto) {
        Recipe r = recipeRepo.findById(id).orElseThrow();
        updateEntity(r, dto);
        return toDto(recipeRepo.save(r));
    }

    @Override
    public void deleteRecipe(UUID recipeId) {
        recipeRepo.deleteById(recipeId);
    }

    private Recipe toEntity(RecipeDTO d) {
        Set<Tag> tags = d.tagIds == null ? Set.of() :
                d.tagIds.stream().map(id -> { Tag t = new Tag(); t.setId(id); return t; }).collect(Collectors.toSet());
        return new Recipe(
                d.recipeId,
                d.title,
                d.description,
                d.dietCode == null ? Diet.NONE : d.dietCode,
                tags,
                d.allergens
        );
    }

    private RecipeDTO toDto(Recipe r) {
        RecipeDTO d = new RecipeDTO();
        d.recipeId = r.getRecipeId();
        d.title = r.getTitle();
        d.description = r.getDescription();
        d.dietCode = r.getDietCode();
        d.allergens = r.getAllergens();
        d.tagIds = r.getTags() == null ? Set.of() : r.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        return d;
    }

    private void updateEntity(Recipe r, RecipeDTO d) {
        r.setTitle(d.title);
        r.setDescription(d.description);
        r.setDietCode(d.dietCode == null ? Diet.NONE : d.dietCode);
        r.setAllergens(d.allergens);
        r.setTags(d.tagIds == null ? Set.of() :
                d.tagIds.stream().map(id -> { Tag t = new Tag(); t.setId(id); return t; }).collect(Collectors.toSet()));
    }

    private Specification<Recipe> toSpec(RecipeFilters f) {
        return (root, q, cb) -> {
            var p = cb.conjunction();
            if (f == null) return p;

            if (f.diet != null && f.diet != Diet.NONE)
                p.getExpressions().add(cb.equal(root.get("dietCode"), f.diet));

            if (f.excludeAllergens != null && !f.excludeAllergens.isEmpty()) {
                var allergensJoin = root.joinSet("allergens");
                p.getExpressions().add(cb.not(allergensJoin.in(f.excludeAllergens)));
            }

            if (f.tagIds != null && !f.tagIds.isEmpty()) {
                SetJoin<Recipe, Tag> tagJoin = root.joinSet("tags");
                p.getExpressions().add(tagJoin.get("id").in(f.tagIds));
                q.distinct(true);
            }

            if (f.query != null && !f.query.isBlank()) {
                String like = "%" + f.query.toLowerCase() + "%";
                p.getExpressions().add(cb.or(
                        cb.like(cb.lower(root.get("title")), like),
                        cb.like(cb.lower(root.get("description")), like)
                ));
            }
            return p;
        };
    }
}
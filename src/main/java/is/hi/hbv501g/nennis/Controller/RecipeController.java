package is.hi.hbv501g.nennis.Controller;
import is.hi.hbv501g.nennis.Api.RecipeFilters;
import is.hi.hbv501g.nennis.Api.RecipeDTO;
import is.hi.hbv501g.nennis.Services.RecipeService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAll(@ModelAttribute RecipeFilters filters) {
        return ResponseEntity.ok(service.getAll(filters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getRecipeById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeDTO>> search(@RequestParam("q") String q,
                                                  @ModelAttribute RecipeFilters filters) {
        return ResponseEntity.ok(service.search(q, filters));
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> add(@RequestBody RecipeDTO dto) {
        return ResponseEntity.ok(service.addRecipe(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> update(@PathVariable UUID id, @RequestBody RecipeDTO dto) {
        return ResponseEntity.ok(service.updateRecipe(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
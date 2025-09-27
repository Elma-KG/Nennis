package is.hi.hbv501g.nennis.Controllers;
import is.hi.hbv501g.nennis.Api.RecipeFilters;
import is.hi.hbv501g.nennis.dto.RecipeDto;
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
    public ResponseEntity<List<RecipeDto>> getAll(@ModelAttribute RecipeFilters filters) {
        return ResponseEntity.ok(service.getAll(filters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getRecipeById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeDto>> search(@RequestParam("q") String q,
                                                  @ModelAttribute RecipeFilters filters) {
        return ResponseEntity.ok(service.search(q, filters));
    }

    @PostMapping
    public ResponseEntity<RecipeDto> add(@RequestBody RecipeDto dto) {
        return ResponseEntity.ok(service.addRecipe(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> update(@PathVariable UUID id, @RequestBody RecipeDto dto) {
        return ResponseEntity.ok(service.updateRecipe(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
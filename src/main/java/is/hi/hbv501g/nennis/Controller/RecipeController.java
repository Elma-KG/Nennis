package is.hi.hbv501g.nennis.Controller;
import is.hi.hbv501g.nennis.Api.RecipeFilters;
import is.hi.hbv501g.nennis.Api.RecipeDTO;
import is.hi.hbv501g.nennis.Services.RecipeService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAll(RecipeFilters filters) {
        return ResponseEntity.ok(service.getAll(filters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeDTO>> search(@RequestParam String q, RecipeFilters filters) {
        return ResponseEntity.ok(service.search(q, filters));
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> add(@RequestBody RecipeDTO dto) {
        return ResponseEntity.ok(service.add(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> update(@PathVariable Long id, @RequestBody RecipeDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
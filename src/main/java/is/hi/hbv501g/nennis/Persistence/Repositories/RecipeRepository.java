package is.hi.hbv501g.nennis.persistence.Repositories;

import is.hi.hbv501g.nennis.persistence.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface RecipeRepository extends  JpaRepository<Recipe, UUID>{
    List<Recipe> findByDietCode(Diet diet);
    Optional<Recipe> findById(UUID id);
    void deleteById(UUID id);
}

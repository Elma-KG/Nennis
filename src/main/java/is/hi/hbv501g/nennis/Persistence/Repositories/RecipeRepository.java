package is.hi.hbv501g.nennis.Persistence.Repositories;

import is.hi.hbv501g.nennis.Enums.Diet;
import is.hi.hbv501g.nennis.Persistence.Entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID>, JpaSpecificationExecutor<Recipe> {
    List<Recipe> findByDietCode(Diet diet);
}

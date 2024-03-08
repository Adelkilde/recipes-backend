package dat3.recipe.api;

import dat3.recipe.dto.RecipeDto;
import dat3.recipe.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.CacheControl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes(@RequestParam(required = false) String category) {
    if(category != null) {
        System.out.println("Category: " + category);
    }
    List<RecipeDto> recipes = recipeService.getAllRecipes(category);

    CacheControl cacheControl = CacheControl.maxAge(2, TimeUnit.MINUTES).cachePublic();

    return ResponseEntity.ok()
            .cacheControl(cacheControl)
            .body(recipes);
}

    @GetMapping(path ="/{id}")
    public RecipeDto getRecipeById(@PathVariable int id) {
        return recipeService.getRecipeById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping
    public RecipeDto addRecipe(@RequestBody RecipeDto request) {
        return recipeService.addRecipe(request);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PutMapping(path = "/{id}")
    public RecipeDto addRecipe(@RequestBody RecipeDto request,@PathVariable int id) {
        return recipeService.editRecipe(request,id);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteRecipe(@PathVariable int id) {
        return recipeService.deleteRecipe(id);
    }
}


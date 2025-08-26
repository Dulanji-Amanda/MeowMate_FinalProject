package lk.ijse.gdse.meowmate_backend.controller;

import lk.ijse.gdse.meowmate_backend.dto.CatDTO;
import lk.ijse.gdse.meowmate_backend.service.CatService;
import lk.ijse.gdse.meowmate_backend.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cats")
@RequiredArgsConstructor
public class CatController {

    private final CatService catService;
    private final JWTUtil jwtUtil;

    private Long getUserIdFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new SecurityException("Invalid Authorization header");
        }
        String token = authHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        if (userId == null) {
            throw new SecurityException("Invalid or expired token");
        }
        return userId;
    }

    //     @PreAuthorize("hasRole('ADMIN') or hasRole('USER')") // Temporarily commented out to fix 403
    @PostMapping("/saveCat")
    public ResponseEntity<CatDTO> addCat(@RequestBody CatDTO catDTO,
                                         @RequestHeader("Authorization") String authHeader) {

        Long ownerId = getUserIdFromToken(authHeader);

        if (catDTO.getCatName() == null || catDTO.getBreed() == null
                || catDTO.getAge() == null || catDTO.getStatus() == null) {
            return ResponseEntity.badRequest().build();
        }

        CatDTO createdCat = catService.createCat(catDTO, ownerId);
        if (createdCat == null || createdCat.getId() == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(createdCat);
    }

    @GetMapping
    public ResponseEntity<List<CatDTO>> getMyCats(@RequestHeader("Authorization") String authHeader) {
        Long ownerId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(catService.getCatsByOwnerId(ownerId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CatDTO>> getAllCats() {
        return ResponseEntity.ok(catService.getAllCats());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatDTO> updateCat(@PathVariable Long id,
                                            @RequestBody CatDTO catDTO,
                                            @RequestHeader("Authorization") String authHeader) {
        Long ownerId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(catService.updateCat(id, catDTO, ownerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCat(@PathVariable Long id,
                                          @RequestHeader("Authorization") String authHeader) {
        Long ownerId = getUserIdFromToken(authHeader);
        catService.deleteCat(id, ownerId);
        return ResponseEntity.noContent().build();
    }
}
//package lk.ijse.gdse.meowmate_backend.controller;
//
//import lk.ijse.gdse.meowmate_backend.dto.CatDTO;
//import lk.ijse.gdse.meowmate_backend.service.CatService;
//import lk.ijse.gdse.meowmate_backend.util.JWTUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/cats")
//@CrossOrigin(origins = "http://localhost:5500")
//@RequiredArgsConstructor
//public class CatController {
//
//
//    private final CatService catService;
//    private final JWTUtil jwtUtil;
//
//    private Long getUserIdFromToken(String authHeader) {
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new SecurityException("Invalid Authorization header");
//        }
//        String token = authHeader.substring(7);
//        Long userId = jwtUtil.extractUserId(token);
//        if (userId == null) {
//            throw new SecurityException("Invalid or expired token");
//        }
//        return userId;
//    }
//
//
//
//    @PostMapping(
//            value = "/saveCat",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//    )
//    public ResponseEntity<CatDTO> addDog(@RequestPart("cat") CatDTO dogDTO,
//                                         @RequestPart("image") MultipartFile image,
//                                         @RequestHeader("Authorization") String authHeader) throws Exception {
//        Long ownerId = getUserIdFromToken(authHeader);
//        byte[] imageBytes = image.getBytes();
//        CatDTO createdDog = catService.createCat(dogDTO, ownerId, imageBytes);
//        return ResponseEntity.ok(createdDog);
//    }
//
//
//    @GetMapping
//    public ResponseEntity<List<CatDTO>> getMyCats(@RequestHeader("Authorization") String authHeader) {
//        Long ownerId = getUserIdFromToken(authHeader);
//        return ResponseEntity.ok(catService.getCatsByOwnerId(ownerId));
//    }
//
//
//
//
//
//    @GetMapping("/all")
//    public ResponseEntity<List<CatDTO>> getAllCats() {
//        return ResponseEntity.ok(catService.getAllCats());
//    }
//
//
//
//
//
//    @PutMapping(
//            value = "/{id}",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//    )
//    public ResponseEntity<CatDTO> updateDog(@PathVariable Long id,
//                                            @RequestPart("cat") CatDTO catDTO,
//                                            @RequestPart(value = "image", required = false) MultipartFile image,
//                                            @RequestHeader("Authorization") String authHeader) throws Exception {
//        Long ownerId = getUserIdFromToken(authHeader);
//        byte[] imageBytes = image != null ? image.getBytes() : null;
//        return ResponseEntity.ok(catService.updateCat(id, catDTO, ownerId, imageBytes));
//    }
//
//
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCat(@PathVariable Long id,
//                                          @RequestHeader("Authorization") String authHeader) {
//        Long ownerId = getUserIdFromToken(authHeader);
//        catService.deleteCat(id, ownerId);
//        return ResponseEntity.noContent().build();
//    }
//}






package lk.ijse.gdse.meowmate_backend.controller;

import lk.ijse.gdse.meowmate_backend.dto.CatDTO;
import lk.ijse.gdse.meowmate_backend.service.CatService;
import lk.ijse.gdse.meowmate_backend.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/cats")
@CrossOrigin(origins = "http://localhost:5500")
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
        if (userId == null) throw new SecurityException("Invalid or expired token");
        return userId;
    }

    @PostMapping(value = "/saveCat", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CatDTO> saveCat(
            @RequestPart("cat") CatDTO catDTO,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestHeader("Authorization") String authHeader) throws Exception {
        try {
            Long ownerId = getUserIdFromToken(authHeader);
            byte[] imageBytes = (image != null) ? image.getBytes() : null;
            CatDTO createdCat = catService.createCat(catDTO, ownerId, imageBytes);
            return ResponseEntity.ok(createdCat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(403).build(); // or 401
        }
    }


    @GetMapping
    public ResponseEntity<List<CatDTO>> getMyCats(@RequestHeader("Authorization") String authHeader) {
        Long ownerId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(catService.getCatsByOwnerId(ownerId));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CatDTO> updateCat(@PathVariable Long id,
                                            @RequestPart("cat") CatDTO catDTO,
                                            @RequestPart(value = "image", required = false) MultipartFile image,
                                            @RequestHeader("Authorization") String authHeader) throws Exception {
        Long ownerId = getUserIdFromToken(authHeader);
        byte[] imageBytes = (image != null) ? image.getBytes() : null;
        return ResponseEntity.ok(catService.updateCat(id, catDTO, ownerId, imageBytes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCat(@PathVariable Long id,
                                          @RequestHeader("Authorization") String authHeader) {
        Long ownerId = getUserIdFromToken(authHeader);
        catService.deleteCat(id, ownerId);
        return ResponseEntity.noContent().build();
    }
}

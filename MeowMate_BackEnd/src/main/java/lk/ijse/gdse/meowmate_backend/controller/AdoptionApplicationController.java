//package lk.ijse.gdse.meowmate_backend.controller;
//
//import lk.ijse.gdse.meowmate_backend.entity.AdoptionApplications;
//import lk.ijse.gdse.meowmate_backend.service.AdoptionApplicationService;
//import lk.ijse.gdse.meowmate_backend.util.JWTUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/adoptions")
//@CrossOrigin(origins = "http://localhost:5500")
//@RequiredArgsConstructor
//public class AdoptionApplicationController {
//
//    private final AdoptionApplicationService adoptionService;
//    private final JWTUtil jwtUtil;
//
//    @PostMapping("/request/{petId}")
//    public ResponseEntity<AdoptionApplications> requestAdoption(
//            @PathVariable Long petId,
//            @RequestBody Map<String,String> body,
//            @RequestHeader("Authorization") String authHeader) {
//
//        Long adopterId = getUserIdFromToken(authHeader);
//        String message = body.getOrDefault("message", "");
//        AdoptionApplications app = adoptionService.requestAdoption(petId, adopterId, message);
//        return ResponseEntity.ok(app);
//    }
//
//    @GetMapping("/my-requests")
//    public ResponseEntity<List<AdoptionApplications>> myRequests(@RequestHeader("Authorization") String authHeader){
//        Long adopterId = getUserIdFromToken(authHeader);
//        return ResponseEntity.ok(adoptionService.getApplicationsByAdopter(adopterId));
//    }
//
//    @GetMapping("/for-pet/{petId}")
//    public ResponseEntity<List<AdoptionApplications>> forPet(@PathVariable Long petId,
//                                                            @RequestHeader("Authorization") String authHeader){
//        // you may want to check owner permission here before returning
//        return ResponseEntity.ok(adoptionService.getApplicationsForPet(petId));
//    }
//
//    @PatchMapping("/{id}/status")
//    public ResponseEntity<AdoptionApplications> updateStatus(@PathVariable Long id,
//                                                            @RequestBody Map<String,String> body,
//                                                            @RequestHeader("Authorization") String authHeader){
//        // you should check permission (owner) before allowing status change in production
//        AdoptionApplications updated = adoptionService.updateStatus(id, body.get("status"));
//        return ResponseEntity.ok(updated);
//    }
//
//    private Long getUserIdFromToken(String authHeader){
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new SecurityException("Missing or invalid Authorization header");
//        }
//        String token = authHeader.substring(7);
//        Long userId = jwtUtil.extractUserId(token);
//        if (userId == null) throw new SecurityException("Invalid or expired token");
//        return userId;
//    }
//}

package lk.ijse.gdse.meowmate_backend.controller;

import lk.ijse.gdse.meowmate_backend.entity.AdoptionApplications;
import lk.ijse.gdse.meowmate_backend.service.AdoptionApplicationService;
import lk.ijse.gdse.meowmate_backend.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/adoptions")
@CrossOrigin(origins = "http://localhost:5500")
@RequiredArgsConstructor
public class AdoptionApplicationController {

    private final AdoptionApplicationService adoptionService;
    private final JWTUtil jwtUtil;

    @PostMapping("/request/{petId}")
    public ResponseEntity<AdoptionApplications> requestAdoption(
            @PathVariable Long petId,
            @RequestBody Map<String,String> body,
            @RequestHeader("Authorization") String authHeader) {

        Long adopterId = getUserIdFromToken(authHeader);
        String message = body.getOrDefault("message", "");
        AdoptionApplications app = adoptionService.requestAdoption(petId, adopterId, message);
        return ResponseEntity.ok(app);
    }

    @GetMapping("/my-requests")
    public ResponseEntity<List<AdoptionApplications>> myRequests(@RequestHeader("Authorization") String authHeader){
        Long adopterId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(adoptionService.getApplicationsByAdopter(adopterId));
    }

    @GetMapping("/for-pet/{petId}")
    public ResponseEntity<List<AdoptionApplications>> forPet(@PathVariable Long petId,
                                                            @RequestHeader("Authorization") String authHeader){
        // In production, verify caller is the pet owner
        return ResponseEntity.ok(adoptionService.getApplicationsForPet(petId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AdoptionApplications> updateStatus(@PathVariable Long id,
                                                            @RequestBody Map<String,String> body,
                                                            @RequestHeader("Authorization") String authHeader){
        // In production, check permission (owner) before allowing status change
        AdoptionApplications updated = adoptionService.updateStatus(id, body.get("status"));
        return ResponseEntity.ok(updated);
    }

    private Long getUserIdFromToken(String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new SecurityException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        if (userId == null) throw new SecurityException("Invalid or expired token");
        return userId;
    }
}

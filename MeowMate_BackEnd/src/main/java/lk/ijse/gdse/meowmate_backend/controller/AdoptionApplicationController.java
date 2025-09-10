




// correct code --------------------------------------------------------------------


//
//
//package lk.ijse.gdse.meowmate_backend.controller;
//
//import lk.ijse.gdse.meowmate_backend.dto.AdoptionApplicationDTO;
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
//    // Helper to extract user ID from token
//    private Long getUserIdFromToken(String authHeader) {
//        if (authHeader == null || !authHeader.startsWith("Bearer "))
//            throw new SecurityException("Invalid Authorization header");
//        return jwtUtil.extractUserId(authHeader.substring(7));
//    }
//
//    // Helper to extract user email from token
//    private String getUserEmailFromToken(String authHeader) {
//        if (authHeader == null || !authHeader.startsWith("Bearer "))
//            throw new SecurityException("Invalid Authorization header");
//        return jwtUtil.extractUsername(authHeader.substring(7));
//    }
//
//    // Request adoption
//    @PostMapping("/request/{catId}")
//    public ResponseEntity<AdoptionApplications> requestAdoption(
//            @PathVariable Long catId,
//            @RequestBody Map<String, String> body,
//            @RequestHeader("Authorization") String authHeader) {
//
//        Long adopterId = getUserIdFromToken(authHeader);
//        String adopterEmail = getUserEmailFromToken(authHeader);
//        String message = body.getOrDefault("message", "");
//
//        AdoptionApplications app = adoptionService.requestAdoption(catId, adopterId, adopterEmail, message);
//        return ResponseEntity.ok(app);
//    }
//
//    // Get all requests made by the current user
//    @GetMapping("/my-requests")
//    public ResponseEntity<List<AdoptionApplications>> myRequests(
//            @RequestHeader("Authorization") String authHeader) {
//
//        Long adopterId = getUserIdFromToken(authHeader);
//        List<AdoptionApplications> applications = adoptionService.getApplicationsByAdopter(adopterId);
//        return ResponseEntity.ok(applications);
//    }
//
//    // Get all requests for a specific cat
//    @GetMapping("/for-pet/{catId}")
//    public ResponseEntity<List<AdoptionApplications>> getRequestsForPet(@PathVariable Long catId) {
//        List<AdoptionApplications> applications = adoptionService.getApplicationsForPet(catId);
//        return ResponseEntity.ok(applications);
//    }
//
//    // Update adoption application status (approve/reject)
//    @PatchMapping("/{applicationId}/status")
//    public ResponseEntity<AdoptionApplications> updateStatus(
//            @PathVariable Long applicationId,
//            @RequestBody Map<String, String> body) {
//
//        String status = body.get("status");
//        AdoptionApplications updated = adoptionService.updateStatus(applicationId, status);
//        return ResponseEntity.ok(updated);
//    }
//
//}










// test code ---------------------------------------------------------------------------------------------------------

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

    private Long getUserIdFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new SecurityException("Invalid Authorization header");
        return jwtUtil.extractUserId(authHeader.substring(7));
    }

    private String getUserEmailFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new SecurityException("Invalid Authorization header");
        return jwtUtil.extractUsername(authHeader.substring(7));
    }

    // Send adoption request
    @PostMapping("/request/{catId}")
    public ResponseEntity<AdoptionApplications> requestAdoption(
            @PathVariable Long catId,
            @RequestBody Map<String, String> body,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long adopterId = getUserIdFromToken(authHeader);
        String adopterEmail = getUserEmailFromToken(authHeader);
        String message = body.getOrDefault("message", "");
        AdoptionApplications app = adoptionService.requestAdoption(catId, adopterId, adopterEmail, message);
        return ResponseEntity.ok(app);
    }

    // Get all requests made by the current user
    @GetMapping("/my-requests")
    public ResponseEntity<List<AdoptionApplications>> myRequests(@RequestHeader("Authorization") String authHeader) {
        Long adopterId = getUserIdFromToken(authHeader);
        List<AdoptionApplications> applications = adoptionService.getApplicationsByAdopter(adopterId);
        return ResponseEntity.ok(applications);
    }

    // Update adoption status (approve/reject)
    @PatchMapping("/{applicationId}/status")
    public ResponseEntity<AdoptionApplications> updateStatus(
            @PathVariable Long applicationId,
            @RequestBody Map<String, String> body
    ) {
        String status = body.get("status");
        AdoptionApplications updated = adoptionService.updateStatus(applicationId, status);
        return ResponseEntity.ok(updated);
    }



    @GetMapping("/all")
    public ResponseEntity<List<AdoptionApplications>> getAllApplications() {
        List<AdoptionApplications> applications = adoptionService.getAllApplications();
        return ResponseEntity.ok(applications);
    }











}






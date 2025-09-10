//package lk.ijse.gdse.meowmate_backend.controller;
//
//import lk.ijse.gdse.meowmate_backend.dto.LostCatDTO;
//import lk.ijse.gdse.meowmate_backend.entity.LostCat;
//import lk.ijse.gdse.meowmate_backend.entity.LostStatus;
//import lk.ijse.gdse.meowmate_backend.repo.LostCatRepository;
//import lk.ijse.gdse.meowmate_backend.service.LostCatService;
//import lk.ijse.gdse.meowmate_backend.util.JWTUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/lostcats")
//@CrossOrigin(origins = "http://localhost:5500")
//@RequiredArgsConstructor
//public class LostCatController {
//
//    private final LostCatService lostCatService;
//    private final LostCatRepository lostCatRepository;
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
//    @PostMapping("/report")
//    public ResponseEntity<LostCatDTO> reportMissing(@RequestParam Long catId,
//                                                    @RequestParam String lastSeenLocation,
//                                                    @RequestHeader("Authorization") String authHeader) {
//        Long userId = getUserIdFromToken(authHeader);
//        LostCatDTO lostCatDTO = lostCatService.reportMissing(catId, userId, lastSeenLocation);
//        return ResponseEntity.ok(lostCatDTO);
//    }
//
//    @GetMapping("/missing")
//    public List<LostCat> getMissingCats() {
//        return lostCatRepository.findByStatus(LostStatus.MISSING);
//    }
//}


















package lk.ijse.gdse.meowmate_backend.controller;

import lk.ijse.gdse.meowmate_backend.dto.LostCatDTO;
import lk.ijse.gdse.meowmate_backend.service.LostCatService;
import lk.ijse.gdse.meowmate_backend.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lostcats")
@CrossOrigin(origins = "http://localhost:5500")
@RequiredArgsConstructor
public class LostCatController {

    private final LostCatService lostCatService;
    private final JWTUtil jwtUtil;
    private final EmailSenderServiceController emailSenderServiceController;

    private Long getUserIdFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) throw new SecurityException("Invalid Authorization header");
        return jwtUtil.extractUserId(authHeader.substring(7));
    }

    @PostMapping("/report")
    public ResponseEntity<LostCatDTO> reportMissing(@RequestParam Long catId,
                                                    @RequestParam String lastSeenLocation,
                                                    @RequestHeader("Authorization") String authHeader) {
        Long userId = getUserIdFromToken(authHeader);
        return ResponseEntity.ok(lostCatService.reportMissing(catId, userId, lastSeenLocation));
    }

    @GetMapping("/missing")
    public ResponseEntity<List<LostCatDTO>> getMissingCats() {
        return ResponseEntity.ok(lostCatService.getAllMissingCats());
    }


    @DeleteMapping("/by-cat/{catId}")
    public ResponseEntity<Void> deleteLostCatByCatId(@PathVariable Long catId) {
        lostCatService.deleteByCatId(catId);
        return ResponseEntity.noContent().build();
    }





//    @PostMapping("/sighting/{catId}")
//    public ResponseEntity<Void> reportSighting(
//            @PathVariable Long catId,
//            @RequestBody Map<String, String> payload) {
//
//        String location = payload.get("location");
//
//        // Compose HTML email
//        String toEmail = "kavindutharin@gmail.com";
//        String subject = "🐱 Lost Cat Sighting Reported!";
//        String body = "<h3>Lost Cat Sighting Reported!</h3>"
//                + "<p><strong>Cat ID:</strong> " + catId + "</p>"
//                + "<p><strong>Location Seen:</strong> " + location + "</p>"
//                + "<p>Thanks,<br/>MeowMate Team</p>";
//
//        emailSenderServiceController.sendSimpleEmail(toEmail, subject, body);
//
//        return ResponseEntity.ok().build();
//    }



// correct code ---------------------------------------------------------------------------------------------------------


//    @PostMapping("/sighting/{catId}")
//    public ResponseEntity<Void> reportSighting(
//            @PathVariable Long catId,
//            @RequestBody Map<String, String> payload) {
//
//        String location = payload.get("location");
//
//        // Send email using the new formatted HTML content
//        emailSenderServiceController.sendSightingEmail("kavindutharin@gmail.com", catId, location);
//
//        return ResponseEntity.ok().build();
//    }

//-------------------------------------------------------------------------------------------------------------------------



    //new code ----------------------------------------------------------------------------------------------------------


    @PostMapping("/sighting/{catId}")
    public ResponseEntity<Void> reportSighting(
            @PathVariable Long catId,
            @RequestBody Map<String, String> payload) {

        String location = payload.get("location");

        // Find owner email by catId
        String ownerEmail = lostCatService.getOwnerEmailByCatId(catId);

        // Send email to owner
        emailSenderServiceController.sendSightingEmail(ownerEmail, catId, location);

        // Optional: log sighting in DB
        lostCatService.reportSighting(catId, null, location);

        return ResponseEntity.ok().build();
    }







}

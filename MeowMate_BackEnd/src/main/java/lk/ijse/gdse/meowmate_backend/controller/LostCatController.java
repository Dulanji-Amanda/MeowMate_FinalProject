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

@RestController
@RequestMapping("/api/lostcats")
@CrossOrigin(origins = "http://localhost:5500")
@RequiredArgsConstructor
public class LostCatController {

    private final LostCatService lostCatService;
    private final JWTUtil jwtUtil;

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
}

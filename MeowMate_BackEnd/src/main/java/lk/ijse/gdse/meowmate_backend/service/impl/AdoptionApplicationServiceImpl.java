

// correct code

//
//package lk.ijse.gdse.meowmate_backend.service.impl;
//
//import lk.ijse.gdse.meowmate_backend.controller.EmailSenderServiceController;
//import lk.ijse.gdse.meowmate_backend.dto.AdoptionApplicationDTO;
//import lk.ijse.gdse.meowmate_backend.entity.AdoptionApplications;
//import lk.ijse.gdse.meowmate_backend.entity.Adoption_Status;
//import lk.ijse.gdse.meowmate_backend.entity.Cat;
//import lk.ijse.gdse.meowmate_backend.entity.User;
//import lk.ijse.gdse.meowmate_backend.repo.AdoptionApplicationRepository;
//import lk.ijse.gdse.meowmate_backend.repo.CatRepository;
//import lk.ijse.gdse.meowmate_backend.repo.UserRepository;
//import lk.ijse.gdse.meowmate_backend.service.AdoptionApplicationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class AdoptionApplicationServiceImpl implements AdoptionApplicationService {
//
//    private final AdoptionApplicationRepository repo;
//    private final CatRepository catRepo;
//    private final UserRepository userRepo;
//    private final EmailSenderServiceController emailSender;
//
//    @Override
//    public AdoptionApplications requestAdoption(Long catId, Long adopterId, String adopterEmail, String message) {
//        AdoptionApplications app = AdoptionApplications.builder()
//                .petId(catId)
//                .adopterId(adopterId)
//                .status(Adoption_Status.PENDING)
//                .build();
//        AdoptionApplications saved = repo.save(app);
//
//        // Send email
//        String ownerEmail = getOwnerEmailByCatId(catId);
//        emailSender.sendAdoptionRequestEmail(ownerEmail, catId, message, adopterEmail);
//
//        return saved; // return entity, not DTO
//    }
//
//    @Override
//    public List<AdoptionApplications> getApplicationsByAdopter(Long adopterId) {
//        return repo.findByAdopterId(adopterId);
//    }
//
//    @Override
//    public List<AdoptionApplications> getApplicationsForPet(Long petId) {
//        return repo.findByPetId(petId);
//    }
//
//    @Override
//    public AdoptionApplications updateStatus(Long id, String status) {
//        AdoptionApplications app = repo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Application not found: " + id));
//        app.setStatus(Adoption_Status.valueOf(status.toUpperCase()));
//        return repo.save(app);
//    }
//
//
//    @Override
//    public String getOwnerEmailByCatId(Long catId) {
//        Cat cat = catRepo.findById(catId)
//                .orElseThrow(() -> new RuntimeException("Cat not found"));
//
//        User owner = userRepo.findById(cat.getOwnerId())
//                .orElseThrow(() -> new RuntimeException("Owner not found"));
//
//        if (owner.getEmail() == null || owner.getEmail().isEmpty()) {
//            throw new RuntimeException("Owner email not found");
//        }
//
//        return owner.getEmail();
//    }
//
//
//
//}
//





















// test code get all cats -----------------------------------------------------------------------------------------------





package lk.ijse.gdse.meowmate_backend.service.impl;

import lk.ijse.gdse.meowmate_backend.entity.AdoptionApplications;
import lk.ijse.gdse.meowmate_backend.entity.Adoption_Status;
import lk.ijse.gdse.meowmate_backend.entity.Cat;
import lk.ijse.gdse.meowmate_backend.entity.User;
import lk.ijse.gdse.meowmate_backend.repo.AdoptionApplicationRepository;
import lk.ijse.gdse.meowmate_backend.repo.CatRepository;
import lk.ijse.gdse.meowmate_backend.repo.UserRepository;
import lk.ijse.gdse.meowmate_backend.service.AdoptionApplicationService;
import lk.ijse.gdse.meowmate_backend.controller.EmailSenderServiceController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionApplicationServiceImpl implements AdoptionApplicationService {

    private final AdoptionApplicationRepository repo;
    private final CatRepository catRepo;
    private final UserRepository userRepo;
    private final EmailSenderServiceController emailSender;

    @Override
    public AdoptionApplications requestAdoption(Long catId, Long adopterId, String adopterEmail, String message) {
        AdoptionApplications app = AdoptionApplications.builder()
                .petId(catId)
                .adopterId(adopterId)
                .status(Adoption_Status.PENDING)
                .build();

        AdoptionApplications saved = repo.save(app);

        // Send email to owner
        String ownerEmail = getOwnerEmailByCatId(catId);
        emailSender.sendAdoptionRequestEmail(ownerEmail, catId, message, adopterEmail);

        return saved;
    }

    @Override
    public List<AdoptionApplications> getApplicationsByAdopter(Long adopterId) {
        return repo.findByAdopterId(adopterId);
    }

    @Override
    public List<AdoptionApplications> getApplicationsForPet(Long petId) {
        return repo.findByPetId(petId);
    }

    @Override
    public AdoptionApplications updateStatus(Long id, String status) {
        AdoptionApplications app = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found: " + id));
        app.setStatus(Adoption_Status.valueOf(status.toUpperCase()));
        return repo.save(app);
    }

    @Override
    public String getOwnerEmailByCatId(Long catId) {
        Cat cat = catRepo.findById(catId)
                .orElseThrow(() -> new RuntimeException("Cat not found"));
        User owner = userRepo.findById(cat.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        if (owner.getEmail() == null || owner.getEmail().isEmpty()) {
            throw new RuntimeException("Owner email not found");
        }
        return owner.getEmail();
    }

    @Override
    public List<AdoptionApplications> getAllApplications() {
        return repo.findAll();
    }
}

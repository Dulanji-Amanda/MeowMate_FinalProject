package lk.ijse.gdse.meowmate_backend.service.impl;


import lk.ijse.gdse.meowmate_backend.entity.AdoptionApplications;
import lk.ijse.gdse.meowmate_backend.entity.Adoption_Status;
import lk.ijse.gdse.meowmate_backend.repo.AdoptionApplicationRepository;
import lk.ijse.gdse.meowmate_backend.service.AdoptionApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionApplicationServiceImpl implements AdoptionApplicationService {


    private final AdoptionApplicationRepository repo;

    @Override
    public AdoptionApplications requestAdoption(Long petId, Long adopterId, String message) {
        // message currently unused at DB; you can extend entity to store it if needed
        AdoptionApplications app = AdoptionApplications.builder()
                .petId(petId)
                .adopterId(adopterId)
                .status(Adoption_Status.PENDING)
                .build();
        return repo.save(app);
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
}

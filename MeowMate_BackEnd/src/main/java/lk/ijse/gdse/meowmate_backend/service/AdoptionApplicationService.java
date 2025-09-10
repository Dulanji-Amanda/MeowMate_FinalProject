package lk.ijse.gdse.meowmate_backend.service;

import lk.ijse.gdse.meowmate_backend.entity.AdoptionApplications;

import java.util.List;

public interface AdoptionApplicationService {
    AdoptionApplications requestAdoption(Long petId, Long adopterId, String adopterEmail, String message);
    List<AdoptionApplications> getApplicationsByAdopter(Long adopterId);
    List<AdoptionApplications> getApplicationsForPet(Long petId);
    AdoptionApplications updateStatus(Long id, String status);


    String getOwnerEmailByCatId(Long catId);

    List<AdoptionApplications> getAllApplications(); // <-- add this

}

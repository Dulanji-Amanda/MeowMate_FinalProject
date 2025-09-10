package lk.ijse.gdse.meowmate_backend.service;

import lk.ijse.gdse.meowmate_backend.dto.LostCatDTO;

import java.util.List;

public interface LostCatService {
    LostCatDTO reportMissing(Long catId, Long userId, String lastSeenLocation);
    List<LostCatDTO> getAllMissingCats();
    void deleteByCatId(Long catId);  // ✅ new method for cascade delete

    void reportSighting(Long catId, Long userId, String location);






    String getOwnerEmailByCatId(Long catId);

}

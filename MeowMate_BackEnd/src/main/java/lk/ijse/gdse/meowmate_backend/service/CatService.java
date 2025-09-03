package lk.ijse.gdse.meowmate_backend.service;

import lk.ijse.gdse.meowmate_backend.dto.CatDTO;

import java.util.List;

public interface CatService {

    CatDTO createCat(CatDTO catDTO, Long ownerId, byte[] imageBytes);
    List<CatDTO> getAllCats();
    List<CatDTO> getCatsByOwnerId(Long ownerId);
    CatDTO updateCat(Long dogId, CatDTO dogDTO, Long ownerId, byte[] imageBytes);
    void deleteCat(Long dogId, Long ownerId);
}

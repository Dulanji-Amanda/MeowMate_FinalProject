package lk.ijse.gdse.meowmate_backend.service.impl;

import lk.ijse.gdse.meowmate_backend.dto.CatDTO;
import lk.ijse.gdse.meowmate_backend.entity.Cat;
import lk.ijse.gdse.meowmate_backend.entity.Cat_Status;
import lk.ijse.gdse.meowmate_backend.repo.CatRepository;
import lk.ijse.gdse.meowmate_backend.service.CatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;
    private static final Logger logger = LoggerFactory.getLogger(CatServiceImpl.class);

    private CatDTO toDTO(Cat cat) {
        return CatDTO.builder()
                .id(cat.getId())
                .catName(cat.getCatName())
                .breed(cat.getBreed())
                .age(cat.getAge())
                .status(cat.getStatus().name())
                .ownerId(cat.getOwnerId())
                .build();
    }

    private Cat toEntity(CatDTO dto, Long ownerId) {
        return Cat.builder()
                .catName(dto.getCatName())
                .breed(dto.getBreed())
                .age(dto.getAge())
                .status(Cat_Status.valueOf(dto.getStatus()))
                .ownerId(ownerId)
                .build();
    }

    @Override
    public CatDTO createCat(CatDTO catDTO, Long ownerId) {
        try {
            logger.info("Creating cat: {}", catDTO);
            Cat cat = toEntity(catDTO, ownerId);
            Cat savedCat = catRepository.save(cat);
            logger.info("Cat created successfully: {}", savedCat);
            return toDTO(savedCat);
        } catch (Exception e) {
            logger.error("Error creating cat: {}", e.getMessage());
            throw new RuntimeException("Failed to create cat: " + e.getMessage());
        }
    }

    @Override
    public List<CatDTO> getAllCats() {
        try {
            List<Cat> cats = catRepository.findAll();
            return cats.stream().map(this::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error getting all cats: {}", e.getMessage());
            throw new RuntimeException("Failed to get cats: " + e.getMessage());
        }
    }

    @Override
    public List<CatDTO> getCatsByOwnerId(Long ownerId) {
        try {
            List<Cat> cats = catRepository.findByOwnerId(ownerId);
            return cats.stream().map(this::toDTO).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error getting cats for owner {}: {}", ownerId, e.getMessage());
            throw new RuntimeException("Failed to get cats for owner: " + e.getMessage());
        }
    }

    @Override
    public CatDTO updateCat(Long catId, CatDTO catDTO, Long ownerId) {
        try {
            Cat existingCat = catRepository.findById(catId)
                    .orElseThrow(() -> new RuntimeException("Cat not found"));

            if (!existingCat.getOwnerId().equals(ownerId)) {
                throw new RuntimeException("Unauthorized to update this cat");
            }

            existingCat.setCatName(catDTO.getCatName());
            existingCat.setBreed(catDTO.getBreed());
            existingCat.setAge(catDTO.getAge());
            existingCat.setStatus(Cat_Status.valueOf(catDTO.getStatus()));

            Cat updatedCat = catRepository.save(existingCat);
            return toDTO(updatedCat);
        } catch (Exception e) {
            logger.error("Error updating Cat {}: {}", catId, e.getMessage());
            throw new RuntimeException("Failed to update cat: " + e.getMessage());
        }
    }

    @Override
    public void deleteCat(Long catId, Long ownerId) {
        try {
            Cat cat = catRepository.findById(catId)
                    .orElseThrow(() -> new RuntimeException("Cat not found"));

            if (!cat.getOwnerId().equals(ownerId)) {
                throw new RuntimeException("Unauthorized to delete this cat");
            }

            catRepository.deleteById(catId);
        } catch (Exception e) {
            logger.error("Error deleting cat {}: {}", catId, e.getMessage());
            throw new RuntimeException("Failed to delete cat: " + e.getMessage());
        }
    }
}
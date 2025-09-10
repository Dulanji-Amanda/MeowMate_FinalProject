//package lk.ijse.gdse.meowmate_backend.service.impl;
//
//import lk.ijse.gdse.meowmate_backend.dto.CatDTO;
//import lk.ijse.gdse.meowmate_backend.entity.Cat;
//import lk.ijse.gdse.meowmate_backend.entity.Cat_Status;
//import lk.ijse.gdse.meowmate_backend.repo.CatRepository;
//import lk.ijse.gdse.meowmate_backend.service.CatService;
//import lk.ijse.gdse.meowmate_backend.util.ImgBBService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Service
//@RequiredArgsConstructor
//public class CatServiceImpl implements CatService {
//
//    private final CatRepository catRepository;
//    private final ImgBBService imgBBService;
//
//    private static final Logger logger = LoggerFactory.getLogger(CatServiceImpl.class);
//
//    private CatDTO toDTO(Cat cat) {
//        return CatDTO.builder()
//                .id(cat.getId())
//                .catName(cat.getCatName())
//                .breed(cat.getBreed())
//                .age(cat.getAge())
//                .status(cat.getStatus().name())
//                .imageUrl(cat.getImageUrl())
//                .ownerId(cat.getOwnerId())
//                .build();
//    }
//
//    private Cat toEntity(CatDTO dto, Long ownerId ,String imageUrl) {
//        return Cat.builder()
//                .catName(dto.getCatName())
//                .breed(dto.getBreed())
//                .age(dto.getAge())
//                .status(Cat_Status.valueOf(dto.getStatus()))
//                .imageUrl(dto.getImageUrl())
//                .ownerId(ownerId)
//                .build();
//    }
//
//
//    @Override
//    public CatDTO createCat(CatDTO catDTO, Long ownerId, byte[] imageBytes) {
//        String uploadedUrl = imgBBService.uploadImage(imageBytes);
//        Cat dog = toEntity(catDTO, ownerId, uploadedUrl);
//        Cat saved = catRepository.save(dog);
//        return toDTO(saved);
//    }
//
//
//
//
//    @Override
//    public List<CatDTO> getAllCats() {
//        try {
//            List<Cat> cats = catRepository.findAll();
//            return cats.stream().map(this::toDTO).collect(Collectors.toList());
//        } catch (Exception e) {
//            logger.error("Error getting all cats: {}", e.getMessage());
//            throw new RuntimeException("Failed to get cats: " + e.getMessage());
//        }
//    }
//
//
//
//    @Override
//    public List<CatDTO> getCatsByOwnerId(Long ownerId) {
//        return catRepository.findByOwnerId(ownerId).stream().map(this::toDTO).collect(Collectors.toList());
//    }
//
//
//
//    @Override
//    public CatDTO updateCat(Long catId, CatDTO catDTO, Long ownerId, byte[] imageBytes) {
//        try {
//            Cat existingCat = catRepository.findById(catId)
//                    .orElseThrow(() -> new RuntimeException("Cat not found"));
//
//            if (!existingCat.getOwnerId().equals(ownerId)) {
//                throw new RuntimeException("Unauthorized to update this cat");
//            }
//
//
//            String newImageUrl = existingCat.getImageUrl();
//            if (imageBytes != null && imageBytes.length > 0) {
//                newImageUrl = imgBBService.uploadImage(imageBytes);
//            }
//
//            existingCat.setCatName(catDTO.getCatName());
//            existingCat.setBreed(catDTO.getBreed());
//            existingCat.setAge(catDTO.getAge());
//            existingCat.setStatus(Cat_Status.valueOf(catDTO.getStatus()));
//
//            Cat updatedCat = catRepository.save(existingCat);
//            return toDTO(updatedCat);
//
//        } catch (Exception e) {
//            logger.error("Error updating Cat {}: {}", catId, e.getMessage());
//            throw new RuntimeException("Failed to update cat: " + e.getMessage());
//        }
//    }
//
//
//
//    @Override
//    public void deleteCat(Long catId, Long ownerId) {
//        try {
//            Cat cat = catRepository.findById(catId)
//                    .orElseThrow(() -> new RuntimeException("Cat not found"));
//
//            if (!cat.getOwnerId().equals(ownerId)) {
//                throw new RuntimeException("Unauthorized to delete this cat");
//            }
//
//            catRepository.deleteById(catId);
//        } catch (Exception e) {
//            logger.error("Error deleting cat {}: {}", catId, e.getMessage());
//            throw new RuntimeException("Failed to delete cat: " + e.getMessage());
//        }
//    }
//
//
//
//
//
//
//
//
//}





























package lk.ijse.gdse.meowmate_backend.service.impl;

import lk.ijse.gdse.meowmate_backend.dto.CatDTO;
import lk.ijse.gdse.meowmate_backend.entity.Cat;
import lk.ijse.gdse.meowmate_backend.entity.Cat_Status;
import lk.ijse.gdse.meowmate_backend.repo.CatRepository;
import lk.ijse.gdse.meowmate_backend.service.CatService;
import lk.ijse.gdse.meowmate_backend.util.ImgBBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatServiceImpl implements CatService {

    private final CatRepository catRepository;
    private final ImgBBService imgBBService;

    private CatDTO toDTO(Cat cat) {
        return CatDTO.builder()
                .id(cat.getId())
                .catName(cat.getCatName())
                .breed(cat.getBreed())
                .age(cat.getAge())
                .status(cat.getStatus().name())
                .ownerId(cat.getOwnerId())
                .imageUrl(cat.getImageUrl())
                .build();
    }

    private Cat toEntity(CatDTO dto, Long ownerId, String imageUrl) {
        return Cat.builder()
                .catName(dto.getCatName())
                .breed(dto.getBreed())
                .age(dto.getAge())
                .status(Cat_Status.valueOf(dto.getStatus()))
                .ownerId(ownerId)
                .imageUrl(imageUrl)
                .build();
    }

    @Override
    public CatDTO createCat(CatDTO catDTO, Long ownerId, byte[] imageBytes) {
        String uploadedUrl = (imageBytes != null) ? imgBBService.uploadImage(imageBytes) : "";
        Cat cat = toEntity(catDTO, ownerId, uploadedUrl);
        Cat saved = catRepository.save(cat);
        return toDTO(saved);
    }

    @Override
    public List<CatDTO> getAllCats() {
        return catRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CatDTO> getCatsByOwnerId(Long ownerId) {
        return catRepository.findByOwnerId(ownerId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CatDTO updateCat(Long catId, CatDTO catDTO, Long ownerId, byte[] imageBytes) {
        Cat existingCat = catRepository.findById(catId)
                .orElseThrow(() -> new RuntimeException("Cat not found"));

        if (!existingCat.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized to update this cat");
        }

        String newImageUrl = existingCat.getImageUrl();
        if (imageBytes != null && imageBytes.length > 0) {
            newImageUrl = imgBBService.uploadImage(imageBytes);
        }

        existingCat.setCatName(catDTO.getCatName());
        existingCat.setBreed(catDTO.getBreed());
        existingCat.setAge(catDTO.getAge());
        existingCat.setStatus(Cat_Status.valueOf(catDTO.getStatus()));
        existingCat.setImageUrl(newImageUrl);

        Cat updatedCat = catRepository.save(existingCat);
        return toDTO(updatedCat);
    }

    @Override
    public void deleteCat(Long catId, Long ownerId) {
        Cat cat = catRepository.findById(catId)
                .orElseThrow(() -> new RuntimeException("Cat not found"));

        if (!cat.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized to delete this cat");
        }
        catRepository.deleteById(catId);
    }



}

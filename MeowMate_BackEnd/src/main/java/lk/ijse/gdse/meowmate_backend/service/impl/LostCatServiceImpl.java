//package lk.ijse.gdse.meowmate_backend.service.impl;
//
//import lk.ijse.gdse.meowmate_backend.dto.LostCatDTO;
//import lk.ijse.gdse.meowmate_backend.entity.LostCat;
//import lk.ijse.gdse.meowmate_backend.entity.LostStatus;
//import lk.ijse.gdse.meowmate_backend.repo.LostCatRepository;
//import lk.ijse.gdse.meowmate_backend.service.LostCatService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class LostCatServiceImpl implements LostCatService {
//
//    private final LostCatRepository lostCatRepo;
//
//    @Override
//    public LostCatDTO reportMissing(Long catId, Long userId, String lastSeenLocation) {
//        LostCat lostCat = LostCat.builder()
//                .catId(catId)
//                .userId(userId)
//                .lastSeenLocation(lastSeenLocation)
//                .status(LostStatus.MISSING)
//                .build();
//
//        LostCat saved = lostCatRepo.save(lostCat);
//
//        return LostCatDTO.builder()
//                .id(saved.getId())
//                .catId(saved.getCatId())
//                .userId(saved.getUserId())
//                .lastSeenLocation(saved.getLastSeenLocation())
//                .description(saved.getStatus().name())
//                .build();
//    }
//}




package lk.ijse.gdse.meowmate_backend.service.impl;

import lk.ijse.gdse.meowmate_backend.dto.LostCatDTO;
import lk.ijse.gdse.meowmate_backend.entity.Cat;
import lk.ijse.gdse.meowmate_backend.entity.LostCat;
import lk.ijse.gdse.meowmate_backend.entity.LostStatus;
import lk.ijse.gdse.meowmate_backend.repo.CatRepository;
import lk.ijse.gdse.meowmate_backend.repo.LostCatRepository;
import lk.ijse.gdse.meowmate_backend.service.LostCatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LostCatServiceImpl implements LostCatService {

    private final LostCatRepository lostCatRepo;
    private final CatRepository catRepo;

    @Override
    public LostCatDTO reportMissing(Long catId, Long userId, String lastSeenLocation) {
        LostCat lostCat = LostCat.builder()
                .catId(catId)
                .userId(userId)
                .lastSeenLocation(lastSeenLocation)
                .status(LostStatus.MISSING)
                .build();
        LostCat saved = lostCatRepo.save(lostCat);
        Cat cat = catRepo.findById(catId).orElse(null);
        return LostCatDTO.builder()
                .id(saved.getId())
                .catId(saved.getCatId())
                .userId(saved.getUserId())
                .lastSeenLocation(saved.getLastSeenLocation())
                .status(saved.getStatus().name())
                .catName(cat != null ? cat.getCatName() : "Unknown")
                .breed(cat != null ? cat.getBreed() : "Unknown")
                .age(cat != null ? cat.getAge() : null)
                .imageUrl(cat != null ? cat.getImageUrl() : "https://via.placeholder.com/400x300?text=No+Image")
                .build();
    }

    @Override
    public List<LostCatDTO> getAllMissingCats() {
        return lostCatRepo.findByStatus(LostStatus.MISSING)
                .stream()
                .map(lostCat -> {
                    Cat cat = catRepo.findById(lostCat.getCatId()).orElse(null);
                    return LostCatDTO.builder()
                            .id(lostCat.getId())
                            .catId(lostCat.getCatId())
                            .userId(lostCat.getUserId())
                            .lastSeenLocation(lostCat.getLastSeenLocation())
                            .status(lostCat.getStatus().name())
                            .catName(cat != null ? cat.getCatName() : "Unknown")
                            .breed(cat != null ? cat.getBreed() : "Unknown")
                            .age(cat != null ? cat.getAge() : null)
                            .imageUrl(cat != null ? cat.getImageUrl() : "https://via.placeholder.com/400x300?text=No+Image")
                            .build();
                })
                .collect(Collectors.toList());
    }


    @Override
    public void deleteByCatId(Long catId) {
        lostCatRepo.findByCatId(catId).ifPresent(lostCatRepo::delete);
    }
}

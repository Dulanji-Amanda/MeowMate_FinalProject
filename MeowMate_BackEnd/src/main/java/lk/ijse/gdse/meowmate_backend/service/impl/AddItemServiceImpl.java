package lk.ijse.gdse.meowmate_backend.service.impl;

import lk.ijse.gdse.meowmate_backend.dto.AddItemDto;
import lk.ijse.gdse.meowmate_backend.entity.AddItem;
import lk.ijse.gdse.meowmate_backend.repo.AddItemRepository;
import lk.ijse.gdse.meowmate_backend.service.AddItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddItemServiceImpl implements AddItemService {

    private final AddItemRepository addItemRepository;

    private AddItemDto toDto(AddItem entity) {
        return AddItemDto.builder()
                .id(entity.getId())
                .listingName(entity.getListingName())
                .listingDescription(entity.getListingDescription())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .imageUrl(entity.getImageUrl())
                .build();
    }

    private AddItem toEntity(AddItemDto dto) {
        return AddItem.builder()
                .listingName(dto.getListingName())
                .listingDescription(dto.getListingDescription())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .imageUrl(dto.getImageUrl())
                .build();
    }

    @Override
    public AddItemDto saveItem(AddItemDto dto) {
        AddItem saved = addItemRepository.save(toEntity(dto));
        return toDto(saved);
    }

    @Override
    public List<AddItemDto> getAllItems() {
        return addItemRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public AddItemDto updateItem(Long id, AddItemDto dto) {
        AddItem existing = addItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        existing.setListingName(dto.getListingName());
        existing.setListingDescription(dto.getListingDescription());
        existing.setPrice(dto.getPrice());
        existing.setQuantity(dto.getQuantity());
        existing.setImageUrl(dto.getImageUrl());
        return toDto(addItemRepository.save(existing));
    }

    @Override
    public void deleteItem(Long id) {
        addItemRepository.deleteById(id);
    }
}

package lk.ijse.gdse.meowmate_backend.service;

import lk.ijse.gdse.meowmate_backend.dto.AddItemDto;

import java.util.List;

public interface AddItemService {
    AddItemDto saveItem(AddItemDto dto);
    List<AddItemDto> getAllItems();
    AddItemDto updateItem(Long id, AddItemDto dto);
    void deleteItem(Long id);
}

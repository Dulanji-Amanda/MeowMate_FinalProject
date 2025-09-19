//package lk.ijse.gdse.meowmate_backend.controller;
//
//import lk.ijse.gdse.meowmate_backend.dto.AddItemDto;
//import lk.ijse.gdse.meowmate_backend.service.AddItemService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/items")
//@CrossOrigin(origins = {"http://127.0.0.1:5501", "http://localhost:5500"})
//@RequiredArgsConstructor
//public class AddItemController {
//
//    private final AddItemService addItemService;
//
//    @PostMapping("/save")
//    public ResponseEntity<AddItemDto> saveItem(@RequestBody AddItemDto dto) {
//        return ResponseEntity.ok(addItemService.saveItem(dto));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<AddItemDto>> getAllItems() {
//        return ResponseEntity.ok(addItemService.getAllItems());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<AddItemDto> updateItem(@PathVariable Long id, @RequestBody AddItemDto dto) {
//        return ResponseEntity.ok(addItemService.updateItem(id, dto));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
//        addItemService.deleteItem(id);
//        return ResponseEntity.noContent().build();
//    }
//}



package lk.ijse.gdse.meowmate_backend.controller;

import lk.ijse.gdse.meowmate_backend.dto.AddItemDto;
import lk.ijse.gdse.meowmate_backend.entity.AddItem;
import lk.ijse.gdse.meowmate_backend.repo.AddItemRepository;
import lk.ijse.gdse.meowmate_backend.service.AddItemService;
import lk.ijse.gdse.meowmate_backend.util.ImgBBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = {"http://127.0.0.1:5501", "http://localhost:5500"})
@RequiredArgsConstructor
public class AddItemController {

    private final AddItemService addItemService;
    private final ImgBBService imgBBService;
    private final AddItemRepository addItemRepository;

    // Save item with image
    @PostMapping("/save")
    public ResponseEntity<AddItemDto> saveItem(
            @RequestParam("listingName") String listingName,
            @RequestParam("listingDescription") String listingDescription,
            @RequestParam("price") String price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        String imageUrl = null;
        if(imageFile != null && !imageFile.isEmpty()) {
            imageUrl = imgBBService.uploadImage(imageFile.getBytes());
        }

        AddItemDto dto = AddItemDto.builder()
                .listingName(listingName)
                .listingDescription(listingDescription)
                .price(price)
                .quantity(quantity)
                .imageUrl(imageUrl)
                .build();

        return ResponseEntity.ok(addItemService.saveItem(dto));
    }

    @GetMapping
    public ResponseEntity<List<AddItemDto>> getAllItems() {
        return ResponseEntity.ok(addItemService.getAllItems());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddItemDto> updateItem(
            @PathVariable Long id,
            @RequestParam("listingName") String listingName,
            @RequestParam("listingDescription") String listingDescription,
            @RequestParam("price") String price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        String imageUrl = null;
        if(imageFile != null && !imageFile.isEmpty()) {
            imageUrl = imgBBService.uploadImage(imageFile.getBytes());
        }

        AddItemDto dto = AddItemDto.builder()
                .listingName(listingName)
                .listingDescription(listingDescription)
                .price(price)
                .quantity(quantity)
                .imageUrl(imageUrl)
                .build();

        return ResponseEntity.ok(addItemService.updateItem(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        addItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }





    @PutMapping("/{id}/decrease")
    public ResponseEntity<?> decreaseItemQty(@PathVariable Long id) {
        Optional<AddItem> optionalItem = addItemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AddItem item = optionalItem.get();
        if (item.getQuantity() > 0) {
            item.setQuantity(item.getQuantity() - 1);
            addItemRepository.save(item);
            return ResponseEntity.ok("Quantity decreased");
        } else {
            return ResponseEntity.badRequest().body("Item out of stock");
        }
    }




}

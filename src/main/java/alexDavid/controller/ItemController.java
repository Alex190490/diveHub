package alexDavid.controller;

import alexDavid.dtos.ItemDTO.ItemRequestDto;
import alexDavid.dtos.ItemDTO.ItemResponseDto;
import alexDavid.models.Item;
import lombok.RequiredArgsConstructor;
import alexDavid.mappers.ItemMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import alexDavid.service.ItemService.ItemService;

import java.util.List;

@RestController
@RequestMapping("/divehub/items")
@RequiredArgsConstructor
@CrossOrigin
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;


    @GetMapping("/all")
    public ResponseEntity<List<ItemResponseDto>> getAllItems(
    ) {
        return ResponseEntity.ok(itemMapper.toResponse(itemService.findAll()));
    }

    @GetMapping("/getItem/{id}")
    public ResponseEntity<ItemResponseDto> getItemById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(itemMapper.toResponse(itemService.findById(id)));
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<ItemResponseDto>> getItemByTag(
            @PathVariable String tag
    ) {
        return ResponseEntity.ok(itemMapper.toResponse(itemService.findProductsByTagIgnoreCase(tag)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ItemResponseDto>> getItemContainsInName(
            @PathVariable String name
    ) {
        return ResponseEntity.ok(itemMapper.toResponse(itemService.findByNameContainsIgnoreCase(name)));
    }

    @GetMapping("/price/{orderedBy}")
    public ResponseEntity<List<ItemResponseDto>> getItemOrderByPrice(
            @PathVariable Integer orderedBy
    ) {
        if(orderedBy==0) return ResponseEntity.ok(itemMapper.toResponse(itemService.findAllByOrderByFinal_priceDesc()));
        else return ResponseEntity.ok(itemMapper.toResponse(itemService.findAllByOrderByFinal_priceAsc()));
    }

    @PostMapping("/createItem")
    public ResponseEntity<ItemResponseDto> postItem(
            @RequestBody ItemRequestDto productRequestDto
    ) {
        Item productSaved = itemService.save(itemMapper.toModel(productRequestDto));
        return ResponseEntity.created(null).body(itemMapper.toResponse(productSaved));
    }

    @PutMapping("/updateItem/{id}")
    public ResponseEntity<ItemResponseDto> putItem(
            @PathVariable Long id,
            @RequestBody ItemRequestDto productRequestDto
    ) {
        Item productUpdated = itemService.update(id, itemMapper.toModel(productRequestDto));
        return ResponseEntity.ok(itemMapper.toResponse(productUpdated));
    }

    @DeleteMapping("/deleteItem/{id}")
    public void deleteById(
            @PathVariable Long id
    ) {
        itemService.deleteProductById(id);
    }
}



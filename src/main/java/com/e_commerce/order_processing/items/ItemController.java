package com.e_commerce.order_processing.items;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService service;


    @GetMapping()
    public List<Item> getAll() {
        return service.findAll();
    }

    @PostMapping()
    public void postStudent(@Valid @RequestBody ItemDto dto) {
        service.save();
    }
}

package com.e_commerce.order_processing.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("items")
public class ItemController {

    @Autowired
    ItemService service;


    @GetMapping()
    public List<Item> getAll() {
        return service.findAll();
    }

    @PostMapping()
    public void postStudent(@Valid @RequestBody ItemDto dto) {
        service.save();
    }
}

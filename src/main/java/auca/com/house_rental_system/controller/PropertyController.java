package auca.com.house_rental_system.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import auca.com.house_rental_system.model.Property;
import auca.com.house_rental_system.service.PropertyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping
    public Page<Property> getAllProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return propertyService.getProperties(page, size, sortBy, direction);
    }

    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        
        return propertyService.createProperty(property);
    }

    
}

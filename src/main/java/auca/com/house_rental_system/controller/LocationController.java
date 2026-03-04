package auca.com.house_rental_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import auca.com.house_rental_system.model.Location;
import auca.com.house_rental_system.service.LocationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    
    private final LocationService locationService;

    @GetMapping("/provinces")
    public List<Location> getProvinces() {
        return locationService.getAllProvinces();
    }

    @GetMapping("/{parentId}/children")
    public List<Location> getChildren(@PathVariable Long parentId) {
        return locationService.getChildren(parentId);
    }

}

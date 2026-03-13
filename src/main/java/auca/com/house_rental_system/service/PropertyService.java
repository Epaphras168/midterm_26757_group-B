package auca.com.house_rental_system.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import auca.com.house_rental_system.model.Property;
import auca.com.house_rental_system.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public Page<Property> getProperties(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return propertyRepository.findAll(pageable);
    }

    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

}

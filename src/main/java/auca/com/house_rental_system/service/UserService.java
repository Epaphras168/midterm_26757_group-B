package auca.com.house_rental_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import auca.com.house_rental_system.dto.UserDto;
import auca.com.house_rental_system.model.User;
import auca.com.house_rental_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {
    private final UserRepository userRepository;
    // private final LocationService locationService;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<UserDto> getUsersByProvince(String provinceName, String provinceCode) {
        // Using native recursive query
        List<User> users = userRepository.findAllUsersInProvince(provinceName, provinceCode);
        return users.stream()
                .map(UserDto::new)
                .toList();
    }


    
}

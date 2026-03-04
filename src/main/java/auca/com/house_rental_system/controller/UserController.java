package auca.com.house_rental_system.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import auca.com.house_rental_system.dto.UserDto;
import auca.com.house_rental_system.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/by-province")
    public List<UserDto> getUsersByProvince(
            @RequestParam(required = false) String provinceName,
            @RequestParam(required = false) String provinceCode) {
        if ((provinceName == null || provinceName.isBlank()) && (provinceCode == null || provinceCode.isBlank())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one parameter (provinceName or provinceCode) must be provided.");
        }
        return userService.getUsersByProvince(provinceName, provinceCode);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(Map.of("exists", exists));
    }
    
}

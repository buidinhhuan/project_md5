package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ra.model.domain.Users;
import ra.model.dto.request.CategoryRequest;
import ra.model.dto.response.CategoryResponse;
import ra.security.user_principle.UserPrinciple;
import ra.service.IUserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v4/test")
@CrossOrigin("*")
public class TestController {
    @Autowired
    IUserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Users>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Users> update(
            @AuthenticationPrincipal UserPrinciple userPrinciple,
            @PathVariable Long id,
            @RequestBody Users updatedUser) {
       Users updated = userService.update(userPrinciple, id);
        return ResponseEntity.ok(updated);
    }
}
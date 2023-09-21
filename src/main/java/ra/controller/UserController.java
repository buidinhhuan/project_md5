package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ra.model.domain.Users;
import ra.model.dto.request.ChangePassword;
import ra.model.dto.request.ForgotPasswordRequest;
import ra.security.user_principle.UserDetailService;
import ra.security.user_principle.UserPrinciple;
import ra.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    UserDetailService userDetailService;
    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<Users>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/unlockAndBlock/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Users> unlockAndBlock(
             @PathVariable Long id){
        Users updated = userService.unlockAndBlock(id);
        return ResponseEntity.ok(updated);
    }
    @PutMapping("/profile/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Users> updateProfile(
             @PathVariable Long id){
        Users updated = userService.unlockAndBlock(id);
        return ResponseEntity.ok(updated);
    }
    @PutMapping("/changePassword")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Users> changePassword(@RequestBody ChangePassword changePassword){
        Users users = userDetailService.getUserFromAuthentication();
        Users userUd = userService.changePassword(users, changePassword);
        return new ResponseEntity<>(userUd, HttpStatus.OK);
    }
    @PutMapping("/forgotPassword")
     public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
         List<Users> userFG = userService.forgotPassword(forgotPasswordRequest);
        return new ResponseEntity<>(userFG, HttpStatus.OK);
    }
}
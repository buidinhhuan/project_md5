package ra.service;

import ra.model.domain.Users;
import ra.model.dto.request.ChangePassword;
import ra.model.dto.request.ForgotPasswordRequest;
import ra.model.dto.request.FormSignUpDto;
import ra.security.user_principle.UserPrinciple;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<Users> findAll();
    Optional<Users> findByUserName(String username);
    Optional<Users> findById(Long userId);
    Users save(FormSignUpDto users);
    Users unlockAndBlock(Long id);
    Users changePassword(Users users, ChangePassword changePassword);
    List<Users> forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
}

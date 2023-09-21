package ra.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.model.domain.Role;
import ra.model.domain.RoleName;
import ra.model.domain.Users;
import ra.model.dto.request.ChangePassword;
import ra.model.dto.request.ForgotPasswordRequest;
import ra.model.dto.request.FormSignUpDto;
import ra.repository.IUserRepository;
import ra.service.IRoleService;
import ra.service.IUserService;
import javax.persistence.EntityExistsException;
 import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private MailService mailService;
    private Map<String, LocalDateTime> verificationCodes = new HashMap<>();

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }


    @Override
    public Optional<Users> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<Users> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Users save(FormSignUpDto form) throws EntityExistsException {
        String validEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String validPhone = "^[0-9]{10}$";
        if (userRepository.existsByUsername(form.getUsername())) {
            throw new EntityExistsException("Tài khoản đã tồn tại");
        }
        if (form.getName() == null || form.getName().trim().isEmpty()) {
            throw new EntityExistsException("Tên không được để trống");
        }
        if (form.getEmail() == null || form.getEmail().trim().isEmpty()) {
            throw new EntityExistsException("Email không được để trống");
        } else if (form.getEmail() == validEmail) {
            throw new EntityExistsException("Email không đúng định ");
        }
        if (form.getPhone() == null || form.getPhone().trim().isEmpty()) {
            throw new EntityExistsException("Phone không được để trống");
        } else if (form.getPhone() == validPhone) {
            throw new EntityExistsException("Phone không đúng định ");
        }
        if (form.getUsername() == null || form.getUsername().trim().isEmpty()) {
            throw new EntityExistsException("Tài khoản không được để trống");
        }
        if (form.getPassword() == null || form.getPassword().trim().isEmpty()) {
            throw new EntityExistsException("Mật khẩu không được để trống");
        } else if (form.getPassword().trim().length() < 6) {
            throw new EntityExistsException("Mật khẩu  phải trên 6 ký tự");
        }
        // lấy ra danh sách các quyền và chuyển thành đối tượng Users
        Set<Role> roles = new HashSet<>();
        if (form.getRoles() == null || form.getRoles().isEmpty()) {
            roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        } else {
            form.getRoles().stream().forEach(
                    role -> {
                        switch (role) {
                            case "admin":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                            case "user":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
                        }
                    }
            );
        }


        Users users = Users.builder()
                .name(form.getName())
                .email(form.getEmail())
                .phone(form.getPhone())
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .status(true)
                .roles(roles)
                .build();

        return userRepository.save(users);
    }

    @Override
    public Users unlockAndBlock(Long id) throws EntityExistsException {
        Optional<Users> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new EntityExistsException("ID User không tồn tại ");
        }
        Users userToUpdate = optionalUser.get();
        if (userToUpdate.getId()==1) {
            throw new EntityExistsException("ID User không tồn tại  ");
        }
        // Thực hiện cập nhật các thông tin của người dùng
        userToUpdate.setStatus(!userToUpdate.isStatus());
        // Lưu tài khoản sau khi cập nhật
        return userRepository.save(userToUpdate);
    }

    @Override
    public Users changePassword(Users users, ChangePassword changePassword) throws EntityExistsException {
        String oldPassword = changePassword.getOldPassword();
        String newPassword = changePassword.getNewPassword();
        // Mã hóa mật khẩu mới
        String encodedPassword = passwordEncoder.encode(newPassword);
        if (!passwordEncoder.matches(oldPassword, users.getPassword())) {
            throw new EntityExistsException("Mật khẩu không đúng");
        }
        users.setPassword(encodedPassword);
        userRepository.save(users);
        throw new EntityExistsException("Câp nhật mật khẩu thành công");
    }

    @Override
    public List<Users> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws EntityExistsException {
        String TK = forgotPasswordRequest.getUsername();
        String EM = forgotPasswordRequest.getEmail();
        String newPassword = forgotPasswordRequest.getNewPassword();
        List<Users> users = userRepository.findAll();
        boolean flag = false;

        for (Users u : users) {
            if (u.getUsername().equals(TK) && u.getEmail().equals(EM)) {
                mailService.sendEmail(u.getEmail(), "mã mật khẩu của bạn là", "mã MK");

                // Mã hóa mật khẩu mới
                String encodedPassword = passwordEncoder.encode(newPassword);
                u.setPassword(encodedPassword);
                userRepository.save(u);
                flag = true; // Đánh dấu rằng đã tìm thấy người dùng
                break; // Dừng vòng lặp sau khi tìm thấy người dùng
            }
        }

        if (!flag) {
            throw new EntityExistsException("Tài khoản hoặc email không đúng");
        }
        return null;

    }




//    @Override
//    public List<Users> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws EntityExistsException {
//        String TK = forgotPasswordRequest.getUsername();
//        String EM = forgotPasswordRequest.getEmail();
//        String newPassword = forgotPasswordRequest.getNewPassword();
//        List<Users> users = userRepository.findAll();
//        boolean flag = false;
//
//        for (Users u : users) {
//            if (u.getUsername().equals(TK) && u.getEmail().equals(EM)) {
//                // Tạo mã xác nhận ngẫu nhiên và gửi nó đến email của người dùng
//                String verificationCode = generateVerificationCode();
//                mailService.sendEmail(u.getEmail(), "Xác minh tài khoản", "Mã xác nhận của bạn: " + verificationCode);
//
//                // Lưu mã xác nhận và thời gian hết hạn của nó
//                verificationCode.wait(verificationCode, LocalDateTime.now().plusMinutes(15));
//
//                flag = true; // Đánh dấu rằng đã tìm thấy người dùng
//                break; // Dừng vòng lặp sau khi tìm thấy người dùng
//            }
//        }
//
//        if (!flag) {
//            throw new EntityExistsException("Tài khoản hoặc email không đúng");
//        }
//        return null;
//    }
//
////     Kiểm tra mã xác nhận và cập nhật mật khẩu
//    public void verifyAndChangePassword(String verificationCode, String newPassword) {
//        String verificationCodes = generateVerificationCode();
//        LocalDateTime expirationTime = verificationCodes.get(verificationCode);
//
//        if (expirationTime != null && LocalDateTime.now().isBefore(expirationTime)) {
//            // Tìm người dùng theo mã xác nhận
//            Optional<Users> userOptional = userRepository.findByVerificationCode(verificationCode);
//            if (userOptional.isPresent()) {
//                Users user = userOptional.get();
//                user.setPassword(passwordEncoder.encode(newPassword));
//                userRepository.save(user);
//            }
//        } else {
//            throw new EntityNotFoundException("Mã xác nhận không hợp lệ hoặc đã hết hạn.");
//        }
//    }
//
//    // Tạo mã xác nhận ngẫu nhiên
//    private String generateVerificationCode() {
//        int codeLength = 6;
//        String characters = "0123456789";
//        Random random = new Random();
//        StringBuilder code = new StringBuilder();
//
//        for (int i = 0; i < codeLength; i++) {
//            int index = random.nextInt(characters.length());
//            code.append(characters.charAt(index));
//        }
//
//        return code.toString();
//    }


}

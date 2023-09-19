package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.model.domain.CartItem;
import ra.model.domain.Role;
import ra.model.domain.RoleName;
import ra.model.domain.Users;
import ra.model.dto.request.FormSignUpDto;
import ra.repository.IUserRepository;
import ra.security.user_principle.UserPrinciple;
import ra.service.IRoleService;
import ra.service.IUserService;

import javax.persistence.EntityExistsException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }
    @Autowired
    private IRoleService roleService;
    @Override
    public Optional<Users> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<Users> findById(Long userId) {
        return  userRepository.findById(userId);
    }

    @Override
    public Users save(FormSignUpDto form)  throws EntityExistsException {
        if (userRepository.existsByUsername(form.getUsername())){
            throw new EntityExistsException("User is exists");
        }
        // lấy ra danh sách các quyền và chuyển thành đối tượng Users
        Set<Role> roles  = new HashSet<>();
        if (form.getRoles()==null||form.getRoles().isEmpty()){
            roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        }else {
            form.getRoles().stream().forEach(
                    role->{
                        switch (role){
                            case "admin":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                            case "user":
                                roles.add(roleService.findByRoleName(RoleName.ROLE_USER));}}
            );
        }


        Users users = Users.builder()
                .name(form.getName())
                .email(form.getEmail())
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .status(true)
                .roles(roles)
                 .build();

        return userRepository.save(users);
    }

    @Override
    public Users update(UserPrinciple userPrinciple, Long id) throws EntityExistsException{
        Optional<Users> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent() ) {
            throw new EntityExistsException("User not found with id: " + id);
        }
        Users userToUpdate = optionalUser.get();
        if (userToUpdate.getId() == 1 ){
            throw new EntityExistsException("User not found with id: " + id);
        }
        // Kiểm tra xem userPrinciple có quyền cập nhật tài khoản này không
//        if (!userPrinciple.getId().equals(userToUpdate.getId())) {
//            throw new EntityExistsException("You do not have permission to update this user");
//        }
        // Thực hiện cập nhật các thông tin của người dùng
        userToUpdate.setStatus(!userPrinciple.isStatus()); // Đặt trạng thái thành false (hoặc các cập nhật khác)
        // Lưu tài khoản sau khi cập nhật
        return userRepository.save(userToUpdate);
    }

}

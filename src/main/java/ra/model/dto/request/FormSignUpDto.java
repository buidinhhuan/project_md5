package ra.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;


import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormSignUpDto {
    @NotBlank(message = "Tên không được để trống")
    private String name;
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Không đúng định dạng Email")
    private String email;
    @NotBlank(message = "Số điện thoại không được để trống")
     private String phone;

    @NotBlank(message = "Tên tài khoản không được để trống")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải trên 6 ký tự")
    private String password;
    private List<String> roles;
 }

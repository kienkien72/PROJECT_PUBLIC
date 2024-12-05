package vn.ndkien.laptopshop.service.validator;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.ndkien.laptopshop.domain.auth.Register;
import vn.ndkien.laptopshop.service.UserService;

@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, Register> {

    private final UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(Register user, ConstraintValidatorContext context) {
        boolean valid = true;

        // Kt mật khẩu có trùng hay không
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            // Báo lỗi ở trường thông tin ConfirmPassword
            context.buildConstraintViolationWithTemplate("Mật khẩu không phải trùng khớp")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        if (this.userService.checkEmailExists(user.getEmail())) {
            context.buildConstraintViolationWithTemplate("Email đã tồn tại")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        return valid;
    }
}

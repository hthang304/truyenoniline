package online.hthang.truyenonline.validator;

import online.hthang.truyenonline.annotations.UniqueEmail;
import online.hthang.truyenonline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Huy Thang on 13/10/2018
 * @project truyenmvc
 */

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserService userService;

    private String message;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        try {
            valid = email != null && !userService.checkEmailExits(email);
        } catch (Exception e) {

        }
        if (!valid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}

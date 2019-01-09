package online.hthang.truyenonline.validator;

import online.hthang.truyenonline.annotations.UniqueUserName;
import online.hthang.truyenonline.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Huy Thang on 13/10/2018
 * @project truyenmvc
 */
public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {

    @Autowired
    private UserService userServicel;

    private String message;

    @Override
    public void initialize(UniqueUserName constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        try {
            valid = userName != null && !userServicel.checkUserNameExits(userName);
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

package online.hthang.truyenonline.annotations;

import online.hthang.truyenonline.validator.UniqueUserNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Huy Thang on 13/10/2018
 * @project truyenmvc
 */

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUserNameValidator.class)
@Documented
public @interface UniqueUserName {

    String message() default "{hthang.truyenmvc.user.uName.UniqueUserName.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
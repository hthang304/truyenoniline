package online.hthang.truyenonline.form;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author Huy Thang on 16/10/2018
 * @project truyenonline
 */

@NoArgsConstructor
@Data
public class ForgotUser implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "{hthang.truyenmvc.forgotuser.username.empty.message}")
    private String username;

    @NotEmpty(message = "{hthang.truyenmvc.forgotuser.email.empty.message}")
    private String email;
}

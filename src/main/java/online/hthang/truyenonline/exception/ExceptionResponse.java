package online.hthang.truyenonline.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Huy Thang on 28/11/2018
 * @project truyenonline
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    Boolean error;
    String message;
    String server;
    String myrate;
    Long myrating;

}

package online.hthang.truyenonline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Huy Thang on 28/11/2018
 * @project truyenonline
 */

@ResponseStatus(HttpStatus.LENGTH_REQUIRED)
public class HttpSizeException extends Exception {

    public HttpSizeException(String message) {
        super(message);
    }
}

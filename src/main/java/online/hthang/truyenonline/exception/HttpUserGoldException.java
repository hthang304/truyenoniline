package online.hthang.truyenonline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Huy Thang on 28/11/2018
 * @project truyenonline
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class HttpUserGoldException extends Exception {
    private static final String EXCEPTION_MESSAGE = "Số dư không đủ để thanh toán";

    public HttpUserGoldException() {
        super(EXCEPTION_MESSAGE);
    }
}

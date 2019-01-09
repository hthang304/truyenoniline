package online.hthang.truyenonline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class HttpNotFoundException extends Exception {

    private static final String EXCEPTION_MESSAGE = "Xin lỗi. Liên kết bạn tìm không có hoặc đã bị xóa.";

    public HttpNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }

}

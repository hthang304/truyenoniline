package online.hthang.truyenonline.exception;

import online.hthang.truyenonline.service.CategoryService;
import online.hthang.truyenonline.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final InformationService informationService;

    private final CategoryService categoryService;

    @Autowired
    public GlobalExceptionHandler(InformationService informationService, CategoryService categoryService) {
        this.informationService = informationService;
        this.categoryService = categoryService;
    }

    private void getMenuAndInfo(ModelAndView modelAndView, String title) {

        // Lấy Title Cho Page
        modelAndView.addObject("title", title);

        // Lấy List Category cho Menu
        modelAndView.addObject("categorylist", categoryService.getCategoryMenu());

        // Lấy Information của Web
        modelAndView.addObject("information", informationService.getWebInfomation());
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ModelAndView myError(NotFoundException ex) {
        ModelAndView mav = new ModelAndView("web/ErrorPage.html");
        mav.addObject("number", "404");
        mav.addObject("message", ex.getMessage());
        getMenuAndInfo(mav, "Trang này không tồn tại");

        return mav;
    }

    @ExceptionHandler(HttpNotLoginException.class)
    public final ResponseEntity< Object > handleUserHttpNotLoginException(HttpNotLoginException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpUserLockedException.class)
    public final ResponseEntity< Object > handleHttpUserLockedException(HttpUserLockedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpNotFoundException.class)
    public final ResponseEntity< Object > handleHttpNotFoundException(HttpNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpSizeException.class)
    public final ResponseEntity< Object > handleHttpSizeException(HttpSizeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.LENGTH_REQUIRED);
    }

    @ExceptionHandler(HttpUserGoldException.class)
    public final ResponseEntity< Object > handleHttpUserGoldException(HttpUserGoldException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMyException.class)
    public final ResponseEntity< Object > handleHttpMyException(HttpMyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
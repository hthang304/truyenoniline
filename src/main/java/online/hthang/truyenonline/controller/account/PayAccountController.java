package online.hthang.truyenonline.controller.account;

import online.hthang.truyenonline.entity.MyUserDetails;
import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.exception.NotFoundException;
import online.hthang.truyenonline.service.CategoryService;
import online.hthang.truyenonline.service.InformationService;
import online.hthang.truyenonline.service.UserService;
import online.hthang.truyenonline.utils.ConstantsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

/**
 * @author Huy Thang on 29/11/2018
 * @project truyenonline
 */

@Controller
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@RequestMapping(value = "/tai-khoan")
public class PayAccountController {

    private final static Logger logger = LoggerFactory.getLogger(PayAccountController.class);
    private final InformationService informationService;
    private final CategoryService categoryService;
    private final UserService userService;
    @Value("${hthang.truyenmvc.title.pay}")
    private String title;

    @Autowired
    public PayAccountController(InformationService informationService, CategoryService categoryService, UserService userService) {
        this.informationService = informationService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    private void getMenuAndInfo(Model model,
                                String title) {

        // Lấy Title Cho Page
        model.addAttribute("title", title);

        // Lấy List Category cho Menu
        model.addAttribute("categorylist", categoryService.getCategoryMenu());

        // Lấy Information của Webm
        model.addAttribute("information", informationService.getWebInfomation());
    }

    @RequestMapping("/nap-dau")
    public String defaultPage(Model model, Principal principal) throws NotFoundException {
        // Lấy Danh sách truyện đang đọc của người dùng
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        //Lấy thông tin Tài Khoản đăng nhập
        Optional< User > optionalUser = userService.getUserByID(loginedUser.getUser().getUID());
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("Tài khoản không tồn tại mời liên hệ admin để biết thêm thông tin");
        }
        User user = optionalUser.get();
        if (user.getUStatus().equals(ConstantsUtils.STATUS_DENIED)) {
            throw new NotFoundException("Tài khoản của bạn đã bị khóa mời liên hệ admin để biết thêm thông tin");
        }

        model.addAttribute("codePay", user.getUID() + "-" + user.getUName());

        getMenuAndInfo(model, title);

        return "web/account/payPage";
    }

    @RequestMapping("/log_xu/")
    public String logPaymentPage(Model model, Principal principal) throws NotFoundException {
        // Lấy Danh sách truyện đang đọc của người dùng
        MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
        //Lấy thông tin Tài Khoản đăng nhập
        Optional< User > optionalUser = userService.getUserByID(loginedUser.getUser().getUID());
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("Tài khoản không tồn tại mời liên hệ admin để biết thêm thông tin");
        }
        User user = optionalUser.get();
        if (user.getUStatus().equals(ConstantsUtils.STATUS_DENIED)) {
            throw new NotFoundException("Tài khoản của bạn đã bị khóa mời liên hệ admin để biết thêm thông tin");
        }

        model.addAttribute("codePay", user.getUID() + "-" + user.getUName());

        getMenuAndInfo(model, title);

        return "web/account/logPayPage";
    }
}

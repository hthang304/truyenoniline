package online.hthang.truyenonline.controller.web;

import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.service.CategoryService;
import online.hthang.truyenonline.service.InformationService;
import online.hthang.truyenonline.service.SecurityService;
import online.hthang.truyenonline.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Huy Thang on 12/10/2018
 * @project truyenmvc
 */

@Controller
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@RequestMapping("/dang-ky")
public class RegisterController {

    Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Value("${hthang.truyenmvc.title.register}")
    private String titleRegister;

    @Autowired
    private InformationService informationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    private void getMenuAndInfo(Model model, String title) {

        // Lấy Title Cho Page
        model.addAttribute("title", title);

        // Lấy List Category cho Menu
        model.addAttribute("categorylist", categoryService.getCategoryMenu());

        // Lấy Information của Web
        model.addAttribute("information", informationService.getWebInfomation());
    }

    @GetMapping
    public String showRegisterForm(final Model model) {
        logger.info("Get Dang ky");
        // Lấy List Category Menu
        getMenuAndInfo(model, titleRegister);

        model.addAttribute("user", new User());
        return "web/registerPage";
    }

    @PostMapping
    public String register(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {
        boolean hasError = result.hasErrors();
        if (hasError) {
            getMenuAndInfo(model, titleRegister);
            model.addAttribute("user", user);
            return "web/registerPage";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setUPass(passwordEncoder.encode(user.getPasswordRegister()));

        //Lưu Người dùng đăng ký trong database
        userService.registerUser(user);

        //Đăng nhập sau khi đăng ký thành công
        securityService.autologin(user.getUName(), user.getPasswordRegisterConfirm(), request);

        return "redirect:/";
    }
}

package online.hthang.truyenonline.controller.web;

import online.hthang.truyenonline.form.ForgotUser;
import online.hthang.truyenonline.entity.Mail;
import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.service.CategoryService;
import online.hthang.truyenonline.service.EmailService;
import online.hthang.truyenonline.service.InformationService;
import online.hthang.truyenonline.service.UserService;
import online.hthang.truyenonline.utils.WebUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Huy Thang on 15/10/2018
 * @project truyenmvc
 */

@Controller
@PropertySource(value = {"classpath:messages.properties",
        "classpath:ValidationMessages.properties"},
        encoding = "UTF-8")
@RequestMapping("/quen-mat-khau")
public class ResetPasswordController {

    Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);

    @Value("${hthang.truyenmvc.title.forgot}")
    private String titleForgot;

    @Value("${hthang.truyenmvc.forgotuser.notfound.message}")
    private String notFoundUser;

    @Value("${hthang.truyenmvc.email.form}")
    private String emailForm;

    @Value("${hthang.truyenmvc.email.display}")
    private String emailDisplay;

    @Value("${hthang.truyenmvc.email.subject}")
    private String emailSubject;

    @Value("${hthang.truyenmvc.email.signature}")
    private String emailSignature;

    @Value("${hthang.truyenmvc.email.url}")
    private String emailUrl;

    @Autowired
    private InformationService informationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private void getMenuAndInfo(Model model, String title) {

        // Lấy Title Cho Page
        model.addAttribute("title", title);

        // Lấy List Category cho Menu
        model.addAttribute("categorylist", categoryService.getCategoryMenu());

        // Lấy Information của Web
        model.addAttribute("information", informationService.getWebInfomation());
    }

    @GetMapping
    public String showForgotForm(final Model model) {

        logger.info("Get Quen Mat Khau");

        // Lấy List Category Menu
        getMenuAndInfo(model, titleForgot);

        model.addAttribute("user", new ForgotUser());

        return "web/forgotPage";
    }

    @PostMapping
    public String register(@Valid ForgotUser forgotUser, BindingResult result, Model model, RedirectAttributes redirect) {
        boolean hasError = result.hasErrors();
        if (!hasError) {
            User user = userService.getForgotUser(forgotUser.getUsername(), forgotUser.getEmail());
            System.out.println(user);
            if (user == null) {
                model.addAttribute("error", notFoundUser);
                hasError = true;
            } else {
                String newPassword = WebUtils.randomPassword();
                if (sendMail(user, newPassword)) {
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    user.setUPass(passwordEncoder.encode(newPassword));
                    userService.updateUser(user);
                    redirect.addFlashAttribute("success", "Mật khẩu của bạn đã được thay đổi! Mời vào email để xem mật khẩu mới!");
                    return "redirect:/dang-nhap";
                } else {
                    model.addAttribute("error", "Có lỗi xảy ra mong bạn vui lòng quay lại sau!");
                    hasError = true;
                }

            }
        }
        if (hasError) {
            getMenuAndInfo(model, titleForgot);
            model.addAttribute("user", forgotUser);
            return "web/forgotPage";
        }


        return "redirect:/";
    }

    private boolean sendMail(User user, String newPassword) {
        Mail mail = new Mail();
        mail.setFrom(emailForm);
        mail.setTo(user.getUEmail());
        mail.setSubject(emailSubject);
        mail.setFromDisplay(emailDisplay);
        logger.info("Send mail");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("name", user.getUDname());
        modelMap.put("url", emailUrl);
        modelMap.put("signature", emailSignature);
        modelMap.put("password", newPassword);
        mail.setModel(modelMap);
        return emailService.sendSimpleMessage(mail);
    }
}

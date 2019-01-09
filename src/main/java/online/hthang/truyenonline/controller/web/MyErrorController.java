package online.hthang.truyenonline.controller.web;

import online.hthang.truyenonline.service.CategoryService;
import online.hthang.truyenonline.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    final CategoryService categoryService;

    final InformationService informationService;

    @Autowired
    public MyErrorController(CategoryService categoryService, InformationService informationService) {
        this.categoryService = categoryService;
        this.informationService = informationService;
    }

    private void getMenuAndInfo(Model model, String title) {

        // Lấy Title Cho Page
        model.addAttribute("title", title);

        // Lấy List Category cho Menu
        model.addAttribute("categorylist", categoryService.getCategoryMenu());

        // Lấy Information của Web
        model.addAttribute("information", informationService.getWebInfomation());
    }

    @RequestMapping("/error")
    public String handleError(Model model) {
        String number = "404";
        String mss = "Trang này không tồn tại hoặc đã bị xóa";
        model.addAttribute("number", number);
        model.addAttribute("message", mss);
        getMenuAndInfo(model, "Trang này không tồn tại");
        return "web/ErrorPage";
    }

    @RequestMapping("/403error")
    public String handle403Error(Model model) {
        String number = "403";
        String mss = "Bạn Không đủ quyền đăng nhập trang/thực hiện hành động này!";
        model.addAttribute("number", number);
        model.addAttribute("message", mss);
        getMenuAndInfo(model, "Không đủ quyền");
        return "web/ErrorPage";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}


package online.hthang.truyenonline.controller.web;

import online.hthang.truyenonline.entity.Chapter;
import online.hthang.truyenonline.entity.MyUserDetails;
import online.hthang.truyenonline.entity.Story;
import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.projections.NewStory;
import online.hthang.truyenonline.projections.TopConverter;
import online.hthang.truyenonline.projections.TopStory;
import online.hthang.truyenonline.service.*;
import online.hthang.truyenonline.utils.ConstantsUtils;
import online.hthang.truyenonline.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * @author Huy Thang
 */

@Controller
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class HomeController {

    private final InformationService informationService;
    private final CategoryService categoryService;
    private final StoryService storyService;
    private final UserService userService;
    private final ChapterService chapterService;
    Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Value("${hthang.truyenmvc.title.home}")
    private String titleHome;
    @Value("${hthang.truyenmvc.title.login}")
    private String titleLogin;

    @Autowired
    public HomeController(InformationService informationService,
                          CategoryService categoryService,
                          StoryService storyService,
                          UserService userService,
                          ChapterService chapterService) {
        this.informationService = informationService;
        this.categoryService = categoryService;
        this.storyService = storyService;
        this.userService = userService;
        this.chapterService = chapterService;
    }

    private void getMenuAndInfo(Model model, String title) {

        // Lấy Title Cho Page
        model.addAttribute("title", title);

        // Lấy List Category cho Menu
        model.addAttribute("categorylist", categoryService.getCategoryMenu());

        // Lấy Information của Web
        model.addAttribute("information", informationService.getWebInfomation());
    }

    @RequestMapping(value = "/")
    public String homePage(Model model, Principal principal, HttpServletRequest request, HttpServletResponse response) {
        // Kiểm tra người dùng đã đăng nhập chưa
        if (principal != null) {
            // Lấy Danh sách truyện đang đọc của người dùng
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            User user = loginedUser.getUser();
            List< Chapter > chapterListFavorites = chapterService.getAllChapterFavoritesByUser(user.getUID());
            model.addAttribute("listFavorites", chapterListFavorites);
        } else {
            model.addAttribute("listFavorites", null);
        }
        //Lấy ngày bắt đầu của tuần
        Date firstDayOfWeek = DateUtils.getFirstDayOfWeek();

        //Lấy ngày kết thúc của tuần
        Date lastDayOfWeek = DateUtils.getLastDayOfWeek();

        // Lấy Danh Sách Truyện Top View trong Tuần
        List<TopStory> topStoryWeek = storyService
                .getTopStory(firstDayOfWeek, lastDayOfWeek,
                        ConstantsUtils.PAGE_DEFAULT, ConstantsUtils.RANK_SIZE)
                .getContent();
        model.addAttribute("topStoryWeek", topStoryWeek);

        // Lấy Danh Sách Truyện Mới Hoàn Thành
        List< Story > topCompleted = storyService.getNewStoryCompleted();

        model.addAttribute("storyCompleted", topCompleted);

        // Lấy Danh Sách Truyện Vip Mới Cập Nhật
        List<NewStory> topvipstory = storyService
                .getVipStoryNew(ConstantsUtils.PAGE_DEFAULT, ConstantsUtils.RANK_SIZE)
                .getContent();
        model.addAttribute("vipStory", topvipstory);
        getMenuAndInfo(model, titleHome);
        return "web/homePage";
    }

    @RequestMapping(value = "/dang-nhap")
    public String loginPage(Model model, Principal principal) {

        if (principal != null) {
            logger.info("Đã vào đây");
            return "redirect:/";
        }
        getMenuAndInfo(model, titleLogin);

        return "web/loginPage";
    }

    @PostMapping(value = "/search")
    public String getSearch(@RequestParam("txtKeyword") String searchKey) {
        return "redirect:/tim-kiem/" + UriUtils.encode(searchKey, "UTF-8");
    }

}

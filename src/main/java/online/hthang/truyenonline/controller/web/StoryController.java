package online.hthang.truyenonline.controller.web;

import online.hthang.truyenonline.entity.Chapter;
import online.hthang.truyenonline.entity.MyUserDetails;
import online.hthang.truyenonline.entity.Story;
import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.exception.NotFoundException;
import online.hthang.truyenonline.projections.ChapterOfStory;
import online.hthang.truyenonline.projections.CommentSummary;
import online.hthang.truyenonline.projections.SearchStory;
import online.hthang.truyenonline.projections.StorySummary;
import online.hthang.truyenonline.service.*;
import online.hthang.truyenonline.utils.ConstantsListUtils;
import online.hthang.truyenonline.utils.ConstantsUtils;
import online.hthang.truyenonline.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Huy Thang on 17/10/2018
 * @project truyenonline
 */

@Controller
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@RequestMapping("/truyen")
public class StoryController {

    private static final Logger logger = LoggerFactory.getLogger(StoryController.class);
    private final InformationService informationService;
    private final CategoryService categoryService;
    private final StoryService storyService;
    private final SratingService sratingService;
    private final UfavoritesService ufavoritesService;

    @Autowired
    public StoryController(InformationService informationService,
                           CategoryService categoryService,
                           StoryService storyService,
                           SratingService sratingService, UfavoritesService ufavoritesService) {
        this.informationService = informationService;
        this.categoryService = categoryService;
        this.storyService = storyService;
        this.sratingService = sratingService;
        this.ufavoritesService = ufavoritesService;
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

    @RequestMapping("/{sID}")
    public String defaultStoryController(@PathVariable("sID") String sID,
                                         Principal principal,
                                         Model model) throws Exception {

        StorySummary story = checkStoryID(sID);

        model.addAttribute("story", story);

        User user = getUserLogin(principal);

        getRating(model, user, story);

        getListStoryOfConverter(model, story);

        getMenuAndInfo(model, story.getvnName());

        checkConverter(model, user, story);

        getChapterReadByUser(user, story.getsID(), model);

        return "web/storyPage";
    }

    private StorySummary checkStoryID(String sID) throws NotFoundException {

        // Kiểm tra sID != null
        // Kiểm tra sID có phải kiểu long
        if (sID == null || !WebUtils.checkLongNumber(sID)) {
            throw new NotFoundException();
        }

        // Lấy Category theo cID
        Optional< StorySummary > storyOptional = storyService.getStoryBySIDAndStatus(Long.parseLong(sID)
                , ConstantsListUtils.LIST_STORY_DISPLAY);

        if (!storyOptional.isPresent()) {
            throw new NotFoundException();
        }
        return storyOptional.get();
    }

    // Kiểm Tra Người Dùng Đã Đăng Nhập Chưa
    private User getUserLogin(Principal principal) {
        User user = null;
        if (principal != null) {

            // Lấy Người Dùng đang đăng nhập
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            user = loginedUser.getUser();
        }
        return user;
    }

    // Kiểm Tra Rating
    private void getRating(Model model,
                           User user,
                           StorySummary story) {
        // Chưa đăng nhập
        boolean checkRating = false;
        if (user != null) {
            //Nếu người đọc là người đăng thì tính là đã đánh giá
            if (story.getuID().equals(user.getUID())) {
                // Người đọc là Converter
                checkRating = true;
            } else {

                // Kiểm tra Người dùng đã đánh giá chưa
                if (sratingService
                        .checkRatingWithUser(story.getsID(), user.getUID())) {
                    // Người dùng đã đánh giá
                    checkRating = true;
                }
            }
        }
        model.addAttribute("countRating", sratingService.getSumRaitingOfStory(story.getsID()));
        model.addAttribute("rating", checkRating);
    }

    // Kiểm Tra Converter
    private void checkConverter(Model model,
                                User user,
                                StorySummary story) {

        boolean checkConverter = false;
        if (user != null) {
            checkConverter = Objects.equals(user.getUID(), story.getuID());
        }
        model.addAttribute("checkConverter", checkConverter);
    }

    // Lấy List Truyện Của Người Đăng
    private void getListStoryOfConverter(Model model,
                                         StorySummary story) {
        List< SearchStory > list = storyService
                .getListStoryOfConverter(story.getuID(), ConstantsListUtils.LIST_STORY_DISPLAY);

        model.addAttribute("storyConverter", list);
    }

    private void getChapterReadByUser(User user, Long sID, Model model) {
        Chapter chapter = null;
        if (user != null) {
            chapter = ufavoritesService.getChapterReadNewByUser(user.getUID(), sID);
        }
        model.addAttribute("readChapter", chapter);
    }
}

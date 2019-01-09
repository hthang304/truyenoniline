package online.hthang.truyenonline.controller.web;

import online.hthang.truyenonline.entity.Category;
import online.hthang.truyenonline.projections.NewStory;
import online.hthang.truyenonline.projections.TopStory;
import online.hthang.truyenonline.exception.NotFoundException;
import online.hthang.truyenonline.service.CategoryService;
import online.hthang.truyenonline.service.InformationService;
import online.hthang.truyenonline.service.StoryService;
import online.hthang.truyenonline.utils.ConstantsUtils;
import online.hthang.truyenonline.utils.DateUtils;
import online.hthang.truyenonline.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Huy Thang
 */
@Controller
@RequestMapping("/the-loai")
public class CategoryController {

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final InformationService informationService;

    private final CategoryService categoryService;

    private final StoryService storyService;

    @Autowired
    public CategoryController(InformationService informationService, CategoryService categoryService, StoryService storyService) {
        this.informationService = informationService;
        this.categoryService = categoryService;
        this.storyService = storyService;
    }

    private Category checkCategoryID(String cid) throws NotFoundException {

        // Kiểm tra cid != null
        // Kiểm tra cid có phải kiểu int
        if (cid == null || !WebUtils.checkIntNumber(cid)) {
            throw new NotFoundException();
        }

        // Lấy Category theo cID
        Optional<Category> category = categoryService.getCategoryByID(Integer.parseInt(cid));

        if (!category.isPresent()) {
            throw new NotFoundException();
        }
        return category.get();
    }

    private void getMenuAndInfo(Model model, String title) {

        // Lấy Title Cho Page
        model.addAttribute("title", title);

        // Lấy List Category cho Menu
        model.addAttribute("categorylist", categoryService.getCategoryMenu());

        // Lấy Information của Web
        model.addAttribute("information", informationService.getWebInfomation());
    }

    private void loadData(Category category, int pagenumber, Model model) {
        Page<NewStory> page = storyService.getStoryNewByCID(category.getCID(), pagenumber, ConstantsUtils.PAGE_SIZE_DEFAULT);

        // Lấy tổng số trang
        int total = page.getTotalPages();

        // Kiểm tra tổng số trang có nhỏ hơn pagenumber không
        if (total < pagenumber) {
            pagenumber = ConstantsUtils.PAGE_DEFAULT;
            page = storyService.getVipStoryNew(pagenumber, ConstantsUtils.PAGE_SIZE_DEFAULT);
            total = page.getTotalPages();
        }

        // Lấy List Story
        List<NewStory> lstStory = page.getContent();

        // Lấy số trang hiện tại
        int current = page.getNumber() + 1;

        // Lấy số trang bắt đầu
        int begin = Math.max(1, current - 2);

        //Lấy số trang kết thúc
        int end = Math.min(begin + 4, page.getTotalPages());

        String urlIndex = "/the-loai/" + category.getCID() + "/"+ category.getCMetatitle();
        model.addAttribute("listStory", lstStory);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("totalIndex", total);
        model.addAttribute("currentIndex", current);
        model.addAttribute("urlIndex", urlIndex);
    }

    private void loadTopView(Model model, Category category) {
        //Lấy ngày bắt đầu của tháng
        Date firstDayOfMonth = DateUtils.getFirstDayOfMonth();

        //Lấy ngày bắt đầu của tuần
        Date firstDayOfWeek = DateUtils.getFirstDayOfWeek();

        //Lấy ngày kết thúc của tháng
        Date lastDayOfMonth = DateUtils.getLastDayOfMonth();

        //Lấy ngày kết thúc của tuần
        Date lastDayOfWeek = DateUtils.getLastDayOfWeek();

        //Lấy Top View Truyện Vip Trong Tháng
        List<TopStory> listMonthTop = storyService.getTopStoryByCID(firstDayOfMonth, lastDayOfMonth, category.getCID(),
                ConstantsUtils.PAGE_DEFAULT, ConstantsUtils.RANK_SIZE)
                .get()
                .collect(Collectors.toList());

        //Lấy Top View Truyện Vip Trong Tuần
        List<   TopStory> listWeekTop = storyService.getTopStoryByCID(firstDayOfWeek, lastDayOfWeek, category.getCID(),
                ConstantsUtils.PAGE_DEFAULT, ConstantsUtils.RANK_SIZE)
                .get()
                .collect(Collectors.toList());

        model.addAttribute("topmonthview", listMonthTop);

        model.addAttribute("topview", listWeekTop);
    }

    @RequestMapping("/{cid}")
    public String theloaiPage(@PathVariable("cid") String cid) throws NotFoundException {

        // Lấy Category theo cID
        Category category = checkCategoryID(cid);

        return "redirect:/the-loai/" + cid + "/" + category.getCMetatitle();
    }

    @RequestMapping("/{cid}/{cmetaTitle}")
    public String theloaiPage(@PathVariable("cid") String cid,
                              @PathVariable("cmetaTitle") String cmetaTitle,
                              Model model) throws NotFoundException {

        Category category = checkCategoryID(cid);

        // Kiểm tra Metatitle có đúng hay không
        // Nếu không chuyển trang sang định dạng đúng
        if (!cmetaTitle.equalsIgnoreCase(category.getCMetatitle())) {

            return "redirect:/the-loai/" + cid + "/" + category.getCMetatitle();

        }

        String title = "Truyện " + category.getCName();

        // Lấy Thông tin Category cho menu và Information Web
        getMenuAndInfo(model, title);

        loadData(category, ConstantsUtils.PAGE_DEFAULT, model);

        loadTopView(model, category);

        return "web/categoryPage";
    }

    @RequestMapping("/{cid}/{cmetaTitle}/trang-{page}")
    public String doLoadStoryByCategoryAndPage(@PathVariable("cid") String cid,
                                               @PathVariable("cmetaTitle") String cmetaTitle,
                                               @PathVariable("page") String page,
                                               Model model) throws NotFoundException {

        Category category = checkCategoryID(cid);
        // Kiểm tra Metatitle có đúng hay không
        // Nếu không chuyển trang sang định dạng đúng
        if (!cmetaTitle.equalsIgnoreCase(category.getCMetatitle())) {

            return "redirect:/the-loai/" + cid + "/" + category.getCMetatitle() + "/trang-" + page;
        }


        int pagenumber = WebUtils.checkPageNumber(page);

        String title = "Truyện " + category.getCName();

        // Lấy Thông tin Category cho menu và Information Web
        getMenuAndInfo(model, title);

        loadData(category, pagenumber, model);

        loadTopView(model, category);

        return "web/categoryPage";
    }
}

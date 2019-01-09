package online.hthang.truyenonline.restful;

import online.hthang.truyenonline.projections.NewStory;
import online.hthang.truyenonline.projections.SearchStory;
import online.hthang.truyenonline.projections.TopConverter;
import online.hthang.truyenonline.projections.TopStory;
import online.hthang.truyenonline.service.StoryService;
import online.hthang.truyenonline.service.UserService;
import online.hthang.truyenonline.utils.ConstantsListUtils;
import online.hthang.truyenonline.utils.ConstantsUtils;
import online.hthang.truyenonline.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author Huy Thang on 25/12/2018
 * @project truyenonline
 */

@RestController
@RequestMapping(value = "/api/home")
public class HomeRestfulController {

    private final StoryService storyService;

    private final UserService userService;

    @Autowired
    public HomeRestfulController(StoryService storyService, UserService userService) {
        this.storyService = storyService;
        this.userService = userService;
    }

    //Lấy Top 3 Truyện Mới Của Converter
    @PostMapping(value = "/topViewMonth")
    public ResponseEntity< ? > loadStoryTopViewMonth() {
        //Lấy ngày bắt đầu của tháng
        Date firstDayOfMonth = DateUtils.getFirstDayOfMonth();

        //Lấy ngày kết thúc của tháng
        Date lastDayOfMonth = DateUtils.getLastDayOfMonth();

        // Lấy Danh Sách Truyện Top View trong tháng
        List< TopStory > topstory = storyService
                .getTopStory(firstDayOfMonth, lastDayOfMonth,
                        ConstantsUtils.PAGE_DEFAULT, ConstantsUtils.RANK_SIZE)
                .getContent();
        return new ResponseEntity<>(topstory, HttpStatus.OK);
    }

    //Lấy Top 3 Truyện Mới Của Converter
    @PostMapping(value = "/storyNewUpdate")
    public ResponseEntity< ? > loadStoryNewUpdate() {
        // Lấy Danh Sách Truyện Mới Cập Nhật
        List< NewStory > listNewStory = storyService
                .getStoryNew(ConstantsUtils.PAGE_DEFAULT, ConstantsUtils.PAGE_SIZE_HOME)
                .getContent();
        return new ResponseEntity<>(listNewStory, HttpStatus.OK);
    }

    //Lấy Top 3 Truyện Mới Của Converter
    @PostMapping(value = "/storyVipNew")
    public ResponseEntity< ? > loadStoryVipNew() {
        //Lấy ngày bắt đầu của tuần
        Date firstDayOfWeek = DateUtils.getFirstDayOfWeek();

        //Lấy ngày kết thúc của tuần
        Date lastDayOfWeek = DateUtils.getLastDayOfWeek();

        // Lấy Danh Sách Truyện Vip Top trong tuần
        List<NewStory> topvipstory = storyService
                .getVipStoryNew(ConstantsUtils.PAGE_DEFAULT, ConstantsUtils.RANK_SIZE)
                .getContent();
        return new ResponseEntity<>(topvipstory, HttpStatus.OK);
    }

    //Lấy Top 3 Truyện Mới Của Converter
    @PostMapping(value = "/topConveter")
    public ResponseEntity< ? > loadStoryOfConverter() {
        // Lấy Danh Sách Top Converter
        List< TopConverter > topConverters = userService
                .getTopConverter(ConstantsUtils.PAGE_DEFAULT, ConstantsUtils.RANK_SIZE);

        return new ResponseEntity<>(topConverters, HttpStatus.OK);
    }
}

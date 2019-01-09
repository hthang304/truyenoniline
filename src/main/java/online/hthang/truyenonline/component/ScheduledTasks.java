package online.hthang.truyenonline.component;

import online.hthang.truyenonline.service.ChapterService;
import online.hthang.truyenonline.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Huy Thang on 22/11/2018
 * @project truyenonline
 */

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private final ChapterService chapterService;

    @Autowired
    public ScheduledTasks(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @Scheduled(fixedRate = 300000)
    public void updateStatusVipChapter() {
        logger.info("Cập Nhật Trạng Thái Chương Vip");
        chapterService.updateStatusChapterVip();
    }
}

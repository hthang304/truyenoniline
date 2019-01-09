package online.hthang.truyenonline.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * @author Huy Thang on 23/12/2018
 * @project truyenonline
 */
public interface ConveterSummary {

    Long getUID();

    String getUName();

    String getUDname();

    String getUAvatar();

    Double getGold();

    Date getCreateDate();

    @Value("#{@myComponent.countChapterOfUser(target.uID)}")
    Long getCountChapter();

    @Value("#{@myComponent.countStoryOfUser(target.uID)}")
    Long getCountStory();
}

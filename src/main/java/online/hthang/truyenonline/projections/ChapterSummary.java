package online.hthang.truyenonline.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * @author Huy Thang on 20/11/2018
 * @project truyenonline
 */
public interface ChapterSummary {

    Long getChID();

    Integer getChNumber();

    String getChName();

    @Value("#{target.story.sID}")
    Long getsID();

    Integer getChStatus();

    @Value("#{@myComponent.getBetewwen(target.createDate)}")
    String getTimeUpdate();
}

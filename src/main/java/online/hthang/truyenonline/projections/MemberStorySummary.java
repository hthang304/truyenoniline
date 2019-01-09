package online.hthang.truyenonline.projections;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Huy Thang on 20/11/2018
 * @project truyenonline
 */
public interface MemberStorySummary {

    Long getsID();

    String getVnName();

    String getsImages();

    String getsAuthor();

    Long getsView();

    Integer getSDealStatus();

    @Value("#{@myComponent.getBetewwen(target.sUpdate)}")
    String getTimeUpdate();

    @Value("#{@myComponent.getNewChapter(target.sID)}")
    ChapterSummary getNewChapter();

}

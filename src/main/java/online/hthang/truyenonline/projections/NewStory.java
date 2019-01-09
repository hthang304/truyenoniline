package online.hthang.truyenonline.projections;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Huy Thang
 */

public interface NewStory {

    Long getSID();

    String getVnName();

    String getSImages();

    String getSAuthor();

    String getSInfo();

    @Value("#{@myComponent.getBetewwen(target.sUpdate)}")
    String getTimeUpdate();

    Long getChID();

    Integer getChNumber();

    String getUDname();

    String getUName();

    Integer getSDealStatus();

}

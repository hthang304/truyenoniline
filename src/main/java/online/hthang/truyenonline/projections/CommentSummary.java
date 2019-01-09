package online.hthang.truyenonline.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * @author Huy Thang on 05/12/2018
 * @project truyenonline
 */
public interface CommentSummary {

    Long getComID();

    String getContent();

    @Value(value = "#{@myComponent.getBetewwen(target.createDate)}")
    String getTimeUpdate();

    UserSummary getUser();

}

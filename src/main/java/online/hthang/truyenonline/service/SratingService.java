package online.hthang.truyenonline.service;

import online.hthang.truyenonline.entity.UserRating;

import java.util.Date;
import java.util.Optional;

/**
 * @author Huy Thang
 */
public interface SratingService {

    /**
     * Kiểm tra Tồn Tại UserRating theo
     *
     * @param sID
     * @param uID
     * @return "true" nếu đã có bình chọn, "false" nếu chưa có bình chọn phù hợp
     */
    boolean checkRatingWithUser(Long sID, Long uID);

    /**
     * Kiểm tra Tồn Tại UserRating theo
     *
     * @param sID
     * @param locationIP
     * @return Optional<UserRating>
     */
    Optional< UserRating > checkRatingWithLocationIP(Long sID, String locationIP, Date startDate, Date endDate);

    /**
     * Đếm số lượng đánh giá
     *
     * @param sID
     * @return Long
     */
    Long getSumRaitingOfStory(Long sID);

    /**
     * Thực Hiện Đánh giá
     *
     * @param uID
     * @param sID
     * @param locationIP
     * @param rating
     * @return Float
     */
    Float saveRating(Long uID, Long sID, String locationIP, Integer rating);
}

package online.hthang.truyenonline.service.impl;

import online.hthang.truyenonline.entity.UserRating;
import online.hthang.truyenonline.repository.UserRatingRepository;
import online.hthang.truyenonline.service.SratingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author Huy Thang
 */
@Service
public class UserRatingServiceImpl implements SratingService {

    @Autowired
    private UserRatingRepository userRatingRepository;

    /**
     * Kiểm tra Tồn Tại UserRating theo
     *
     * @param uID
     * @param sID
     * @return "true" nếu đã có bình chọn, "false" nếu chưa có bình chọn phù hợp
     */
    @Override
    public boolean checkRatingWithUser(Long sID, Long uID) {
        return userRatingRepository
                .existsSratingByStory_sIDAndUser_uID(sID, uID);
    }

    /**
     * Kiểm tra Tồn Tại UserRating theo
     *
     * @param sID
     * @param locationIP
     * @param startDate
     * @param endDate
     * @return Optional<UserRating>
     */
    @Override
    public Optional< UserRating > checkRatingWithLocationIP(Long sID, String locationIP, Date startDate, Date endDate) {
        return userRatingRepository
                .findByStory_sIDAndLocationIPAndCreateDateBetween(sID, locationIP, startDate, endDate);
    }

    /**
     * Đếm số lượng đánh giá của Truyện
     *
     * @param sID
     * @return Long
     */
    @Override
    public Long getSumRaitingOfStory(Long sID) {
        return userRatingRepository
                .countByStory_sID(sID);
    }

    /**
     * Thực Hiện Đánh giá
     *
     * @param uID
     * @param sID
     * @param locationIP
     * @param rating
     * @return true / false
     */
    @Override
    public Float saveRating(Long uID, Long sID, String locationIP, Integer rating) {
        return userRatingRepository
                .saveRating(uID,sID,locationIP,rating);
    }
}

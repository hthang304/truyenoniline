package online.hthang.truyenonline.service;

import online.hthang.truyenonline.entity.Chapter;
import online.hthang.truyenonline.entity.User;

import java.util.Date;

/**
 * @author Huy Thang
 */
public interface PayService {

    /**
     * Kiểm Tra đã tồn tại thanh toán trong khoảng
     *
     * @param chID
     * @param uID
     * @param startDate
     * @param endDate
     *
     * @return true/false
     */
    boolean checkDealStoryVip(Long chID, Long uID, Date startDate, Date endDate);

    boolean saveDealChapter(Chapter chapter, User user);

    /**
     * Lưu Giao Dịch Với Trạng Thái
     *
     * @param uID
     * @param price
     * @return true/false
     */
    void savePay(Long uID , Double price, Integer status);
}

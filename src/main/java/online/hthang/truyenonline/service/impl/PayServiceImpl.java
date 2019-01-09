package online.hthang.truyenonline.service.impl;

import online.hthang.truyenonline.entity.Chapter;
import online.hthang.truyenonline.entity.Pay;
import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.repository.PayRepository;
import online.hthang.truyenonline.service.PayService;
import online.hthang.truyenonline.utils.ConstantsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Huy Thang
 */
@Service
public class PayServiceImpl implements PayService {

    private final PayRepository payRepository;

    @Autowired
    public PayServiceImpl(PayRepository payRepository) {
        this.payRepository = payRepository;
    }

    /**
     * Kiểm Tra đã tồn tại thanh toán trong khoảng
     *
     * @param chID
     * @param uID
     * @param startDate
     * @param endDate
     * @return true/false
     */
    @Override
    public boolean checkDealStoryVip(Long chID, Long uID, Date startDate, Date endDate) {
        return payRepository
                .existsByChapter_ChIDAndPayer_uIDAndCreateDateBetweenAndPayStatus(chID, uID,
                        startDate, endDate, ConstantsUtils.PAY_STATUS);
    }

    @Override
    public boolean saveDealChapter(Chapter chapter, User user) {
        return payRepository
                .transferPayChapter(user.getUID(),
                        chapter.getUser().getUID(),
                        chapter.getChID(),
                        chapter.getPrice(),
                        ConstantsUtils.PAY_STATUS);
    }

    /**
     * Lưu Giao Dịch Với Trạng Thái
     *
     * @param uID
     * @param price
     * @param status
     * @return true/false
     */
    @Override
    public void savePay(Long uID, Double price, Integer status) {
        User user = new User();
        user.setUID(uID);
        Pay pay = new Pay();
        pay.setPayer(user);
        pay.setPrice(price);
        pay.setPayStatus(status);
        payRepository.save(pay);
    }
}
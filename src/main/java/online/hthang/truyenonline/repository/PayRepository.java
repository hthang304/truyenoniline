package online.hthang.truyenonline.repository;

import online.hthang.truyenonline.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author Huy Thang
 */

@Repository
public interface PayRepository extends JpaRepository<Pay, Long> {

    /**
     * Kiểm Tra đã tồn tại thanh toán trong khoảng
     *
     * @param chID
     * @param uID
     * @param startDate
     * @param endDate
     * @param payStatus
     * @return true - nếu tồn tại/false - nếu không tồn tại
     */
    boolean existsByChapter_ChIDAndPayer_uIDAndCreateDateBetweenAndPayStatus(Long chID,
                                                                                Long uID,
                                                                                Date startDate,
                                                                                Date endDate,
                                                                                Integer payStatus);

    /**
     * Thực Hiện Thanh Toán
     *
     * @param payerID
     * @param receiverID
     * @param chapterID
     * @param price
     * @param payStatus
     * @return true - nếu thanh toán thành công / false - nếu thanh toán thất bại và roll back dữ liệu
     */
    @Procedure("payChapter")
    boolean transferPayChapter(@Param("payerID") Long payerID,
                               @Param("receiverID") Long receiverID,
                               @Param("chapterID") Long chapterID,
                               @Param("price") Double price,
                               @Param("payStatus") Integer payStatus);
}

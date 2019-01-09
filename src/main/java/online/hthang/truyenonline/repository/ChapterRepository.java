package online.hthang.truyenonline.repository;

import online.hthang.truyenonline.entity.Chapter;
import online.hthang.truyenonline.projections.ChapterOfStory;
import online.hthang.truyenonline.projections.ChapterSummary;
import online.hthang.truyenonline.utils.ConstantsQueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Huy Thang
 */
@Repository
public interface ChapterRepository extends JpaRepository< Chapter, Long > {

    @Query(value = ConstantsQueryUtils.LIST_ALL_FAVORITES_CHAPTER,
            nativeQuery = true)
    List< Chapter > getAllFavoritesByUser(@Param("uID") Long uID, @Param("ufStatus") Integer ufStatus
            , @Param("chStatus") List< Integer > listChStatus, @Param("sStatus") Integer sStatus);

    /**
     * Lấy Chapter Theo
     *
     * @param chID
     * @param sID
     * @param listStatus
     * @return Optional<Chapter>
     */
    Optional< Chapter > findChapterByChIDAndStory_sIDAndChStatusIn(Long chID, Long sID, List< Integer > listStatus);

    /**
     * Lấy Chapter Theo
     *
     * @param chID
     * @param listStatus
     * @return Optional<Chapter>
     */
    Optional< Chapter > findChapterByChIDAndChStatusIn(Long chID, List< Integer > listStatus);

    /**
     * Lấy Chapter ID Tiếp Theo
     *
     * @param chSerial
     * @param sID
     * @param listStatus
     * @return Optional<Long>
     */
    @Query(value = ConstantsQueryUtils.NEXT_CHAPTER,
            nativeQuery = true)
    Optional< Long > getNextChapter(@Param("chSerial") float chSerial, @Param("sID") Long sID, @Param("chStatus") List< Integer > listStatus);

    /**
     * Lấy Chapter ID Trước
     *
     * @param chSerial
     * @param sID
     * @param listStatus
     * @return Optional<Long>
     */
    @Query(value = ConstantsQueryUtils.PREVIOUS_CHAPTER,
            nativeQuery = true)
    Optional< Long > getPreviousChapter(@Param("chSerial") float chSerial, @Param("sID") Long sID, @Param("chStatus") List< Integer > listStatus);

    /**
     * Lấy Chapter ID Đầu Tiên
     *
     * @param sID
     * @param listStatus
     * @return Optional<Long>
     */
    @Query(value = ConstantsQueryUtils.CHAPTER_HEAD,
            nativeQuery = true)
    Optional< ChapterSummary > getChapterHead(@Param("sID") Long sID, @Param("chStatus") List< Integer > listStatus);

    /**
     * Lấy Chapter ID Mới Nhất
     *
     * @param sID
     * @param listStatus
     * @return Optional<Long>
     */
    @Query(value = ConstantsQueryUtils.CHAPTER_NEW,
            nativeQuery = true)
    Optional< ChapterSummary > getNewChapter(@Param("sID") Long sID, @Param("chStatus") List< Integer > listStatus);

    /**
     * Cập Nhật Status Chapter Vip Khi Đến Hạn Dealine
     *
     * @param status
     * @param vipStatus
     */
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE chapter ch SET ch.chStatus = :status"
            + " WHERE ch.chStatus= :vipStatus AND ch.dealine<=NOW()", nativeQuery = true)
    void updateStatusChapterVip(@Param("status") Integer status,
                                @Param("vipStatus") Integer vipStatus);

    /**
     * Đếm số chương đăng bởi uID
     *
     * @param uID
     * @param listStatus
     * @return Long
     */
    Long countByUser_uIDAndChStatusIn(Long uID, List< Integer > listStatus);

    /**
     * Lấy CHương Theo Truyện
     *
     * @param sID
     * @param listStatus
     * @return Page
     */
    Page< ChapterOfStory > findByStory_sIDAndChStatusInOrderByChSerialDesc(Long sID,
                                                                             List< Integer > listStatus,
                                                                             Pageable pageable);

    /**
     * Lấy Tất Cả CHương Theo Truyện
     *
     * @param sID
     * @param listStatus
     */
    List< ChapterOfStory > findByStory_sIDAndChStatusInOrderByChSerialDesc(Long sID,
                                                                               List< Integer > listStatus);
}

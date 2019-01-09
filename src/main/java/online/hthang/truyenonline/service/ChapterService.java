package online.hthang.truyenonline.service;

import online.hthang.truyenonline.entity.Chapter;
import online.hthang.truyenonline.projections.ChapterOfStory;
import online.hthang.truyenonline.projections.ChapterSummary;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * @author Huy Thang
 */
public interface ChapterService {

    /**
     * Lấy List Tất Cả Chapter Favorites Của Người Dùng
     *
     * @param uID
     * @return List<Chapter>
     */
    List< Chapter > getAllChapterFavoritesByUser(Long uID);

    /**
     * Lấy Chapter Theo
     *
     * @param sID
     * @param chID
     * @return Optional<Chapter>
     */
    Optional< Chapter > getChapterBySIDAndChID(Long sID, Long chID);

    /**
     * Cập nhật Chapter
     *
     * @param chapter
     * @return void
     */
    void updateChapter(Chapter chapter);

    /**
     * Lấy Chapter ID Tiếp Theo
     *
     * @param chSerial
     * @param sID
     * @return Long
     */
    Long getNextChapterID(float chSerial, Long sID);

    /**
     * Lấy Chapter ID Trước
     *
     * @param chSerial
     * @param sID
     * @return Long
     */
    Long getPreviousChapterID(float chSerial, Long sID);

    /**
     * Lấy Chapter Theo
     *
     * @param chID
     * @return Chapter
     */
    Chapter getChapterByID(Long chID);

    /**
     * Lấy Chapter ID Chương Đầu
     *
     * @param sID
     * @param listStatus
     * @return ChapterSummary
     */
    ChapterSummary getChapterIDHead(Long sID, List< Integer > listStatus);

    /**
     * Lấy Chapter ID Chương mới nhất
     *
     * @param sID
     * @param listStatus
     * @return Long
     */
    ChapterSummary getChapterIDNew(Long sID, List< Integer > listStatus);

    /**
     * Cập Nhật Trạng Thái Chapter Vip nếu đến dealine
     */
    void updateStatusChapterVip();

    /**
     * Đếm số chương đăng bởi uID
     *
     * @param uID
     * @param listStatus
     * @return Long
     */
    Long countChapterByUser(Long uID, List< Integer > listStatus);

    /**
     * Lấy Chapter Theo Story
     *
     * @param sID
     * @param listStatus
     * @param page
     * @param size
     * @return Page<ChapterOfStory>
     */
    Page< ChapterOfStory > getChapterOfStory(Long sID, List< Integer > listStatus, int page, int size);

    /**
     * Lấy Chapter Theo Story
     *
     * @param sID
     * @param listStatus
     * @return List<ChapterOfStory>
     */
    List< ChapterOfStory > getAllChapterOfStory(Long sID, List< Integer > listStatus);
}

package online.hthang.truyenonline.service;

import online.hthang.truyenonline.entity.Story;
import online.hthang.truyenonline.projections.*;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Huy Thang
 */
public interface StoryService {

    /**
     * Lấy List Truyện Mới Cập Nhật
     *
     * @param page
     * @param size
     * @return Page<NewStory>
     */
    Page< NewStory > getStoryNew(int page, int size);

    /**
     * Lấy List Truyện Vip Mới Cập Nhật
     *
     * @param page
     * @param size
     * @return Page<NewStory>
     */
    Page< NewStory > getVipStoryNew(int page, int size);

    /**
     * Lấy List Truyện Mới Cập Nhật theo Category
     *
     * @param cID
     * @param page
     * @param size
     * @return Page<NewStory>
     */
    Page< NewStory > getStoryNewByCID(Integer cID, int page, int size);

    /**
     * Lấy List Truyện Mới Cập Nhật theo Category
     *
     * @param cID
     * @param page
     * @param size
     * @param endDate
     * @param startDate
     * @return Page<TopStory>
     */
    Page< TopStory > getTopStoryByCID(Date startDate, Date endDate, Integer cID, int page, int size);

    /**
     * Lấy List Truyện Top View Trong Khoảng
     *
     * @param startDate
     * @param endDate
     * @param page
     * @param size
     * @return Page<TopStory>
     */
    Page< TopStory > getTopStory(Date startDate, Date endDate, int page, int size);

    /**
     * Lấy List Truyện Vip Top Deal Trong Khoảng
     *
     * @param startDate
     * @param endDate
     * @param page
     * @param size
     * @return Page<TopStory>
     */
    Page< TopStory > getTopStoryVip(Date startDate, Date endDate, int page, int size);

    /**
     * Lấy List Truyện Hoàn Thành Top View Trong Khoảng
     *
     * @param startDate
     * @param endDate
     * @param page
     * @param size
     * @return Page<TopStory>
     */
    Page< TopStory > getTopStoryComplete(Date startDate, Date endDate, int page, int size);

    /**
     * Lấy Top 10 Truyện Mới hoàn thành
     *
     * @return List<Story>
     */
    List< Story > getNewStoryCompleted();

    /**
     * Lấy Page Truyện Hoàn Thành
     *
     * @param page
     * @param size
     * @return Page<NewStory>
     */
    Page< NewStory > getStoryCompletedByPage(int page, int size);

    /**
     * Lấy List Truyện Theo searchName
     *
     * @param searchName
     * @return Page<SearchStory>
     */
    List< SearchStory > getSearch(String searchName);

    /**
     * Kiểm tra Tồn Tại Truyện Theo
     *
     * @param sID
     * @return boolean
     */
    boolean searchStoryByID(Long sID);

    /**
     * Cập nhật lươt xem
     *
     * @param story
     * @return void
     */
    void updateViewStory(Story story);

    /**
     * Tìm Kiếm List Truyện Mới Theo
     *
     * @param page
     * @param size
     * @return Page<NewStory>
     */
    Page< NewStory > searchStoryByPage(String search, int page, int size);

    /**
     * Lấy Truyện Theo
     *
     * @param sID
     * @param listStatus
     * @return Page<NewStory>
     */
    Optional< StorySummary > getStoryBySIDAndStatus(Long sID, List< Integer > listStatus);

    /**
     * Lấy Danh Sách Truyện Đăng Của Converter
     *
     * @param uID
     * @param listStatus
     * @return List<TopStory>
     */
    List< SearchStory > getListStoryOfConverter(Long uID, List< Integer > listStatus);

    /**
     * Lấy Danh Sách Truyện Theo Converter
     *
     * @param uID
     * @param listStatus
     * @return Page<MemberStorySummary>
     */
    Page< MemberStorySummary > getStoryByConverter(List< Integer > listStatus, Long uID, int page, int size);

    /**
     * Đếm số truyện đăng bởi uID
     *
     * @param uID
     * @param listStatus
     * @return Long
     */
    Long countStoryByUser(Long uID, List< Integer > listStatus);

    /**
     * Lấy Truyện Theo
     *
     * @param sID
     * @return Page<Story>
     */
    Optional< Story > searchStoryBySID(Long sID);

    /**
     * Lấy Top 3 Truyện Mới Đăng Của Converter
     *
     * @param uID
     * @param listStatus
     * @return List<NewStory>
     */
    List< SearchStory > getTop3StoryOfConverter(Long uID, List< Integer > listStatus);
}

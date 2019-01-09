package online.hthang.truyenonline.repository;

import online.hthang.truyenonline.entity.Story;
import online.hthang.truyenonline.projections.*;
import online.hthang.truyenonline.utils.ConstantsQueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Huy Thang
 */

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findTop10BySStatus(Integer sStatus);

    /**
     * Lấy Danh sách Truyện Mới
     *
     * @param listChStatus
     * @param listStatus
     * @param pageable
     * @return Page<TopStory>
     */
    @Query(value = ConstantsQueryUtils.STORY_NEW_UPDATE,
            countQuery = ConstantsQueryUtils.COUNT_STORY_NEW_UPDATE,
            nativeQuery = true)
    Page<NewStory> getStoryNew(@Param("chStatus") List<Integer> listChStatus, @Param("sStatus") List<Integer> listStatus,
                               Pageable pageable);

    /**
     * Lấy Danh sách Truyện Mới Theo Thể Loại
     *
     * @param listChStatus
     * @param cID
     * @param listStatus
     * @param pageable
     * @return Page<TopStory>
     */
    @Query(value = ConstantsQueryUtils.STORY_NEW_UPDATE_BY_CATEGORY,
            countQuery = ConstantsQueryUtils.COUNT_STORY_NEW_UPDATE_BY_CATEGORY,
            nativeQuery = true)
    Page<NewStory> getStoryNewByCategory(@Param("chStatus") List<Integer> listChStatus, @Param("cID") Integer cID,
                                         @Param("sStatus") List<Integer> listStatus, Pageable pageable);

    /**
     * Lấy Danh sách Truyện Top
     *
     * @param startDate
     * @param endDate
     * @param listStatus
     * @param pageable
     * @return Page<TopStory>
     */
    @Query(value = ConstantsQueryUtils.STORY_TOP_VIEW,
            countQuery = ConstantsQueryUtils.COUNT_STORY_TOP_VIEW,
            nativeQuery = true)
    Page<TopStory> getTopStory(@Param("sStatus") List<Integer> listStatus, @Param("startDate") Date startDate,
                               @Param("endDate") Date endDate, Pageable pageable);

    /**
     * Lấy Danh sách Truyện Top Theo Category
     *
     * @param cID
     * @param startDate
     * @param endDate
     * @param listStatus
     * @param pageable
     * @return Page<TopStory>
     */
    @Query(value = ConstantsQueryUtils.STORY_TOP_VIEW_BY_CATEGORY,
            countQuery = ConstantsQueryUtils.COUNT_STORY_TOP_VIEW_BY_CATEGORY,
            nativeQuery = true)
    Page<TopStory> getTopStoryByCategory(@Param("cID") Integer cID, @Param("sStatus") List<Integer> listStatus, @Param("startDate") Date startDate,
                                         @Param("endDate") Date endDate, Pageable pageable);

    /**
     * Lấy Danh sách Truyện VIP Top
     *
     * @param startDate
     * @param endDate
     * @param sDealStatus
     * @param listStatus
     * @param pageable
     * @return Page<TopStory>
     */
    @Query(value = ConstantsQueryUtils.STORY_VIP_TOP_VIEW,
            countQuery = ConstantsQueryUtils.COUNT_STORY_VIP_TOP_VIEW,
            nativeQuery = true)
    Page<TopStory> getTopStoryVip(@Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate, @Param("sDealStatus") Integer sDealStatus,
                                  @Param("sStatus") List<Integer> listStatus, Pageable pageable);

    /**
     * Lấy Danh sách Truyện Hoàn Thành
     *
     * @param listStatus
     * @param sStatus
     * @param pageable
     * @return Page<NewStory>
     */
    @Query(value = ConstantsQueryUtils.STORY_COMPLETE,
            countQuery = ConstantsQueryUtils.COUNT_STORY_COMPLETE,
            nativeQuery = true)
    Page<NewStory> getPageStoryComplete(@Param("chStatus") List<Integer> listStatus, @Param("sStatus") Integer sStatus,
                                        Pageable pageable);

    /**
     * Lấy Danh sách Truyện Hoàn Thành
     *
     * @param listChStatus
     * @param pageable
     * @param listStatus
     * @return Page<NewStory>
     */
    @Query(value = ConstantsQueryUtils.VIP_STORY_NEW_UPDATE,
            countQuery = ConstantsQueryUtils.COUNT_VIP_STORY_NEW_UPDATE,
            nativeQuery = true)
    Page<NewStory> getVipStoryNew(@Param("chStatus") List<Integer> listChStatus, @Param("sStatus") List<Integer> listStatus,
                                  @Param("sDealStatus") Integer sDealStatus, Pageable pageable);

    /**
     * Lấy Top View Truyện Hoàn Thành Trong Tháng
     *
     * @param sStatus
     * @param startDate
     * @param endDate
     * @param pageable
     * @return Page<Story>
     */
    @Query(value = ConstantsQueryUtils.COMPLETE_STORY_TOP_VIEW_SWAPPER,
            countQuery = ConstantsQueryUtils.COUNT_COMPLETE_STORY_TOP_VIEW_SWAPPER,
            nativeQuery = true)
    Page<Story> getCompleteStoryTopView(@Param("sStatus") Integer sStatus, @Param("startDate") Date startDate,
                                        @Param("endDate") Date endDate, Pageable pageable);

    /**
     * Lấy Top View Truyện Hoàn Thành Trong Tháng
     *
     * @param searchName
     * @param sStatus
     * @return Page<SearchStory>
     */
    List<SearchStory> findTop10ByvnNameContainingAndSStatusNot(String searchName, Integer sStatus);

    /**
     * Kiểm tra Tồn Tại Story Theo ID Và Status
     *
     * @param sStatus
     * @param sID
     * @return boolean
     */
    boolean existsStoryBySIDAndSStatusNot(Long sID, Integer sStatus);

    /**
     * Tìm kiếm Danh sách Truyện Theo
     *
     * @param listChStatus
     * @param listStatus
     * @param pageable
     * @return Page<NewStory>
     */
    @Query(value = ConstantsQueryUtils.SEARCH_STORY,
            countQuery = ConstantsQueryUtils.COUNT_SEARCH_STORY,
            nativeQuery = true)
    Page<NewStory> getSearchStory(@Param("chStatus") List<Integer> listChStatus, @Param("search") String search, @Param("sStatus") List<Integer> listStatus,
                                  Pageable pageable);

    /**
     * Tìm Truyện Theo
     *
     * @param sID
     * @param listStatus
     * @return Optional<StorySummary>
     */
    Optional<StorySummary> findBysIDAndSStatusIn(Long sID, List<Integer> listStatus);

    /**
     * Lấy Danh Sách Truyện Theo converter
     *
     * @param uID
     * @param listStatus
     * @return List<TopStory>
     */
    List<SearchStory> findTop5BySConverter_uIDAndSStatusInOrderByCreateDate(Long uID, List<Integer> listStatus);

    /**
     * Lấy Danh Sách Truyện Theo Converter
     *
     * @param uID
     * @param listStatus
     * @return Page<MemberStorySummary>
     */
    Page<MemberStorySummary> findBySConverter_uIDAndSStatusInOrderByCreateDateDesc(Long uID,
                                                                            List<Integer> listStatus,
                                                                            Pageable pageable);

    /**
     * Đếm số truyện đăng bởi uID
     *
     * @param uID
     * @param listStatus
     * @return Long
     */
    Long countBySConverter_uIDAndSStatusIn(Long uID, List<Integer> listStatus);

    /**
     * Lấy Top 3 Truyện Mới Đăng Của Converter
     *
     * @param uID
     * @param listStatus
     * @return List<SearchStory>
     */
    List<SearchStory> findTop3BySConverter_uIDAndSStatusInOrderByCreateDateDesc(Long uID,
                                                                                List<Integer> listStatus);


}

package online.hthang.truyenonline.service.impl;

import online.hthang.truyenonline.entity.Story;
import online.hthang.truyenonline.projections.*;
import online.hthang.truyenonline.repository.StoryRepository;
import online.hthang.truyenonline.service.StoryService;
import online.hthang.truyenonline.utils.ConstantsListUtils;
import online.hthang.truyenonline.utils.ConstantsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Huy Thang
 */
@Service
@Transactional
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;

    @Autowired
    public StoryServiceImpl(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    /**
     * Lấy List Truyện Mới Cập Nhật
     *
     * @param page
     * @param size
     * @return Page<NewStory>
     */
    @Override
    public Page< NewStory > getStoryNew(int page, int size) {
        List< Integer > listStatus = new ArrayList<>();
        listStatus.add(ConstantsUtils.STORY_STATUS_COMPLETED);
        listStatus.add(ConstantsUtils.STORY_STATUS_GOING_ON);
        List< Integer > listChStatus = new ArrayList<>();
        listChStatus.add(ConstantsUtils.CHAPTER_VIP_ACTIVED);
        listChStatus.add(ConstantsUtils.CHAPTER_ACTIVED);
        Pageable pageable = PageRequest.of(page - 1, size);
        return storyRepository
                .getStoryNew(listChStatus, listStatus, pageable);
    }

    /**
     * Lấy List Truyện Vip Mới Cập Nhật
     *
     * @param page
     * @param size
     * @return Page<NewStory>
     */
    @Override
    public Page< NewStory > getVipStoryNew(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return storyRepository
                .getVipStoryNew(ConstantsListUtils.LIST_CHAPTER_DISPLAY,
                        ConstantsListUtils.LIST_STORY_DISPLAY, ConstantsUtils.STATUS_ACTIVED, pageable);
    }

    /**
     * Lấy List Truyện Mới Cập Nhật theo Category
     *
     * @param cID
     * @param page
     * @param size
     * @return Page<NewStory>
     */
    @Override
    public Page< NewStory > getStoryNewByCID(Integer cID, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return storyRepository
                .getStoryNewByCategory(ConstantsListUtils.LIST_CHAPTER_DISPLAY, cID,
                        ConstantsListUtils.LIST_STORY_DISPLAY, pageable);
    }

    /**
     * Lấy List Truyện Mới Cập Nhật theo Category
     *
     * @param startDate
     * @param endDate
     * @param cID
     * @param page
     * @param size
     * @return Page<TopStory>
     */
    @Override
    public Page< TopStory > getTopStoryByCID(Date startDate, Date endDate, Integer cID, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return storyRepository.getTopStoryByCategory(cID, ConstantsListUtils.LIST_STORY_DISPLAY,
                startDate, endDate, pageable);
    }

    /**
     * Lấy List Truyện Top View Trong Khoảng
     *
     * @param startDate
     * @param endDate
     * @param page
     * @param size
     * @return Page<TopStory>
     */
    @Override
    public Page< TopStory > getTopStory(Date startDate, Date endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return storyRepository
                .getTopStory(ConstantsListUtils.LIST_STORY_DISPLAY, startDate,
                        endDate, pageable);
    }

    /**
     * Lấy List Truyện Vip Top Deal Trong Khoảng
     *
     * @param startDate
     * @param endDate
     * @param page
     * @param size
     * @return Page<TopStory>
     */
    @Override
    public Page< TopStory > getTopStoryVip(Date startDate, Date endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return storyRepository
                .getTopStoryVip(startDate, endDate, ConstantsUtils.STATUS_ACTIVED,
                        ConstantsListUtils.LIST_STORY_DISPLAY, pageable);
    }

    /**
     * Lấy List Truyện Hoàn Thành Top View Trong Khoảng
     *
     * @param startDate
     * @param endDate
     * @param page
     * @param size
     * @return Page<TopStory>
     */
    @Override
    public Page< TopStory > getTopStoryComplete(Date startDate, Date endDate, int page, int size) {
        List< Integer > listStatus = new ArrayList<>();
        listStatus.add(ConstantsUtils.STORY_STATUS_COMPLETED);
        Pageable pageable = PageRequest.of(page - 1, size);
        return storyRepository
                .getTopStory(listStatus, startDate, endDate, pageable);
    }

    /**
     * Lấy Top 10 Truyện Mới hoàn thành
     *
     * @return List<Story>
     */
    @Override
    public List< Story > getNewStoryCompleted() {
        return storyRepository
                .findTop10BySStatus(ConstantsUtils.STORY_STATUS_COMPLETED);
    }

    /**
     * Lấy Page Truyện Hoàn Thành
     *
     * @param page
     * @param size
     * @return Page<NewStory>
     */
    @Override
    public Page< NewStory > getStoryCompletedByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "sUpdate");
        return storyRepository
                .getPageStoryComplete(ConstantsListUtils.LIST_CHAPTER_DISPLAY, ConstantsUtils.STORY_STATUS_COMPLETED, pageable);
    }

    /**
     * Lấy List Truyện Theo searchName
     *
     * @param searchName
     * @return Page<SearchStory>
     */
    @Override
    public List< SearchStory > getSearch(String searchName) {
        return storyRepository
                .findTop10ByvnNameContainingAndSStatusNot(searchName,
                        ConstantsUtils.STORY_STATUS_HIDDEN);
    }

    /**
     * Kiểm tra Tồn Tại Truyện Theo
     *
     * @param sID
     * @return boolean
     */
    @Override
    public boolean searchStoryByID(Long sID) {
        return storyRepository.existsStoryBySIDAndSStatusNot(sID, ConstantsUtils.STORY_STATUS_HIDDEN);
    }

    /**
     * Cập nhật lươt xem
     *
     * @param story
     * @return void
     */
    @Override
    public void updateViewStory(Story story) {
        storyRepository.save(story);
    }

    /**
     * Tìm Kiếm List Truyện Mới Theo
     *
     * @param search
     * @param page
     * @param size
     * @return Page<NewStory>
     */
    @Override
    public Page< NewStory > searchStoryByPage(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return storyRepository
                .getSearchStory(ConstantsListUtils.LIST_CHAPTER_DISPLAY, search,
                        ConstantsListUtils.LIST_STORY_DISPLAY, pageable);
    }

    /**
     * Lấy Truyện Theo
     *
     * @param sID
     * @param listStatus
     * @return Page<NewStory>
     */
    @Override
    public Optional< StorySummary > getStoryBySIDAndStatus(Long sID, List< Integer > listStatus) {
        return storyRepository.findBysIDAndSStatusIn(sID, listStatus);
    }

    /**
     * Lấy Danh Sách Truyện Đăng Của Converter
     *
     * @param uID
     * @param listStatus
     * @return List<SearchStory>
     */
    @Override
    public List< SearchStory > getListStoryOfConverter(Long uID, List< Integer > listStatus) {
        return storyRepository
                .findTop5BySConverter_uIDAndSStatusInOrderByCreateDate(uID, listStatus);
    }

    @Override
    public Page< MemberStorySummary > getStoryByConverter(List< Integer > listStatus, Long uID, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return storyRepository
                .findBySConverter_uIDAndSStatusInOrderByCreateDateDesc(uID, listStatus, pageable);
    }

    /**
     * Đếm số truyện đăng bởi uID
     *
     * @param uID
     * @param listStatus
     * @return Long
     */
    @Override
    public Long countStoryByUser(Long uID, List< Integer > listStatus) {
        return storyRepository.countBySConverter_uIDAndSStatusIn(uID, listStatus);
    }

    /**
     * Lấy Truyện Theo
     *
     * @param sID
     * @return Page<Story>
     */
    @Override
    public Optional< Story > searchStoryBySID(Long sID) {
        return storyRepository.findById(sID);
    }

    /**
     * Lấy Top 3 Truyện Mới Đăng Của Converter
     *
     * @param uID
     * @param listStatus
     * @return List<SearchStory>
     */
    @Override
    public List< SearchStory > getTop3StoryOfConverter(Long uID, List< Integer > listStatus) {
        return storyRepository.findTop3BySConverter_uIDAndSStatusInOrderByCreateDateDesc(uID, listStatus);
    }
}

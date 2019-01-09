package online.hthang.truyenonline.service.impl;

import online.hthang.truyenonline.entity.Chapter;
import online.hthang.truyenonline.projections.ChapterOfStory;
import online.hthang.truyenonline.projections.ChapterSummary;
import online.hthang.truyenonline.repository.ChapterRepository;
import online.hthang.truyenonline.service.ChapterService;
import online.hthang.truyenonline.utils.ConstantsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Huy Thang
 */
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    /**
     * Lấy Chapter Theo
     *
     * @param chID
     * @return Chapter
     */
    @Override
    public Chapter getChapterByID(Long chID) {
        List< Integer > listStatus = new ArrayList<>();
        listStatus.add(ConstantsUtils.STORY_STATUS_COMPLETED);
        listStatus.add(ConstantsUtils.STORY_STATUS_GOING_ON);
        Optional< Chapter > chapter = chapterRepository
                .findChapterByChIDAndChStatusIn(chID, listStatus);
        if (chapter.isPresent()) {
            return chapter.get();
        } else {
            return null;
        }
    }

    @Override
    public List< Chapter > getAllChapterFavoritesByUser(Long uID) {
        List< Integer > listStatus = new ArrayList<>();
        listStatus.add(ConstantsUtils.CHAPTER_VIP_ACTIVED);
        listStatus.add(ConstantsUtils.CHAPTER_ACTIVED);
        return chapterRepository
                .getAllFavoritesByUser(uID, ConstantsUtils.STATUS_ACTIVED,
                        listStatus, ConstantsUtils.STORY_STATUS_HIDDEN);
    }

    /**
     * Lấy Chapter Theo
     *
     * @param sID
     * @param chID
     * @return Optional<Chapter>
     */
    @Override
    public Optional< Chapter > getChapterBySIDAndChID(Long sID, Long chID) {
        List< Integer > listStatus = new ArrayList<>();
        listStatus.add(ConstantsUtils.STORY_STATUS_COMPLETED);
        listStatus.add(ConstantsUtils.STORY_STATUS_GOING_ON);
        return chapterRepository.findChapterByChIDAndStory_sIDAndChStatusIn(chID,
                sID, listStatus);
    }

    /**
     * Cập nhật Chapter
     *
     * @param chapter
     * @return void
     */
    @Override
    public void updateChapter(Chapter chapter) {
        chapterRepository.save(chapter);
    }

    /**
     * Lấy Chapter ID Tiếp Theo
     *
     * @param chSerial
     * @param sID
     * @return Long
     */
    @Override
    public Long getNextChapterID(float chSerial, Long sID) {
        List< Integer > list = new ArrayList<>();
        list.add(ConstantsUtils.CHAPTER_ACTIVED);
        list.add(ConstantsUtils.CHAPTER_VIP_ACTIVED);
        Optional< Long > nextID = chapterRepository.getNextChapter(chSerial, sID, list);
        if (nextID.isPresent()) {
            return nextID.get();
        } else {
            return Long.valueOf(0);
        }
    }

    /**
     * Lấy Chapter ID Trước
     *
     * @param chSerial
     * @param sID
     * @return Long
     */
    @Override
    public Long getPreviousChapterID(float chSerial, Long sID) {
        List< Integer > list = new ArrayList<>();
        list.add(ConstantsUtils.CHAPTER_ACTIVED);
        list.add(ConstantsUtils.CHAPTER_VIP_ACTIVED);
        Optional< Long > previousID = chapterRepository.getPreviousChapter(chSerial, sID, list);
        if (previousID.isPresent()) {
            return previousID.get();
        } else {
            return Long.valueOf(0);
        }
    }

    /**
     * Lấy Chapter ID Chương Đầu
     *
     * @param sID
     * @param listStatus
     * @return Long
     */
    @Override
    public ChapterSummary getChapterIDHead(Long sID, List< Integer > listStatus) {
        Optional< ChapterSummary > chapterIDHead = chapterRepository.getChapterHead(sID, listStatus);
        if (chapterIDHead.isPresent()) {
            return chapterIDHead.get();
        } else {
            return null;
        }
    }

    /**
     * Lấy Chapter ID Chương mới nhất
     *
     * @param sID
     * @param listStatus
     * @return Long
     */
    @Override
    public ChapterSummary getChapterIDNew(Long sID, List< Integer > listStatus) {
        Optional< ChapterSummary > chapterIDNew = chapterRepository.getNewChapter(sID, listStatus);
        if (chapterIDNew.isPresent()) {
            return chapterIDNew.get();
        } else {
            return null;
        }
    }

    /**
     * Cập Nhật Trạng Thái Chapter Vip nếu đến dealine
     */
    @Override
    public void updateStatusChapterVip() {
        chapterRepository.updateStatusChapterVip(ConstantsUtils.CHAPTER_ACTIVED,
                ConstantsUtils.CHAPTER_VIP_ACTIVED);
    }

    /**
     * Đếm số chương đăng bởi uID
     *
     * @param uID
     * @param listStatus
     * @return Long
     */
    @Override
    public Long countChapterByUser(Long uID, List< Integer > listStatus) {
        return chapterRepository.countByUser_uIDAndChStatusIn(uID, listStatus);
    }

    /**
     * Đếm số chương đăng bởi uID
     *
     * @param sID
     * @param listStatus
     * @param page
     * @param size
     * @return Page<ChapterSummary>
     */
    @Override
    public Page< ChapterOfStory > getChapterOfStory(Long sID, List< Integer > listStatus, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return chapterRepository
                .findByStory_sIDAndChStatusInOrderByChSerialDesc(sID, listStatus, pageable);
    }

    /**
     * Lấy Chapter Theo Story
     *
     * @param sID
     * @param listStatus
     * @return Page<ChapterSummary>
     */
    @Override
    public List< ChapterOfStory > getAllChapterOfStory(Long sID, List< Integer > listStatus) {
        return chapterRepository
                .findByStory_sIDAndChStatusInOrderByChSerialDesc(sID, listStatus);
    }
}

package online.hthang.truyenonline.service.impl;

import online.hthang.truyenonline.entity.Chapter;
import online.hthang.truyenonline.entity.Favorites;
import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.repository.FavoritesRepository;
import online.hthang.truyenonline.service.UfavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author Huy Thang
 */

@Service
public class FavoritesServiceImpl implements UfavoritesService {

    private final FavoritesRepository favoritesRepository;

    @Autowired
    public FavoritesServiceImpl(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    /**
     * Kiểm tra tồn tại Favorites trong khoảng
     *
     * @param chID
     * @param uID
     * @param startDate
     * @param endDate
     * @return boolean
     */
    @Override
    public boolean checkChapterAndUserInTime(Long chID, Long uID, Date startDate, Date endDate) {
        return favoritesRepository
                .existsUfavoritesByChapter_ChIDAndUser_uIDAndDateViewBetween(chID, uID, startDate, endDate);
    }

    /**
     * Kiểm tra tồn tại Favorites trong khoảng
     *
     * @param chID
     * @param locationIP
     * @param startDate
     * @param endDate
     * @return boolean
     */
    @Override
    public boolean checkChapterAndLocationIPInTime(Long chID, String locationIP, Date startDate, Date endDate) {
        return favoritesRepository
                .existsUfavoritesByChapter_ChIDAndLocationIPAndDateViewBetween(chID, locationIP, startDate, endDate);
    }

    /**
     * Lưu Lịch Sử đọc truyện
     *
     * @param chapter
     * @param user
     * @param LocationIP
     * @param uView
     * @return void
     */
    @Override
    public void saveUfavorite(Chapter chapter, User user, String LocationIP, Integer uView) {
        Favorites favorites = new Favorites();
        favorites.setStatus(uView);
        favorites.setUser(user);
        favorites.setChapter(chapter);
        favorites.setLocationIP(LocationIP);
        favoritesRepository.save(favorites);
    }

    /**
     * Lấy Chapter Mới đọc
     *
     * @param uID
     * @param sID
     * @return Favorites
     */
    @Override
    public Chapter getChapterReadNewByUser(Long uID, Long sID) {
        Optional< Favorites > ufavorites = favoritesRepository
                .findTopByUser_uIDAndChapter_Story_sIDOrderByDateViewDesc(uID, sID);
        return ufavorites.map(Favorites::getChapter).orElse(null);
    }
}

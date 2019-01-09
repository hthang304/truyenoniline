package online.hthang.truyenonline.repository;

import online.hthang.truyenonline.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * @author Huy Thang
 */
@Repository
public interface FavoritesRepository extends JpaRepository< Favorites, Long> {

    /**
     * Kiểm tra tồn tại Favorites trong khoảng
     *
     * @param chID
     * @param uID
     * @param startDate
     * @param endDate
     *
     * @return boolean
     */
    boolean existsUfavoritesByChapter_ChIDAndUser_uIDAndDateViewBetween(Long chID, Long uID, Date startDate, Date endDate);

    /**
     * Kiểm tra tồn tại Favorites trong khoảng
     *
     * @param chID
     * @param locationIP
     * @param startDate
     * @param endDate
     *
     * @return boolean
     */
    boolean existsUfavoritesByChapter_ChIDAndLocationIPAndDateViewBetween(Long chID, String locationIP, Date startDate, Date endDate);

    /**
     * Lấy Lịch sử đọc mới nhất
     *
     * @param uID
     * @param sID
     *
     * @return Optional<Favorites>
     */
    Optional< Favorites > findTopByUser_uIDAndChapter_Story_sIDOrderByDateViewDesc(Long uID, Long sID);
}

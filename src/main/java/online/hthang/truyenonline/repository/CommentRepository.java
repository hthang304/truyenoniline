package online.hthang.truyenonline.repository;

import online.hthang.truyenonline.entity.Comment;
import online.hthang.truyenonline.projections.CommentSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Huy Thang
 */
@Repository
public interface CommentRepository extends JpaRepository< Comment, Long > {

    /**
     * Lấy Page Comment của truyện
     *
     * @param sID
     * @param status
     * @return Page<CommentSummary>
     */
    Page< CommentSummary > findByStory_sIDAndComStatusOrderByCreateDateDesc(Long sID, Integer status, Pageable pageable);

    /**
     * Lấy List All comment của truyện
     * @param sID
     * @param status
     * @return List<CommentSummary>
     */
    List<CommentSummary> findAllByStory_sIDAndComStatusOrderByCreateDateDesc(Long sID, Integer status);
}

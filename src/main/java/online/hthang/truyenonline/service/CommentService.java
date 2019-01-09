package online.hthang.truyenonline.service;

import online.hthang.truyenonline.entity.Story;
import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.projections.CommentSummary;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Huy Thang
 */
public interface CommentService {

    /**
     * Lấy Page Comment theo truyện
     *
     * @param sID
     * @param size
     * @param page
     * @return Page<CommentSummary>
     */
    Page< CommentSummary > getCommentOfStoryByPage(Long sID, int page,int size);

    /**
     * Lấy List All Comment theo truyện
     * @param sID
     * @return List<CommentSummary>
     */
    List<CommentSummary> getAllCommentOfStory(Long sID);

    /**
     * Lưu Bình Luận
     * @param user
     * @param comment
     * @param story
     * @return boolean
     */
    boolean saveComment(User user, Story story, String comment);
}

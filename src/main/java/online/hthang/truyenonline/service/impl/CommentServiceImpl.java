package online.hthang.truyenonline.service.impl;

import online.hthang.truyenonline.entity.Comment;
import online.hthang.truyenonline.entity.Story;
import online.hthang.truyenonline.entity.User;
import online.hthang.truyenonline.projections.CommentSummary;
import online.hthang.truyenonline.repository.CommentRepository;
import online.hthang.truyenonline.service.CommentService;
import online.hthang.truyenonline.utils.ConstantsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Huy Thang
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;


    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Lấy Page Comment theo truyện
     *
     * @param sID
     * @param size
     * @param page
     * @return Page<CommentSummary>
     */
    @Override
    public Page< CommentSummary > getCommentOfStoryByPage(Long sID, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return commentRepository
                .findByStory_sIDAndComStatusOrderByCreateDateDesc(sID, ConstantsUtils.STATUS_ACTIVED, pageable);
    }

    /**
     * Lấy List All Comment theo truyện
     *
     * @param sID
     * @return List<CommentSummary>=
     */
    @Override
    public List< CommentSummary > getAllCommentOfStory(Long sID) {
        return commentRepository
                .findAllByStory_sIDAndComStatusOrderByCreateDateDesc(sID, ConstantsUtils.STATUS_ACTIVED);
    }

    /**
     * Lưu Bình Luận
     *
     * @param story
     * @param comment
     * @return boolean
     */
    @Override
    public boolean saveComment(User user, Story story, String comment) {
        Comment newComment = new Comment();
        newComment.setStory(story);
        newComment.setUser(user);
        newComment.setContent(comment);
        commentRepository.save(newComment);
        return newComment.getComID() != null;
    }
}

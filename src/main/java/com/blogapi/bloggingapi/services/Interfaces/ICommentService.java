package com.blogapi.bloggingapi.services.Interfaces;

import com.blogapi.bloggingapi.payload.CommentDTO;

public interface ICommentService {
    // create comment wrto post
    CommentDTO createComment(CommentDTO commentDTO, Integer postId);

    // delete comment
    void deleteComment(Integer commentId);
}

package com.blogapi.bloggingapi.services.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.bloggingapi.entities.Comment;
import com.blogapi.bloggingapi.entities.Post;
import com.blogapi.bloggingapi.entities.User;
import com.blogapi.bloggingapi.exceptions.ResourceNotFoundException;
import com.blogapi.bloggingapi.payload.CommentDTO;
import com.blogapi.bloggingapi.repositories.CommentRepository;
import com.blogapi.bloggingapi.repositories.PostRepository;
import com.blogapi.bloggingapi.repositories.UserRepository;
import com.blogapi.bloggingapi.services.Interfaces.ICommentService;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private PostRepository _postRepository;

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private UserService _userService;

    @Autowired
    private CommentRepository _commentRepository;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId) {
        // get User
        User user = this._userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User",
                        "User id",
                        userId));
        // get post
        Post post = this._postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post",
                        "post id",
                        postId));
        // update comment
        Comment comment = this.dtoToObj(commentDTO);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = this._commentRepository.save(comment);
        return this.objToDto(savedComment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        // get comment by id
        Comment comment = this._commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comment",
                        "Comment id",
                        commentId));
        this._commentRepository.delete(comment);
    }

    public CommentDTO objToDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setUser(this._userService.userToDto(comment.getUser()));
        commentDTO.setPostId(comment.getPost().getId());
        return commentDTO;
    }

    public Comment dtoToObj(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        return comment;
    }

}

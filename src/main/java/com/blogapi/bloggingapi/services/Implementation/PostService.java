package com.blogapi.bloggingapi.services.Implementation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.bloggingapi.entities.Category;
import com.blogapi.bloggingapi.entities.Post;
import com.blogapi.bloggingapi.entities.User;
import com.blogapi.bloggingapi.exceptions.ResourceNotFoundException;
import com.blogapi.bloggingapi.payload.PostDTO;
import com.blogapi.bloggingapi.repositories.CategoryRepository;
import com.blogapi.bloggingapi.repositories.PostRepository;
import com.blogapi.bloggingapi.repositories.UserRepository;
import com.blogapi.bloggingapi.services.Interfaces.IPostService;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository _postRepository;

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private UserService _userService;

    @Autowired
    private CategoryRepository _categoryRepository;

    @Autowired
    private CategoryService _categoryService;

    @Override
    public PostDTO create(PostDTO postDTO, Integer userId, Integer categoryId) {
        // get user
        User user = this._userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User",
                        " Id ",
                        userId));
        // get category
        Category category = this._categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category",
                        "Id",
                        categoryId));
        // map to post
        Post post = this.dtoToObj(postDTO);
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        if (post.getImgName().equals(null)) {
            post.setImgName("default.png");
        }

        // save to DB
        Post savedPost = this._postRepository.save(post);

        return this.objToDto(savedPost);
    }

    @Override
    public PostDTO update(PostDTO postDTO, Integer postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Integer postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<PostDTO> getAll() {
        List<Post> allPosts = this._postRepository.findAll();
        return allPosts.stream().map(post -> this.objToDto(post))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getById(Integer postId) {
        Post getSinglePost = this._postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post",
                        "Post id",
                        postId));
        return this.objToDto(getSinglePost);
    }

    @Override
    public List<PostDTO> getAllByUser(Integer userId) {
        // get user
        User user = this._userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User",
                        " Id ",
                        userId));
        // gel all posts by user
        List<Post> postsByUser = this._postRepository.findAllByUser(user);

        // map POST => POSTDTO
        List<PostDTO> posts = postsByUser.stream().map(
                (post) -> this.objToDto(post))
                .collect(Collectors.toList());
        return posts;
    }

    @Override
    public List<PostDTO> getAllByCategories(Integer categoryId) {
        // get category
        Category category = this._categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category",
                        "Id",
                        categoryId));

        // gel all posts by category
        List<Post> postsByUser = this._postRepository.findAllByCategory(category);

        // map POST => POSTDTO
        List<PostDTO> posts = postsByUser.stream().map(
                post -> this.objToDto(post)).collect(Collectors.toList());
        return posts;
    }

    public Post dtoToObj(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAddedDate(postDTO.getAddedDate());
        post.setImgName(postDTO.getImgName());
        // post.setUser(this._userService.dtoToUser(postDTO.getUser()));
        // post.setCategory(this._categoryService.dtoToObj(postDTO.getCategory()));
        return post;
    }

    public PostDTO objToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setAddedDate(post.getAddedDate());
        postDTO.setImgName(post.getImgName());
        postDTO.setUser(this._userService.userToDto(post.getUser()));
        postDTO.setCategory(this._categoryService.objToDto(post.getCategory()));
        return postDTO;
    }

}

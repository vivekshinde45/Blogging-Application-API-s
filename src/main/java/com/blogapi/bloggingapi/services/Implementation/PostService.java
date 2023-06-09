package com.blogapi.bloggingapi.services.Implementation;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapi.bloggingapi.entities.Category;
import com.blogapi.bloggingapi.entities.Post;
import com.blogapi.bloggingapi.entities.User;
import com.blogapi.bloggingapi.exceptions.ResourceNotFoundException;
import com.blogapi.bloggingapi.payload.CommentDTO;
import com.blogapi.bloggingapi.payload.PostDTO;
import com.blogapi.bloggingapi.payload.PostResponse;
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

    @Autowired
    private CommentService _commentService;

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
        // if (post.getImgName().length() == 0) {
        post.setImgName("default.png");
        // }

        // save to DB
        Post savedPost = this._postRepository.save(post);

        return this.objToDto(savedPost);
    }

    @Override
    public PostDTO update(PostDTO postDTO, Integer postId) {
        Post post = this._postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post",
                        "Post id",
                        postId));
        post.setTitle(postDTO.getTitle());
        post.setContent(post.getContent());
        // if (postDTO.getImgName().length() == 0) {
        post.setImgName(postDTO.getImgName());
        // }
        Post updatedPost = this._postRepository.save(post);
        return this.objToDto(updatedPost);
    }

    @Override
    public void delete(Integer postId) {
        Post post = this._postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post",
                        "Post id",
                        postId));
        this._postRepository.delete(post);
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
    public PostResponse getByPage(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
//         if (!sortDir.isBlank() && sortDir.equalsIgnoreCase("asc")) {
//             sort = Sort.by(sortBy).ascending();
//         } else {
//             sort = Sort.by(sortBy).descending();
//         }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> postRecords = this._postRepository.findAll(pageable);
        List<Post> allPosts = postRecords.getContent();

        List<PostDTO> posts = allPosts.stream().map(
                post -> this.objToDto(post)).collect(Collectors.toList());

        PostResponse response = new PostResponse();
        response.setContent(posts);
        response.setPageNumber(postRecords.getNumber());
        response.setPageSize(postRecords.getSize());
        response.setTotalElements(postRecords.getTotalElements());
        response.setTotalPages(postRecords.getTotalPages());
        response.setLastPage(postRecords.isLast());
        return response;
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

    @Override
    public List<PostDTO> search(String keyword) {
        List<Post> searchResult = this._postRepository.findByTitleContaining(keyword);
        return searchResult.stream().map(
                post -> this.objToDto(post)).collect(Collectors.toList());
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

        Set<CommentDTO> postCommentsDto = post.getComments().stream().map(
                postComment -> this._commentService.objToDto(postComment))
                .collect(Collectors.toSet());

        postDTO.setComments(postCommentsDto);
        return postDTO;
    }

}

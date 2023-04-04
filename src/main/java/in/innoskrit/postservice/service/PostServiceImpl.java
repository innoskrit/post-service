package in.innoskrit.postservice.service;

import in.innoskrit.postservice.constant.Constant;
import in.innoskrit.postservice.entity.Post;
import in.innoskrit.postservice.entity.Response;
import in.innoskrit.postservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Override
    public Response createPost(Post post) {
        Post post1 = postRepository.save(post);
        if(post1 == null) {
            return new Response(HttpStatus.SERVICE_UNAVAILABLE.value(), Constant.POST_CREATION_FAILED, null);
        }
        return new Response(HttpStatus.OK.value(), Constant.POST_CREATION_SUCCESSFULL, post1);
    }

    @Override
    public Response getPosts() {
        List<Post> posts = postRepository.findAll();
        if(posts == null) {
            return new Response(HttpStatus.SERVICE_UNAVAILABLE.value(), Constant.POSTS_FETCHING_FAILED, null);
        }
        return new Response(HttpStatus.OK.value(), Constant.POSTS_FETCHING_SUCCESS, posts);
    }

    @Override
    public Response getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if(!post.isPresent()) {
            return new Response(HttpStatus.BAD_REQUEST.value(), Constant.POST_FETCHING_FAILED, null);
        }
        return new Response(HttpStatus.OK.value(), Constant.POST_FETCHING_SUCCESS, post.get());
    }

    @Override
    public Response updatePost(Post post, Long id) {
        Optional<Post> existingPost = postRepository.findById(id);
        if(!existingPost.isPresent()) {
            return new Response(HttpStatus.BAD_REQUEST.value(), Constant.POST_UPDATION_FAILED, null);
        }
        post.setId(existingPost.get().getId());
        Post post1 = postRepository.save(post);
        return new Response(HttpStatus.OK.value(), Constant.POST_UPDATION_SUCCESS, post1);
    }

    @Override
    public Response deletePost(Long id) {
        Optional<Post> existingPost = postRepository.findById(id);
        if(!existingPost.isPresent()) {
            return new Response(HttpStatus.BAD_REQUEST.value(), Constant.POST_DELETION_FAILED, null);
        }
        postRepository.deleteById(id);
        return new Response(HttpStatus.OK.value(), Constant.POST_DELETION_SUCCESS, null);
    }
}

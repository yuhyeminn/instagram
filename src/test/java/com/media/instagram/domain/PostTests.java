package com.media.instagram.domain;

import com.media.instagram.repository.LocationRepository;
import com.media.instagram.repository.PostRepository;
import com.media.instagram.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 참고
 * https://flyburi.com/609
 * https://bebong.tistory.com/entry/JPA-Lazy-Evaluation-LazyInitializationException-could-not-initialize-proxy-%E2%80%93-no-Session
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    LocationRepository locationRepository;

    Post samplePost;
    User sampleUser;
    Location sampleLocation;

    @BeforeAll
    public void setUp(){
        sampleUser = userRepository.save(new User());
        sampleLocation = locationRepository.save(new Location("new Location"));
        samplePost = new Post(sampleUser, "temporary image field", "",sampleLocation);
    }

    @AfterAll
    public void clear(){
        userRepository.deleteAll();
        locationRepository.deleteAll();
    }

    @Test
    @Transactional
    public void dataCRUDTest(){
        // CREATE
        Post result = postRepository.save(samplePost);
        assertNotNull(result);

        // READ
        long postId = result.getPostId();
        Post compare = postRepository.getOne(postId);

        // READ -> value comfirm
        assertEquals(result.getWriter(), compare.getWriter());
        assertEquals(result.getContent(), compare.getContent());
        assertEquals(result.getLike(), compare.getLike());
        assertEquals(result.getLocation(), compare.getLocation());

        // UPDATE
        result.setLike(5);
        postRepository.save(result);

        // UPDATE -> value comfirm
        compare = postRepository.getOne(postId);
        assertEquals(compare.getLike(), 5);

        // DELETE
        postRepository.deleteById(postId);
        long count = postRepository.count();

        // DELETE -> value comfirm
        assertEquals(count, 0);
    }


}

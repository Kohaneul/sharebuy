package sharebuy;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sharebuy.domain.post.domain.PostStatus;
import sharebuy.domain.post.entity.Post;
import sharebuy.domain.post.repository.ParticipationRepository;
import sharebuy.domain.post.repository.PostRepository;
import sharebuy.domain.post.service.PostService;
import sharebuy.domain.user.domain.UserStatus;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static sharebuy.domain.user.domain.UserStatus.ACTIVE;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class ParticipationTest {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParticipationRepository participationRepository;

    /**
     * 테스트 끝나면 데이터 클리어
     */
    @AfterEach
    void testClear(){
        participationRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    /**
     * ex1 주문 참여
     * 모집인원은 5명인데
     * 동시 참여 시도 인원 : 10명
     * 순서에 따라서 5명만 참여하고 나머지 5명에 대해서는 throw
     */
    @Test
    void participation_test1() throws InterruptedException {
        final int THREAD_COUNT = 10;

        ExecutorService executorService
                = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        AtomicInteger success = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();
        User owner = userRepository.save(TestFixture.user(UUID.randomUUID(), ACTIVE));
        Post post = postRepository.save(TestFixture.post(UUID.randomUUID(), owner, PostStatus.RECRUITING));
        UUID postId = post.getId();
        List<User> userList = savedDummyUser(THREAD_COUNT);

        for (User user : userList) {
            executorService.submit(()->{
                try{
                    postService.participate(postId,user.getId());

                    success.incrementAndGet();
                }
                catch(Exception e){
                    e.printStackTrace();
                    fail.incrementAndGet();
                }
                finally{
                    latch.countDown();
                }
            });
        }
        latch.await();

        assertThat(success.get()).isEqualTo(5);
        assertThat(fail.get()).isEqualTo(5);

    }


    /**
     * ex2 주문 참여
     * 최대 모집인원 : 3명이고
     * 현재 참여중인 인원 : 1명
     * 동시 참여 시도 인원 : 6명
     * 순서에 따라서 2명만 참여하고 나머지 4명에 대해서는 throw
     */
    @Test
    void participation_test2() throws InterruptedException {
        final int THREAD_COUNT = 6;

        ExecutorService executorService
                = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        AtomicInteger success = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();
        User owner = userRepository.save(TestFixture.user(UUID.randomUUID(), ACTIVE));
        Post post = postRepository.save(TestFixture.post(UUID.randomUUID(), owner, PostStatus.RECRUITING,1,3));
        UUID postId = post.getId();
        List<User> userList = savedDummyUser(THREAD_COUNT);

        for (User user : userList) {
            executorService.submit(()->{
                try{
                    postService.participate(postId,user.getId());

                    success.incrementAndGet();
                }
                catch(Exception e){
                    e.printStackTrace();
                    fail.incrementAndGet();
                }
                finally{
                    latch.countDown();
                }
            });
        }
        latch.await();

        assertThat(success.get()+fail.get()).isEqualTo(THREAD_COUNT);
    }

    /**
     * ex3 주문 참여
     * 최대 모집인원 : 10명이고
     * 현재 참여중인 인원 : 1명
     * 동시 참여 시도 인원 : 30명
     * 8명 일떄 주문 마감 시킴
     * 나머지 23일때 throw
     */
    @Test
    void participation_test3() throws InterruptedException {
      final int THREAD_COUNT=  30;
      AtomicInteger success = new AtomicInteger();
      AtomicInteger fail = new AtomicInteger();
      CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

      User postOwner = userRepository.save(TestFixture.user(UUID.randomUUID(), ACTIVE));
      Post post = postRepository.save(TestFixture.post(UUID.randomUUID(), postOwner, PostStatus.RECRUITING, 1, 10));
      List<User> users = savedDummyUser(THREAD_COUNT);
      UUID postId = post.getId();


        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        for (User user : users) {
            executorService.submit(()->{
               try{
                   postService.participate(postId,user.getId());
                   int current = success.incrementAndGet();

                   if(current==7){
                       postService.orderEnd(postId,postOwner.getId());
                   }
               }
               catch(Exception e){
                   e.printStackTrace();
                   fail.incrementAndGet();
               }
               finally {
                   countDownLatch.countDown();
               }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        assertThat(success.get()).isEqualTo(7);
        assertThat(fail.get()).isEqualTo(23);
    }

    /**
     * 마감후 참여 불가
     */
    @Test
    void test4() throws InterruptedException {

        final int THREAD_COUNT=  30;
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        AtomicInteger success = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();
        User postOwner = userRepository.save(TestFixture.user(UUID.randomUUID(), ACTIVE));
        UUID ownerId = postOwner.getId();
        Post post = postRepository.save(TestFixture.post(UUID.randomUUID(), postOwner, PostStatus.RECRUITING, 2, 10));
        List<User> users = savedDummyUser(THREAD_COUNT);
        UUID postId = post.getId();
        postService.orderEnd(postId,ownerId);


        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        for (User user : users) {
            executorService.submit(()->{
                try{
                    log.info("********* {}= 성공",user.getLoginId());
                    postService.participate(postId,user.getId());
                    success.incrementAndGet();
                }
                catch(Exception e){
                    e.printStackTrace();
                    fail.incrementAndGet();
                }
                finally{
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        Assertions.assertThat(success.get()).isEqualTo(0);
        Assertions.assertThat(fail.get()).isEqualTo(THREAD_COUNT);

    }

    /**
     * dummy data 저장
     * @return
     */
    private List<User> savedDummyUser(int threadCount) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            User user = TestFixture.user(ACTIVE);
            users.add(user);
        }
        userRepository.saveAll(users);

        return users;
    }

}

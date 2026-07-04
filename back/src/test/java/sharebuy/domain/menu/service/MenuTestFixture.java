package sharebuy.domain.menu.service;

import sharebuy.common.domain.Location;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.post.domain.Appointment;
import sharebuy.domain.post.domain.ParticipationStatus;
import sharebuy.domain.post.domain.Place;
import sharebuy.domain.post.domain.PostStatus;
import sharebuy.domain.post.entity.Participation;
import sharebuy.domain.post.entity.Post;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.domain.UserStatus;
import sharebuy.domain.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static sharebuy.domain.order.domain.Category.ETC;
import static sharebuy.domain.post.domain.PurchaseType.ONLINE;
import static sharebuy.domain.user.domain.Gen.Y;

public class MenuTestFixture {

    public static User user(UUID id,UserStatus userStatus) {
        return User.builder()
                .id(id)
                .userStatus(userStatus)
                .password("ABC")
                .address(new Address("주소1","주소2","우편번호",10.1,10.2))
                .email(emailGenerate())
                .telephone(telephoneGenerator())
                .birth(LocalDate.now())
                .gen(Y)
                .nickName("TEST")
                .roleType(RoleType.USER)
                .loginId(loginIdGenerator())
                .build();
    }
    public static User user(UserStatus userStatus) {
        return User.builder()
                .userStatus(userStatus)
                .password("ABC")
                .address(new Address("주소1","주소2","우편번호",10.1,10.2))
                .email(emailGenerate())
                .telephone(telephoneGenerator())
                .birth(LocalDate.now())
                .gen(Y)
                .nickName("TEST")
                .roleType(RoleType.USER)
                .loginId(loginIdGenerator())
                .build();
    }


    public static Post post(UUID postId, User owner, PostStatus status) {
        Appointment appointment = setAppointment();

        return new Post(
                postId,
                owner,
                "제목",
                "내용",
                ONLINE,
                "쿠팡",
                "1234",
                "https://test.com",
                10000,
                500,
                LocalDateTime.now(),
                status,
                appointment,
                List.of(),
                List.of(),
                0,
                5,
                ETC
        );
    }
    public static Post post(UUID postId, User owner, PostStatus status,Integer currentParticipants,Integer maxParticipants) {
        Appointment appointment = setAppointment();

        return new Post(
                postId,
                owner,
                "제목",
                "내용",
                ONLINE,
                "쿠팡",
                "1234",
                "https://test.com",
                10000,
                500,
                LocalDateTime.now(),
                status,
                appointment,
                List.of(),
                List.of(),
                currentParticipants,
                maxParticipants,
                ETC
        );
    }

    private static Appointment setAppointment() {
        LocalDateTime localDateTime = LocalDateTime.now()
                .plusDays(3)
                .withHour(14)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        ;
        Appointment appointment = new Appointment(new Place(new Location(10.1,10.2),"장소1"),localDateTime);
        return appointment;
    }

    public static Participation participation(
            User user,
            Post post,
            int amount,
            ParticipationStatus status
    ) {
        return Participation.builder()
                .id(UUID.randomUUID())
                .user(user)
                .post(post)
                .amount(amount)
                .payAt(LocalDateTime.now())
                .participationStatus(status)
                .build();
    }

    private static String telephoneGenerator(){
        Random random = new Random();
        return String.format("010-%04d-%04d", random.nextInt(10000),random.nextInt(10000));
    };

    private static String loginIdGenerator(){
        Random random = new Random();
        return "test_"+random.nextInt(100000);
    }

    private static String emailGenerate(){
        Random random= new Random();
        return "test_"+random.nextInt(1000)+"@naver.com";
    }
}

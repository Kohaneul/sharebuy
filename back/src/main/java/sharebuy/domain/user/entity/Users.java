package sharebuy.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.domain.Gen;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "아이디는 필수입니다.")
    @Column(nullable = false,unique = true)
    private String userId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "주소는 필수입니다.")
    @Embedded
    private Address address;

    @NotBlank(message = "연락처는 필수입니다.")
    @Column(nullable = false,unique = true)
    private String telephone;

    @NotNull(message = "생년월일은 필수입니다.")
    @Column(nullable = false)
    private LocalDate birth;

    @Email
    @NotBlank(message = "이메일은 필수입니다.")
    @Column(nullable = false)
    private String email;

    @NotNull(message = "성별정보는 필수입니다.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gen gen;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;


}

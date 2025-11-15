package sharebuy.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @NotBlank(message = "이메일은 필수입니다.")
    @Column(nullable = false)
    private Address address;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String birth;

    @Email
    private String email;
    private Gen gen;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}

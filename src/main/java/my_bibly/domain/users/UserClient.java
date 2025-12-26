package my_bibly.domain.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import my_bibly.domain.base.UserBase;

@Entity
@Getter
@Setter
public class UserClient extends UserBase {
    private String username;
    private String password;
    private int rating;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;
}
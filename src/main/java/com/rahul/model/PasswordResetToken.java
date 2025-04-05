package com.rahul.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private @NonNull String token;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private @NonNull User user;

    private @NonNull Date expiryDate;

    public boolean isExpired(){
        return expiryDate.before(new Date());
    }

}

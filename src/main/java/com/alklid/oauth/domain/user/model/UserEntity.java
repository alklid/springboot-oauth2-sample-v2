package com.alklid.oauth.domain.user.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "users_sid_seq", allocationSize = 1)
    @Column(name = "sid")
    @Setter(AccessLevel.NONE)
    private Long sid;

    private String email;
    private String name;
    private String pwd;
    private String permissions;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(updatable = true, name = "last_modified_at")
    private Instant lastModifiedAt;

    @Column(name = "date_update")
    private Instant dateUpdate;
}

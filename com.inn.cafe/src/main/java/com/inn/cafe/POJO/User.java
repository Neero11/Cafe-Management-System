package com.inn.cafe.POJO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")
@Data
@Builder
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")

public class User implements Serializable {
    @Serial
    private  static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private String  id;

    @Column(name = "name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private boolean status;

    @Column(name = "role")
    private String role;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}

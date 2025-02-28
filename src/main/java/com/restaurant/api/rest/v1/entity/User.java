package com.restaurant.api.rest.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.restaurant.api.rest.v1.vo.UserRequestVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonRootName(value = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id")
    private Long id;

    @Column(name = "fullname", length = 80, nullable = false)
    @JsonProperty(value = "fullname")
    private String fullname;

    @Column(name = "username", length = 80, nullable = false)
    @JsonProperty(value = "username")
    private String username;

    @Column(name = "email", length = 50, nullable = false)
    @JsonProperty(value = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password", length = 100, nullable = false)
    @JsonProperty(value = "password")
    private String password;

    @Column(name = "language_code", length = 5, nullable = false)
    @JsonProperty(value = "languageCode")
    private String languageCode;

    @Column(name = "currency_code", length = 3, nullable = false)
    @JsonProperty(value = "currencyCode")
    private String currencyCode;

    @Column(name = "timezone", length = 6, nullable = false)
    @JsonProperty(value = "timezone")
    private String timezone;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, columnDefinition = "datetime")
    @JsonProperty(value = "creationDate")
    private LocalDateTime creationDate;

    @ManyToMany
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups = new ArrayList<>();

    public User(UserRequestVO userRequestVO) {
        this.fullname = userRequestVO.getFullname().trim().replaceAll("\\s+", " ");
        this.username = userRequestVO.getUsername().trim().replaceAll("\\s+", " ");
        this.email = userRequestVO.getEmail().trim().replaceAll("\\s+", " ");
        this.password = userRequestVO.getPassword().trim().replaceAll("\\s+", "");
        this.languageCode = userRequestVO.getLanguageCode().trim().replaceAll("\\s+", "");
        this.currencyCode = userRequestVO.getCurrencyCode().trim().replaceAll("\\s+", "");
        this.timezone = userRequestVO.getTimezone().trim().replaceAll("\\s+", "");
        this.creationDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", timezone='" + timezone + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) || Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullname, username);
    }
}

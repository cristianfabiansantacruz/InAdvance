package com.test.usersapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.PrePersist;


@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @Column(nullable = false, length = 300)
    private String name;

    @Column(nullable = false, length = 300, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false)
    private String token;

    @Column
    @CreationTimestamp
    private Date createDate;

    @Column
    @UpdateTimestamp()
    private Date modifiedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<PhoneEntity> phones;

//    @Column(columnDefinition = "boolean default TRUE")
//    @Column(columnDefinition="BOOLEAN DEFAULT 1")
    @Column
    @ColumnDefault("TRUE")
    private Boolean isActive;

    @Column
    @LastModifiedDate
    private Date lastLoginDate;

    @PrePersist
    public void prePersist(){
        if(getIsActive() == null) setIsActive(true);

        if(getLastLoginDate() == null) setLastLoginDate(new Date());
    }

}

package com.whiz.app.boot.domain.model;

import com.whiz.app.boot.domain.model.vo.Credential;
import com.whiz.app.boot.domain.model.vo.Fullname;
import com.whiz.app.boot.interfaces.dto.form.NewUserForm;
import com.whiz.app.boot.interfaces.dto.form.UserUpdatedForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@Entity
@Table(indexes = {
    @Index(name = "IDX_USER_NAME", columnList = "username")
})
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER_PROFILE_ID")
    @GenericGenerator(
        name = "SEQ_USER_PROFILE_ID",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "sequence_name", value = "SEQ_USER_PROFILE_ID"),
            @Parameter(name = "initial_value", value = "4"),
            @Parameter(name = "increment_size", value = "1")
        }
    )
    private Long id;

    @Embedded
    private Credential credential;

    @Embedded
    private Fullname fullname;

    @NotNull
    @Size(min = 5, max = 50)
    @Email
    private String email;

    @NotNull
    private Boolean activated;

    @ManyToMany
    @JoinTable(
        name = "USER_AUTHORITY",
        joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")
        }
    )
    @BatchSize(size = 20)
    private List<Authority> authorities = new ArrayList<>();

    public static UserProfile from(NewUserForm newUserForm, List<Authority> authorities) {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        return builder()
            .credential(new Credential(newUserForm.getUsername(), bcpe.encode(newUserForm.getUsername())))
            .fullname(new Fullname(newUserForm.getFirstname(), newUserForm.getLastname()))
            .email(newUserForm.getEmail())
            .activated(true)
            .authorities(authorities)
            .build();
    }

    public UserProfile sync(UserUpdatedForm userUpdatedForm, List<Authority> authorities) {
        setFullname(new Fullname(userUpdatedForm.getFirstname(), userUpdatedForm.getLastname()));
        setEmail(userUpdatedForm.getEmail());
        setActivated(userUpdatedForm.getActivated());
        setAuthorities(authorities);
        return this;
    }

    @Override
    public String toString() {
        return "UserProfile#{" +
            "id=" + id +
            ",credential=" + credential +
            ",fullname=" + fullname +
            ",email=" + email +
            ",activated=" + activated +
            ",authorities=" + authorities +
            "}";
    }
}
package com.service.template.user.entity;

import com.service.template.common.security.UserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "template_user")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends AuditableEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Enumerated(STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(name = "email")
    private String email;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "display_name")
    private String displayName;

    @Type(type = "string-array")
    @Column(
            name = "custom_claims",
            columnDefinition = "text[]"
    )
    private String[] customClaims;
}

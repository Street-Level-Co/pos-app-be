package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserOrganizationId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "org_id", nullable = false)
    private UUID orgId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserOrganizationId entity = (UserOrganizationId) o;
        return Objects.equals(this.userId, entity.userId) &&
               Objects.equals(this.orgId, entity.orgId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, orgId);
    }
}

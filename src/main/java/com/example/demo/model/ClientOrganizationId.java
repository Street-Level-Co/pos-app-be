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
public class ClientOrganizationId implements Serializable {
    private static final long serialVersionUID = 446486820955368666L;
    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "org_id", nullable = false)
    private UUID orgId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ClientOrganizationId entity = (ClientOrganizationId) o;
        return Objects.equals(this.clientId, entity.clientId) &&
               Objects.equals(this.orgId, entity.orgId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, orgId);
    }

}

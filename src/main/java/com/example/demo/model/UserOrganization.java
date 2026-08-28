package com.example.demo.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_organization")
public class UserOrganization implements Serializable {
    @EmbeddedId
    private UserOrganizationId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("orgId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    public UserOrganization(User user, Organization org) {
        this.user = user;
        this.org = org;
        this.id = new UserOrganizationId(user.getId(), org.getId());
    }
}

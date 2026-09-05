package com.example.demo.repository;

import com.example.demo.model.Organization;
import com.example.demo.model.User;
import com.example.demo.model.UserOrganization;
import com.example.demo.model.UserOrganizationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserOrganizationRepository extends JpaRepository<UserOrganization, UserOrganizationId> {

    @Query("select uo.org from UserOrganization uo where uo.user.id = :userId")
    List<Organization> findOrganizationsByUserId(@Param("userId") UUID userId);

    @Query("select uo.user from UserOrganization uo where uo.org.id = :orgId")
    List<User> findUsersByOrgId(@Param("orgId") UUID orgId);
}

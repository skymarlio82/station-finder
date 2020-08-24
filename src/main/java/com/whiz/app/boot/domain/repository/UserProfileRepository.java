package com.whiz.app.boot.domain.repository;

import com.whiz.app.boot.domain.model.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {

    @Query(value = "select up from UserProfile up where up.credential.username=?1")
    @EntityGraph(attributePaths = "authorities")
    Optional<UserProfile> findOneWithAuthoritiesByUsername(String username);

    @EntityGraph(attributePaths = "authorities")
    Optional<UserProfile> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Optional<UserProfile> findOneByEmailIgnoreCase(String email);

    @Query(value = "select up from UserProfile up order by up.id")
    Page<UserProfile> findAllUserProfiles(Pageable pageable);
}
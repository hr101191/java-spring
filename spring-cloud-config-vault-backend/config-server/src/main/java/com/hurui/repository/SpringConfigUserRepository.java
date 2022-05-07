package com.hurui.repository;

import com.hurui.entity.SpringConfigUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringConfigUserRepository extends JpaRepository<SpringConfigUser, String> {
}

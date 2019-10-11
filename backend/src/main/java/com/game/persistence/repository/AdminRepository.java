package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin , Long> {

}

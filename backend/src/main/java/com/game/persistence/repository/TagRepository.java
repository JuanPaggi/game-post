package com.game.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.persistence.models.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag , Long> {

}

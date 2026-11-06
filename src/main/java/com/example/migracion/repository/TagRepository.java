package com.example.migracion.repository;

import com.example.migracion.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String tagName);
}

package com.vsouza.editora.repositories;

import com.vsouza.editora.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Boolean existsByEmail(String email);
}

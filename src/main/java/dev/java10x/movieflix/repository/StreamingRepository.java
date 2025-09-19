package dev.java10x.movieflix.repository;

import dev.java10x.movieflix.entity.Streaming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamingRepository extends JpaRepository<Streaming, Long>{
}

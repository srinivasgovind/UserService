package dev.srinivas.UserService.repository;

import dev.srinivas.UserService.model.Session;
import dev.srinivas.UserService.model.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByTokenAndUser_Id(String token, Long userId);
}

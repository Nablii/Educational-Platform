package tn.enis.repo;

import tn.enis.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFullNameAndPassword(String fullName, String password);
    Optional<User> findByFullNameOrEmail(String fullName, String email);
}

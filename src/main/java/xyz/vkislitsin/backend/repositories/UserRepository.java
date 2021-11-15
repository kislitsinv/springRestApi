package xyz.vkislitsin.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.vkislitsin.backend.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT name FROM User WHERE id = ?1", nativeQuery = true)
    String findNameById(Long id);

    @Query(value = "SELECT MAX(id) FROM User", nativeQuery = true)
    Long findMaxId();

    User findByName(String userName);

    List<User> findByOrderByNameAsc();
}

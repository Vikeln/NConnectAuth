package co.ke.auth.repositories;

import co.ke.auth.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RoleDao extends JpaRepository<Role, Integer> {
    Role findByName(String name);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions WHERE r.id = ?1")
    Role findRole(Integer id);

    List<Role> findAllByDateTimeCreatedIsNotNullOrderByIdDesc();
}

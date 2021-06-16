package co.ke.auth.repositories;

import co.ke.auth.entities.Role;
import co.ke.auth.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PermissionDao extends JpaRepository<Permission, Integer> {
    Permission findByName(String name);

    List<Permission> findAllByRolesIn(List<Role> roles);

    List<Permission> findAllByNameIn(List<String> roles);

    List<Permission> findAllByIdIn(List<Integer> ids);
}

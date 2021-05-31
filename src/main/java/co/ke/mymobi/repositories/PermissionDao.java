package co.ke.mymobi.repositories;

import co.ke.mymobi.entities.Permission;
import co.ke.mymobi.entities.Role;
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

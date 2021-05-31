package co.ke.mymobi.repositories;

import co.ke.mymobi.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface TenantDao extends JpaRepository<Tenant, Integer> {
    Tenant findDistinctByName(String name);
    Optional<Tenant> findDistinctByAppKey(String appKey);
    boolean existsByAppKey(String key);
}

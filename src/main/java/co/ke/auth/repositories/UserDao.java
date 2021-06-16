package co.ke.auth.repositories;

import co.ke.auth.entities.Role;
import co.ke.auth.entities.User;
import co.ke.auth.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserDao extends JpaRepository<User, Integer> {
    Optional<User> findDistinctByCorrelator(String c);

    Optional<User> findByEmailAndUserName(String c, String u);

    Optional<User> findByEmailOrUserNameIgnoreCase(String c, String u);

    //    User findByEmail(String email);
    Optional<User> findByEmail(String email);

    User findTopByUserNameIgnoreCase(String userName);

    User findByPhoneNumberLike(String phoneNumber);

    Boolean existsByUserNameIgnoreCase(String username);

    Boolean existsByEmailIgnoreCase(String email);

    List<User> findAllByTenantOrderByIdDesc(Tenant t);

    Collection<User> findAllByIdIn(List<Integer> ids);

    Collection<User> findAllByRole(Role role);
}

package co.ke.auth.repositories;


import co.ke.auth.entities.Account;
import co.ke.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountDao extends JpaRepository<Account, Integer> {
    Optional<Account> findTop1ByUsername(String username);

    Optional<Account> findDistinctByUserId(User user);

    @Query(
            value = "SELECT ap.permission_id FROM tbl_account_permissions ap WHERE ap.account_id = ?1",
            nativeQuery = true
    )
    List<Integer> findAccountPermissions(Integer account);
}


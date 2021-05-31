package co.ke.mymobi.repositories;


import co.ke.mymobi.entities.Account;
import co.ke.mymobi.entities.Permission;
import co.ke.mymobi.entities.User;
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


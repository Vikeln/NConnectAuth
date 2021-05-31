package co.ke.mymobi.repositories;

import co.ke.mymobi.entities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserTypeDao extends JpaRepository<UserType, Integer> {
}


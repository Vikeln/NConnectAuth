package co.ke.auth.repositories;

import co.ke.auth.entities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserTypeDao extends JpaRepository<UserType, Integer> {
}


package net.mahamanjari.springboot.repository;

import net.mahamanjari.springboot.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {

    UserAccount findByUsername(String username);

}

package technikal.task.fishmarket.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import technikal.task.fishmarket.models.ShopUser;

import java.util.Optional;

public interface ShopUserRepository extends JpaRepository<ShopUser, Long> {
    @Transactional(readOnly = true)
    Optional<ShopUser> findByUsername(String username);
}

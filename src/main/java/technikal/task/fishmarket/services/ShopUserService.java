package technikal.task.fishmarket.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import technikal.task.fishmarket.models.ShopUser;
import technikal.task.fishmarket.models.ShopUserRole;

public class ShopUserService implements UserDetailsService {

    private final ShopUserRepository shopUserRepository;

    private final PasswordEncoder passwordEncoder;

    public ShopUserService(ShopUserRepository shopUserRepository, PasswordEncoder passwordEncoder) {
        this.shopUserRepository = shopUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void recreateDefaultUsers() {
        ShopUser admin = new ShopUser();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(ShopUserRole.ADMIN);
        shopUserRepository.save(admin);

        ShopUser user = new ShopUser();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRole(ShopUserRole.USER);

        shopUserRepository.save(user);
    }

    public void registerUser(String username, String password) {
        if (shopUserRepository.findByUsername(username).isPresent()) {
            throw new ShopUserAlreadyExistsException("Такий користувач вже існує: " + username);
        }
        ShopUser user = new ShopUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(ShopUserRole.USER);
        shopUserRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return shopUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Користувач не знайдений: " + username));
    }
}

package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public User findByUsername(String username);

    void add(User user);
    List<User> listUsers();
    void update(User user);
    void delete(User user);
    User getById(int id);

    void add(Role role);
    List<Role> listRoles();
    Role getRoleById(int id);
}

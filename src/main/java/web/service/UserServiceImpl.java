package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findByUsername(s);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("user с именем %s не найден", s));
        }
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), mapRoleToAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthority(Collection<Role> roles) {
        return roles.stream().map(m -> new SimpleGrantedAuthority(m.getName())).collect(Collectors.toList());
    }

    @Transactional
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void update(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public User getById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public Role getRoleById(int id) {
        return roleRepository.getById(id);
    }
}

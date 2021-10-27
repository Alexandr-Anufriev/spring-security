package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Service
public class AddGuest {
    private final UserService userService;

    @Autowired
    public AddGuest(UserService userService) {
        this.userService = userService;
        addGuestToDB();
    }

    @PostConstruct
    private void addGuestToDB() {
        System.out.println(userService.existsByUsername("guest"));
        if(!userService.existsByUsername("guest")){
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(new Role(2,"ROLE_USER"));
            User guest = new User("guest","guest","guest@mail.ru", roleSet);
            userService.add(guest);
        }
    }
}

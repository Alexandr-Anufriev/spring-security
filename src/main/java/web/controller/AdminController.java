package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping()
    public String getAdminsPage(Principal principal, Model model) { // principal - это инфо о тек пользователе
        model.addAttribute("admin", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.listUsers());
        return "/admin/admin";
    }
    @GetMapping("/new")
	public String newUser(@ModelAttribute("user") User user) {
		return "/admin/new";
	}

    @PostMapping
	public String create(@ModelAttribute("user") User user) {
        user.setRoles(Collections.singleton(new Role(2, "ROLE_USER")));
		userService.add(user);
		return "redirect:/admin";
	}

	@GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        User user = userService.findByUsername(userService.getById(id).getUsername());
        List<Role> roles = userService.listRoles();
        model.addAttribute("user", user);
        model.addAttribute("role", roles);
        return "/admin/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user) {
        Set<Role> set = new HashSet<>();
        set.add(new Role(2, "ROLE_USER"));
        if((user.getUsername()).equals("admin")) {
            set.add(new Role(1, "ROLE_ADMIN"));
        }
        user.setRoles(set);
        System.out.println("patch" + user);
        userService.update(user);
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        User user = userService.getById(id);
        userService.delete(user);
        return "redirect:/admin";
    }

}

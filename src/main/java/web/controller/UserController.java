package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public String showUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/users/user";
    }


    // 		--- из CRUD	---

//	@GetMapping("users")
//	public String index(Model model) {
//
//		return "users/index";
//	}
//	@GetMapping("users/{id}")
//	public String user(@PathVariable("id") int id, Model model) {
//		model.addAttribute("user", userService.getUser(id));
//		return "users/user";
//	}
//	@GetMapping("users/new")
//	public String newUser(@ModelAttribute("user") User user) {
//		return "/users/new";
//	}
//
//	@GetMapping("users/{id}/edit")
//	public String edit(@PathVariable("id") int id, Model model) {
//		model.addAttribute("user", userService.getUser(id));
//		return "/users/edit";
//	}
//
//	@PatchMapping("users/{id}")
//	public String update(@ModelAttribute("user") User user) {
//		userService.update(user);
//		return "redirect:/users";
//	}
//	@DeleteMapping("users/{id}")
//	public String delete(@PathVariable("id") int id) {
//		userService.delete(id);
//		return "redirect:/users";
//	}
}
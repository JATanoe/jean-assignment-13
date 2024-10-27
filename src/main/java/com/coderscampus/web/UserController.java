package com.coderscampus.web;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ch.qos.logback.core.model.Model;
import com.coderscampus.domain.Account;
import com.coderscampus.domain.User;
import com.coderscampus.repository.AccountRepository;
import com.coderscampus.service.AccountService;
import com.coderscampus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	private final AccountService accountService;

	@Autowired
	public UserController(UserService userService, AccountService accountService) {
		this.userService = userService;
		this.accountService = accountService;
	}
	
	@GetMapping("/register")
	public String getCreateUser (ModelMap model) {
		model.put("user", new User());
		model.addAttribute("user", new User());
//		return "users/create";
		return "register";
	}
	
	@PostMapping("/register")
	public String postCreateUser (User user) {
		System.out.println(user);
		userService.saveUser(user);
		return "redirect:/users";
	}
	
	@GetMapping("/users")
	public String getAllUsers (ModelMap model) {
		Set<User> users = userService.findAll();

		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
			model.addAttribute("user", users.iterator().next());
		}
		model.addAttribute("users", users);
//		return "users/index";
		return "users";
	}
	
	@GetMapping("/users/{userId}")
	public String getOneUser (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		model.put("users", Collections.singletonList(user));
		model.put("user", user);
		model.addAttribute("user", user);
		return "users";
	}

	@PostMapping("/users/{userId}")
	public String postOneUser (User user) {
		userService.saveUser(user);
		return "redirect:/users/" + user.getUserId();
	}

	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser (@PathVariable Long userId) {
		userService.delete(userId);
		return "redirect:/users";
	}

	@PostMapping("/users/{userId}/accounts")
	public String postCreateAccount (@PathVariable Long userId) {
		User user = userService.findById(userId);
		Account account = new Account();
		Long accountId = userService.saveAccount(user, account).getAccountId();
		return "redirect:/users/" + userId + "/accounts/" + accountId;
	}

	@GetMapping("/users/{userId}/accounts/{accountId}")
    public String getOneAccount(@PathVariable Long userId, @PathVariable Long accountId, ModelMap model) {
        User user = userService.findById(userId);
        Account account = accountService.findById(accountId);
		userService.saveAccount(user, account);
        model.addAttribute("account", account);
        model.addAttribute("user", user);
        return "account";
    }

	@PostMapping("/users/{userId}/accounts/{accountId}")
	public String postUpdateAccount (Account account, User user) {
		userService.saveAccount(user, account);
		return "redirect:/users/" + user.getUserId() + "/accounts/" + account.getAccountId();
	}

}

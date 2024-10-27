package com.coderscampus.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.coderscampus.domain.Account;
import com.coderscampus.domain.Address;
import com.coderscampus.domain.User;
import com.coderscampus.repository.AccountRepository;
import com.coderscampus.repository.AddressRepository;
import com.coderscampus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private final UserRepository userRepo;
	private final AccountRepository accountRepo;
	private final AddressRepository addressRepo;
	private final AccountService accountService;

	@Autowired
	public UserService(UserRepository userRepo, AccountRepository accountRepo, AddressRepository addressRepo, AccountService accountService) {
		this.userRepo = userRepo;
		this.accountRepo = accountRepo;
		this.addressRepo = addressRepo;
		this.accountService = accountService;
	}

	public List<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	public List<User> findByNameAndUsername(String name, String username) {
		return userRepo.findByNameAndUsername(name, username);
	}
	
	public List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2) {
		return userRepo.findByCreatedDateBetween(date1, date2);
	}
	
	public User findExactlyOneUserByUsername(String username) {
		List<User> users = userRepo.findExactlyOneUserByUsername(username);
		if (!users.isEmpty())
			return users.getFirst();
		else
			return new User();
	}
	
	public Set<User> findAll () {
		return userRepo.findAllUsersWithAccountsAndAddresses();
	}
	
	public User findById(Long userId) {
		Optional<User> userOpt = userRepo.findById(userId);
		return userOpt.orElse(new User());
	}

	public User saveUser(User user) {
		if (user.getUserId() == null) {
			Address address = user.getAddress();
			user.setAddress(address);
			address.setUser(user);

			Account checking = new Account();
			checking.setAccountName("Checking Account");
			checking.getUsers().add(user);

			Account savings = new Account();
			savings.setAccountName("Savings Account");
			savings.getUsers().add(user);

			user.getAccounts().add(checking);
			user.getAccounts().add(savings);
			accountRepo.save(checking);
			accountRepo.save(savings);
			return userRepo.save(user);
		}

		User existingUser = this.findById(user.getUserId());
		existingUser.setUsername(user.getUsername());
		existingUser.setPassword(user.getPassword());
		existingUser.setName(user.getName());

		Address existingAddress = user.getAddress();
		existingAddress.setUserId(user.getUserId());
		existingAddress.setAddressLine1(user.getAddress().getAddressLine1());
		existingAddress.setAddressLine2(user.getAddress().getAddressLine2());
		existingAddress.setCity(user.getAddress().getCity());
		existingAddress.setRegion(user.getAddress().getRegion());
		existingAddress.setCountry(user.getAddress().getCountry());
		existingAddress.setZipCode(user.getAddress().getZipCode());
		addressRepo.save(existingAddress);

		return userRepo.save(existingUser);
	}

	public void delete(Long userId) {
		userRepo.deleteById(userId);
	}

	public Account saveAccount(User user, Account account) {
		if (account.getAccountId() == null) {
			int numberOfAccounts = user.getAccounts().size() + 1;
			Account newAccount = new Account();
			newAccount.setAccountName("Account #" + numberOfAccounts);
			newAccount.getUsers().add(user);
			user.getAccounts().add(newAccount);
			return accountRepo.save(newAccount);
		}
		Account existingAccount = accountService.findById(account.getAccountId());
		existingAccount.setAccountName(account.getAccountName());
		return accountRepo.save(account);
    }

}

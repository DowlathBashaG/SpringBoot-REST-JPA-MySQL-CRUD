package io.dowlathbasha.restjpamysql.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.dowlathbasha.restjpamysql.exception.UserNotFoundException;
import io.dowlathbasha.restjpamysql.model.User;
import io.dowlathbasha.restjpamysql.repository.UserRepository;

@RestController
public class UserResource {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			 throw new UserNotFoundException("id -"+id);
		}
		return user.get();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder
				       .fromCurrentRequest()
				       .path("/{id}")
				       .buildAndExpand(savedUser.getId())
				       .toUri();
				       
			
		return ResponseEntity.created(location).build();
				
	}

}

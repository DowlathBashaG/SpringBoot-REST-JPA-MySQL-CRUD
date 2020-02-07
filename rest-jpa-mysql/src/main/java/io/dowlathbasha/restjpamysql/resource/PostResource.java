package io.dowlathbasha.restjpamysql.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.dowlathbasha.restjpamysql.exception.UserNotFoundException;
import io.dowlathbasha.restjpamysql.model.Post;
import io.dowlathbasha.restjpamysql.model.User;
import io.dowlathbasha.restjpamysql.repository.PostRepository;
import io.dowlathbasha.restjpamysql.repository.UserRepository;

@RestController
public class PostResource {   

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@GetMapping("/users/{id}/posts")
	public List<Post>  retrieveAllPostsByUser(@PathVariable int id){
		   Optional<User> user = userRepository.findById(id);
		   if(!user.isPresent()) {
			   throw new UserNotFoundException("id-"+id);
		   }
		   return user.get().getPosts();
		   
	}
	
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Object> createPosts(@PathVariable int id,@RequestBody Post post){
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent()) {
			 throw new UserNotFoundException("id- "+id);
		}
	    User user= userOptional.get();
		post.setUser(user);
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.
				       fromCurrentRequest().
				       path("/{id}").
				       buildAndExpand(post.getPostId()).toUri();
		
		return org.springframework.http.ResponseEntity.created(location).build();
	}
}

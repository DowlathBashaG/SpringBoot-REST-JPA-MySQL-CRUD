# SpringBoot-REST-JPA-MySQL-CRUD

How to expose your endpoints in REST API.

Step 1 :  IDENTIFY YOUR RESOURCES

          Example :  Lets take for instance an application that manages a library.
                     (Ex Resources) :
                                        User
                                        Post
                                        
Step 2 :  DEFINE YOUR ENDPOINTS AND METHODS
          
          User :
          
          Get all users GET :    /users
          Create user   POST :   /users
          Retrieve one user GET : /users/{id}    ->  /users/1
          Delete a user GET : /user/{id}   -> /user/1
          
          Post :
          
          Retrieve all posts for a user  GET : /users/{id}/posts
          Create  a posts for a user POST : /users/{id}/posts
          Retrieve details of a post GET : /users/{id}/posts/{post_id}
          
Step 3 :  EXTERNALIZE YOUR RESOURCES
          
          As JSON / PDF / TEXT / XML

Step 4 :  IMPLEMENT THE IDENTIFIED ENDPOINTS 
            
          @GetMapping("/users")
          public List<User> findAllUsers(){
                 return userRepository.findAll();
          }
          
          @GetMapping("/users/{id}")
          public User findUser(@PathVariable int id){
                Optional<User> optionalUser = userRepository.findById(id);
                if(!optionalUser.isPresent()){
                     throw new UserNotFoundException("id-"+id);
                 }
                return optionalUser.get();
          }
           
         @PostMapping("/users")
         public ResposeEntity<Object> createUser(@RequestBody User user){
              User savedUser = userRepository.save(user);
              URI location = ServlertsUriCompoentsBuilder().
                        fromCurrentRequest().
                        path("/{id}").
                        buildAndExpand(savedUser.get()).
                        toUri().
              return ReponseEntity.created(location).build();
         }
         
         @DeletMapping("/uses/{id}")
         public void deleteUser(@PathVariable int id){
               userRepository.deleteById(id);
         }
         
         @GetMapping("/users/{id}/posts")
         public List<Post> retrieveAllPostsByUser(@PathVariable int id){
                Optional<user> user= userRepository.findById(id);
                if(!user.isPresent){
                      throw new UserNotFoundException("id-"+id);
                 }
                      return user.get().getPosts();
        }
        
        @PostMapping("/users/{id}/posts/{id}")
        public ResponseEntity<Post>  createPost(@PathVariable int id, @RequestBody Post post){
                Optional<user> userOptional = userRepository.findById(id);
                if(!userOptional.isPresent(){
                    throw new UserNotFoundException("id-"+id);
                }
                
                User user = userOptional.get();
                post.setUser(user);
                postRepository.save(post);
                
                
                URI location = ServlertsUriCompoentBuilder.
                               fromCurrentRequest().
                               path("/{id}").
                               buildAndExpand(post.getPostId()).toUri():
                               
               return ResponseEntity.created(location).toUril();
                               
      }       
          
This Repository contains Model, Repository ,Resource Folders.

1. application.properties / yml file change your username and password for MySQL.
2. two tables belongs userinfo schema.
      
      A. User
      B. Post
      
      User having with Post - OneToMany  ( One user having more posts )
      Post having with User - ManyToOne  ( Many post having one User  )
      
 Url's :
 
 A. GET All Users :   http://localhost:9001/users/
 
 B. GET Specific User based on Id : http://localhost:9001/users/10
 
 C. Create User using Post Method : http://localhost:9001/users/
 
 D. Get All Users post based on Id : http://localhost:9001/users/1/post
 
 E: Create Post using Post Method (Specific User Id . Already implemented OneToMany and ManyToOne : http://localhost:9001/users/1/post
 
 F: Get All users and posts based on postId : http://localhost:9001/users/1/post/100
      
      

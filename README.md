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
         
         @DeletMapping("/users/{id}")
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
 
 
 
 # 11 things you should know about GET vs POST

For quite a long time I have been using this question during interviews and it is quite surprising the number of people who doesn’t have enough understanding of the two most used RESTful API methods: GET and POST. Because I believe many people will feel tempted to not read this through, I prepared a simple quiz based on differences between these two methods and followed with not only the answers but their applications.


- GET or POST?

- Considering a browser as a client, which method’s parameters are stored on browser’s session history?

- Considering a browser as a client, which method can be cached?

- Which one is a “safe” method?

- Which one is NOT idempotent?

- If I copy and paste an endpoint url to a browser’s address bar and press enter, which method is invoked by default?

- Which method request has a body?

- Considering a Static Website, what is the only method this app responds to?

- Which method has length restrictions?

- Which method is more secure and should be used to deal with sensitive data?

- Which method can be bookmarked?

- Which method only allows ASCII characters?

Do you know all the answers and why? If you don’t, there is no reason to be ashamed. Lets analyze these two methods with more details and understand their peculiarities.

GET parameters will be stored on browser’s session history.
The browser uses its history to navigate the user back and forward, consequently, every new URL inputted will go automatically to the history. But what if a GET request is done implicitly through a SPA (via ajax or axios)? In this case the request URL has to be pushed manually via history.pushState and it can be updated on the browser’s URL address without redirecting the user.'

Consider the 2 different scenarios:

A. Go to https://www.kogan.com/au/shop/?q=laptop and click on any filter (“free shipping” for example). The website will make a new GET request, fetch the response, re-render the list of products, update the browser url address and save the url in the browser history (not necessarily on this order). Although the page was not refreshed, the new query was still pushed to the browser history, hence you will be able to navigate back to the initial page.


Note that a new request is made without refreshing the page, the back button is now enabled and the url set in the browser is even different of the one in the inspection.

B. Now got to https://www.kogan.com/au/shop/?q=laptop and instead of clicking on the any filter, scroll to the bottom and click on “View More”. The website will make a new GET request, fetch the response, re-render the list of products and update the browser url address. However, it won’t save the url in the browser history. Although the page was not refreshed, because the new query was not pushed to the browser history, you will NOT be able to navigate back to the initial page.

Note that a new request is made without refreshing the page, the back button is still not enabled even after the browser url change.
In regards to POST, if the user navigates back after submitting a form, the data will be re-submitted (the browser should alert the user that the data are about to be re-submitted), but it won’t be kept on the history.

2. GET responses can be cached

Because GET is idempotent and most of the webpage resources are returned via this method, the browser by default will cache get requests. So the next time you visit the webpage, instead of going to the server and requesting all the images again, it will simply load from the browser’s cache.
In the context of REST APIs, when making multiple identical requests has the same effect as making a single request — then that REST API is called idempotent.
The benefit of it, is that your website will load much faster after the first time. However, new deployments could not reach the client if they have the old website cached in their browsers. As a result, for the cache to be invalidated, the URL of each request has to change or the client has to do it on their browsers. For example, if the CSS of your website is represented by the URL cdn-static-1.medium.com/_/fp/css/main-branding-base.-y85vioUz7M8dDBgC99oNg.css, a new deployment will only change the layout of thewebsite if this url is changed to something like cdn-static-1.medium.com/_/fp/css/main-branding-base.newhashkey.css.


POST can’t be cached on the client side.
POST is not Idempotent (4).

3. Get is a safe method

Because this method should NEVER change a resource (If it does you are not implementing RESTful best practices), it is considered safe.
Safe methods are HTTP methods that do not modify resources.
As a result, it can be safely cached, saved on browser’s history and saved on search engines, such as google. This is because the parameters will be stored in the URL and recalling the method won’t cause any change in the server. The POST, on the other hand, is not a safe method.

7. Static Websites only responds to GET requests

A static website is an application which does not need any other tool to process its files as they will return browser readable content (Javascript, images, CSS, HTML). As a result, it only needs to respond to GET requests in order to return the html pages. So, what are the implications of this information?
Did you know you can host a static website by just using AWS S3? I am confident that many people will say yes. However did also know that it only allows GET and HEAD requests? According to their documentation, all the objects requested on a S3 bucket has to be through GET.

8. Which method has length restrictions?

It is really important to understand that the GET method will always be translated to an URL in a browser (http protocol) and only ASCII characters are allowed (11). In other words, any text you put in your browser URL address will be making a server GET request (5) and, even if a GET request is done implicitly through a website (via ajax for example), it will still convert it to an URL format with the parameters as in a querystring.
Knowing that a GET request on a browser will send the parameters through the URL, what do you think will happen if there are too many filters? Although this number can slightly differ between browsers, a safe URL length limit is often 2048 characters, minus the number of characters in the actual path. As a result, if you are building an website in which your product can have a large variety of filters, the GET method may not be enough.
Regarding POST, because the payload is send in the request’s body, technically, there is no limit of data to be sent (6) neither data type restrictions.


9. Which method is more secure and should be used to deal with sensitive data?


While many people get this one right, they usually don’t know why. Considering a basic user-password login endpoint, have you ever wondered why it is used a POST and not a GET? Yes, it is related to safety, but not because the information is hidden. It is related to one of the first points we discussed here: browser caching and history.
Imagine if you start typing a website on your browser and it automatically suggests an URL with user and password as part of the query string. It may sound strange but a GET login endpoint would allow that and I am sure that is not what you want.


10. Which method can be bookmarked?


POST should not be bookmarked and the reasons are a combination of a few topics discussed above:
Attempting to bookmark a POST will just result in a GET operation on the URL.
The method is not idempotent so there is not guarantee the response will always be the same. It could result in a duplicated bank transaction, for example.
The URL would lose its parameters as bookmark doesn’t support a body payload.
It may contain sensitive data, which should not be stored.


Conclusion

Although GET is very often used to fetch data and POST to send/save it, we can conclude that there are much more about these two methods and being aware of theses peculiarities will not only help with a better code, but also architecture, design and problem solving. So, how was your score?
Resources

https://restfulapi.net/idempotent-rest-apis/
https://www.w3schools.com/tags/ref_httpmethods.asp
https://restfulapi.net/http-methods/
https://docs.aws.amazon.com/AmazonS3/latest/dev/WebsiteHosting.htm
      
      

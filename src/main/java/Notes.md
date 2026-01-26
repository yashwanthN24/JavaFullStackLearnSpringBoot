## Annotation used So Far 

##### Basic 

``@Component``- To tell java to manage Object Creation for that class you mark this annotation on , Marks a class so Spring automatically finds and manages it as a reusable object.

  ``@Bean:``  - Tells a method in config class to create and provide a specific reusable object.

 ``@Configuration:``  Marks a class as a place to define and setup reusable objects using @Bean methods.

``@AutoWired`` - To Do Dependency at the property level But Generally we dont use this as we prefer (Constructor Based Dependency Injection)

```@Autowired``` works on fields, setters, or constructors to inject dependencies automatically by type.
​

```@Qualifier``` resolves which bean to pick when multiple matches exist by name.

   ``@Primary`` Used when you want to priortize one class (Bean) over other when mulitiple options are possible for Dependency injection 
   
#####  SpringBoot WEB and MVC Specific

``@RestController`` - used to mark a class as an Controller for handling requests and even json conversion via jakson as its a combination of @Controller + @ResponseBody 

``@RequestMapping`` - To mark Controller Level base prefix like users/ 

 ``@GetMapping`` - To Mark a method/route handler as a GET HTTP Method handler 
 
 ``@PostMapping`` - To Mark a method/route handler as a POST HTTP Method handler 
 
  ``@PutMapping`` - To Mark a method/route handler as a PUT HTTP Method handler 
  
   ``@PatchMapping`` - To Mark a method/route handler as a PATCH HTTP Method handler
    
``@DeleteMapping`` -  To Mark a method/route handler as a DELETE HTTP Method handler 

``@RequestBody`` - For parsing RequestBody passed generally for POST Request often called as payload

  ``@PathVariable`` - For parsing the variables used in the url route path 
  
``@RequestParam`` - For parsing query params passed with get request often for pagination  like ?age=value&page=2

 ``@Service`` - To mark a class as a service where we handle the core bussiness logic like interaction with db via repository etc 
 
``@Repository`` - To mark a class/interface as a Repository to interact with an Entity (Table) in DB 

  #### Entity Specific

``@Entity`` - To mark a class as a Entity and object of this mark as Rows in that Entity

``@Id`` - To mark a field as  primary key of the entity 

``@GeneratedValue`` - Makes the primary key field as autoincrement so that user doesnt need to do manually , used primarily with primary key @Id marked field 

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING)

   For Enums types in Entity creates a jointable automatically to the field you apply as its a collection field like HashSet()


  ##### DTO Specific (Data Transfer Objects)

Uses Jakarta validation Annoation like @NotNull , @NotBlank , @Min , @Max etc

`@Valid`- To Enforce validation on the Request body in post or put reuest `used common at parameter level ex: @RequestBody @Valid EmployeeDTO e

Lombok Specific Annotation Lombok is commonly used for generating getters and setters and constructors using annotation avoiding boilerplatecode 

``@Data`` - Lombok Generates Getters and Setters 

``@AllArgsConstructor`` - Lombok generates all arguments constructor 

``@NoArgConstructor`` - Lombok generates No Argument constructor 

``@Builder`` - Lombok provide Builder pattern for this class meaning static method builder() provide instance of this class and can use method chaining upon it to call setter and set values and finally build the overall object

  ``@ReqArgConstructor`` - constructs a constructor for private final instance fields at class level  

### Exception Hanling Specific and Global Annoations 

``@ExceptionHandler`` - for handling exeption specific to one controller . Annotation applied on a method 

``@RestControllerAdvice`` - for handling all exception globally . Annotation applied on a class 


General Annotation are @Override , @FunctionalInterface these are for developer experience meaning 
they explicity get tp know we are overriding and This is a FunctionalInterface keep codebase clean as compiler enforce compiler time issues 

### Spring JPA and Hibernate 

 ``@Query`` - To provide custom jpql query or native-sql-query for your repository interface 

   ``@Transactional`` - To enable transactional context to enable same persistance context of hibernate lifecycle 

##### Relationships among Entities 

   ``@OneToOne`` - To establish one to one relationship between two entities 

``@ManyToOne`` -   

``@OneToMany`` 

``@ManyToMany`` 

``@JoinColumn`` - To control the foriegn key field naming 

 ``@JoinTable`` - Used along with @ManyToMany used to control the field names in join or junction table 

 ``@ToString.Exclude`` - To Exclude a field from ToString representation of an object without printing this field 

#### Other important Production ready annoatations 

``@Audit``  To Audit the current entity and track changes like updatedAt and createdAt and changed to specifice rows and their data and who did that change 

``@Slf4j``  - Lombok Annotation to setup Logger of SLF4j package build into spring  for that class 
            - Expands to 
```java 
private static final Logger log = LoggerFactory.getLogger(yourClassname.class);`
```

#### Spring Security Specific Annotations 

``@EnableWebSecurity`` - To take control of the filter chain applied to a @Configuration Class common for springboot seciruty setup 

``@EnableMethodSecurity(securedEnabled = true)``
 For Authoriazation at method level Role Based access control to enable @Secured annotation use 

  ``@Secured``  - for specify authorization based on Role 

   ``@PreAuthorize`` - for specifying authorization based on the Role and permission via expression


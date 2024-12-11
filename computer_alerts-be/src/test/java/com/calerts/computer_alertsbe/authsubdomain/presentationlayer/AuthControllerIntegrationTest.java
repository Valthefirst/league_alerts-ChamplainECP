package com.calerts.computer_alertsbe.authsubdomain.presentationlayer;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@ActiveProfiles("test")
//class AuthControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserService userService;
//
//    private UserRequestDTO validUserRequest;
//
//    @BeforeEach
//    void setUp() {
//        validUserRequest = new UserRequestDTO();
//        validUserRequest.setFirstName("Test");
//        validUserRequest.setEmail("test@example.com");
//        validUserRequest.setProfilePictureUrl("https://test.com/image.jpg");
//        validUserRequest.setUserId("auth0|123456");
//    }
//
//    @Test
//    void createUser_Successful() throws Exception {
//        // Arrange
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestJson = objectMapper.writeValueAsString(validUserRequest);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/create")
//                        .with(SecurityMockMvcRequestPostProcessors.oidcLogin().idToken(token -> token.subject(validUserRequest.getUserId())).authorities(new SimpleGrantedAuthority("ROLE_USER")))
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.firstName").value(validUserRequest.getFirstName()))
//                .andExpect(jsonPath("$.email").value(validUserRequest.getEmail()))
//                .andExpect(jsonPath("$.userId").value(validUserRequest.getUserId()));
//    }
//
//    @Test
//    void createUser_InvalidRequest() throws Exception {
//        // Arrange
//        UserRequestDTO invalidRequest = new UserRequestDTO();
//        invalidRequest.setUserId("auth0|invalid");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestJson = objectMapper.writeValueAsString(invalidRequest);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/create")
//                        .with(SecurityMockMvcRequestPostProcessors.oidcLogin().idToken(token -> token.subject(invalidRequest.getUserId())))
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void createUser_ConflictWhenUserExists() throws Exception {
//        // Arrange: Save a user in the service
//        userService.createUser(validUserRequest);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestJson = objectMapper.writeValueAsString(validUserRequest);
//
//        // Act & Assert
//        mockMvc.perform(post("/api/create")
//                        .with(SecurityMockMvcRequestPostProcessors.oidcLogin().idToken(token -> token.subject(validUserRequest.getUserId())))
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andExpect(status().isConflict());
//    }
//}

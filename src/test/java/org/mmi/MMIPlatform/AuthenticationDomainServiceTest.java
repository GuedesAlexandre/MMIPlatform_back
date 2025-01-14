package org.mmi.MMIPlatform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mmi.MMIPlatform.domain.models.User;
import org.mmi.MMIPlatform.domain.services.AuthenticationDomainService;
import org.mmi.MMIPlatform.infrastructure.dao.UserDao;
import org.mmi.MMIPlatform.infrastructure.db.adapter.UserDbAdapter;
import org.mmi.MMIPlatform.infrastructure.mapper.UserDaoMapper;
import org.mmi.MMIPlatform.infrastructure.security.jwt.JwtAdapter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationDomainServiceTest {

    @Mock
    private UserDaoMapper userDaoMapper;

    @Mock
    private UserDbAdapter userDbAdapter;

    @Mock
    private JwtAdapter jwtAdapter;

    @Mock
    private Argon2PasswordEncoder argon2PasswordEncoder;

    @InjectMocks
    private AuthenticationDomainService authenticationDomainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitUser() {
        User user = User.builder()
                .email("cherifa.boucetta@univ-eiffel.fr")
                .build();
        UserDao userDao = new UserDao();
        userDao.setEmail("cherifa.boucetta@univ-eiffel.fr");

        when(userDaoMapper.userToUserDao(any(User.class))).thenReturn(userDao);
        when(userDbAdapter.getUserDaoByEmail(anyString())).thenReturn(null);
        when(userDbAdapter.saveUserDao(any(UserDao.class))).thenReturn(userDao);
        when(userDaoMapper.userDaoToUser(any(UserDao.class))).thenReturn(user);

        User result = authenticationDomainService.initUser(user);

        assertNotNull(result);
        assertEquals("cherifa.boucetta@univ-eiffel.fr", result.getEmail());
        verify(userDbAdapter, times(1)).saveUserDao(any(UserDao.class));
    }

    @Test
    void testGetAllUsers() {
        UserDao userDao = new UserDao();
        userDao.setEmail("cherifa.boucetta@univ-eiffel.fr");
        List<UserDao> userDaoList = Collections.singletonList(userDao);
        User user = User.builder()
                .email("cherifa.boucetta@univ-eiffel.fr")
                .build();

        when(userDbAdapter.getAllUserDao()).thenReturn(userDaoList);
        when(userDaoMapper.userDaoListToUserList(anyList())).thenReturn(Collections.singletonList(user));

        List<User> result = authenticationDomainService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("cherifa.boucetta@univ-eiffel.fr", result.getFirst().getEmail());
    }

    @Test
    void testUpdateUser() {
        User user = User.builder()
                .email("cherifa.boucetta@univ-eiffel.fr")
                .build();
        UserDao userDao = new UserDao();
        userDao.setEmail("cherifa.boucetta@univ-eiffel.fr");

        when(userDaoMapper.userToUserDao(any(User.class))).thenReturn(userDao);
        when(userDbAdapter.updateUserDao(any(UserDao.class))).thenReturn("User updated successfully");
        when(userDaoMapper.userDaoToUser(any(UserDao.class))).thenReturn(user);

        User result = authenticationDomainService.updateUser(user);

        assertNotNull(result);
        assertEquals("cherifa.boucetta@univ-eiffel.fr", result.getEmail());
    }

    @Test
    void testDeleteUser() {
        String email = "cherifa.boucetta@univ-eiffel.fr";

        when(userDbAdapter.deleteUserDaoByEmail(anyString())).thenReturn("User deleted successfully");

        String result = authenticationDomainService.deleteUser(email);

        assertEquals("User deleted successfully with email : cherifa.boucetta@univ-eiffel.fr", result);
    }

    @Test
    void testAuthenticateUser() {
        String email = "cherifa.boucetta@univ-eiffel.fr";
        String password = "MMIPl@tform24!";
        UserDao userDao = new UserDao();
        userDao.setEmail(email);
        userDao.setPassword(Base64.getEncoder().encodeToString(password.getBytes()));

        when(userDbAdapter.getUserDaoByEmail(anyString())).thenReturn(userDao);
        when(argon2PasswordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtAdapter.generateToken(any(UserDao.class))).thenReturn("token");

        String result = authenticationDomainService.authenticateUser(email, password);

        assertNotNull(result);
        assertEquals("token", result);
    }

    @Test
    void testValidateToken() {
        String token = "token";

        when(jwtAdapter.validateToken(anyString())).thenReturn(true);

        Boolean result = authenticationDomainService.validateToken(token);

        assertTrue(result);
    }
}
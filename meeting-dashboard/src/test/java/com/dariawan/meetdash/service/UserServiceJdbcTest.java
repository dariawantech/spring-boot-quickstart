package com.dariawan.meetdash.service;

import com.dariawan.meetdash.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceJdbcTest {
    
    @Autowired 
    private DataSource dataSource;
    
    @Autowired 
    private UserService userService;
    
    @Before
    public void cleanTestData() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "delete from d_user where username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "orochi");
            ps.executeUpdate();
        }
    }
    
    @Test
    public void testFindAllUser() {
        List<User> users = userService.findAllUsers(1, 20);
        assertNotNull(users);
        assertTrue(users.size() == 2);
        for (User user : users) {
            assertNotNull(user.getId());
            assertNotNull(user.getUsername());
            assertNotNull(user.getPassword());
        }
    }
    
    @Test
    public void testSaveUpdateDeleteUser() throws Exception{
        User u = new User();
        u.setUsername("orochi");
        u.setPassword("$3rpenTL0rd");
        u.setFirstName("Shin-Orochi");
        
        userService.save(u);
        assertNotNull(u.getId());
        
        User findUser = userService.findUserById(u.getId());
        assertEquals("orochi", findUser.getUsername());
        assertEquals("Shin-Orochi", findUser.getFirstName());
        
        // update record
        u.setFirstName("Orochi");
        userService.save(u);
        
        // test after update
        findUser = userService.findUserById(u.getId());
        assertEquals("Orochi", findUser.getFirstName());
        
        // test delete
        userService.delete(u);
        
        // query after delete
        findUser = userService.findUserById(u.getId());        
        assertTrue(findUser==null);
    }
}

/**
 * Spring Boot Quickstart - Meeting Dashboard (https://www.dariawan.com/tutorials/spring/spring-boot-quick-start/)
 * Copyright (C) 2019 Dariawan <hello@dariawan.com>
 *
 * Creative Commons Attribution-ShareAlike 4.0 International License
 *
 * Under this license, you are free to:
 * # Share - copy and redistribute the material in any medium or format
 * # Adapt - remix, transform, and build upon the material for any purpose,
 *   even commercially.
 *
 * The licensor cannot revoke these freedoms
 * as long as you follow the license terms.
 *
 * License terms:
 * # Attribution - You must give appropriate credit, provide a link to the
 *   license, and indicate if changes were made. You may do so in any
 *   reasonable manner, but not in any way that suggests the licensor
 *   endorses you or your use.
 * # ShareAlike - If you remix, transform, or build upon the material, you must
 *   distribute your contributions under the same license as the original.
 * # No additional restrictions - You may not apply legal terms or
 *   technological measures that legally restrict others from doing anything the
 *   license permits.
 *
 * Notices:
 * # You do not have to comply with the license for elements of the material in
 *   the public domain or where your use is permitted by an applicable exception
 *   or limitation.
 * # No warranties are given. The license may not give you all of
 *   the permissions necessary for your intended use. For example, other rights
 *   such as publicity, privacy, or moral rights may limit how you use
 *   the material.
 *
 * You may obtain a copy of the License at
 *   https://creativecommons.org/licenses/by-sa/4.0/
 *   https://creativecommons.org/licenses/by-sa/4.0/legalcode
 */
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

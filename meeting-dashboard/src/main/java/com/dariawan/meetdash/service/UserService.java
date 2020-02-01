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

import com.dariawan.meetdash.dao.UserDao;
import com.dariawan.meetdash.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {
    
    @Autowired
    private UserDao userDao;
    
    public void save(User u) {
        // since this is 1st time
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(u.getPassword());
        u.setPassword(hashedPassword);

        userDao.save(u);
    }

    public void delete(User m) {
        userDao.delete(m);
    }

    public User findUserById(int id) {
        return userDao.findById(id);
    }

    public User findUserByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return null;
        }
        return userDao.findByUsername(username);
    }

    public List<User> findAllUsers(int pageNumber, int rowPerPage) {
        return userDao.findAll(pageNumber, rowPerPage);
    }

    public Long countAllUsers() {
        return userDao.count();
    }
}

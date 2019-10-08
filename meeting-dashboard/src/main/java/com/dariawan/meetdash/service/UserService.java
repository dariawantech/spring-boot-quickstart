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

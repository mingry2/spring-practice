package com.likelion.dao;

import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    @Test
    void addAndGet() throws SQLException {
        User user1 = new User("7","DDD","7777");

        UserDao userDao = context.getBean("awsUserDao",UserDao.class);
        userDao.deleteAll();
        assertEquals(0,userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        User user = userDao.findById(user1.getId());

        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(),user.getPassword());
    }

    @Test
    void count() throws SQLException {

        User user1 = new User("4","ggg","1234");
        User user2 = new User("5","hhh","1235");
        User user3 = new User("6","jjj","1236");

        UserDao userDao = context.getBean("awsUserDao",UserDao.class);
        userDao.deleteAll();
        assertEquals(0,userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());
    }
}
package com.likelion.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {
    // 조립 해주는 것
    @Bean
    public UserDao awsUserDao() { // 날개 5개 선풍기
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(awsConnectionMaker);
        return userDao;
    }

    @Bean
    public UserDao localUserDao() { // 날개 4개 선풍기
        UserDao userDao = new UserDao(new LocalConnectionMaker());
        return userDao;
    }
}


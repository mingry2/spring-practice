package com.likelion.dao;

import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.EmptyStackException;
import java.util.Map;

public class UserDao {

    private ConnectionMaker connectionMaker;
    public UserDao() {
        this.connectionMaker = new AWSConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void deleteAll() throws SQLException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement ps = conn.prepareStatement("delete from users");
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public int getCount() throws SQLException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement ps = conn.prepareStatement("select count(*) from users");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        conn.close();
        return count;
    }

    public void add(User user) {
        Map<String, String> env = System.getenv();
        try {
            Connection c = connectionMaker.makeConnection();

            PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) {
        Map<String, String> env = System.getenv();
        Connection c;
        try {
            c = connectionMaker.makeConnection();

            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            User user = null;
            if(rs.next()) {
                user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
            }
            rs.close();
            pstmt.close();
            c.close();

            // 없으면 exception
            if(user == null) throw new EmptyResultDataAccessException(1);

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao();
        userDao.add(new User("2","BBB","2222")); // --추가
//        User user = userDao.findById("4"); // --검색
//        System.out.println(user.getName());
//        userDao.deleteAll(); // --삭제
    }
}
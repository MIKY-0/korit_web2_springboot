package com.koreait.spring_boot_study.repository.mapper;

import com.koreait.spring_boot_study.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    int addUser(User user); // 회원가입 - User엔티티 전달.
    Optional<User> getUserByUserName(String userName);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(int userId);
}

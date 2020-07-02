package com.note.back.dao;

import com.note.back.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer> {
    //使用username找到对应的user实体
    User findByUsername(String username);

}

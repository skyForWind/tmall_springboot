package com.how2java.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.how2java.tmall.pojo.User;

public interface UserDao extends JpaRepository<User,Integer>{

}
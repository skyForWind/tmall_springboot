package com.how2java.tmall.dao;

import com.how2java.tmall.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 *  CategoryDAO 类集成了 JpaRepository，就提供了CRUD和分页的各种常见功能。 这就是采用 JPA 方便的地方~
 * */
public interface CategoryDao extends JpaRepository<Category, Integer> {
}
package com.example.demoSpringSecurity.repository;

import com.example.demoSpringSecurity.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBUserRepository extends JpaRepository<DBUser, Integer> {
  DBUser findByUsername(String username);

  DBUser save(DBUser user);
}

package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.TodoEntity;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    List<TodoEntity> findByUserId(String userId);       // Spring Data JPA가 자동으로 "SELECT * FROM TodoRepository WHERE uiserId = '{userId}'" 같은 쿼리 작성해서 실행

    // 복잡한 쿼리는 @Query annotation을 활용
    @Query("SELECT * FROM Todo t WHERE t.title = ?1")   // ?1은 매개변수의 순서 위치 의미
    List<TodoEntity> findByTitle(String title);
}

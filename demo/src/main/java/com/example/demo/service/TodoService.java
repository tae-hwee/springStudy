package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public String testService() {
        //TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My First Todo Item").build();
        //TodoEntity 저장
        repository.save(entity);
        //TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();

        return savedEntity.getTitle();
    }

    /**
     * entity의 유효성을 검증하는 validate()를 따로 빼서 정의하는 refactoring
     * create()의 경우 직접 method body를 작성했음
     */
    private void validate(final TodoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if (entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        // Validation
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if (entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }

        repository.save(entity);

        log.info("Entity ID: {} is saved.", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> retreive(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        // STEP 1 입력받은 entity의 유효성 검증
        validate(entity);

        // STEP 2 넘겨받은 entity의 id를 이용해 TodoEntity를 가져옴.
        final Optional<TodoEntity> original = repository.findById((entity.getId()));

        // STEP 3 넘겨받은 TodoEntity에 새 값을 덮어줌
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            // STEP 4 DB에 새로 덮어 쓴 entity를 저장
            repository.save(todo);
        });

        // STEP 5 retrieve()를 호출해 모든 TodoList를 반환
        return retreive(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);

        try {
            // STEP 1 입력 받은 entity를 삭제
            repository.delete(entity);
        } catch (Exception e) {
            // STEP 1-1 만약 exception 발생 시 id와 exception을 로깅
            log.error("Error occurred while deleting the entity ", entity.getId(), e);
            // STEP 1-2 exception을 controller로 전송. DB 내부 로직을 캡슐화하려면 e를 반환하지 않고 새로운 exception object를 반환
            throw new RuntimeException("Error occurred while deleting the entity " + entity.getId());
        }

        // STEP 2 새로운 TodoList를 가져와서 리턴
        return retreive(entity.getUserId());
    }
}

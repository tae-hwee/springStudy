package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.service.TodoService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {
    @Autowired TodoService service;

    @GetMapping("/testService")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);

        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String tempUserId = "temporary-user";

            // STEP 1 TodoEntity로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);

            // STEP 2 생성 시점에는 id가 없어야 하므로 id를 null로 초기화
            entity.setId(null);

            // STEP 3 임시 사용자 id를 설정. 아직 인증/인가 기능 없으므로 temporary-user만 사용 가능한 app으로 작성
            entity.setUserId(tempUserId);

            // STEP 4 서비스 이용해 TodoEntity를 생성
            List<TodoEntity> entities = service.create(entity);

            // STEP 5 java stream을 이용해 반환된 entity list를 TodoDTO로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // STEP 6 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // STEP 7 ResponseDTO를 반환
            return ResponseEntity.ok().body(response);
        } catch(Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping
    public ResponseEntity<?> retreiveTodoList() {
        String tempUserId = "temporary-user";

        // STEP 1 service method retreive() method를 이용해 TodoList를 가져옴
        List<TodoEntity> entities = service.retreive(tempUserId);

        // STEP 2 Java Stream을 이용해 반환된 entity list를 TodoDTO로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // STEP 3 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // STEP 4 ResponseDTO를 반환
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
        String tempUser = "temporary-user";

        // STEP 1 입력받은 dto를 entity로 변환
        TodoEntity entity = TodoDTO.toEntity(dto);

        // STEP 2 id를 tempUser로 초기화
        entity.setUserId(tempUser);

        // STEP 3 Service Layer에서 정의한 update()를 호출해 entity를 수정
        List<TodoEntity> entities = service.update(entity);

        // STEP 4 Java Stream을 이용해 반환된 entity를 TodoDTO 리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // STEP 5 변환된 TodoDTO 리스트를 ResponseDTO로 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // 변환된 ResponseDTO를 반환
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
        try {
            String tempUserId = "temporary-user";

            // STEP 1 입력 받은 dto를 entity로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);

            // STEP 2 인증인가가 아직 없으므로 임시 사용자 아이디를 설정.
            entity.setUserId(tempUserId);

            // STEP 3 service를 호출해 entity를 삭제
            List<TodoEntity> entities = service.delete(entity);

            // STEP 4 Java Stream을 이용해 삭제된 entity를 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // STEP 5 변환된 TodoDTO List를 이용해 ResponseDTO를 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // STEP 6 ResponseDTO를 반환
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}

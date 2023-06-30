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
}

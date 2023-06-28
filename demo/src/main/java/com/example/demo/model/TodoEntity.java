package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoEntity {
    private String id;          // object의 id
    private String userId;      // object를 생성한 사용자의 id
    private String title;       // To Do title (예: 운동하기)
    private boolean done;       // To Do 완료 여부
}

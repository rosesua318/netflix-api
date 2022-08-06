package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    /*
    private String UserName;
    private String id;
    private String email;
    private String password;
     */
    private int membership; // basic=1, standard=2, premium=3
    // 카드는 외부 api 이용 가능성 있음
    private Card card; // 카드 번호, 만료일(월/년), 이름, 생년, 생월, 생일, 전화번호
    private String email;
    private String password;
}

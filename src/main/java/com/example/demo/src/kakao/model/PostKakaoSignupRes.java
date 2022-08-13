package com.example.demo.src.kakao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostKakaoSignupRes {
    private int userIdx;
    private String jwt;
}

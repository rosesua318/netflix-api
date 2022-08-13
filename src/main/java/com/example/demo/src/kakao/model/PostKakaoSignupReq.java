package com.example.demo.src.kakao.model;

import com.example.demo.src.user.model.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostKakaoSignupReq {
    private int userIdx;
    private String email;
    private String password;
    private int membershipIdx;
    private Card card;
}

package com.example.demo.src.content.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostEvaluateReq {
    private int userIdx;
    private int contentIdx;
    private int likes;
}

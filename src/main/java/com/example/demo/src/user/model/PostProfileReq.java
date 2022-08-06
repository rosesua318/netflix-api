package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostProfileReq {
    private String name;
    private String image;
    private int isKids;
    private int presentLangIdx;
}

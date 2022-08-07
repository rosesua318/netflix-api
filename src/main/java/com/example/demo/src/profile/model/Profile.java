package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Profile {
    private int userIdx;
    private int profileIdx;
    private String name;
    private String image;
    private String password;
    private int isNext;
    private int isPreview;
    private int presentLangIdx;
    private String status;
}

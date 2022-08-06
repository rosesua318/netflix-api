package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PutProfileReq {
    private int profileIdx;
    private String name;
    private String image;
    private int isNext;
    private int isPreview;
    private int presentLangIdx;
}

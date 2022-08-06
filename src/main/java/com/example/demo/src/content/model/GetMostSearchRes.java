package com.example.demo.src.content.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMostSearchRes {
    private int contentIdx;
    private String title;
    private String thumbnail;
}

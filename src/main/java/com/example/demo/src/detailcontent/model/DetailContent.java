package com.example.demo.src.detailcontent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DetailContent {
    private int dtContentIdx;
    private int episode;
    private String title;
    private String playtime;
    private String info;
    private int canDownload;
}

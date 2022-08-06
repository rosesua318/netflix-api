package com.example.demo.src.content.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetHotRes {
    private int contentIdx;
    private String title;
    private String thumbnail;
    private String previewUrl;

    private String videoUrl;
    private String age;
    private String info;
    private int isZzim;
    private List<String> features;

    public GetHotRes(int contentIdx, String title, String thumbnail, String previewUrl, String videoUrl, String age, String info, int isZzim) {
        this.contentIdx = contentIdx;
        this.title = title;
        this.thumbnail = thumbnail;
        this.previewUrl = previewUrl;
        this.videoUrl = videoUrl;
        this.age = age;
        this.info = info;
        this.isZzim = isZzim;
    }
}

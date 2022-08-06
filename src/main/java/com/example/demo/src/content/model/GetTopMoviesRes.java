package com.example.demo.src.content.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetTopMoviesRes {
    private int contentIdx;

    private int ranking;
    private String title;
    private String thumbnail;
    private String previewUrl;

    private String videoUrl;
    private String age;
    private String info;
    private int isZzim;
    private List<String> features;

    public GetTopMoviesRes(int contentIdx, int ranking, String title, String thumbnail, String previewUrl, String videoUrl, String age, String info, int isZzim) {
        this.contentIdx = contentIdx;
        this.ranking = ranking;
        this.title = title;
        this.thumbnail = thumbnail;
        this.previewUrl = previewUrl;
        this.videoUrl = videoUrl;
        this.age = age;
        this.info = info;
        this.isZzim = isZzim;
    }
}

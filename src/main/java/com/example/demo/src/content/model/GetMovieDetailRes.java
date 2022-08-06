package com.example.demo.src.content.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetMovieDetailRes {
    private int contentIdx;
    private String title;
    private String previewUrl;
    private String videoUrl;
    private String releasedAt;
    private String age;
    private String playtime;
    private String quality;
    private int isSaved;
    private String info;
    private List<String> actors;
    private List<String> producers;
    private List<String> writers;
    private List<String> genres;
    private List<String> features;
    private int isZzim;
    private int evaluation;

    public GetMovieDetailRes(int contentIdx, String title, String previewUrl, String videoUrl, String releasedAt, String age, String playtime, String quality, int isSaved, String info, int isZzim, int evaluation) {
        this.contentIdx = contentIdx;
        this.title = title;
        this.previewUrl = previewUrl;
        this.videoUrl = videoUrl;
        this.releasedAt = releasedAt;
        this.age = age;
        this.playtime = playtime;
        this.quality = quality;
        this.isSaved = isSaved;
        this.info = info;
        this.isZzim = isZzim;
        this.evaluation = evaluation;
    }
}

package com.example.demo.src.detailcontent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetSeriesDetailRes {
    private int contentIdx;
    private String title;
    private String previewUrl;
    private String videoUrl;
    private String releasedAt;
    private String age;
    private String totalSeason;
    private String quality;
    private int isSaved;
    private String info;
    private List<String> actors;
    private List<String> grades;
    private List<String> genres;
    private List<String> features;
    private int isZzim;
    private int evaluation;
    private String season;
    private List<DetailContent> detailContents;

    public GetSeriesDetailRes(int contentIdx, String title, String previewUrl, String videoUrl, String releasedAt, String age, String quality, int isSaved, String info, int isZzim, int evaluation) {
        this.contentIdx = contentIdx;
        this.title = title;
        this.previewUrl = previewUrl;
        this.videoUrl = videoUrl;
        this.releasedAt = releasedAt;
        this.age = age;
        this.quality = quality;
        this.isSaved = isSaved;
        this.info = info;
        this.isZzim = isZzim;
        this.evaluation = evaluation;
    }
}

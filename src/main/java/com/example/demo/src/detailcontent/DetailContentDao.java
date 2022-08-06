package com.example.demo.src.detailcontent;

import com.example.demo.src.content.model.GetMovieDetailRes;
import com.example.demo.src.content.model.GetTopMoviesRes;
import com.example.demo.src.detailcontent.model.DetailContent;
import com.example.demo.src.detailcontent.model.GetSeriesDetailRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DetailContentDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetSeriesDetailRes getSeriesDetails(int profileIdx, int contentIdx, int season) {
        String getSeriesDetailsQuery = "select c.contentIdx, c.title, c.previewUrl, c.videoUrl, c.releasedAt, c.age, c.quality, " +
                "case when(select sc.contentIdx from SaveContent sc where sc.contentIdx = c.contentIdx and sc.profileIdx = ?) " +
                "is not null then 1 else 0 end as isSaved, c.info, " +
                "case when(select lc.contentIdx from LikeContent lc where lc.contentIdx = c.contentIdx and lc.profileIdx = ?) " +
                "is not null then 1 else 0 end as isZzim, " +
                "(select e.likes from Evaluate e where profileIdx = ? and contentIdx = ?) as evaluation" +
                " from Content c where c.contentIdx = ?";
        Object[] getSeriesDetailsParams = new Object[]{profileIdx, profileIdx, profileIdx, contentIdx, contentIdx};

        GetSeriesDetailRes getSeriesDetailRes = this.jdbcTemplate.queryForObject(getSeriesDetailsQuery,
                (rs, rowNum) -> new GetSeriesDetailRes(
                        rs.getInt("contentIdx"),
                        rs.getString("title"),
                        rs.getString("previewUrl"),
                        rs.getString("videoUrl"),
                        rs.getString("releasedAt"),
                        rs.getString("age"),
                        rs.getString("quality"),
                        rs.getInt("isSaved"),
                        rs.getString("info"),
                        rs.getInt("isZzim"),
                        rs.getInt("evaluation")),
                getSeriesDetailsParams);

        String getTotalSeasonQuery = "select count(*) as season from DetailContent dc join Content c on dc.contentIdx = c.contentIdx " +
                "where c.contentIdx = ? group by dc.season";
        String getTotalSeasonParams = String.valueOf(contentIdx);

        List<Integer> totalSeason = this.jdbcTemplate.query(getTotalSeasonQuery,
                (rs, rowNum) -> new Integer(
                        rs.getString("season")),
                getTotalSeasonParams);
        getSeriesDetailRes.setTotalSeason("시즌 ".concat(String.valueOf(totalSeason.size())).concat("개"));



        String getActorQuery = "select a.name from Content c join ContentActor ca on c.contentIdx = ca.contentIdx " +
                "join Actor a on a.actorIdx = ca.actorIdx where c.contentIdx = ?";
        String getActorParams = String.valueOf(contentIdx);
        List<String> actors = this.jdbcTemplate.query(getActorQuery,
                (rs, rowNum) -> new String(
                        rs.getString("name")),
                getActorParams);
        getSeriesDetailRes.setActors(actors);

        String getGradeQuery = "select vg.category from Content c join ContentVideoGrade cv on c.contentIdx = cv.contentIdx " +
                "join VideoGrade vg on vg.videoGradeIdx = cv.videoGradeIdx where c.contentIdx = ?";
        String getGradeParams = String.valueOf(contentIdx);
        List<String> grades = this.jdbcTemplate.query(getGradeQuery,
                (rs, rowNum) -> new String(
                        rs.getString("category")),
                getGradeParams);
        getSeriesDetailRes.setGrades(grades);

        String getGenreQuery = "select g.type from Content c join ContentGenre cg on c.contentIdx = cg.contentIdx " +
                "join Genre g on g.genreIdx = cg.genreIdx where c.contentIdx = ?";
        String getGenreParams = String.valueOf(contentIdx);
        List<String> genres = this.jdbcTemplate.query(getGenreQuery,
                (rs, rowNum) -> new String(
                        rs.getString("type")),
                getGenreParams);
        getSeriesDetailRes.setGenres(genres);

        String getFeatureQuery = "select f.type from Content c join SeriesFeature sf on c.contentIdx = sf.contentIdx " +
                "join Feature f on sf.featureIdx = f.featureIdx where c.contentIdx = ?";
        String getFeatureParams = String.valueOf(contentIdx);
        List<String> features = this.jdbcTemplate.query(getFeatureQuery,
                (rs, rowNum) -> new String(
                        rs.getString("type")),
                getFeatureParams);
        getSeriesDetailRes.setFeatures(features);

        getSeriesDetailRes.setSeason("시즌 ".concat(String.valueOf(season)));


        String getDetailContentQuery = "select dc.dtContentIdx, dc.episode, dc.title, dc.playtime, dc.info, dc.canDownload" +
                " from DetailContent dc join Content c on dc.contentIdx = c.contentIdx where c.contentIdx = ? and season = ?";
        Object[] getDetailContentParams = new Object[]{contentIdx, "시즌 ".concat(String.valueOf(season))};

        List<DetailContent> detailContents = this.jdbcTemplate.query(getDetailContentQuery,
                (rs, rowNum) -> new DetailContent(
                        rs.getInt("dtContentIdx"),
                        rs.getInt("episode"),
                        rs.getString("title"),
                        rs.getTime("playtime").toString().split(":")[1].concat("분"),
                        rs.getString("info"),
                        rs.getInt("canDownload")),
                getDetailContentParams);

        getSeriesDetailRes.setDetailContents(detailContents);

        return getSeriesDetailRes;
    }
}

package com.example.demo.src.content;

import com.example.demo.src.content.model.*;
import com.example.demo.src.profile.model.GetProfileRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ContentDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int checkContent(int contentIdx) {
        String checkContentQuery = "select exists(select contentIdx from Content where contentIdx = ?)";
        String checkContentParams = String.valueOf(contentIdx);
        return this.jdbcTemplate.queryForObject(checkContentQuery,
                int.class,
                checkContentParams);

    }

    public int checkEvaluate(int profileIdx, int contentIdx) {
        String checkEvaluateQuery = "select exists(select evaluateIdx from Evaluate where profileIdx = ? and contentIdx = ?)";
        Object[] checkEvaluateParams = new Object[]{profileIdx, contentIdx};
        return this.jdbcTemplate.queryForObject(checkEvaluateQuery,
                int.class,
                checkEvaluateParams);

    }

    public GetEvaluateRes getEvaluate(int profileIdx, int contentIdx) {
        String getEvaluateQuery = "select evaluateIdx, contentIdx, likes from Evaluate where profileIdx = ? and contentIdx = ?";
        Object[] getEvaluateParams = new Object[]{profileIdx, contentIdx};
        return this.jdbcTemplate.queryForObject(getEvaluateQuery,
                (rs, rowNum) -> new GetEvaluateRes(
                        rs.getInt("evaluateIdx"),
                        rs.getInt("contentIdx"),
                        rs.getInt("likes")),
                getEvaluateParams);
    }

    public List<GetLikeRes> getLikes(int profileIdx){
        String getLikesQuery = "select c.contentIdx, c.title, c.thumbnail from Content c join LikeContent lc" +
                " ON c.contentIdx = lc.contentIdx JOIN Profile p ON p.profileIdx = lc.profileIdx where p.profileIdx =?";
        String getLikesParams = String.valueOf(profileIdx);
        return this.jdbcTemplate.query(getLikesQuery,
                (rs, rowNum) -> new GetLikeRes(
                        rs.getInt("contentIdx"),
                        rs.getString("title"),
                        rs.getString("thumbnail")),
                getLikesParams);

    }

    public List<GetMostSearchRes> getMostSearches(){
        String getMostSearchesQuery = "select c.contentIdx, c.title, c.thumbnail from Content c join MostSearch ms" +
                " ON c.contentIdx = ms.contentIdx";
        return this.jdbcTemplate.query(getMostSearchesQuery,
                (rs, rowNum) -> new GetMostSearchRes(
                        rs.getInt("contentIdx"),
                        rs.getString("title"),
                        rs.getString("thumbnail")));

    }

    public List<GetMainCategoryRes> getMainCategories() {
        String getMainCategoriesQuery = "select mc.idx, c.type from MainCategory mc join Category c " +
                "on mc.categoryIdx = c.categoryIdx";
        return this.jdbcTemplate.query(getMainCategoriesQuery,
                (rs, rowNum) -> new GetMainCategoryRes(
                        rs.getInt("idx"),
                        rs.getString("type")));

    }

    public List<GetSeriesCategoryRes> getSeriesCategories() {
        String getSeriesCategoriesQuery = "select sc.seriesCategoryIdx, c.type from SeriesCategory sc join Category c " +
                "on sc.categoryIdx = c.categoryIdx";
        return this.jdbcTemplate.query(getSeriesCategoriesQuery,
                (rs, rowNum) -> new GetSeriesCategoryRes(
                        rs.getInt("seriesCategoryIdx"),
                        rs.getString("type")));

    }

    public List<GetMovieCategoryRes> getMovieCategories() {
        String getMovieCategoriesQuery = "select mc.movieCategoryIdx, c.type from MovieCategory mc join Category c " +
                "on mc.categoryIdx = c.categoryIdx";
        return this.jdbcTemplate.query(getMovieCategoriesQuery,
                (rs, rowNum) -> new GetMovieCategoryRes(
                        rs.getInt("movieCategoryIdx"),
                        rs.getString("type")));

    }

    public List<GetHotRes> getHots(int profileIdx) {
        String getHotsQuery = "select c.contentIdx, c.title, c.thumbnail, c.previewUrl, c.videoUrl, c.age, c.info," +
                "case when(select lc.contentIdx from LikeContent lc where lc.contentIdx = c.contentIdx and lc.profileIdx = ?) " +
                "is not null then 1 else 0 end as isZzim from Content c join Hot on Hot.contentIdx = c.contentIdx";
        Object[] getHotsParams = new Object[]{profileIdx};

        List<GetHotRes> getHotRes = this.jdbcTemplate.query(getHotsQuery,
                (rs, rowNum) -> new GetHotRes(
                        rs.getInt("contentIdx"),
                        rs.getString("title"),
                        rs.getString("thumbnail"),
                        rs.getString("previewUrl"),
                        rs.getString("videoUrl"),
                        rs.getString("age"),
                        rs.getString("info"),
                        rs.getInt("isZzim")),
                getHotsParams);

        for(GetHotRes g : getHotRes) {
            String getFeatureQuery = "select f.type from Content c join NewHotFeature nf on c.contentIdx = nf.contentIdx " +
                    "join Feature f on nf.featureIdx = f.featureIdx where c.contentIdx = ?";
            String getFeatureParams = String.valueOf(g.getContentIdx());
            List<String> features = this.jdbcTemplate.query(getFeatureQuery,
                    (rs, rowNum) -> new String(
                            rs.getString("type")),
                    getFeatureParams);
            g.setFeatures(features);
        }
        return getHotRes;
    }

    public List<GetTopSeriesRes> getTopSeries(int profileIdx) {
        String getTopSeriesQuery = "select c.contentIdx, t.ranking, c.title, c.thumbnail, c.previewUrl, c.videoUrl, c.age, c.info," +
                "case when(select lc.contentIdx from LikeContent lc where lc.contentIdx = c.contentIdx and lc.profileIdx = ?) " +
                "is not null then 1 else 0 end as isZzim from Content c join Top10Series t on t.contentIdx = c.contentIdx";
        Object[] getTopSeriesParams = new Object[]{profileIdx};

        List<GetTopSeriesRes> getTopSeriesRes = this.jdbcTemplate.query(getTopSeriesQuery,
                (rs, rowNum) -> new GetTopSeriesRes(
                        rs.getInt("contentIdx"),
                        rs.getInt("ranking"),
                        rs.getString("title"),
                        rs.getString("thumbnail"),
                        rs.getString("previewUrl"),
                        rs.getString("videoUrl"),
                        rs.getString("age"),
                        rs.getString("info"),
                        rs.getInt("isZzim")),
                getTopSeriesParams);

        for(GetTopSeriesRes g : getTopSeriesRes) {
            String getFeatureQuery = "select f.type from Content c join NewHotFeature nf on c.contentIdx = nf.contentIdx " +
                    "join Feature f on nf.featureIdx = f.featureIdx where c.contentIdx = ?";
            String getFeatureParams = String.valueOf(g.getContentIdx());
            List<String> features = this.jdbcTemplate.query(getFeatureQuery,
                    (rs, rowNum) -> new String(
                            rs.getString("type")),
                    getFeatureParams);
            g.setFeatures(features);
        }
        return getTopSeriesRes;
    }

    public List<GetTopMoviesRes> getTopMovies(int profileIdx) {
        String getTopMoviesQuery = "select c.contentIdx, t.ranking, c.title, c.thumbnail, c.previewUrl, c.videoUrl, c.age, c.info," +
                "case when(select lc.contentIdx from LikeContent lc where lc.contentIdx = c.contentIdx and lc.profileIdx = ?) " +
                "is not null then 1 else 0 end as isZzim from Content c join Top10Movie t on t.contentIdx = c.contentIdx";
        Object[] getTopMoviesParams = new Object[]{profileIdx};

        List<GetTopMoviesRes> getTopMoviesRes = this.jdbcTemplate.query(getTopMoviesQuery,
                (rs, rowNum) -> new GetTopMoviesRes(
                        rs.getInt("contentIdx"),
                        rs.getInt("ranking"),
                        rs.getString("title"),
                        rs.getString("thumbnail"),
                        rs.getString("previewUrl"),
                        rs.getString("videoUrl"),
                        rs.getString("age"),
                        rs.getString("info"),
                        rs.getInt("isZzim")),
                getTopMoviesParams);

        for(GetTopMoviesRes g : getTopMoviesRes) {
            String getFeatureQuery = "select f.type from Content c join NewHotFeature nf on c.contentIdx = nf.contentIdx " +
                    "join Feature f on nf.featureIdx = f.featureIdx where c.contentIdx = ?";
            String getFeatureParams = String.valueOf(g.getContentIdx());
            List<String> features = this.jdbcTemplate.query(getFeatureQuery,
                    (rs, rowNum) -> new String(
                            rs.getString("type")),
                    getFeatureParams);
            g.setFeatures(features);
        }
        return getTopMoviesRes;
    }

    public GetMovieDetailRes getMovieDetails(int profileIdx, int contentIdx) {
        String getMovieDetailsQuery = "select c.contentIdx, c.title, c.previewUrl, c.videoUrl, c.releasedAt, c.age, c.playtime, c.quality, " +
                "case when(select sc.contentIdx from SaveContent sc where sc.contentIdx = c.contentIdx and sc.profileIdx = ?) " +
                "is not null then 1 else 0 end as isSaved, c.info, " +
                "case when(select lc.contentIdx from LikeContent lc where lc.contentIdx = c.contentIdx and lc.profileIdx = ?) " +
                "is not null then 1 else 0 end as isZzim, " +
                "(select e.likes from Evaluate e where profileIdx = ? and contentIdx = ?) as evaluation" +
                " from Content c where c.contentIdx = ?";
        Object[] getMovieDetailsParams = new Object[]{profileIdx, profileIdx, profileIdx, contentIdx, contentIdx};

        GetMovieDetailRes getMovieDetailRes = this.jdbcTemplate.queryForObject(getMovieDetailsQuery,
                (rs, rowNum) -> new GetMovieDetailRes(
                        rs.getInt("contentIdx"),
                        rs.getString("title"),
                        rs.getString("previewUrl"),
                        rs.getString("videoUrl"),
                        rs.getString("releasedAt"),
                        rs.getString("age"),
                        rs.getString("playtime").split(":")[0].concat("시간 ").concat(rs.getTime("playtime").toString().split(":")[1]).concat("분"),
                        rs.getString("quality"),
                        rs.getInt("isSaved"),
                        rs.getString("info"),
                        rs.getInt("isZzim"),
                        rs.getInt("evaluation")),
                getMovieDetailsParams);


        String getActorQuery = "select a.name from Content c join ContentActor ca on c.contentIdx = ca.contentIdx " +
                "join Actor a on a.actorIdx = ca.actorIdx where c.contentIdx = ?";
        String getActorParams = String.valueOf(contentIdx);
        List<String> actors = this.jdbcTemplate.query(getActorQuery,
                    (rs, rowNum) -> new String(
                            rs.getString("name")),
                    getActorParams);
        getMovieDetailRes.setActors(actors);

        String getProducerQuery = "select p.name from Content c join ContentProducer cp on c.contentIdx = cp.contentIdx " +
                "join Producer p on p.producerIdx = cp.producerIdx where c.contentIdx = ?";
        String getProducerParams = String.valueOf(contentIdx);
        List<String> producers = this.jdbcTemplate.query(getProducerQuery,
                    (rs, rowNum) -> new String(
                            rs.getString("name")),
                    getProducerParams);
        getMovieDetailRes.setProducers(producers);

        String getWriterQuery = "select w.name from Content c join ContentWriter cw on c.contentIdx = cw.contentIdx " +
                "join Writer w on w.writerIdx = cw.writerIdx where c.contentIdx = ?";
        String getWriterParams = String.valueOf(contentIdx);
        List<String> writers = this.jdbcTemplate.query(getWriterQuery,
                    (rs, rowNum) -> new String(
                            rs.getString("name")),
                    getWriterParams);
        getMovieDetailRes.setWriters(writers);

        String getGenreQuery = "select g.type from Content c join ContentGenre cg on c.contentIdx = cg.contentIdx " +
                "join Genre g on g.genreIdx = cg.genreIdx where c.contentIdx = ?";
        String getGenreParams = String.valueOf(contentIdx);
        List<String> genres = this.jdbcTemplate.query(getGenreQuery,
                    (rs, rowNum) -> new String(
                            rs.getString("type")),
                    getGenreParams);
        getMovieDetailRes.setGenres(genres);

        String getFeatureQuery = "select f.type from Content c join MovieFeature mf on c.contentIdx = mf.contentIdx " +
                "join Feature f on mf.featureIdx = f.featureIdx where c.contentIdx = ?";
        String getFeatureParams = String.valueOf(contentIdx);
        List<String> features = this.jdbcTemplate.query(getFeatureQuery,
                    (rs, rowNum) -> new String(
                            rs.getString("type")),
                    getFeatureParams);
        getMovieDetailRes.setFeatures(features);

        return getMovieDetailRes;
    }

    public int evaluate(int profileIdx, PostEvaluateReq postEvaluateReq) {
        String evaluateQuery = "insert into Evaluate (likes, profileIdx, contentIdx) VALUES (?, ?, ?)";
        Object[] evaluateParams = new Object[]{postEvaluateReq.getLikes(), profileIdx, postEvaluateReq.getContentIdx()};
        this.jdbcTemplate.update(evaluateQuery, evaluateParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int updateEvaluate(int profileIdx, PostEvaluateReq postEvaluateReq) {
        String checkEvaluateQuery = "select evaluateIdx from Evaluate where profileIdx = ? and contentIdx = ?";
        Object[] checkEvaluateParams = new Object[]{profileIdx, postEvaluateReq.getContentIdx()};
        int evaluateIdx = this.jdbcTemplate.queryForObject(checkEvaluateQuery,
                int.class,
                checkEvaluateParams);
        String updateEvaluateQuery = "update Evaluate set likes = ? where evaluateIdx = ?";
        Object[] updateEvaluateParams = new Object[]{postEvaluateReq.getLikes(), evaluateIdx};
        this.jdbcTemplate.update(updateEvaluateQuery, updateEvaluateParams);

        return evaluateIdx;
    }
}

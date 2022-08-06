package com.example.demo.src.content;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.content.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
public class ContentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ContentProvider contentProvider;

    @Autowired
    private final ContentService contentService;

    @Autowired
    private final JwtService jwtService;

    public ContentController(ContentProvider contentProvider, ContentService contentService, JwtService jwtService) {
        this.contentProvider = contentProvider;
        this.contentService = contentService;
        this.jwtService = jwtService;
    }

    /**
     * 내가 찜한 콘텐츠 조회 API
     * [GET] /likes/:profileIdx
     * @return BaseResponse<List<GetLikeRes>>
     */
    @ResponseBody
    @GetMapping("/likes/{profileIdx}")
    public BaseResponse<List<GetLikeRes>> getLikes(@PathVariable("profileIdx") int profileIdx) throws BaseException {
        List<GetLikeRes> getLikeRes = contentProvider.getLikes(profileIdx);
        return new BaseResponse<>(getLikeRes);
    }

    /**
     * 최다 검색 조회 API
     * [GET] /searches
     * @return BaseResponse<List<GetMostSearchRes>>
     */
    @ResponseBody
    @GetMapping("/searches")
    public BaseResponse<List<GetMostSearchRes>> getMostSearches() throws BaseException {
        List<GetMostSearchRes> getMostSearchRes = contentProvider.getMostSearches();
        return new BaseResponse<>(getMostSearchRes);
    }

    /**
     * 메인 카테고리 조회 API
     * [GET] /categories/main
     * @return BaseResponse<List<GetMainCategoryRes>>
     */
    @ResponseBody
    @GetMapping("/categories/main")
    public BaseResponse<List<GetMainCategoryRes>> getMainCategories() throws BaseException {
        List<GetMainCategoryRes> getMainCategoryRes = contentProvider.getMainCategories();
        return new BaseResponse<>(getMainCategoryRes);
    }

    /**
     * 시리즈 카테고리 조회 API
     * [GET] /categories/series
     * @return BaseResponse<List<GetSeriesCategoryRes>>
     */
    @ResponseBody
    @GetMapping("/categories/series")
    public BaseResponse<List<GetSeriesCategoryRes>> getSeriesCategories() throws BaseException {
        List<GetSeriesCategoryRes> getSeriesCategoryRes = contentProvider.getSeriesCategories();
        return new BaseResponse<>(getSeriesCategoryRes);
    }

    /**
     * 영화 카테고리 조회 API
     * [GET] /categories/movie
     * @return BaseResponse<List<GetMovieCategoryRes>>
     */
    @ResponseBody
    @GetMapping("/categories/movie")
    public BaseResponse<List<GetMovieCategoryRes>> getMovieCategories() throws BaseException {
        List<GetMovieCategoryRes> getMovieCategoryRes = contentProvider.getMovieCategories();
        return new BaseResponse<>(getMovieCategoryRes);
    }

    /**
     * 콘텐츠 평가 및 취소 API
     * [POST] /evaluations/:profileIdx
     * @return BaseResponse<PostEvaluateRes>
     */
    @ResponseBody
    @PostMapping("/evaluations/{profileIdx}")
    public BaseResponse<PostEvaluateRes> evaluate(@PathVariable("profileIdx") int profileIdx, @RequestBody PostEvaluateReq postEvaluateReq) throws BaseException {
        PostEvaluateRes postEvaluateRes = contentService.evaluate(profileIdx, postEvaluateReq);
        return new BaseResponse<>(postEvaluateRes);
    }

    /**
     * 콘텐츠 평가 조회 API
     * [GET] /evaluations/:profileIdx
     * @return BaseResponse<GetEvaluateRes>
     */
    // 삭제 필요
    @ResponseBody
    @GetMapping("/evaluations/{profileIdx}")
    public BaseResponse<GetEvaluateRes> getEvaluate(@PathVariable("profileIdx") int profileIdx, @RequestParam(name="contentIdx") int contentIdx) throws BaseException {
        GetEvaluateRes getEvaluateRes = contentProvider.getEvaluate(profileIdx, contentIdx);
        return new BaseResponse<>(getEvaluateRes);
    }

    /**
     * 모두의 인기 콘텐츠 조회 API
     * [GET] /hot/:profileIdx
     * @return BaseResponse<List<GetHotRes>>
     */
    @ResponseBody
    @GetMapping("/hot/{profileIdx}")
    public BaseResponse<List<GetHotRes>> getHots(@PathVariable("profileIdx") int profileIdx) throws BaseException {
        List<GetHotRes> getHotRes = contentProvider.getHots(profileIdx);
        return new BaseResponse<>(getHotRes);
    }

    /**
     * Top 10 시리즈 조회 API
     * [GET] /topSeries/:profileIdx
     * @return BaseResponse<List<GetTopSeriesRes>>
     */
    @ResponseBody
    @GetMapping("/topseries/{profileIdx}")
    public BaseResponse<List<GetTopSeriesRes>> getTopSeries(@PathVariable("profileIdx") int profileIdx) throws BaseException {
        List<GetTopSeriesRes> getTopSeries = contentProvider.getTopSeries(profileIdx);
        return new BaseResponse<>(getTopSeries);
    }

    /**
     * Top 10 영화 조회 API
     * [GET] /topMovies/:profileIdx
     * @return BaseResponse<List<GetTopMoviesRes>>
     */
    @ResponseBody
    @GetMapping("/topmovies/{profileIdx}")
    public BaseResponse<List<GetTopMoviesRes>> getTopMovies(@PathVariable("profileIdx") int profileIdx) throws BaseException {
        List<GetTopMoviesRes> getTopMovies = contentProvider.getTopMovies(profileIdx);
        return new BaseResponse<>(getTopMovies);
    }

    /**
     * 영화 상세 페이지 조회 API
     * [GET] /movies/:profileIdx
     * @return BaseResponse<GetMovieDetailRes>
     */
    @ResponseBody
    @GetMapping("/movies/{profileIdx}")
    public BaseResponse<GetMovieDetailRes> getMovieDetails(@PathVariable("profileIdx") int profileIdx, @RequestParam(name = "contentIdx") int contentIdx) throws BaseException {
        GetMovieDetailRes getMovieDetailRes = contentProvider.getMovieDetails(profileIdx, contentIdx);
        return new BaseResponse<>(getMovieDetailRes);
    }
}

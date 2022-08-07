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

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

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
    public BaseResponse<List<GetLikeRes>> getLikes(@PathVariable("profileIdx") int profileIdx, @RequestParam(name="userIdx") int userIdx) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetLikeRes> getLikeRes = contentProvider.getLikes(profileIdx);
            return new BaseResponse<>(getLikeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 최다 검색 조회 API
     * [GET] /searches/:userIdx
     * @return BaseResponse<List<GetMostSearchRes>>
     */
    @ResponseBody
    @GetMapping("/searches/{userIdx}")
    public BaseResponse<List<GetMostSearchRes>> getMostSearches(@PathVariable("userIdx") int userIdx) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetMostSearchRes> getMostSearchRes = contentProvider.getMostSearches();
            return new BaseResponse<>(getMostSearchRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 메인 카테고리 조회 API
     * [GET] /categories/main
     * @return BaseResponse<List<GetMainCategoryRes>>
     */
    @ResponseBody
    @GetMapping("/categories/main")
    public BaseResponse<List<GetMainCategoryRes>> getMainCategories(@RequestParam(name="userIdx") int userIdx) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetMainCategoryRes> getMainCategoryRes = contentProvider.getMainCategories();
            return new BaseResponse<>(getMainCategoryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 시리즈 카테고리 조회 API
     * [GET] /categories/series
     * @return BaseResponse<List<GetSeriesCategoryRes>>
     */
    @ResponseBody
    @GetMapping("/categories/series")
    public BaseResponse<List<GetSeriesCategoryRes>> getSeriesCategories(@RequestParam(name="userIdx") int userIdx) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetSeriesCategoryRes> getSeriesCategoryRes = contentProvider.getSeriesCategories();
            return new BaseResponse<>(getSeriesCategoryRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 영화 카테고리 조회 API
     * [GET] /categories/movie
     * @return BaseResponse<List<GetMovieCategoryRes>>
     */
    @ResponseBody
    @GetMapping("/categories/movie")
    public BaseResponse<List<GetMovieCategoryRes>> getMovieCategories(@RequestParam(name="userIdx") int userIdx) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetMovieCategoryRes> getMovieCategoryRes = contentProvider.getMovieCategories();
            return new BaseResponse<>(getMovieCategoryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 콘텐츠 평가 및 취소 API
     * [POST] /evaluations/:profileIdx
     * @return BaseResponse<PostEvaluateRes>
     */
    @ResponseBody
    @PostMapping("/evaluations/{profileIdx}")
    public BaseResponse<PostEvaluateRes> evaluate(@PathVariable("profileIdx") int profileIdx, @RequestBody PostEvaluateReq postEvaluateReq) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(postEvaluateReq.getUserIdx() != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostEvaluateRes postEvaluateRes = contentService.evaluate(profileIdx, postEvaluateReq);
            return new BaseResponse<>(postEvaluateRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 콘텐츠 평가 조회 API
     * [GET] /evaluations/:profileIdx
     * @return BaseResponse<GetEvaluateRes>
     */
    /*
    @ResponseBody
    @GetMapping("/evaluations/{profileIdx}")
    public BaseResponse<GetEvaluateRes> getEvaluate(@PathVariable("profileIdx") int profileIdx, @RequestParam(name="contentIdx") int contentIdx) throws BaseException {
        GetEvaluateRes getEvaluateRes = contentProvider.getEvaluate(profileIdx, contentIdx);
        return new BaseResponse<>(getEvaluateRes);
    }
     */

    /**
     * 모두의 인기 콘텐츠 조회 API
     * [GET] /hot/:profileIdx
     * @return BaseResponse<List<GetHotRes>>
     */
    @ResponseBody
    @GetMapping("/hot/{profileIdx}")
    public BaseResponse<List<GetHotRes>> getHots(@PathVariable("profileIdx") int profileIdx, @RequestParam(name="userIdx") int userIdx) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetHotRes> getHotRes = contentProvider.getHots(profileIdx);
            return new BaseResponse<>(getHotRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * Top 10 시리즈 조회 API
     * [GET] /topSeries/:profileIdx
     * @return BaseResponse<List<GetTopSeriesRes>>
     */
    @ResponseBody
    @GetMapping("/topseries/{profileIdx}")
    public BaseResponse<List<GetTopSeriesRes>> getTopSeries(@PathVariable("profileIdx") int profileIdx, @RequestParam(name="userIdx") int userIdx) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetTopSeriesRes> getTopSeries = contentProvider.getTopSeries(profileIdx);
            return new BaseResponse<>(getTopSeries);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * Top 10 영화 조회 API
     * [GET] /topMovies/:profileIdx
     * @return BaseResponse<List<GetTopMoviesRes>>
     */
    @ResponseBody
    @GetMapping("/topmovies/{profileIdx}")
    public BaseResponse<List<GetTopMoviesRes>> getTopMovies(@PathVariable("profileIdx") int profileIdx, @RequestParam(name="userIdx") int userIdx) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetTopMoviesRes> getTopMovies = contentProvider.getTopMovies(profileIdx);
            return new BaseResponse<>(getTopMovies);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 영화 상세 페이지 조회 API
     * [GET] /movies/:profileIdx
     * @return BaseResponse<GetMovieDetailRes>
     */
    @ResponseBody
    @GetMapping("/movies/{profileIdx}")
    public BaseResponse<GetMovieDetailRes> getMovieDetails(@PathVariable("profileIdx") int profileIdx, @RequestParam(name="userIdx") int userIdx, @RequestParam(name = "contentIdx") int contentIdx) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetMovieDetailRes getMovieDetailRes = contentProvider.getMovieDetails(profileIdx, contentIdx);
            return new BaseResponse<>(getMovieDetailRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

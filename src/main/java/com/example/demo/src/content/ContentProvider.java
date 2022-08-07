package com.example.demo.src.content;

import com.example.demo.config.BaseException;
import com.example.demo.src.content.model.*;
import com.example.demo.src.profile.model.GetProfileRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.POST_NOT_EXISTS_CONTENT;

@Service
@Transactional
public class ContentProvider {

    private final ContentDao contentDao;

    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());


    public ContentProvider(ContentDao contentDao, JwtService jwtService) {
        this.contentDao = contentDao;
        this.jwtService = jwtService;
    }

    public int checkContent(int contentIdx) throws BaseException  {
        try {
            return contentDao.checkContent(contentIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkEvaluate(int profileIdx, int contentIdx) throws BaseException  {
        try {
            return contentDao.checkEvaluate(profileIdx, contentIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetEvaluateRes getEvaluate(int profileIdx, int contentIdx) throws BaseException {
        if(checkContent(contentIdx) == 0) {
            throw new BaseException(POST_NOT_EXISTS_CONTENT);
        }
        try {
            return contentDao.getEvaluate(profileIdx, contentIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetLikeRes> getLikes(int profileIdx) throws BaseException {
        try{
            List<GetLikeRes> getLikeRes = contentDao.getLikes(profileIdx);
            return getLikeRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMostSearchRes> getMostSearches() throws BaseException {
        try{
            List<GetMostSearchRes> getMostSearchRes = contentDao.getMostSearches();
            return getMostSearchRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMainCategoryRes> getMainCategories() throws BaseException {
        try {
            List<GetMainCategoryRes> getMainCategoryRes = contentDao.getMainCategories();
            return getMainCategoryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSeriesCategoryRes> getSeriesCategories() throws BaseException {
        try {
            List<GetSeriesCategoryRes> getSeriesCategoryRes = contentDao.getSeriesCategories();
            return getSeriesCategoryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetMovieCategoryRes> getMovieCategories() throws BaseException {
        try {
            List<GetMovieCategoryRes> getMovieCategoryRes = contentDao.getMovieCategories();
            return getMovieCategoryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetHotRes> getHots(int profileIdx) throws BaseException {
        try {
            List<GetHotRes> getHotRes = contentDao.getHots(profileIdx);
            return getHotRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetTopSeriesRes> getTopSeries(int profileIdx) throws BaseException {
        try {
            List<GetTopSeriesRes> getTopSeriesRes = contentDao.getTopSeries(profileIdx);
            return getTopSeriesRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetTopMoviesRes> getTopMovies(int profileIdx) throws BaseException {
        try {
            List<GetTopMoviesRes> getTopMoviesRes = contentDao.getTopMovies(profileIdx);
            return getTopMoviesRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetMovieDetailRes getMovieDetails(int profileIdx, int contentIdx) throws BaseException {
        try {
            GetMovieDetailRes getMovieDetailRes = contentDao.getMovieDetails(profileIdx, contentIdx);
            return getMovieDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

package com.example.demo.src.detailcontent;

import com.example.demo.config.BaseException;
import com.example.demo.src.content.model.GetMovieDetailRes;
import com.example.demo.src.detailcontent.model.GetSeriesDetailRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class DetailContentProvider {
    private final DetailContentDao detailContentDao;

    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DetailContentProvider(DetailContentDao detailContentDao, JwtService jwtService) {
        this.detailContentDao = detailContentDao;
        this.jwtService = jwtService;
    }

    public GetSeriesDetailRes getSeriesDetails(int profileIdx, int contentIdx, int season) throws BaseException {
        //try {
            GetSeriesDetailRes getSeriesDetailRes = detailContentDao.getSeriesDetails(profileIdx, contentIdx, season);
            return getSeriesDetailRes;
        //} catch (Exception exception) {
        //    throw new BaseException(DATABASE_ERROR);
        //}
    }
}

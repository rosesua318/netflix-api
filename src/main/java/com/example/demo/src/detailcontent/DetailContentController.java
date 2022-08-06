package com.example.demo.src.detailcontent;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.content.model.GetMovieDetailRes;
import com.example.demo.src.detailcontent.model.GetSeriesDetailRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
public class DetailContentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final DetailContentProvider detailContentProvider;


    @Autowired
    private final JwtService jwtService;

    public DetailContentController(DetailContentProvider detailContentProvider, JwtService jwtService) {
        this.detailContentProvider = detailContentProvider;
        this.jwtService = jwtService;
    }

    /**
     * 시리즈 상세 페이지 조회 API
     * [GET] /series/:profileIdx
     * @return BaseResponse<GetSeriesDetailRes>
     */
    @ResponseBody
    @GetMapping("/series/{profileIdx}")
    public BaseResponse<GetSeriesDetailRes> getSeriesDetails(@PathVariable("profileIdx") int profileIdx, @RequestParam(name = "contentIdx") int contentIdx,
                                                             @RequestParam(name = "season", defaultValue = "1") int season) throws BaseException {
        GetSeriesDetailRes getSeriesDetailRes = detailContentProvider.getSeriesDetails(profileIdx, contentIdx, season);
        return new BaseResponse<>(getSeriesDetailRes);
    }
}

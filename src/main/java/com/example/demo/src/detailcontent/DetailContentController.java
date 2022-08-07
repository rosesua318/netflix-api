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

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

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
    public BaseResponse<GetSeriesDetailRes> getSeriesDetails(@PathVariable("profileIdx") int profileIdx,
                                                             @RequestParam(name="userIdx") int userIdx,
                                                             @RequestParam(name = "contentIdx") int contentIdx,
                                                             @RequestParam(name = "season", defaultValue = "1") int season) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetSeriesDetailRes getSeriesDetailRes = detailContentProvider.getSeriesDetails(profileIdx, contentIdx, season);
            return new BaseResponse<>(getSeriesDetailRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

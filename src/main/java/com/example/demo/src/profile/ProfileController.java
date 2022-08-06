package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.profile.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/profiles")
public class ProfileController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProfileProvider profileProvider;

    @Autowired
    private final ProfileService profileService;

    @Autowired
    private final JwtService jwtService;

    public ProfileController(ProfileProvider profileProvider, ProfileService profileService, JwtService jwtService) {
        this.profileProvider = profileProvider;
        this.profileService = profileService;
        this.jwtService = jwtService;
    }


    /**
     * 프로필 비밀번호 설정 API
     * [PATCH] /profiles/:profileIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{profileIdx}")
    public BaseResponse<String> modifyPin(@PathVariable("profileIdx") int profileIdx, @RequestParam String keyword, @RequestBody Profile profile) throws BaseException {
        if(profile.getPassword().equals("")) {
            return new BaseResponse<>(PROFILE_EMPTY_PASSWORD);
        }
        if(profile.getPassword().length() > 4) {
            return new BaseResponse<>(PROFILE_LONG_PASSWORD);
        }
        try{
            PatchPinReq patchPinReq = new PatchPinReq(profileIdx, profile.getPassword());
            profileService.modifyPin(patchPinReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 프로필 설정 변경 API
     * [PUT] /profiles/:profileIdx
     * @return BaseResponse<PutProfileRes>
     */
    @ResponseBody
    @PutMapping("/{profileIdx}")
    public BaseResponse<PutProfileRes> modifyInfo(@PathVariable("profileIdx") int profileIdx, @RequestBody Profile profile) throws BaseException {
        if(profile.getName().equals("")) {
            return new BaseResponse<>(POST_PROFILE_EMPTY_NAME);
        }
        if(profile.getName().length() > 45) {
            return new BaseResponse<>(POST_PROFILE_LONG_NAME);
        }
        try {
            PutProfileReq putProfileReq = new PutProfileReq(profile.getProfileIdx(), profile.getName(), profile.getImage(), profile.getIsNext(), profile.getIsPreview(), profile.getPresentLangIdx());
            PutProfileRes putProfileRes = profileService.modifyInfo(putProfileReq);
            return new BaseResponse<>(putProfileRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 프로필 비밀번호 검증 API
     * [POST] /profiles/:profileIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping("/{profileIdx}")
    public BaseResponse<PostPinRes> loginPin(@PathVariable("profileIdx") int profileIdx, @RequestBody PostPinReq postPinReq) {
        if(postPinReq.getPassword().equals("")) {
            return new BaseResponse<>(PROFILE_EMPTY_PASSWORD);
        }
        if(postPinReq.getPassword().length() > 4) {
            return new BaseResponse<>(PROFILE_LONG_PASSWORD);
        }
        try {
            PostPinRes postPinRes = profileProvider.loginPin(profileIdx, postPinReq);
            return new BaseResponse<>(postPinRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 전체 프로필 조회 API
     * [GET] /profiles/:userIdx
     * @return BaseResponse<List<GetProfileRes>>
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetProfileRes>> getProfiles(@PathVariable("userIdx") int userIdx) throws BaseException {
        List<GetProfileRes> getProfilesRes = profileProvider.getProfiles(userIdx);
        return new BaseResponse<>(getProfilesRes);
    }
}

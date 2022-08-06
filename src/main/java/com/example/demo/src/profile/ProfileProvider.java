package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.model.GetProfileRes;
import com.example.demo.src.profile.model.PostPinReq;
import com.example.demo.src.profile.model.PostPinRes;
import com.example.demo.src.profile.model.Profile;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.PostLoginRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ProfileProvider {

    private final ProfileDao profileDao;

    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProfileProvider(ProfileDao profileDao, JwtService jwtService) {
        this.profileDao = profileDao;
        this.jwtService = jwtService;
    }

    public List<GetProfileRes> getProfiles(int userIdx) throws BaseException {
        try{
            List<GetProfileRes> getProfileRes = profileDao.getProfiles(userIdx);
            return getProfileRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostPinRes loginPin(int profileIdx, PostPinReq postPinReq) throws BaseException {
        Profile profile = profileDao.getPin(profileIdx);
        if(profile.getStatus().equals("INACTIVE")) {
            throw new BaseException(INACTIVE_PROFILE);
        }
        if(profile.getPassword().equals(postPinReq.getPassword())){
            return new PostPinRes(profileIdx);
        } else {
            throw new BaseException(FAILED_TO_LOGIN_PIN);
        }
    }
}

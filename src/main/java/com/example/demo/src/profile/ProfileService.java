package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.model.PatchPinReq;
import com.example.demo.src.profile.model.PutProfileReq;
import com.example.demo.src.profile.model.PutProfileRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class ProfileService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProfileDao profileDao;

    private final ProfileProvider profileProvider;

    private final JwtService jwtService;

    @Autowired
    public ProfileService(ProfileDao profileDao, ProfileProvider profileProvider, JwtService jwtService) {
        this.profileDao = profileDao;
        this.profileProvider = profileProvider;
        this.jwtService = jwtService;
    }

    public void modifyPin(PatchPinReq patchPinReq) throws BaseException {
        try {
            int result = profileDao.modifyPin(patchPinReq);
            if(result == 0) {
                throw new BaseException(MODIFY_FAIL_PIN);
            }
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PutProfileRes modifyInfo(PutProfileReq putProfileReq) throws  BaseException {
        try {
            int result = profileDao.modifyInfo(putProfileReq);
            if(result == 0) {
                throw new BaseException(MODIFY_FAIL_INFO);
            }
            return new PutProfileRes(putProfileReq.getProfileIdx());
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

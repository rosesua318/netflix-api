package com.example.demo.src.user;



import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String pwd;
        try{
            //암호화
            pwd = new SHA256().encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userIdx = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostProfileRes createProfile(int userIdx, PostProfileReq postProfileReq) {
        int profileIdx = userDao.createProfile(userIdx, postProfileReq);
        return new PostProfileRes(profileIdx);
    }

    public void modifyPassword(PatchPwdReq patchPwdReq) throws BaseException {
        User user = userDao.getUserInfo(patchPwdReq);
        if(userProvider.checkEmail(patchPwdReq.getEmail()) != 1) {
            throw new BaseException(POST_LOGIN_NOT_EXISTS_EMAIL);
        }
        if(userProvider.checkUser(patchPwdReq.getUserIdx(), patchPwdReq.getEmail()) != 1) {
            throw new BaseException(POST_MODIFY_NOT_EXISTS_EMAIL);
        }
        if(user.getStatus().equals("INACTIVE")) {
            throw new BaseException(INACTIVE_LOGIN);
        }
        String pwd;
        try{
            //암호화
            pwd = new SHA256().encrypt(patchPwdReq.getPassword());
            patchPwdReq.setPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{

            int result = userDao.modifyPassword(patchPwdReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_PASSWORD);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}

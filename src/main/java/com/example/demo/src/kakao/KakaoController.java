package com.example.demo.src.kakao;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.kakao.model.PostKakaoLoginRes;
import com.example.demo.src.kakao.model.PostKakaoSignupReq;
import com.example.demo.src.kakao.model.PostKakaoSignupRes;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import com.example.demo.src.user.model.PostProfileRes;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.src.user.model.User;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/kakao")
public class KakaoController {

    private final KakaoProvider kakaoProvider;
    private final UserProvider userProvider;
    private final UserService userService;

    private final JwtService jwtService;

    public KakaoController(KakaoProvider kakaoProvider, UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.kakaoProvider = kakaoProvider;
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 카카오 인증 코드 발급 페이지 조회API
     * [GET] /kakao/oauth
     * @return String
     */
    @GetMapping(value="/oauth")
    public String kakaoConnect() {

        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + Secret.CLIENT_ID);
        url.append("&redirect_uri=http://localhost:9000/app/kakao/callback");
        url.append("&response_type=code");

        return "redirect:" + url.toString();
    }

    @RequestMapping(value="/callback", produces="application/json", method={RequestMethod.GET, RequestMethod.POST})
    public BaseResponse<PostKakaoLoginRes> kakaoLogin(@RequestParam("code") String code, RedirectAttributes ra, HttpSession session, HttpServletResponse response,
                             Model model) throws BaseException {
        System.out.println("kakao code: " + code);
        JsonNode accessToken = kakaoProvider.getKakaoAccessToken(code);
        String memberId = kakaoProvider.getKakaoUserInfo(accessToken);

        User user = userProvider.checkKakaoId(memberId);

        if(user != null) {
            return new BaseResponse<>(new PostKakaoLoginRes(user.getUserIdx(), jwtService.createJwt(user.getUserIdx()), 0));
        } else {
            int userIdx = userService.createKakaoUser(memberId);
            return new BaseResponse<>(new PostKakaoLoginRes(userIdx, jwtService.createJwt(userIdx), 1));
        }
    }

    @PostMapping("")
    public BaseResponse<PostKakaoSignupRes> kakaoSignup(@RequestBody PostKakaoSignupReq postKakaoSignupReq) throws BaseException{
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(postKakaoSignupReq.getUserIdx() != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if(postKakaoSignupReq.getEmail() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            if(!isRegexEmail(postKakaoSignupReq.getEmail())){
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
            }
            PostKakaoSignupRes postKakaoSignupRes = userService.updateKUser(jwtService.getJwt(), postKakaoSignupReq);
            return new BaseResponse<>(postKakaoSignupRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

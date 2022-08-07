package com.example.demo.src.content;

import com.example.demo.config.BaseException;
import com.example.demo.src.content.model.PostEvaluateReq;
import com.example.demo.src.content.model.PostEvaluateRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.POST_NOT_EXISTS_CONTENT;

@Service
@Transactional
public class ContentService {

    private final ContentDao contentDao;

    private final ContentProvider contentProvider;

    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ContentService(ContentDao contentDao, ContentProvider contentProvider, JwtService jwtService) {
        this.contentDao = contentDao;
        this.contentProvider = contentProvider;
        this.jwtService = jwtService;
    }

    public PostEvaluateRes evaluate(int profileIdx, PostEvaluateReq postEvaluateReq) throws BaseException {
        if(contentProvider.checkContent(postEvaluateReq.getContentIdx()) == 0) {
            throw new BaseException(POST_NOT_EXISTS_CONTENT);
        }
        if(contentProvider.checkEvaluate(profileIdx, postEvaluateReq.getContentIdx()) == 1) {
            try {
                int evaluateIdx = contentDao.updateEvaluate(profileIdx, postEvaluateReq);
                return new PostEvaluateRes(postEvaluateReq.getContentIdx(), postEvaluateReq.getLikes());
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        } else {
            try {
                int evaluateIdx = contentDao.evaluate(profileIdx, postEvaluateReq);
                return new PostEvaluateRes(postEvaluateReq.getContentIdx(), postEvaluateReq.getLikes());
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        }
    }
}

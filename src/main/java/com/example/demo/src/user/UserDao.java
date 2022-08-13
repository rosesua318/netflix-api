package com.example.demo.src.user;


import com.example.demo.src.kakao.model.PostKakaoSignupReq;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User checkKakaoId(String kakaoId) {
        String getUserQuery = "select userIdx, status, email, password from User where kakaoId = ?";
        String getUserParams = kakaoId;
        try {
            return this.jdbcTemplate.queryForObject(getUserQuery,
                    (rs, rowNum) -> new User(
                            rs.getInt("userIdx"),
                            rs.getString("status"),
                            rs.getString("email"),
                            rs.getString("password")),
                    getUserParams);
        } catch (IncorrectResultSizeDataAccessException error) {
            return null;
        }
    }


    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from UserInfo";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        //rs.getString("userName"),
                        //rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password"))
                );
    }

    public List<GetUserRes> getUsersByEmail(String email){
        String getUsersByEmailQuery = "select * from UserInfo where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        //rs.getString("userName"),
                        //rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password")),
                getUsersByEmailParams);
    }

    public GetUserRes getUser(int userIdx){
        String getUserQuery = "select * from User where userIdx = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        //rs.getString("userName"),
                        //rs.getString("ID"),
                        rs.getString("email"),
                        rs.getString("password")),
                getUserParams);
    }
    

    public int createUser(PostUserReq postUserReq){
        String createCardQuery = "insert into Card (cardNumber, name, yymm, birthYear, birthMonth, birthDay, phone) VALUES (?,?,?,?,?,?,?)";
        Object[] createCardParams = new Object[]{postUserReq.getCard().getCardNumber(), postUserReq.getCard().getName(), postUserReq.getCard().getYymm(), postUserReq.getCard().getBirthYear(),
                postUserReq.getCard().getBirthMonth(), postUserReq.getCard().getBirthDay(), postUserReq.getCard().getPhone()};
        this.jdbcTemplate.update(createCardQuery, createCardParams);
        String lastIdQuery = "select last_insert_id()";
        int cardId = this.jdbcTemplate.queryForObject(lastIdQuery, int.class);

        String createUserQuery = "insert into User (membershipIdx, cardIdx, password, email) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getMembership(), cardId, postUserReq.getPassword(), postUserReq.getEmail()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int createKakaoUser(String kakaoId) {
        String createUserQuery = "insert into User (kakaoId) VALUES (?)";
        this.jdbcTemplate.update(createUserQuery, kakaoId);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int updateKUser(PostKakaoSignupReq postKakaoSignupReq) {
        String createCardQuery = "insert into Card (cardNumber, name, yymm, birthYear, birthMonth, birthDay, phone) VALUES (?,?,?,?,?,?,?)";
        Object[] createCardParams = new Object[]{postKakaoSignupReq.getCard().getCardNumber(), postKakaoSignupReq.getCard().getName(),
                postKakaoSignupReq.getCard().getYymm(), postKakaoSignupReq.getCard().getBirthYear(),
                postKakaoSignupReq.getCard().getBirthMonth(), postKakaoSignupReq.getCard().getBirthDay(), postKakaoSignupReq.getCard().getPhone()};
        this.jdbcTemplate.update(createCardQuery, createCardParams);
        String lastIdQuery = "select last_insert_id()";
        int cardId = this.jdbcTemplate.queryForObject(lastIdQuery, int.class);

        String updateUserQuery = "update User set email = ?, password = ?, membershipIdx = ?, cardIdx = ? where userIdx = ?";
        Object[] updateUserParams = new Object[]{postKakaoSignupReq.getEmail(), postKakaoSignupReq.getPassword(),
        postKakaoSignupReq.getMembershipIdx(), cardId, postKakaoSignupReq.getUserIdx()};
        this.jdbcTemplate.update(updateUserQuery, updateUserParams);
        return postKakaoSignupReq.getUserIdx();
    }

    public int createProfile(int userIdx, PostProfileReq postProfileReq) {
        String createProfileQuery = "insert into Profile (name, image, isKids, userIdx, presentLangIdx) VALUES (?,?,?,?,?)";
        Object[] createProfileParams = new Object[]{postProfileReq.getName(), postProfileReq.getImage(), postProfileReq.getIsKids(), userIdx, postProfileReq.getPresentLangIdx()};
        this.jdbcTemplate.update(createProfileQuery, createProfileParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public String getEmail(int userIdx) {
        String getEmailQuery = "select email from User where userIdx = ?";
        String getEmailParams = String.valueOf(userIdx);
        return this.jdbcTemplate.queryForObject(getEmailQuery, String.class, getEmailParams);
    }

    public int modifyPassword(PatchPwdReq patchPwdReq){
        String modifyUserNameQuery = "update User set password = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchPwdReq.getPassword(), patchPwdReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }



    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, password,email,status from User where email = ?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("status"),
                        rs.getString("password"),
                        rs.getString("email")
                ),
                getPwdParams
                );

    }

    public User getUserInfo(PatchPwdReq patchPwdReq) {
        String getUserQuery = "select userIdx, password, email, status from User where userIdx = ?";
        Object[] getUserParams = new Object[]{patchPwdReq.getUserIdx()};

        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("status"),
                        rs.getString("password"),
                        rs.getString("email")
                ),
                getUserParams
        );
    }

}

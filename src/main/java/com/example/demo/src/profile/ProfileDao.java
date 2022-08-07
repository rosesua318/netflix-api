package com.example.demo.src.profile;

import com.example.demo.src.profile.model.*;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProfileDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int modifyPin(PatchPinReq patchPinReq) {
        String modifyPinQuery = "update Profile set password = ? where profileIdx = ? ";
        Object[] modifyPinParams = new Object[]{patchPinReq.getPassword(), patchPinReq.getProfileIdx()};

        return this.jdbcTemplate.update(modifyPinQuery, modifyPinParams);
    }

    public int modifyInfo(PutProfileReq putProfileReq) {
        String modifyInfoQuery = "update Profile set name = ?, image = ?, isNext = ?, isPreview = ?, presentLangIdx = ? where profileIdx = ?";
        Object[] modifyPinParams = new Object[]{putProfileReq.getName(), putProfileReq.getImage(), putProfileReq.getIsNext(), putProfileReq.getIsPreview(), putProfileReq.getPresentLangIdx(), putProfileReq.getProfileIdx()};

        return this.jdbcTemplate.update(modifyInfoQuery, modifyPinParams);
    }

    public List<GetProfileRes> getProfiles(int userIdx){
        String getProfilesQuery = "select profileIdx, name, image, password from Profile where userIdx =?";
        String getProfilesParams = String.valueOf(userIdx);
        return this.jdbcTemplate.query(getProfilesQuery,
                (rs, rowNum) -> new GetProfileRes(
                        rs.getInt("profileIdx"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("password").isEmpty()),
                getProfilesParams);

    }

    public Profile getPin(int profileIdx) {
        String getPinQuery = "select profileIdx, userIdx, name, image, password, isKids, isNext, isPreview, presentLangIdx, status from Profile where profileIdx = ?";
        String getPinParams = String.valueOf(profileIdx);

        return this.jdbcTemplate.queryForObject(getPinQuery,
                (rs,rowNum)-> new Profile(
                        rs.getInt("userIdx"),
                        rs.getInt("profileIdx"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getString("password"),
                        rs.getInt("isNext"),
                        rs.getInt("isPreview"),
                        rs.getInt("presentLangIdx"),
                        rs.getString("status")
                ),
                getPinParams
        );
    }
}

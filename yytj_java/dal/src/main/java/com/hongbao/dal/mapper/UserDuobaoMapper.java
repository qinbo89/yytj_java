package com.hongbao.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hongbao.dal.model.UserDuobao;

public interface UserDuobaoMapper {

    UserDuobao findByDuobaoIdAndUserId(@Param("duobaoId") Long duobaoId, @Param("userId") Long userId);

    List<UserDuobao> findByUserId(@Param("userId") Long userId);

    void insert(UserDuobao userDuobao);

    void addScore(@Param("id") String Long, @Param("score") Integer score);

    List<UserDuobao> findByDuobaoId(@Param("duobaoId") Long duobaoId);

    int updateWinStatus(@Param("id") Long id, @Param("winStatus") int status);

}

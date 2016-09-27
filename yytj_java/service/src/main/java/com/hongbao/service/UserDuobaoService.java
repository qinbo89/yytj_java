package com.hongbao.service;

import java.util.List;

import com.hongbao.dal.model.UserDuobao;

public interface UserDuobaoService {

    UserDuobao findByDuobaoIdAndUserId(Long duobaoId, Long decode);

    void addScore(Long id, Integer onceScore);

    void insert(UserDuobao userDuobao);

    List<UserDuobao> findByUserId(Long userIdLong);

    List<UserDuobao> findByDuobaoId(Long duobaoId);

    int updateWinStatus(Long id, int status);
}

package com.hongbao.service;

import java.util.List;

import com.hongbao.dal.model.Duobao;



public interface DuobaoService {

    List<Duobao> duobaoList();

    Duobao findById(Long duobaoId);

    void addCurrentScore(Long id, Integer onceScore);

    void updateStatus(Long id, int score);

}

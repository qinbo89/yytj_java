package com.hongbao.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hongbao.dal.model.Duobao;


public interface DuobaoMapper {

    List<Duobao> duobaoList();

    Duobao findById(@Param("id") Long id);

    int updateStatus(@Param("id") Long id, @Param("status") int status);

    int addCurrentScore(@Param("id") Long id, @Param("score") Integer score);

}

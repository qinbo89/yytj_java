package com.hongbao.dal.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BaseMapper<T> {

    /**
     * 新增
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 根据id修改
     * @param t
     */
    int updateById(T t);

    /**
     * 根据id删除
     * @param id
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    T findById(@Param("id") Long id);

    /**
     * 根据动态条件分页查询
     * @param queryMap
     * @return
     */
    List<T> findListByCondition(Map<String,Object> queryMap);

    /**
     * 根据动态条件查询总条数
     * @param queryMap
     * @return
     */
    int findCountByCondition(Map<String,Object> queryMap);

    /**
     * 根据user ids查询列表
     * @param userIds
     * @return
     */
    List<T> findListByUserIds(@Param("list") List<Long> userIds);
}

package com.choice.idao;

import com.choice.model.RzrqRequestHand;

public interface RzrqRequestHandMapper {
    int deleteByPrimaryKey(Integer requestId);

    int insert(RzrqRequestHand record);

    int insertSelective(RzrqRequestHand record);

    RzrqRequestHand selectByPrimaryKey(Integer requestId);

    int updateByPrimaryKeySelective(RzrqRequestHand record);

    int updateByPrimaryKey(RzrqRequestHand record);
}
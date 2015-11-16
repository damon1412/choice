package com.choice.service;

import com.choice.model.RzrqRequestHand;

public interface IRzrqRequestHandService {
    int deleteByPrimaryKey(Integer requestId);

    int insert(RzrqRequestHand record);

    int insertSelective(RzrqRequestHand record);

    RzrqRequestHand selectByPrimaryKey(Integer requestId);

    int updateByPrimaryKeySelective(RzrqRequestHand record);

    int updateByPrimaryKey(RzrqRequestHand record);
}

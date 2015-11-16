package com.choice.idao;

import java.util.List;

import com.choice.model.Rzrq;
import com.choice.model.RzrqKey;


public interface rzrqMapper {
	int deleteByPrimaryKey(RzrqKey key);

	int insert(Rzrq record);

	int insertSelective(Rzrq record);

	Rzrq selectByPrimaryKey(RzrqKey key);

	int updateByPrimaryKeySelective(Rzrq record);

	int updateByPrimaryKey(Rzrq record);
	
	List<Rzrq> selectRecent(RzrqKey key);
	
}
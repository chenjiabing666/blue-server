package com.techwells.demo.dao;

import java.util.List;
import com.techwells.demo.domain.Aaa;

public interface AaaMapper {
	
	int deleteByPrimaryKey(Integer aaId);

    int insert(Aaa aaa);

    int insertSelective(Aaa aaa);

    Aaa selectByPrimaryKey(Integer aaId);

    int updateByPrimaryKeySelective(Aaa aaa);

    int updateByPrimaryKey(Aaa aaa);
    
}

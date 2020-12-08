package com.sbs.khr.hanbyuk.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.khr.hanbyuk.dto.Member;

@Mapper
public interface MemberDao {

	Member getMemberByLoginId(@Param("loginId") String loginId);

	Member getMemberByIdForSession(@Param("loginedMemberId") int loginedMemberId);
	
	
}

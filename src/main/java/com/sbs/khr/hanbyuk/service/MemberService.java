package com.sbs.khr.hanbyuk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.khr.hanbyuk.dao.MemberDao;
import com.sbs.khr.hanbyuk.dto.Member;

@Service
public class MemberService {
	
	@Autowired
	private MemberDao memberDao;

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public Member getMemberByIdForSession(int loginedMemberId) {
		return memberDao.getMemberByIdForSession(loginedMemberId);
	}
	
	
	
}

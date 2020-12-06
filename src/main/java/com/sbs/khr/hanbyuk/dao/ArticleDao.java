package com.sbs.khr.hanbyuk.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.khr.hanbyuk.dto.Article;
import com.sbs.khr.hanbyuk.dto.Board;

@Mapper
public interface ArticleDao {
	
	Board getBoardIdByBoardCode(@Param("boardCode") String boardCode);

	void write(Map<String, Object> param);

	List<Article> getForPrintArticles(@Param("boardId") int boardId);

}

package com.sbs.khr.hanbyuk.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.khr.hanbyuk.dao.ArticleDao;
import com.sbs.khr.hanbyuk.dto.Article;
import com.sbs.khr.hanbyuk.dto.Board;

@Service
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;

	public Board getBoardIdByBoardCode(String boardCode) {
		return articleDao.getBoardIdByBoardCode(boardCode);
	}

	public void write(Map<String, Object> param) {
		articleDao.write(param);
	}

	public List<Article> getForPrintArticles(int boardId) {
		return articleDao.getForPrintArticles(boardId);
	}

	public Article getForPrintArticleById(int id) {
		return articleDao.getForPrintArticleById(id);
	}

	public void modify(Map<String, Object> param) {
		articleDao.modify(param);
	}

	public void delete(int boardId, int id) {
		articleDao.delete(boardId, id);
	}

}

package com.sbs.khr.hanbyuk.controller;

import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.khr.hanbyuk.dto.Article;
import com.sbs.khr.hanbyuk.dto.Board;
import com.sbs.khr.hanbyuk.service.ArticleService;
import com.sbs.khr.hanbyuk.util.Util;

@Controller
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	// 사용자 메뉴 시작
	@RequestMapping("usr/article/{boardCode}-list")
	public String showEventList(@PathVariable("boardCode") String boardCode, Model model) {

		Board board = articleService.getBoardIdByBoardCode(boardCode);

		List<Article> articles = articleService.getForPrintArticles(board.getId());

		model.addAttribute("board", board);
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}

	@RequestMapping("usr/article/takeAPhoto")
	public String showTakeAPhoto() {
		return "usr/article/takeAPhoto";
	}

	// 사용자 메뉴 끝
	
	// ---------------------------------------------------------------------------------------------------
	

	// 관리자 메뉴 시작
	@RequestMapping("admin/article/{boardCode}-list")
	public String showEventListAd(@PathVariable("boardCode") String boardCode, Model model) {

		Board board = articleService.getBoardIdByBoardCode(boardCode);

		List<Article> articles = articleService.getForPrintArticles(board.getId());

		model.addAttribute("board", board);
		model.addAttribute("articles", articles);

		return "admin/article/list";
	}

	@RequestMapping("admin/article/write")
	public String showWriteAd() {
		return "admin/article/write";
	}

	@RequestMapping("admin/article/doWrite")
	public String doWriteAd(@RequestParam Map<String, Object> param) {

		String boardCode = Util.getAsStr(param.get("boardCode"));
		Board board = articleService.getBoardIdByBoardCode(boardCode);

		param.put("boardId", board.getId());

		articleService.write(param);

		return "admin/home/main";
	}
	
	@RequestMapping("admin/article/{boardCode}-modify")
	public String showModify(@PathVariable("boardCode") String boardCode, int id, Model model) {
		Article article = articleService.getForPrintArticleById(id);
		
		Board board = articleService.getBoardIdByBoardCode(boardCode);
		
		
		model.addAttribute("board", board);
		model.addAttribute("article", article);
		
		return "admin/article/modify";
	}
	
	
	@RequestMapping("admin/article/{boardCode}-doModify")
	public String doModify(@PathVariable("boardCode") String boardCode, @RequestParam Map<String, Object> param) {
		
		Board board = articleService.getBoardIdByBoardCode(boardCode);
		
		param.put("boardId", board.getId());
		
		articleService.modify(param);
		
		
		return "admin/home/main";
	}
	
	@RequestMapping("admin/article/{boardCode}-doDelete")
	public String doDelete(@PathVariable("boardCode") String boardCode, int id) {
		
		Board board = articleService.getBoardIdByBoardCode(boardCode);
		
		articleService.delete(board.getId(), id);
		
		
		return "admin/home/main";
	}
	
	
	
	
	
	
	// 관리자 페이지에서 사진찍기 어떻게 구현할지 생각하기.
	@RequestMapping("admin/article/takeAPhotoAd")
	public String showTakeAPhotoAd() {
		return "admin/article/takeAPhoto";
	}
	
	
	
	

	// 관리자 메뉴 끝

}

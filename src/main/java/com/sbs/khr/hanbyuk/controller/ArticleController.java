package com.sbs.khr.hanbyuk.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.khr.hanbyuk.dto.Article;
import com.sbs.khr.hanbyuk.dto.Board;
import com.sbs.khr.hanbyuk.service.ArticleService;
import com.sbs.khr.hanbyuk.service.FileService;
import com.sbs.khr.hanbyuk.util.Util;

@Controller
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private FileService fileService;

	// 사용자 메뉴 시작
	@RequestMapping("usr/article/{boardCode}-list")
	public String showEventList(@PathVariable("boardCode") String boardCode, Model model) {

		Board board = articleService.getBoardIdByBoardCode(boardCode);

		List<Article> articles = articleService.getForPrintArticles(board.getId());

		model.addAttribute("board", board);
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}

	
	
	@RequestMapping("usr/article/{boardCode}-detail")
	public String showDetail(@PathVariable("boardCode") String boardCode, int id, Model model) {
		Board board = articleService.getBoardIdByBoardCode(boardCode);
		
		Article article = articleService.getForPrintArticleById(id);
		
		model.addAttribute("board", board);
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
	
	// 사진찍기 시작
	@RequestMapping("usr/article/takeAPhoto")
	public String showTakeAPhoto(Model model) {
		
		Board board = articleService.getBoardIdByBoardCode("takeAPhoto");
		List<Article> articles = articleService.getForPrintArticles(board.getId());
		
		model.addAttribute("board", board);
		model.addAttribute("articles", articles);
		
		return "usr/article/takeAPhoto";
	}
	
	

	
	@RequestMapping("admin/article/goPhoto")
	public String showGoPhoto() {
		return "admin/article/goPhoto";
	}
	
	@RequestMapping("usr/article/doGoPhoto")
	public String showDoGoPhoto(@RequestParam Map<String, Object> param, Model model) {
		
		int newId = articleService.writeByPhoto(param);
		
		model.addAttribute("msg", "메일 발송이 완료되었습니다.");
		
		
		
		
		
		model.addAttribute("replaceUri", "../home/main");
		
		articleService.deleteEmail(param);
		//fileService.deleteFile(Util.getAsInt(param.get("fileIdsStr")));
		/*
		 * 파일을 삭제하면 지정한 경로에서 이미지 파일을 불러 메일에서 보여주므로 보여줄 사진이 없게 된다. 한달 또는 지정한 시간이 흐르면
		 * 사진파일을 삭제하고, 어느 기간 안에 다운로드 하지 않으면 사용할 수 없다고 알리는건 어떨지
		 * 서버가 꺼져도 메일에서 사진 액빡
		 */
		
		
		return "usr/common/redirect";
	}
	
	
	// 사진찍기 끝 
	

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
	public String doWriteAd(@RequestParam Map<String, Object> param, Model model) {
		

		String boardCode = Util.getAsStr(param.get("boardCode"));
		Board board = articleService.getBoardIdByBoardCode(boardCode);

		param.put("boardId", board.getId());

		int id = articleService.write(param);
		
		model.addAttribute("msg", String.format("%d번 게시물이 작성되었습니다.", id));
		model.addAttribute("replaceUri", "../home/main");

		return "admin/common/redirect";
	}
	
	@RequestMapping("admin/article/{boardCode}-modify")
	public String showModify(@PathVariable("boardCode") String boardCode, int id, Model model) {
		Article article = articleService.getForPrintArticleById(id);
		
		System.out.println("article에 파일이 없나? : " + article);
		
		Board board = articleService.getBoardIdByBoardCode(boardCode);
		
		
		model.addAttribute("board", board);
		model.addAttribute("article", article);
		
		return "admin/article/modify";
	}
	
	
	@RequestMapping("admin/article/{boardCode}-doModify")
	public String doModify( @RequestParam Map<String, Object> param, Model model) {
		
		Board board = articleService.getBoardIdByBoardCode(Util.getAsStr(param.get("boardCode")));	
		System.out.println("board가 이상한가?? : " + board);
		
		
		param.put("boardId", board.getId());
		
		
		articleService.modify(param);
		
		
		model.addAttribute("msg", "게시물이 수정되었습니다.");
		model.addAttribute("replaceUri", "../home/main");
		
		
		return "admin/common/redirect";
	}
	
	@RequestMapping("admin/article/{boardCode}-doDelete")
	public String doDelete(@PathVariable("boardCode") String boardCode, int id) {
		
		Board board = articleService.getBoardIdByBoardCode(boardCode);
		
		articleService.delete(board.getId(), id);
		
		
		return "admin/home/main";
	}
	
	@RequestMapping("admin/article/{boardCode}-detail")
	public String showDetailAd(@PathVariable("boardCode") String boardCode, int id, Model model) {
		Board board = articleService.getBoardIdByBoardCode(boardCode);
		
		Article article = articleService.getForPrintArticleById(id);
		
		model.addAttribute("board", board);
		model.addAttribute("article", article);
		
		return "admin/article/detail";
	}
	
	
	// 사진찍기 시작	
	
	
	// 관리자 페이지에서 사진찍기 어떻게 구현할지 생각하기.
	@RequestMapping("admin/article/takeAPhotoAd")
	public String showTakeAPhotoAd(Model model) {
		Board board = articleService.getBoardIdByBoardCode("takeAPhoto");
		List<Article> articles = articleService.getForPrintArticles(board.getId());
		
		model.addAttribute("articles", articles);
		model.addAttribute("board", board);
		
		return "admin/article/takeAPhoto";
	}
	
	

	
	
	
	
	
	// 사진찍기 끝
	
	
	

	// 관리자 메뉴 끝

}

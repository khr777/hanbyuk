package com.sbs.khr.hanbyuk.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.sbs.khr.hanbyuk.dao.ArticleDao;
import com.sbs.khr.hanbyuk.dto.Article;
import com.sbs.khr.hanbyuk.dto.Board;
import com.sbs.khr.hanbyuk.dto.File;
import com.sbs.khr.hanbyuk.util.Util;

@Service
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private FileService fileService;

	@Autowired
	private MailService mailService;
	

	public Board getBoardIdByBoardCode(String boardCode) {
		return articleDao.getBoardIdByBoardCode(boardCode);
	}

	
	public int write(Map<String, Object> param) {
		articleDao.write(param);
		int id = Util.getAsInt(param.get("id"));

		String fileIdsStr = (String) param.get("fileIdsStr");

		if (fileIdsStr != null && fileIdsStr.length() > 0) {
			List<Integer> fileIds = Arrays.asList(fileIdsStr.split(",")).stream().map(s -> Integer.parseInt(s.trim()))
					.collect(Collectors.toList());

			// 파일이 먼저 생성된 후에, 관련 데이터가 생성되는 경우에는, file의 relId가 일단 0으로 저장된다.
			// 그것을 뒤늦게라도 이렇게 고처야 한다.
			for (int fileId : fileIds) {
				fileService.changeRelId(fileId, id);
			}
		}

		return id;
	}

	public List<Article> getForPrintArticles(int boardId) {
		List<Article> articles = articleDao.getForPrintArticles(boardId);
		
		for ( Article article : articles ) {
			List<File> files = fileService.getFiles("article", article.getId(), "common", "attachment");

			Map<String, File> filesMap = new HashMap<>();

			for (File file : files) {
				filesMap.put(file.getFileNo() + "", file);
			}

			Util.putExtraVal(article, "file__common__attachment", filesMap);
		}
		
		
		
		return articles;
	}

	public Article getForPrintArticleById(int id) {
		Article article = articleDao.getForPrintArticleById(id);
		
		List<File> files = fileService.getFiles("article", article.getId(), "common", "attachment");

		Map<String, File> filesMap = new HashMap<>();

		for (File file : files) {
			filesMap.put(file.getFileNo() + "", file);
		}

		Util.putExtraVal(article, "file__common__attachment", filesMap);

		return article;
	}

	public void modify(Map<String, Object> param) {
		articleDao.modify(param);
		
		int id = Util.getAsInt(param.get("id"));

		String fileIdsStr = (String) param.get("fileIdsStr");

		if (fileIdsStr != null && fileIdsStr.length() > 0) {
			List<Integer> fileIds = Arrays.asList(fileIdsStr.split(",")).stream().map(s -> Integer.parseInt(s.trim()))
					.collect(Collectors.toList());

			// 파일이 먼저 생성된 후에, 관련 데이터가 생성되는 경우에는, file의 relId가 일단 0으로 저장된다.
			// 그것을 뒤늦게라도 이렇게 고처야 한다.
			for (int fileId : fileIds) {
				fileService.changeRelId(fileId, id);
			}
		}
	}

	public void delete(int boardId, int id) {
		articleDao.delete(boardId, id);
		fileService.deleteFiles("article", id);
	}


	public int writeByPhoto(Map<String, Object> param) {
		articleDao.writeByPhoto(param);
		String email = Util.getAsStr(param.get("email"));
		int id = Util.getAsInt(param.get("id"));

		String fileIdsStr = (String) param.get("fileIdsStr");

		if (fileIdsStr != null && fileIdsStr.length() > 0) {
			List<Integer> fileIds = Arrays.asList(fileIdsStr.split(",")).stream().map(s -> Integer.parseInt(s.trim()))
					.collect(Collectors.toList());

			// 파일이 먼저 생성된 후에, 관련 데이터가 생성되는 경우에는, file의 relId가 일단 0으로 저장된다.
			// 그것을 뒤늦게라도 이렇게 고처야 한다.
			for (int fileId : fileIds) {
				fileService.changeRelId(fileId, id);
			}
		}
		
			
		
		String mailTitle = "한벽문화관 사진촬영 발송";
		StringBuilder mailBodySb = new StringBuilder();
		
		int fileId = Util.getAsInt(param.get("fileIdsStr"));
		System.out.println("fileId는? : " + fileId);
		mailBodySb.append("<h1>사진</h1>");
		mailBodySb.append("<h2>기념사진을 발송드립니다.</h2>");
		//mailBodySb.append("<img style=\"width:600px;\" src=\"http://localhost:8085/admin/file/showImg?id=" + fileId +  "\"  />");
		mailBodySb.append("<img style=\"width:600px;\" src=\"http://hanbyuk.ouo.nz:8080/admin/file/showImg?id=" + fileId +  "\"  />");
		
		mailService.send(email, mailTitle, mailBodySb.toString());
	
		
		
		return id;
	}


	public void deleteEmail(Map<String, Object> param) {
		articleDao.deleteEmail(param);
	}

}

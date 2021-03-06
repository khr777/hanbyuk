package com.sbs.khr.hanbyuk.config;

import org.springframework.stereotype.Component;

@Component
public class AppConfig {
	
	public int getMaxAttachmentFileNo(String relTypeCode) {
		return 10;
	}


	public String getAttachmentFileExtTypeCode(String relTypeCode, int fileNo) {
		if (fileNo > 1) {
			return "video";
		}

		return "img";
	}

	public String getAttachmentFileExtTypeDisplayName(String relTypeCode, int fileNo) {
		String fileExtTypeCode = getAttachmentFileExtTypeCode(relTypeCode, fileNo);

		switch (fileExtTypeCode) {
		case "img":
			return "이미지";
		default:
			return "비디오";
		}
	}

	public String getAttachemntFileInputAccept(String relTypeCode, int fileNo) {
		String fileExtTypeCode = getAttachmentFileExtTypeCode(relTypeCode, fileNo);

		switch (fileExtTypeCode) {
		case "img":
			return "image/gif,image/jpeg,image/png";
		default:
			return "video/mp4,video/quicktime";
		}
	}



}
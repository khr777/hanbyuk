package com.sbs.khr.hanbyuk.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.LoadingCache;

public class Util {

	public static void changeMapKey(Map<String, Object> param, String oldKey, String newKey) {
		Object value = param.get(oldKey);
		param.remove(oldKey);
		param.put(newKey, value);
	}
	
	
	public static int getAsInt(Object object) {
		if (object instanceof BigInteger) {
			return ((BigInteger) object).intValue();
		} else if (object instanceof Long) {
			return (int) object;
		} else if (object instanceof Integer) {
			return (int) object;
		} else if (object instanceof String) {
			return Integer.parseInt((String) object);
		}

		return -1;
	}


	public static String getAsStr(Object object) {
		if (object == null) {
			return "";
		}

		return object.toString();
	}

	public static boolean isNum(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof Long) {
			return true;
		} else if (obj instanceof Integer) {
			return true;
		} else if (obj instanceof String) {
			try {
				Integer.parseInt((String) obj);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		return false;
	}
	
	public static int getInt(HttpServletRequest req, String paramName) {
		return Integer.parseInt(req.getParameter(paramName));
	}

	public static int getInt(HttpServletRequest req, String paramName, int elseValue) {
		try {
			return Integer.parseInt(req.getParameter(paramName));
		} catch (NumberFormatException e) {
			return elseValue;
		}
	}

	
	public static Map<String, Object> getParamMap(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();

		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			Object paramValue = request.getParameter(paramName);

			param.put(paramName, paramValue);
		}

		return param;
	}


	public static String toJsonStr(Map<String, Object> param) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(param);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return "";
	}


	public static Map<String, Object> getNewMapOf(Map<String, Object> oldMap, String... keys) {
		Map<String, Object> newMap = new HashMap<>();

		for (String key : keys) {
			newMap.put(key, oldMap.get(key));
		}

		return newMap;
	}
	
	public static String getTempPassword(int length) {
		int index = 0;
		char[] charArr = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		
		StringBuffer sb = new StringBuffer();
		
		for ( int i = 0; i < length; i++ ) {
			index = (int) (charArr.length * Math.random());
			sb.append(charArr[index]);
		}
		
		return sb.toString();
	}
	
	public static String sha256(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();

		} catch (Exception ex) {
			return "";
		}
	}
	
	public static Object getExtraVal(Object obj, String key, Object elseValue) {
		Class cls = obj.getClass();

		try {
			Method getExtraMethod = cls.getDeclaredMethod("getExtra");
			Map<String, Object> extra = (Map<String, Object>) getExtraMethod.invoke(obj);

			if (extra == null) {
				return null;
			} else {
				if (extra.get(key) == null) {
					return elseValue;
				} else {
					return extra.get(key);
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static void putExtraVal(Object obj, String key, Object value) {
		Class cls = obj.getClass();

		try {
			Method getExtraMethod = cls.getDeclaredMethod("getExtra");
			Map<String, Object> extra = (Map<String, Object>) getExtraMethod.invoke(obj);

			if (extra == null) {
				extra = new HashMap<>();
				extra.put(key, value);

				Method setExtraMethod = cls.getDeclaredMethod("setExtra", Map.class);
				setExtraMethod.invoke(obj, extra);
			} else {
				extra.put(key, value);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getFileExtTypeCodeFromFileName(String fileName) {
		String ext = getFileExtFromFileName(fileName).toLowerCase();

		switch (ext) {
		case "jpeg":
		case "jpg":
		case "gif":
		case "png":
			return "img";
		case "mp4":
		case "avi":
		case "mov":
			return "video";
		case "mp3":
			return "audio";
		}

		return "etc";
	}

	public static String getFileExtType2CodeFromFileName(String fileName) {
		String ext = getFileExtFromFileName(fileName).toLowerCase();

		switch (ext) {
		case "jpeg":
		case "jpg":
			return "jpg";
		case "gif":
			return ext;
		case "png":
			return ext;
		case "mp4":
			return ext;
		case "mov":
			return ext;
		case "avi":
			return ext;
		case "mp3":
			return ext;
		}

		return "etc";
	}

	public static String getFileExtFromFileName(String fileName) {
		int pos = fileName.lastIndexOf(".");
		String ext = fileName.substring(pos + 1);

		return ext;
	}

	public static byte[] getFileBytes(InputStream fis) {
		byte[] returnValue = null;

		ByteArrayOutputStream baos = null;

		try {
			baos = new ByteArrayOutputStream();

			byte[] buf = new byte[1024];
			int read = 0;

			while ((read = fis.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, read);
			}

			returnValue = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return returnValue;
	}

	public static byte[] getFileBytesFromMultipartFile(MultipartFile multipartFile) {
		try {
			return getFileBytes(multipartFile.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static InputStream getBinaryStreamFromBlob(Blob fileBody) {
		try {
			return fileBody.getBinaryStream();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static <T extends Object> T getCacheData(LoadingCache cache, int key) {
		try {
			return (T) cache.get(key);
		} catch (ExecutionException e) {
			return null;
		}
	}


	public static boolean getDateForpasswordModify(String lastPasswordUpdateDate) {
		
		// 오늘 날짜를 구하기 위한 객체 선언 
		Date date = new Date();
		
		// 1번 [ member의 마지막 updateDate를 String으로 뽑아온다. ] 
		//String updateDateStr = "2020-05-28 10:11:11";
		String updateDateStr = lastPasswordUpdateDate;
		
		// 2번 [ 날짜를 출력할 폼을 지정한다. ]
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		// 3번 [ 마지막으로 변경한 패스워드 변경일을 String에서 date 타입으로 형변환을 한다. ]
		Date updateDate = null;
		try {
			updateDate = transFormat.parse(updateDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 4번 [ updateDateStr을 0000-00-00 형태로 날짜를 출력한다. ]
		// 그러나 updateDate는 형태가 date이므로 Thu May 28 00:00:00 KST 2020 이런값을 출력한다.
		System.out.println("updateDateStr : " + updateDateStr);
		System.out.println("updateDate (String을 Date로 형변환) 결과  : " + updateDate);

		
		// 5번 [ 캘린더로 날짜를 가져오기 위한 메서드를 입력한다. ] 
		Calendar cal = Calendar.getInstance();
		
		// 6번 [ 날짜를 가져오기 위한 캘린더에 date형태로 형변환한 updateDate를 셋팅한다. ] 
		cal.setTime(updateDate);
		
		
		// 7번 [ date형태로 날짜를 셋팅한 캘린더에 셋팅한 날짜로부터 3개월이 경과되는 코드를 입력한다. ]
		cal.add(Calendar.MONTH, 3);
		//cal.add(Calendar.SECOND, 1);
		
		
		// 8번 [ 캘린더에서 마지막 updateDate 날짜로부터 3개월이 초과한 날을 가져온다. ] 
		// 경과한 날짜를 비교하기 쉽도록 
		String currentStr = transFormat.format(cal.getTime());	
		
		// 9 번 [ updateDateStr은 마지막으로 패스워드를 변경한 날짜를 String으로 불러온다. ]
		System.out.println("마지막으로 updateDate 한 날짜 : " + updateDateStr);
		
		
		// 10번 [ currentStr은 3개월이 경과한 날짜(Date)를 String으로 형변환한 값을 가져온다. ] 
		System.out.println("3개월이 경과 한 날짜 : " + currentStr);
		
		
		// 11번 [ 오늘의 날짜를 String으로 가져온다. ]
		String today = transFormat.format(date);
		
		System.out.println("today : " + today);
		
		today = today.replace("-","");
		currentStr = currentStr.replace("-","");
		System.out.println("today : " + today);
		System.out.println("currentStr : " + currentStr);
		
		int todayInt = Integer.parseInt(today);
		int currentInt = Integer.parseInt(currentStr);
		
		
		//12번  updateDate 마지막으로 패스워드를 변경한 날짜로부터 3개월이 초과한 currentStr 날짜와 오늘 날짜를 비교한다.
		// 기준이 되는 today 오늘 날짜가 마지막 개인정보 변경일로부터 3개월 초과한 currentStr보다 크다면 -1이다. 사전식으로 currentStr이 더 빠를테니까 
		// 그렇다면 return true.     
		if ( todayInt > currentInt ) {
			System.out.println("true " + true);
			return true;
		}
		System.out.println(today.compareTo(currentStr));
		System.out.println("false " + false);
		
		return false;
	}
	
	
	// uri를 입력 값을 정확하게 받기위해서(변수와 값을 명확하게 구분하기 위해서) 인코딩 해주는 메서드
	public static String getUriEncoded(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
	public static String getNewUriAndEncoded(String uri, String paramName, String pramValue) {
		return getUriEncoded(getNewUri(uri, paramName, pramValue));
	}
	
	public static String getNewUri(String uri, String paramName, String paramValue) {
		uri = getNewUriRemoved(uri, paramName);

		if (uri.contains("?")) {
			uri += "&" + paramName + "=" + paramValue;
		} else {
			uri += "?" + paramName + "=" + paramValue;
		}

		uri = uri.replace("?&", "?");

		return uri;
	}
	
	public static String getNewUriRemoved(String uri, String paramName) {
		String deleteStrStarts = paramName + "=";
		int delStartPos = uri.indexOf(deleteStrStarts);

		if (delStartPos != -1) {
			int delEndPos = uri.indexOf("&", delStartPos);

			if (delEndPos != -1) {
				delEndPos++;
				uri = uri.substring(0, delStartPos) + uri.substring(delEndPos, uri.length());
			} else {
				uri = uri.substring(0, delStartPos);
			}
		}

		if (uri.charAt(uri.length() - 1) == '?') {
			uri = uri.substring(0, uri.length() - 1);
		}

		if (uri.charAt(uri.length() - 1) == '&') {
			uri = uri.substring(0, uri.length() - 1);
		}

		return uri;
	}
	
	public static String getString(HttpServletRequest req, String paramName, String elseValue) {
		if (req.getParameter(paramName) == null) {
			return elseValue;
		}

		if (req.getParameter(paramName).trim().length() == 0) {
			return elseValue;
		}

		return getString(req, paramName);
	}
	
	public static String getString(HttpServletRequest req, String paramName) {
		return req.getParameter(paramName);
	}


	public static String getDateStrLater(int seconds) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String dateStr = format1.format(System.currentTimeMillis() + seconds * 1000);
		
		return dateStr;
	}
	
	public static String getNowDateStr() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String dateStr = format1.format(System.currentTimeMillis());

		return dateStr;
	}

}

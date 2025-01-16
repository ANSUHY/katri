package com.katri.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/***************************************************
 * <ul>
 * <li>업무 그룹명 : 엑셀 유틸 업무</li>
 * <li>서브 업무명 : 엑셀 유틸 정보 관리</li>
 * <li>설	 명 : 엑셀 유틸 정보</li>
 * <li>작  성  자 : Red 2021</li>
 * <li>작  성  일 : 2021. 02. 14.</li>
 * </ul>
 * <pre>
 * ======================================
 * 변경자/변경일 :
 * 변경사유/내역 :
 * ======================================
 * </pre>
 ***************************************************/
@Slf4j
public class ExcelUtil {

	/*****************************************************
	 * JXLS 엑셀 다운로드
	 * @param filename 파일명
	 * @param browser 브라우저
	 * @return String 인코딩 파일명
	 * @throws InvalidFormatException
	 * @throws ParsePropertyException
	 *****************************************************/
	public void download(HttpServletRequest request, HttpServletResponse response, Map<String , Object> map, String fileName, String templateFile)
				throws ParsePropertyException, InvalidFormatException, IOException {

		// 받아오는 매개변수 mapReq는 Database에서 뽑아온 데이터
		// fileName 은 다운로드 받을때 지정되는 파일명
		// templateFile 는 템플릿 엑셀 파일명
		// tempPath는 템플릿 엑셀파일이 들어가는 경로를 넣어 준다.
		String tempPath = request.getSession().getServletContext().getRealPath("/WEB-INF/template");
		InputStream is = null;
		OutputStream os = null;
		Workbook workbook = null;

		try {
			is = new BufferedInputStream(new FileInputStream(tempPath + "/" + templateFile));

			XLSTransformer xls = new XLSTransformer();

			workbook = xls.transformXLS(is, map);

			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xlsx\"");

			os = response.getOutputStream();

			workbook.write(os);

		} catch (IOException e) {
			log.debug(e.getMessage());
			throw e;
		} finally {
			try {
				is.close();
				os.close();
				workbook.close();
			} catch (IOException e) {
				log.debug(e.getMessage());
			}
		}
	}

	/*****************************************************
	 *  JXLS 엑셀 다운로드
	 * @param request
	 * @param response
	 * @param list 다운 받을 리스트
	 * @param fileName 파일 네임(다운받았을시 파일네임)
	 * @param templateFile 파일템플릿네임
	 * @throws Exception
	*****************************************************/
	public void download2(HttpServletRequest request, HttpServletResponse response, List<Map<String , Object>> list, String fileName, String templateFile)
				throws Exception {

		// 받아오는 매개변수 mapReq는 Database에서 뽑아온 데이터
		// fileName 은 다운로드 받을때 지정되는 파일명
		// templateFile 는 템플릿 엑셀 파일명
		// tempPath는 템플릿 엑셀파일이 들어가는 경로를 넣어 준다.
		String tempPath = request.getSession().getServletContext().getRealPath("/WEB-INF/template");
		InputStream is = null;
		OutputStream os = null;

		try {

			is = new BufferedInputStream(new FileInputStream(tempPath + "/" + templateFile));

			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xlsx\"");
			response.setContentType("application/octet-stream");

			os = response.getOutputStream();

			Context context = new Context();
			context.putVar("list", list);

			JxlsHelper.getInstance().processTemplate(is, os, context);
		} catch (Exception e) {
			log.debug(e.getMessage());
			throw e;
		} finally {
			is.close();
			os.close();
		}

	}

	/*****************************************************
	 * JXLS 엑셀 다운로드
	 * @param filename 파일명
	 * @param browser 브라우저
	 * @return String 인코딩 파일명
	 * @throws InvalidFormatException
	 * @throws ParsePropertyException
	 *****************************************************/
	public void download3(HttpServletRequest request, HttpServletResponse response, Map<String , Object> map, List<Map<String , Object>> list, String fileName, String templateFile)
				throws ParsePropertyException, InvalidFormatException, Exception {

		// 받아오는 매개변수 mapReq는 Database에서 뽑아온 데이터
		// fileName 은 다운로드 받을때 지정되는 파일명
		// templateFile 는 템플릿 엑셀 파일명
		// tempPath는 템플릿 엑셀파일이 들어가는 경로를 넣어 준다.
		String tempPath = request.getSession().getServletContext().getRealPath("/WEB-INF/template");
		InputStream is = null;

		try {
			is = new BufferedInputStream(new FileInputStream(tempPath + "/" + templateFile));

			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xlsx\"");

			try (OutputStream os = response.getOutputStream()) {

				Context context = new Context();
				context.putVar("map", map);
				context.putVar("list", list);

				JxlsHelper.getInstance().processTemplate(is, os, context);
			}

		} catch (Exception e) {
			log.debug(e.getMessage());
			throw e;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				log.debug(e.getMessage());
				throw e;
			}
		}
	}

	/*****************************************************
	 * 엑셀 업로드 데이터 파싱
	 * @param file 엑셀 업로드파일
	 * @param excelHeaderList 엑셀 헤더 정보
	 * @param failureMessages 엑셀 데이터 파싱 오류 메시지
	 * @param effectiveFirstRowIndex 엑셀 파싱 시작 행 로우
	 * @return List<Map<String, Object>> 엑셀 업로드 파싱 데이터 목록
	 * @throws IllegalArgumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 *****************************************************/
	public static List<Map<String, Object>> excelFileToList(MultipartFile file, String[] excelHeaderList, int effectiveFirstRowIndex)
			throws IllegalArgumentException, InvalidFormatException, IOException {

		Row row;
		Cell cell;
		BigDecimal nCellValue;
		List<Map<String, Object>> excelInfoDetail = new ArrayList<Map<String, Object>>();
		Map<String, Object> excelInfoData = new HashMap<String, Object>();

		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(file.getInputStream());

			// set effective position
			int effectiveFirstSheetIndex = 0;
			// 엑셀 양식에는 강제로 시트 1개만 넣도록 한다 따라서 시작인덱스는 0, 마지막 인덱스도 0
			int effectiveLastSheetIndex = 0;

			// traverse sheet
			for (int i = effectiveFirstSheetIndex; i <= effectiveLastSheetIndex; i++) {
				Sheet sheet = wb.getSheetAt(i);
				int effectiveLastRowIndex = sheet.getLastRowNum();

				// 엑셀 Row 단위로 읽기
				for (int j = effectiveFirstRowIndex; j <= effectiveLastRowIndex; j++) {
					excelInfoData = new HashMap<String, Object>();
					row = sheet.getRow(j);

					if(row != null){
						row.getFirstCellNum();
						for (int k = row.getFirstCellNum(); k <= excelHeaderList.length - 1; k++) {
							cell = row.getCell(k);
							if (cell != null) {
								if (cell.getCellType() == CellType.NUMERIC) {
									if( DateUtil.isCellDateFormatted(cell)) {
										Date date = cell.getDateCellValue();
	//									System.out.println("Date --------> "+new SimpleDateFormat("yyyyMMdd").format(date).toString());
										excelInfoData.put(excelHeaderList[k], new SimpleDateFormat("yyyyMMdd").format(date).toString());
									} else {
										nCellValue = new BigDecimal(cell.getNumericCellValue());
										excelInfoData.put(excelHeaderList[k], nCellValue.toString());
									}
								} else if (cell.getCellType() == CellType.FORMULA) {
	//								System.out.println(excelHeaderList[k]+" ["+k+"] cell.getCellFormula() >> "+cell.getCellFormula());
									excelInfoData.put(excelHeaderList[k], cell.getCellFormula());
								} else {
									excelInfoData.put(excelHeaderList[k], cell.getRichStringCellValue().toString());
								}
							}
						}

						excelInfoDetail.add(excelInfoData);
					}
				}
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			throw e;
		} finally {
			wb.close();
		}

		return excelInfoDetail;
	}

}

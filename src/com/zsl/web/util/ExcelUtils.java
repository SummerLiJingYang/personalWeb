package com.zsl.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * Excel上传
 * 
 * @author linjiande
 */
public class ExcelUtils{
	
	public static List<Map<String, String>> parseExcel(MultipartFile xls,Map<String, String> head) throws IOException {
		/** 转换为临时文件*/
		if (null == xls || xls.getSize() == 0) {
			return null;
		}
		String extension = FilenameUtils.getExtension(xls.getOriginalFilename());
		if(!"xlsx".equals(extension) && !"xls".equals(extension)){
			return null;
		}
		File file = UploadUtils.saveTempMartipartFile(xls);//保存临时文件
		
		Workbook workbook = null;
		if("xlsx".equals(extension)){
			workbook = new XSSFWorkbook(new FileInputStream(file));
		}else{
			workbook = new HSSFWorkbook(new FileInputStream(file));
		}
		Sheet sheet = workbook.getSheetAt(0);
		int rowHead = 0;// 标记表头所在行
		final int rowMax = 3;// 最多查找的行数
		final int matchMin = 3;// 确定有效表头至少需要的匹配数
		Map<Integer, String> field = Maps.newLinkedHashMap();// 字段映射
		
		/** 获取表头*/
		while (rowHead < rowMax) {
			int match = 0;// 标记字段匹配数
			Row row = sheet.getRow(rowHead);// 获取当前行
			if (row == null) {
				rowHead++;
				continue;
			}
			int colCount = row.getLastCellNum();// 获取当前行的列数
			for (int col = 0; col < colCount; col++) {// 在当前行逐个单元格匹配，同时标记某列对应字段
				Cell cell = row.getCell(col);// 获取当前单元格
				if(cell != null){
					String v = StringUtils.trimToEmpty(getCellValue(cell));
					if(StringUtils.isNotBlank(v)){
						for (String key : head.keySet()) {
							if (head.get(key).indexOf(v) != -1) {
								match++;
								field.put(col, key);
								break;
							}
						}
					}
				}
			}
			if (match >= matchMin) {// 达到最少匹配数，即认为已找到表头
				break;
			}
			rowHead++;// 下一行
		}
		
		/** 获取数据*/
		List<Map<String, String>> data = Lists.newArrayList();
		if(field.size()>0){
			int rowCount = sheet.getLastRowNum();//行数
			for (int i = rowHead + 1; i <= rowCount; i++) {
				Map<String, String> product = Maps.newLinkedHashMap();
	
				// 获取当前行
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
	
				// 获取当前行的有效列数
				int colCount = row.getLastCellNum();
	
				// 逐个单元格获取数据
				for (int j = 0; j < colCount; j++) {
					Cell cell = row.getCell(j);// 获取当前单元格
					if (cell == null) {
						continue;
					}
					if (field.containsKey(j)) {// 获取当前单元格数据
						product.put(field.get(j),StringUtils.trimToEmpty(getCellValue(cell)));
					}
				}
				data.add(product);
			}
		}
		// 关闭工作簿
		workbook.close();
		FileUtils.deleteQuietly(file);
		return data;
	}
	
	/**
	 * 校验文件是否为空
	 * @param xls
	 * @return
	 */
	private boolean validate(MultipartFile xls) {
		boolean flag = true;
		/** 转换为临时文件*/
		if (null == xls || xls.getSize() == 0) {
			flag = false;
		}
		String extension = FilenameUtils.getExtension(xls.getOriginalFilename());
		if(!"xlsx".equals(extension) && !"xls".equals(extension)){
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 获取表头行号
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private int getRowHead(String extension, File file, Map<String, String> head,Sheet sheet, Map<Integer, String> field) throws FileNotFoundException, IOException {
		
		int rowHead = 0;// 标记表头所在行
		final int rowMax = 3;// 最多查找的行数
		final int matchMin = 3;// 确定有效表头至少需要的匹配数
		/** 获取表头*/
		while (rowHead < rowMax) {
			int match = 0;// 标记字段匹配数
			Row row = sheet.getRow(rowHead);// 获取当前行
			if (row == null) {
				rowHead++;
				continue;
			}
			int colCount = row.getLastCellNum();// 获取当前行的列数
			for (int col = 0; col < colCount; col++) {// 在当前行逐个单元格匹配，同时标记某列对应字段
				Cell cell = row.getCell(col);// 获取当前单元格
				if(cell != null){
					String v = StringUtils.trimToEmpty(getCellValue(cell));
					if(StringUtils.isNotBlank(v)){
						for (String key : head.keySet()) {
							if (head.get(key).indexOf(v) != -1) {
								match++;
								field.put(col, key);
								break;
							}
						}
					}
				}
			}
			if (match >= matchMin) {// 达到最少匹配数，即认为已找到表头
				break;
			}
			rowHead++;// 下一行
		}
		return rowHead;
	}
	
	
	/**
	 * 根据pageNo查询需要查询的页码数
	 * @param xls
	 * @param head
	 * @param pageNo
	 * @return
	 * @throws IOException
	 */
	public List<Map<String, String>> parseExcelWithPageNo(MultipartFile xls,Map<String, String> head, Integer pageNo,Integer pageSize) throws IOException {
		if (null == pageNo) pageNo = 1;
		if (null == pageSize) pageSize = 10;
		if (!validate(xls)) return null;//判空
		String extension = FilenameUtils.getExtension(xls.getOriginalFilename());
		File file = UploadUtils.saveTempMartipartFile(xls);//保存临时文件
		
		Map<Integer, String> field = Maps.newLinkedHashMap();// 字段映射
		Workbook workbook = null; 
		Sheet sheet = null;
		if("xlsx".equals(extension)){
			workbook = new XSSFWorkbook(new FileInputStream(file));
		}else{
			workbook = new HSSFWorkbook(new FileInputStream(file));
		}
		sheet = workbook.getSheetAt(0);
		/** 获取文件页头所在行数*/
		int rowHead = getRowHead(extension, file, head, sheet, field);
		/** 获取数据*/
		List<Map<String, String>> data = Lists.newArrayList();
		
		if(field.size()>0){
			int rowCount = sheet.getLastRowNum();//行数
			
			/** 设置查询数据的开始、截止位置*/
			int from = rowHead+pageSize*(pageNo-1) + 1;
			int to = rowCount;
			if (rowCount > (from + pageSize) ) to = from + pageSize - 1;
			for (int i = rowHead+pageSize*(pageNo-1) + 1; i <= to; i++) {
				Map<String, String> product = Maps.newLinkedHashMap();
				// 获取当前行
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
	
				// 获取当前行的有效列数
				int colCount = row.getLastCellNum();
	
				// 逐个单元格获取数据
				for (int j = 0; j < colCount; j++) {
					Cell cell = row.getCell(j);// 获取当前单元格
					if (cell == null) {
						continue;
					}
					if (field.containsKey(j)) {// 获取当前单元格数据
						product.put(field.get(j),StringUtils.trimToEmpty(getCellValue(cell)));
					}
				}
				data.add(product);
			}
		}
		// 关闭工作簿
		workbook.close();
		FileUtils.deleteQuietly(file);
		return data;
	}
	
	/**
	 * 获取表格值
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getCellValue(Cell cell) {  
        String ret;  
        switch (cell.getCellType()) {  
        case Cell.CELL_TYPE_BLANK:  
            ret = "";  
            break;  
        case Cell.CELL_TYPE_BOOLEAN:  
            ret = String.valueOf(cell.getBooleanCellValue());  
            break;  
        case Cell.CELL_TYPE_ERROR:  
            ret = null;  
            break;  
        case Cell.CELL_TYPE_FORMULA:  
            Workbook wb = cell.getSheet().getWorkbook();  
            CreationHelper crateHelper = wb.getCreationHelper();  
            FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();  
            ret = getCellValue(evaluator.evaluateInCell(cell));  
            break;  
        case Cell.CELL_TYPE_NUMERIC:  
            if (DateUtil.isCellDateFormatted(cell)) {   
                Date theDate = cell.getDateCellValue();  
//                ret = simpleDateFormat.format(theDate);
                ret=null;
            } else {   
                ret = NumberToTextConverter.toText(cell.getNumericCellValue());  
            }  
            break;  
        case Cell.CELL_TYPE_STRING:  
            ret = cell.getRichStringCellValue().getString();  
            break;  
        default:  
            ret = null;  
        }  
          
        return ret; //有必要自行trim  
    }
}
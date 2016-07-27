package com.zsl.web.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;



/**
 * 文档工具
 */
public class DocUtils{
	
    /**
     * 生成数据表格
     */
    public static String tableData(List<Map<String,Object>> body,String[] head,String loadhref)  {
    	
    	StringBuilder sb=new StringBuilder();
    	
    	//导出链接
    	if(StringUtils.isNotEmpty(loadhref)){
    		sb.append("<a style=\"display:block;font-size:12px;padding:10px 0px;\" href=\"");
    		sb.append(loadhref);
    		sb.append("\">导出</a>");
    	}
    	
    	sb.append("<table cellpadding=\"0\" cellspacing=\"0\" style=\"font-size:12px;\">");
    	
    	//表头
        if(ArrayUtils.isNotEmpty(head)){
        	sb.append("<tr>");
        	sb.append("<td style=\"border-top:solid 1px #999999;border-bottom:solid 1px #999999;padding:5px 10px;font-weight:bold;background:#CCCCCC;\">"+"序号"+"</td>");
            for(String v:head){
            	sb.append("<td style=\"border-top:solid 1px #999999;border-bottom:solid 1px #999999;padding:5px 10px;font-weight:bold;background:#CCCCCC;\">"+v+"</td>");
            }
            sb.append("</tr>");
        }
        
        //表体
        int i=0;
        for(Map<String, Object> row:body){
        	i++;
        	String bg=i%2==0 ? "background:#F0F0F9;" : "";
        	sb.append("<tr>");
        	sb.append("<td style=\"border-bottom:solid 1px #999999;padding:5px 10px;"+bg+"\">"+i+"</td>");
        	for(String key : row.keySet()){
            	sb.append("<td style=\"border-bottom:solid 1px #999999;padding:5px 10px;"+bg+"\">"+row.get(key)+"</td>");
            }
            sb.append("<tr>");
            
        }
        
        sb.append("</table>");
        
        return sb.toString();
    }
    
    /**
     * 导出xml数据格式的excel
     * @param body 表体数据，必须
     * @param head 表头数据，可选，默认为空
     * @param name 不带扩展名的文件名，可选，如果空则以当前时间为名字，表标题也用此名字，只导出xls格式
     */
    public static void exportExcelXml(List<Map<String,Object>> body,String[] head,String name,HttpServletResponse response) {
        //替换特殊字符
/*        foreach($body as &$row)
        {
            $row=str_replace(array('<','>','\'','"'),array('&lt;','&gt;','&apos;','&quot;'),$row);
        }*/
        //表标题，若未指定则取当前时间
        name=StringUtils.isBlank(name) ? DateTimeUtils.getFormatDate(null, "yyyyMMddHHmmss") : name;
        
        //生成xml数据
        StringBuilder sb=new StringBuilder();
        sb.append("<?xml version=\"1.0\" ?>"+
            "<?mso-application progid=\"Excel.Sheet\"?>"+
            "<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:html=\"http://www.w3.org/TR/REC-html40\">"+
                "<Styles>"+
                    "<Style ss:ID=\"Default\" ss:Name=\"Normal\">"+
                        "<Alignment ss:Vertical=\"Center\"/>"+
                        "<Borders/>"+
                        "<Font x:CharSet=\"134\" ss:Size=\"10\"/>"+
                        "<Interior/>"+
                        "<NumberFormat/>"+
                        "<Protection/>"+
                    "</Style>"+
                    "<Style ss:ID=\"s1\">"+
                        "<Font ss:Bold=\"1\"/>"+
                        "<Alignment ss:Horizontal=\"Center\"/>"+
                        "<Borders>"+
                        "<Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"+
                        "<Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"+
                        "<Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"+
                        "<Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"+
                        "</Borders>"+
                        "<Interior ss:Color=\"#C0C0C0\" ss:Pattern=\"Solid\" />"+
                    "</Style>"+
                    "<Style ss:ID=\"s2\">"+
                        "<Alignment ss:Horizontal=\"Center\"/>"+
                        "<Borders>"+
                        "<Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"+
                        "<Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"+
                        "<Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"+
                        "<Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\"/>"+
                        "</Borders>"+
                    "</Style>"+
                "</Styles>"+
                "<Worksheet ss:Name=\""+name+"\">"+
                    "<Table  x:FullColumns=\"1\" x:FullRows=\"1\" ss:DefaultColumnWidth=\"100\" ss:DefaultRowHeight=\"15\">"+
                        "<Row>");
        
    	//表头
        if(ArrayUtils.isNotEmpty(head)){
            for(String v:head){
            	sb.append("<Cell ss:StyleID=\"s1\"><Data ss:Type=\"String\">"+v+"</Data></Cell>");
            }
        }
        sb.append("</Row>");
        
        //表体
        for(Map<String, Object> row:body){
        	sb.append("<Row>");
        	for(String key : row.keySet()){
            	sb.append("<Cell ss:StyleID=\"s2\"><Data ss:Type=\"String\">"+row.get(key)+"</Data></Cell>");
            }
        	sb.append("</Row>");
        }

        sb.append("</Table>"+
                "</Worksheet>"+
            "</Workbook>");
        
        //浏览器下载
		response.setContentType("text/plain; charset=utf-8");
		response.setContentType("application/force-download");
		response.setContentType("application/octet-stream");
		response.setContentType("application/download");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "attachment; filename=\""+name+".xls\"");
        
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write(sb.toString());
		writer.flush();

    }
    
}
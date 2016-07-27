package com.zsl.web.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;



/**
 * 分页类
 * @author liujunqing
 * @param <T>
 */
public class Page<T> {
	
	private int pageNo = 1; // 当前页码
	private int pageSize = 20; // 页面大小，设置为“-1”表示不进行分页（分页无效）
	
	private long count;// 总记录数，设置为“-1”表示不查询总数
	
	private int first;// 首页索引
	private int last;// 尾页索引
	private int prev;// 上一页索引
	private int next;// 下一页索引
	
	private boolean firstPage;//是否是第一页
	private boolean lastPage;//是否是最后一页

	private int length = 7;// 显示页面长度
	private int slider = 1;// 前后显示页面长度
	
	private List<T> list = new ArrayList<T>();
	
	private String orderBy = ""; // 标准查询有效， 实例： updatedate desc, name asc
	
	private String funcName = "page"; // 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。
	
	private String message = ""; // 设置提示消息，显示在“共n条”之后
	
	private String hrefUrl = "";

	public Page() {
	}
	
	/**
	 * 构造方法
	 * @param pageNo 当前页码
	 * @param pageSize 分页大小
	 */
	public Page(int pageNo, int pageSize) {
		this(pageNo, pageSize, 0);
	}
	
	/**
	 * 构造方法
	 * @param pageNo 当前页码
	 * @param pageSize 分页大小
	 * @param count 数据条数
	 */
	public Page(int pageNo, int pageSize, long count) {
		this(pageNo, pageSize, count, new ArrayList<T>());
	}
	
	/**
	 * 构造方法
	 * @param pageNo 当前页码
	 * @param pageSize 分页大小
	 * @param count 数据条数
	 * @param list 本页数据对象列表
	 */
	public Page(int pageNo, int pageSize, long count, List<T> list) {
		this.setCount(count);
		this.setPageNo(pageNo);
		this.pageSize = pageSize;
		this.setList(list);
	}
	
	/**
	 * 初始化参数
	 */
	public void initialize(){
				
		//1
		this.first = 1;
		
		this.last = (int)(count / (this.pageSize < 1 ? 20 : this.pageSize) + first - 1);
		
		if (this.count % this.pageSize != 0 || this.last == 0) {
			this.last++;
		}

		if (this.last < this.first) {
			this.last = this.first;
		}
		
		if (this.pageNo <= 1) {
			this.pageNo = this.first;
			this.firstPage=true;
		}

		if (this.pageNo >= this.last) {
			this.pageNo = this.last;
			this.lastPage=true;
		}

		if (this.pageNo < this.last - 1) {
			this.next = this.pageNo + 1;
		} else {
			this.next = this.last;
		}

		if (this.pageNo > 1) {
			this.prev = this.pageNo - 1;
		} else {
			this.prev = this.first;
		}
		
		//2
		if (this.pageNo < this.first) {// 如果当前页小于首页
			this.pageNo = this.first;
		}

		if (this.pageNo > this.last) {// 如果当前页大于尾页
			this.pageNo = this.last;
		}
		
	}
	
	
	/**
	 * 获取分页HTML代码
	 * @return
	 */
	public String getHtml(){
		return toString();
	}
	

	/**
	 * 获取设置总数
	 * @return
	 */
	public long getCount() {
		return count;
	}
	
	/**
	 * 获取总页数
	 * 
	 * @return 总页数
	 */
	public int getTotalPages() {
		return (int) Math.ceil((double) getCount() / (double) getPageSize());
	}

	/**
	 * 设置数据总数
	 * @param count
	 */
	public void setCount(long count) {
		this.count = count;
		if (pageSize >= count){
			pageNo = 1;
		}
	}
	
	/**
	 * 获取当前页码
	 * @return
	 */
	public int getPageNo() {
		return pageNo;
	}
	
	/**
	 * 设置当前页码
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	/**
	 * 获取页面大小
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置页面大小（最大500）
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize <= 0 ? 10000 : pageSize;
	}

	/**
	 * 首页索引
	 * @return
	 */
	@JsonIgnore
	public int getFirst() {
		return first;
	}

	/**
	 * 尾页索引
	 * @return
	 */
	@JsonIgnore
	public int getLast() {
		return last;
	}
	
	/**
	 * 获取页面总数
	 * @return getLast();
	 */
	@JsonIgnore
	public int getTotalPage() {
		return getLast();
	}

	/**
	 * 是否为第一页
	 * @return
	 */
	@JsonIgnore
	public boolean isFirstPage() {
		return firstPage;
	}

	/**
	 * 是否为最后一页
	 * @return
	 */
	@JsonIgnore
	public boolean isLastPage() {
		return lastPage;
	}
	
	/**
	 * 上一页索引值
	 * @return
	 */
	@JsonIgnore
	public int getPrev() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	/**
	 * 下一页索引值
	 * @return
	 */
	@JsonIgnore
	public int getNext() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}
	
	/**
	 * 获取本页数据对象列表
	 * @return List<T>
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 设置本页数据对象列表
	 * @param list
	 */
	public Page<T> setList(List<T> list) {
		this.list = list;
		return this;
	}

	/**
	 * 获取查询排序字符串
	 * @return
	 */
	@JsonIgnore
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置查询排序，标准查询有效， 实例： updatedate desc, name asc
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 获取点击页码调用的js函数名称
	 * function ${page.funcName}(pageNo){location="${ctx}/list-${category.id}${urlSuffix}?pageNo="+i;}
	 * @return
	 */
	@JsonIgnore
	public String getFuncName() {
		return funcName;
	}

	/**
	 * 设置点击页码调用的js函数名称，默认为page，在一页有多个分页对象时使用。
	 * @param funcName 默认为page
	 */
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	/**
	 * 设置提示消息，显示在“共n条”之后
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 分页是否有效
	 * @return this.pageSize==-1
	 */
	@JsonIgnore
	public boolean isDisabled() {
		return this.pageSize==-1;
	}
	
	/**
	 * 是否进行总数统计
	 * @return this.count==-1
	 */
	@JsonIgnore
	public boolean isNotCount() {
		return this.count==-1;
	}
	
	/**
	 * 获取 Hibernate FirstResult
	 */
	public int getFirstResult(){
		initialize();
		int firstResult = (getPageNo() - 1) * getPageSize();
		if (firstResult >= getCount()) {
			firstResult = 0;
		}
		return firstResult;
	}
	
	public int getLastResult(){
		int lastResult = getFirstResult()+getMaxResults();
		if(lastResult>getCount()) {
			lastResult =(int) getCount();
		}
		return lastResult;
	}
	/**
	 * 获取 Hibernate MaxResults
	 */
	public int getMaxResults(){
		return getPageSize();
	}

	
	public String getHrefUrl() {
		return hrefUrl+"pageSize="+pageSize+"&pageNo=";
	}

	public void setHrefUrl(String hrefUrl) {
		this.hrefUrl = hrefUrl;
	}

	/**
	 * 默认输出当前分页标签 
	 * <div class="pagination">${page}</div>
	 */
	@Override
	public String toString() {

		initialize();
		
		StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"page_box\">\n");
	    if(pageNo > 1){
	    	sb.append("<a href=\"javascript:\" onclick=\""+funcName+"(1,"+pageSize+");\">首页</a>\n");
	    }
	    if(pageNo > 1){
	    	sb.append("<a href=\"javascript:\" onclick=\""+funcName+"("+prev+","+pageSize+");\">上一页</a>\n");
	    }
	    int begin = pageNo - (length / 2);

		if (begin < first) {
			begin = first;
		}

		int end = begin + length - 1;

		if (end >= last) {
			end = last;
			begin = end - length + 1;
			if (begin < first) {
				begin = first;
			}
		}
		for (int i = begin; i <= end; i++) {
			if (i == pageNo) {
				sb.append("<span class=\"on\">"+ i +"</span>\n");
			} else {
				sb.append("<a href=\"javascript:\" onclick=\""+funcName+"("+i+","+pageSize+");\">" + i + "</a>\n");
			}
		}
	    if(pageNo < getTotalPages()){
	    	sb.append("<a href=\"javascript:\" onclick=\""+funcName+"("+next+","+pageSize+");\">下一页</a>\n");
	    }
	    if(end < getTotalPages()){
	    	sb.append("<a href=\"javascript:\" onclick=\""+funcName+"("+getTotalPages()+","+pageSize+");\">尾页</a>\n");
	    }
	    //总页数
	    sb.append("<span class=\"total\">共 "+getTotalPages()+" 页</span>\n");
	    sb.append("<span class=\"to\">转到第 <input type=\"text\" value=\""+ pageNo +"\" class=\"n jump_page\" /> 页</span>\n");
	    sb.append("<span class=\"go\">GO</span>\n</div>\n");
		return sb.toString();
	}
	
	/**
	 * 显示链接
	 */
	public String getStringHref() {

		initialize();
		
		StringBuilder sb = new StringBuilder();
		String hrefUrl1 = "";
		sb.append("<div class=\"page_box\">\n");
	    if(pageNo > 1){
	    	hrefUrl1=getHrefUrl()+1;
	    	sb.append("<a href=\""+hrefUrl1+"\">首页</a>\n");
	    }
	    if(pageNo > 1){
	    	hrefUrl1=getHrefUrl()+prev;
	    	sb.append("<a href=\""+hrefUrl1+"\">上一页</a>\n");
	    }
	    int begin = pageNo - (length / 2);

		if (begin < first) {
			begin = first;
		}

		int end = begin + length - 1;

		if (end >= last) {
			end = last;
			begin = end - length + 1;
			if (begin < first) {
				begin = first;
			}
		}
		for (int i = begin; i <= end; i++) {
			if (i == pageNo) {
				sb.append("<span class=\"on\">"+ i +"</span>\n");
			} else {
				hrefUrl1=getHrefUrl()+i;
				sb.append("<a href=\""+hrefUrl1+"\">" + i + "</a>\n");
			}
		}
	    if(pageNo < getTotalPages()){
	    	hrefUrl1=getHrefUrl()+next;
	    	sb.append("<a href=\""+hrefUrl1+"\">下一页</a>\n");
	    }
	    if(end < getTotalPages()){
	    	hrefUrl1=getHrefUrl()+getTotalPages();
	    	sb.append("<a href=\""+hrefUrl1+"\">尾页</a>\n");
	    }
	    //总页数
	    sb.append("<span class=\"total\">共 "+getTotalPages()+" 页</span>\n");
	    sb.append("<span class=\"to\">转到第 <input type=\"text\" value=\""+ pageNo +"\" class=\"n jump_page\" /> 页</span>\n");
	    sb.append("<span class=\"go\">GO</span>\n</div>\n");
		return sb.toString();
	}
	/**
	 * 显示链接
	 */
	public String getStringHref2() {

		initialize();
		
		StringBuilder sb = new StringBuilder();
		String hrefUrl1 = "";
		sb.append("<div class=\"page_box\">\n");
	    if(pageNo > 1){
	    	hrefUrl1= hrefUrl+"1.html";
	    	sb.append("<a href=\""+hrefUrl1+"\">首页</a>\n");
	    }
	    if(pageNo > 1){
	    	hrefUrl1= hrefUrl+prev+".html";
	    	sb.append("<a href=\""+hrefUrl1+"\">上一页</a>\n");
	    }
	    int begin = pageNo - (length / 2);

		if (begin < first) {
			begin = first;
		}

		int end = begin + length - 1;

		if (end >= last) {
			end = last;
			begin = end - length + 1;
			if (begin < first) {
				begin = first;
			}
		}
		for (int i = begin; i <= end; i++) {
			if (i == pageNo) {
				sb.append("<span class=\"on\">"+ i +"</span>\n");
			} else {
				hrefUrl1= hrefUrl+i+".html";
				sb.append("<a href=\""+hrefUrl1+"\">" + i + "</a>\n");
			}
		}
	    if(pageNo < getTotalPages()){
	    	hrefUrl1= hrefUrl+next+".html";
	    	sb.append("<a href=\""+hrefUrl1+"\">下一页</a>\n");
	    }
	    if(end < getTotalPages()){
	    	hrefUrl1= hrefUrl+getTotalPages()+".html";
	    	sb.append("<a href=\""+hrefUrl1+"\">尾页</a>\n");
	    }
	    //总页数
	    sb.append("<span class=\"total\">共 "+getTotalPages()+" 页</span>\n");
	    sb.append("<span class=\"to\">转到第 <input type=\"text\" value=\""+ pageNo +"\" class=\"n jump_page\" /> 页</span>\n");
	    sb.append("<span class=\"go\">GO</span>\n</div>\n");
		return sb.toString();
	}
}

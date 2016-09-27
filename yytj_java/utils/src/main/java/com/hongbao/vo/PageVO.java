package com.hongbao.vo;

import java.util.List;

/**
 * JQUERY EASYUI 分页组件数据格式
 * 
 * @author 于东伟
 *
 * @param <T>
 *            泛型，单行数据对象类型
 */
public class PageVO<T> {

	/** 第几页 */
	private Integer page;
	/** 总行数 */
	private Integer total;
	/** 每页的行数 */
	private List<T> rows;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}

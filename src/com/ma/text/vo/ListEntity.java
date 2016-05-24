package com.ma.text.vo;

import java.util.ArrayList;

import com.ma.text.base.BaseEntity;


public class ListEntity<T> extends BaseEntity {

	private static final long serialVersionUID = 1090686338072092877L;

	/** * 数据条数 */
	private int total;
	/** * list中的参数类型 */
	private ArrayList<T> list;

	/** * 数据条数 */
	private int pageNo;
	/** * 数据条数 */
	private int pageCount;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public ArrayList<T> getList() {
		return list;
	}
	public void setList(ArrayList<T> list) {
		this.list = list;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	@Override
	public String toString() {
		return "ListEntity [total=" + total + ", list=" + list + ", pageNo="
				+ pageNo + ", pageCount=" + pageCount + "]";
	}
	
}
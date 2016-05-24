package com.ma.text.vo.db;

import com.ma.text.base.BaseEntity;
import com.ma.text.db.annotation.Column;
import com.ma.text.db.annotation.PrimaryKey;
import com.ma.text.db.annotation.TableName;

@SuppressWarnings("serial")
@TableName(name = "table_content")
public class ContentVo extends BaseEntity {
	/** 主键ID */
	@PrimaryKey(name = "_id", autoIncrement = true)
	private int id;
	
	@Column(name = "_type_id")
	private int type_id;

	@Column(name = "_title")
	private String title;
	@Column(name = "_content")
	private String content;

	@Column(name = "_createtime")
	private long createtime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}

}
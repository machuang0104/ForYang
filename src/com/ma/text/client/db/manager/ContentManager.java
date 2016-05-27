package com.ma.text.client.db.manager;

import java.util.ArrayList;
import java.util.List;

import com.ma.text.client.db.dao.ContentDao;
import com.ma.text.vo.db.ContentVo;

public class ContentManager {
	private ContentDao mTestDao;

	private static ContentManager mTestManager;

	public static ContentManager getInstance() {
		if (mTestManager == null) {
			mTestManager = new ContentManager();
		}

		return mTestManager;
	}

	private ContentManager() {
		mTestDao = new ContentDao();
	}

	/**
	 * 新增
	 * 
	 * @param entity
	 * @return
	 */
	public void insert(ContentVo entity) {
		mTestDao.insert(entity);
	}

	public void insertUpdate(ContentVo v) {
		mTestDao.insertUpdate(v);
	}

	public void delete(int id) {
		mTestDao.delete("_id = " + id);
	}

	/**
	 * 执行特定的sql1
	 * 
	 */
	public void setBoxStatus(int id, int status, int isOpen, int isEmpty) {
		String sql = "update table_box set _status = " + status + ", _is_open = " + isOpen + ", _is_empty = " + isEmpty
				+ " where _id = " + id;
		mTestDao.execute(sql);
	}

	/**
	 * 改
	 * 
	 * @param entity
	 * @return
	 */
	public boolean update(ContentVo entity) {
		return mTestDao.update(entity);
	}

	public synchronized ArrayList<ContentVo> findByTypeId(int typeId) {
		return (ArrayList<ContentVo>) mTestDao.query("_type_id = " + typeId);
	}

	public synchronized ContentVo findById(int id) {
		List<ContentVo> list = mTestDao.query("_id = " + id);
		if (list != null && list.size() != 0) {
			return list.get(0);
		} else
			return null;
	}
}
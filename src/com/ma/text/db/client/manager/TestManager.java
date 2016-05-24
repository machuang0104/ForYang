package com.ma.text.db.client.manager;

import java.util.ArrayList;
import java.util.List;

import com.ma.text.db.client.dao.TestDao;
import com.ma.text.vo.db.TestVo;

public class TestManager {
	private TestDao mTestDao;

	private static TestManager mTestManager;

	public static TestManager getInstance() {
		if (mTestManager == null) {
			mTestManager = new TestManager();
		}

		return mTestManager;
	}

	private TestManager() {
		mTestDao = new TestDao();
	}

	/**
	 * 新增
	 * 
	 * @param entity
	 * @return
	 */
	public void insert(TestVo entity) {
		mTestDao.insert(entity);
	}

	public void insert(ArrayList<TestVo> list) {
		mTestDao.insert(list);
	}

	public void insertUpdate(TestVo list) {
		mTestDao.insertUpdate(list);
	}

	public void insertUpdate(ArrayList<TestVo> list) {
		mTestDao.insertUpdate(list);
	}


	/**
	 * 执行特定的sql1
	 * 
	 */
	public void setBoxStatus(int id, int status, int isOpen, int isEmpty) {
		String sql = "update table_box set _status = " + status
				+ ", _is_open = " + isOpen + ", _is_empty = " + isEmpty
				+ " where _id = " + id;
		mTestDao.execute(sql);
	}

	/**
	 * 执行特定的sql2
	 * 
	 */
	public void setLockByCondition(int cabinetId, int startBoxNo, int endBoxNo,
			int isLock) {
		String sql = "update table_box set _is_lock = " + isLock
				+ " where _cabinet_id = "
				+ cabinetId + " and _box_no >= " + startBoxNo
				+ " and _box_no <=" + endBoxNo;
		mTestDao.execute(sql);
	}

	/**
	 * 改
	 * 
	 * @param entity
	 * @return
	 */
	public boolean update(TestVo entity) {
		return mTestDao.update(entity);
	}

	/**
	 * 删：sql仅补全where部分条件即可
	 * 
	 * @param cabinetId
	 * @param boardNo
	 */
	public void deleteByBoardNo(int cabinetId, int boardNo) {
		mTestDao.delete("_cabinet_id = " + cabinetId + " and _board_no = "
				+ boardNo);
	}

	public boolean deleteAll() {
		return mTestDao.deleteAll();
	}

	/**
	 * 查询：所有
	 * 
	 * @return
	 */
	public List<TestVo> findList() {
		return mTestDao.query(null);
	}

	/**
	 * 查询：补全where条件
	 */
	public List<TestVo> findByCabinetId(int cabinetId) {
		return mTestDao.query("_cabinet_id = " + cabinetId);
	}

	public TestVo findBoxByCondition(String cabinetNo, int boxNo) {
		List<TestVo> list = mTestDao.query("_cabinet_no = \"" + cabinetNo
				+ "\" and _box_no = " + boxNo);
		if (list.size() == 0) {
			return null;
		}

		return list.get(0);
	}

	public List<TestVo> findBoxByBoxNo(int cabinetId, int startBoxNo,
			int endBoxNo) {
		return mTestDao.query("_cabinet_id = " + cabinetId + " and _box_no >= "
				+ startBoxNo + " and _box_no <= " + endBoxNo);
	}

	/**
	 * 根据指定主键ID查询箱子信息
	 */
	public TestVo findBoxById(int id) {
		List<TestVo> list = mTestDao.query("_id = " + id);

		if (list.size() == 0) {
			return null;
		}

		return list.get(0);
	}

}
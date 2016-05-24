package com.ma.text.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * @title RegexUtil
 * @author libin
 * 
 */
public class RegexUtil {
	// 每位加权因子
	private int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	/**
	 * 邮箱校验
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 车牌校验
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isCard(String card) {
		String str = "^([\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5})$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(card);
		return m.matches();
	}

	/**
	 * 
	 * @title compareSex
	 * @description 根据 身份证获取 性别 (15位最后一位 基数: 男 ；偶数: 女) (18位倒数第二位 基数: 男 ；偶数: 女)
	 * @author libin
	 * @return
	 */
	public static int compareSex(String idCard) {
		int num = 3;
		if (idCard.length() == 15) {
			num = Integer.parseInt(idCard.substring(idCard.length() - 1));
			if ((num % 2) == 0) {
				return 0;
			} else {
				return 1;
			}
		} else if (idCard.length() == 18) {
			num = Integer.parseInt(idCard.substring(idCard.length() - 2,
					idCard.length() - 1));
			if ((num % 2) == 0) {
				return 0;
			} else {
				return 1;
			}
		}
		return 1;
	}

	/**
	 * 身高校验
	 * 
	 * @param height
	 * @return
	 */
	public static boolean isHeight(String height) {
		Pattern p = Pattern.compile("[0-9]{2,3}");
		Matcher m = p.matcher(height);
		return m.matches();
	}

	/**
	 * 手机号码校验
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(18[0,1,2,3,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 就诊卡校验
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMedicalCardNo(String medicalcard) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]{0,20}");
		Matcher m = p.matcher(medicalcard);
		return m.matches();
	}

	/**
	 * 密码校验
	 * 
	 * @param password
	 * @return
	 */
	public static boolean isValidatedPassword(String password) {
		Pattern p = Pattern.compile("[0-9a-zA-Z_]{6,16}");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	/**
	 * 
	 * @title isAddress
	 * @description 家庭住址校验
	 * @author libin
	 * @return
	 */
	public static boolean isAddress(String address) {
		if (Pattern.compile("[\u4e00-\u9fa5]{0,50}").matcher(address).matches())
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @title isUsername
	 * @description 姓名校验
	 * @author libin
	 * @return
	 */
	public static boolean isUsername(String username) {
		if (Pattern.compile("[\u4e00-\u9fa5]{2,7}").matcher(username).matches())
			return true;
		else
			return false;
	}

	/**
	 * 密码强度校验
	 * 
	 * @param password
	 * @return
	 */
	public static int passwordStrengh(String password) {
		int num = 0;
		if (Pattern.compile("[0-9]{6,16}").matcher(password).matches()) {
			num = 1;
		} else if (Pattern.compile("[a-z]{6,16}").matcher(password).matches()) {
			num = 1;
		} else if (Pattern.compile("[A-Z]{6,16}").matcher(password).matches()) {
			num = 1;
		} else if (Pattern.compile("[a-z0-9]{6,16}").matcher(password)
				.matches()) {
			num = 2;
		} else if (Pattern.compile("[A-Z0-9]{6,16}").matcher(password)
				.matches()) {
			num = 2;
		} else if (Pattern.compile("[A-Za-z]{6,16}").matcher(password)
				.matches()) {
			num = 2;
		} else if (Pattern.compile("[A-Za-z0-9]{6,16}").matcher(password)
				.matches()) {
			num = 3;
		}
		return num;
	}

	/**
	 * 身份证号码校验
	 * 
	 * @param idcard
	 * @return
	 */
	public boolean isValidatedAllIdcard(String idcard) {
		if (idcard.length() == 15) {
			idcard = convertIdcarBy15bit(idcard);
		}

		return isValidate18Idcard(idcard);
	}

	public String convertIdcarBy15bit(String idcard) {
		String idcard17 = null;
		// 非15位身份证
		if (idcard.length() != 15) {
			return null;
		}
		if (isDigital(idcard)) {
			// 获取出生年月日
			String birthday = idcard.substring(6, 12);
			Date birthdate = null;
			try {
				birthdate = new SimpleDateFormat("yyMMdd", Locale.getDefault())
						.parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cday = Calendar.getInstance();
			cday.setTime(birthdate);
			String year = String.valueOf(cday.get(Calendar.YEAR));

			idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);

			char c[] = idcard17.toCharArray();
			String checkCode = "";

			if (null != c) {
				int bit[] = new int[idcard17.length()];

				// 将字符数组转为整型数组
				bit = converCharToInt(c);
				int sum17 = 0;
				sum17 = getPowerSum(bit);

				// 获取和值与11取模得到余数进行校验码
				checkCode = getCheckCodeBySum(sum17);
				// 获取不到校验位
				if (null == checkCode) {
					return null;
				}

				// 将前17位与第18位校验码拼接
				idcard17 += checkCode;
			}
		} else { // 身份证包含数字
			return null;
		}
		return idcard17;
	}

	/**
	 * 15位和18位身份证号码的基本数字和位数验校
	 * 
	 * @param idcard
	 * @return
	 */
	public boolean isIdcard(String idcard) {
		return idcard == null || "".equals(idcard) ? false : Pattern.matches(
				"(^//d{15}$)|(//d{17}(?://d|x|X)$)", idcard);
	}

	public String getCheckCodeBySum(int sum17) {
		String checkCode = null;
		switch (sum17 % 11) {
		case 10:
			checkCode = "2";
			break;
		case 9:
			checkCode = "3";
			break;
		case 8:
			checkCode = "4";
			break;
		case 7:
			checkCode = "5";
			break;
		case 6:
			checkCode = "6";
			break;
		case 5:
			checkCode = "7";
			break;
		case 4:
			checkCode = "8";
			break;
		case 3:
			checkCode = "9";
			break;
		case 2:
			checkCode = "x";
			break;
		case 1:
			checkCode = "0";
			break;
		case 0:
			checkCode = "1";
			break;
		}
		return checkCode;
	}

	public int[] converCharToInt(char[] c) throws NumberFormatException {
		int[] a = new int[c.length];
		int k = 0;
		for (char temp : c) {
			a[k++] = Integer.parseInt(String.valueOf(temp));
		}
		return a;
	}

	public boolean isDigital(String str) {
		return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
	}

	public int getPowerSum(int[] bit) {

		int sum = 0;

		if (power.length != bit.length) {
			return sum;
		}

		for (int i = 0; i < bit.length; i++) {
			for (int j = 0; j < power.length; j++) {
				if (i == j) {
					sum = sum + bit[i] * power[j];
				}
			}
		}
		return sum;
	}

	public boolean isValidate18Idcard(String idcard) {
		// 非18位为假
		if (idcard.length() != 18) {
			return false;
		}
		// 获取前17位
		String idcard17 = idcard.substring(0, 17);
		// 获取第18位
		String idcard18Code = idcard.substring(17, 18);
		char c[] = null;
		String checkCode = "";
		// 是否都为数字
		if (isDigital(idcard17)) {
			c = idcard17.toCharArray();
		} else {
			return false;
		}

		if (null != c) {
			int bit[] = new int[idcard17.length()];

			bit = converCharToInt(c);

			int sum17 = 0;

			sum17 = getPowerSum(bit);

			// 将和值与11取模得到余数进行校验码判断
			checkCode = getCheckCodeBySum(sum17);
			if (null == checkCode) {
				return false;
			}
			// 将身份证的第18位与算出来的校码进行匹配，不相等就为假
			if (!idcard18Code.equalsIgnoreCase(checkCode)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isShengao(String height) {
		int lenght = height.length();
		if (lenght == 2) {
			if (Integer.parseInt(height.substring(0, 1)) < 6) {
				return false;
			} else {
				return true;
			}
		} else if (lenght == 3) {
			if (Integer.parseInt(height.substring(0, 1)) >= 3
					|| Integer.parseInt(height.substring(0, 1)) < 1) {
				return false;

			} else if (Integer.parseInt(height.substring(0, 1)) > 1
					&& Integer.parseInt(height.substring(1, 2)) > 4) {
				return false;
			} else {
				return true;
			}
		}

		return true;

	}

	// 根据身份证号码获得出生日期
	public static String getbirthday(String idcard) {
		int length = idcard.length();
		String idcardbirth = "";
		if (length != 0 && length > 0) {
			if (length == 15) {
				idcardbirth = idcard.substring(6, 12);
				idcardbirth = "19" + idcardbirth.substring(0, 2) + "-"
						+ idcardbirth.substring(2, 4) + "-"
						+ idcardbirth.substring(4);
				return idcardbirth;
			} else {
				idcardbirth = idcard.substring(6, 14);
				idcardbirth = idcardbirth.substring(0, 4) + "-"
						+ idcardbirth.substring(4, 6) + "-"
						+ idcardbirth.substring(6);
			}
			return idcardbirth;
		} else {
			return null;
		}

	}

	public static boolean isChinaOrEnglishName(String name) {
		String regx = "(([\u4E00-\u9FA5]{2,5})|([a-zA-Z]{3,10}))";
		if (Pattern.compile(regx).matcher(name).matches())
			return true;
		else
			return false;
	}

	public static boolean isChinaOrEnglishName1(String name) {
		String regx = "(([\u4E00-\u9FA5]{2,10})|([a-zA-Z]{3,20}))";
		if (Pattern.compile(regx).matcher(name).matches())
			return true;
		else
			return false;
	}

	// 中文名字
	public static boolean isChinaName(String name) {
		String regx = "[\u4E00-\u9FA5]{2,5}";
		if (Pattern.compile(regx).matcher(name).matches())
			return true;
		else
			return false;
	}

	// 英文名字
	public static boolean isEnglishName(String name) {
		String regx = "[a-zA-Z]{3,12}";
		if (Pattern.compile(regx).matcher(name).matches())
			return true;
		else
			return false;
	}

	// 过滤特殊字符

	public static boolean stingFilter(String ss) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		if (Pattern.compile(regEx).matcher(ss).matches())
			return true;
		else
			return false;

	}

	public static boolean isNickname(String ss) {
		String regEx = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{2,5}$";
		if (Pattern.compile(regEx).matcher(ss).matches())
			return true;
		else
			return false;

	}

	/**
	 * 隐藏手机号
	 * 
	 * @param str
	 * @return
	 */
	public static String toparsephonenum(String str) {
		return str.substring(0, str.length() - (str.substring(3)).length())
				+ "****" + str.substring(7);
	}

	/**
	 * 
	 * @title 四舍五入
	 * @description TODO
	 * @author libin
	 * @param num
	 * @return
	 */
	public static double toparsenum(double num) {
		return new java.math.BigDecimal(num).setScale(1,
				java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * @title isNetWorkConnected
	 * @description 检测网络是否可用
	 * @author libin
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}

		return false;
	}

	/**
	 * @title isExitsSdcard
	 * @description 检测Sdcard是否存在
	 * @author libin
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @title isExpressNumber
	 * @description 快递单号
	 * @author libin
	 * @param expressnumber
	 * @return
	 */
	public static boolean isExpressNumber(String expressnumber) {
		Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
		Matcher m = p.matcher(expressnumber);
		return m.matches();
	}

	/**
	 * 
	 * @title deleteLuanma
	 * @description 去除乱码
	 * @author libin
	 * @param textstr
	 * @return
	 */
	public static String deleteLuanma(String textstr) {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]|[\u4E00-\u9FA5]|[a-zA-Z0-9]";
		String newstr = "";
		for (int i = 0; i < textstr.length(); i++) {
			if (Pattern.compile(regEx).matcher(textstr.substring(i, i + 1))
					.matches()) {
				newstr = newstr + textstr.substring(i, i + 1);
			}

		}
		return newstr;

	}

	/**
	 * 
	 * @title getstrlength
	 * @description 判断字符串字符长度
	 * @author libin
	 * @param textstr
	 * @return
	 */

	public static int getstrlength(String textstr) {
		byte[] newstr = textstr.getBytes();
		return newstr.length;
	}

	/**
	 * 
	 * @title getDate
	 * @description 获取系统当前时间，并处理
	 * @author libin
	 * @return
	 */

	public static String getDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			month = "0" + month;
		}
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		if (day.length() == 1) {
			day = "0" + day;

		}
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));
		String secon = String.valueOf(c.get(Calendar.SECOND));
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins + ":" + secon);

		return sbBuffer.toString();
	}

	public static boolean StringIsOk(String str) {
		boolean flag = false;

		if (str != null && !"".equals(str)) {
			flag = true;
		}

		return flag;

	}

}
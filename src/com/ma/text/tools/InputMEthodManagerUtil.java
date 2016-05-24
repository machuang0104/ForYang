package com.ma.text.tools;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
/**
 * 
 * @author libin
 *
 */
public class InputMEthodManagerUtil {

	public static void disappear(Context context){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    	if(imm.isActive()){
    		if(((Activity) context).getCurrentFocus()!=null){
    			imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    		}
    	}
	}
}

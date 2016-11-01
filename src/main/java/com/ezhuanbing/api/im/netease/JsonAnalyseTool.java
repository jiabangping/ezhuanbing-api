package com.ezhuanbing.api.im.netease;

import net.sf.json.JSONObject;

public class JsonAnalyseTool {
	
	/**
	 * 
	* @Title: getJsonResultCode 
	* @Description: 检查返回值是否正常
	* @param value
	* @return boolean    返回类型 
	* @throws
	 */
	public static String getJsonResultCode(String value){
		
		JSONObject jsonResult = JSONObject.fromObject(value);
		return jsonResult.getString("code");
	}
	
	/**
	 * 
	* @Title: getJsonResult 
	* @Description: 返回请求的结果
	* @param value
	* @return String info信息
	* @throws
	 */
	public static String getJsonResult(String value){
		JSONObject jsonResult = JSONObject.fromObject(value);
		return jsonResult.getString("info");
	}
	
	/**
	 * 
	* @Title: getJsonResult 
	* @Description: 返回请求的结果
	* @param value
	* @return String    desc描述
	* @throws
	 */
	public static String getJsonDesc(String value){
		JSONObject jsonResult = JSONObject.fromObject(value);
		return jsonResult.getString("desc");
	}
	
	public static String getInfoByJsonCode(String code,String value){
	  try{
	    JSONObject jsonResult = JSONObject.fromObject(value);
        return jsonResult.getString(code);
	  }catch(Exception e){
	    return null;
	  }
		
	}
//	
//	public static void main(String[] args) {
//		System.out.println(getInfoByJsonCode("",""));
//	}
}

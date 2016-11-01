package ezhuanbing;

import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * 该类用于管理全局应用属性配置信息，以用于显示、提示、配置等信息的集中管理及国际化。
 * <p>Title: 全局应用属性配置信息管理类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author RickyLee
 * @version 1.0
 */
public class ApplicationResource
{
	//存放全局应用属性配置信息的名、值对
	private static TreeMap tmKeyValue = null;
    //资源配置文件的文件名
    private static final String APP_RESOURCE_FILENAME = "ApplicationResources";


	/**
	 * 读取Web应用下的WEB-INF\classes目录下的对应的资源文件中的应用属性配置信息。
	 * @param filenameNoExt String 不带扩展名.properties的文件名
	 * @return String
	 */
	private static String getApplicationResources(String filenameNoExt)
	{
		String name = null;
		String value = null;
		tmKeyValue = new TreeMap();

		PropertyResourceBundle configBundle = (PropertyResourceBundle)
											  ResourceBundle.getBundle(filenameNoExt);

		Enumeration keys = configBundle.getKeys();
		while (keys.hasMoreElements())
		{
			name = keys.nextElement().toString();
			//value = StringUtil.getStringISO8859_1(configBundle.getString(name));
			
			value = getStringISO8859_1(configBundle.getString(name));
			
			//value = configBundle.getString(name);
			tmKeyValue.put(name,value);
		}

        //查看系统资源配置情况
        //System.out.println(tmKeyValue);

		return value;
	}
	
	static String getStringISO8859_1(String src) {
		
		return src;
	}

	/**
	 * 从资源配置文件中根据键名取出其对应的值。
	 * @param key String 要取的资源标识名称
	 * @return String 根据资源标识名称从资源文件取得的值
	 */
	public static String getValueByKey(String key)
	{
		if (tmKeyValue == null)
			getApplicationResources(APP_RESOURCE_FILENAME);

        Object objTmp = tmKeyValue.get(key);

        if (objTmp == null)
            return "";
        else
            return objTmp.toString();
	}

	public static void main(String[] args)
	{
        String strNew = getValueByKey("driver");
        System.out.println(strNew);
        strNew = getValueByKey("url");
		System.out.println(strNew);
		//System.out.println();
		//System.out.println(tmKeyValue);
	}
}
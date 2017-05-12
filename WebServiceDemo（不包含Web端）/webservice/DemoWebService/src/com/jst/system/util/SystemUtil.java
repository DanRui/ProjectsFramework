package com.jst.system.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.utils.page.Page;
import com.jst.config.ObjectSerializeConfig;
import com.jst.constant.Message;
import com.jst.handler.MessageHandler;
import com.jst.serializer.ObjectSerializer;
import com.jst.test.model.TestModel;
import com.jst.type.DataType;

/**
 * 
 * @author Administrator
 *
 */
public class SystemUtil {
	
	private static final Log log= LogFactory.getLog(SystemUtil.class);
	
	/**
	 * 初始化查询条件及分页的序列化配置
	 * @return
	 */
	public static ObjectSerializeConfig initQuerySerializeConfig(){
		ObjectSerializeConfig serializeConfig = new ObjectSerializeConfig();
		serializeConfig.setObjectAlias(Page.class, "page");
		serializeConfig.setObjectAlias(List.class, "filters");
		serializeConfig.setObjectAlias(PropertyFilter.class, "propertyFilter");
		serializeConfig.setExcludeField(Page.class, "result");
		serializeConfig.setRemoveClass(false);
		return serializeConfig;
	}
	
	
	/**
	 * 序列化分页Page对象
	 * @param messageHandler
	 * @param dataType
	 * @param serializeConfig
	 * @return
	 */
	public  static Page serializePage(MessageHandler messageHandler, String dataType ,ObjectSerializeConfig serializeConfig){
		String pageString = null;
		
		if (DataType.valueOf(dataType).equals(DataType.XML)) {
			pageString = "<page>" + messageHandler.getParamValue(Message.HEAD_XML_PATH + "/" + "page") + "</page>";
		} else {
			pageString = "{\"page\":" + messageHandler.getParamValue(Message.HEAD_JSON_PATH + "." + "page") + "}";
		}
		log.debug("IBusinessService_设置对象序列化信息");
	
		log.debug("IBusinessService_反序列化Page对象");
		Page<TestModel> page = ObjectSerializer.getInstance(
				DataType.valueOf(dataType), serializeConfig).deserialize(
				Page.class, pageString);
		log.debug("IBusinessService_反序列化List<PropertyFilter>对象");
		
	
		
		return page;
	}
	
	/**
	 * 序列化查询条件 List<PropertyFilter>对象
	 * @param messageHandler
	 * @param dataType
	 * @param serializeConfig
	 * @return
	 */
	public static List<PropertyFilter> serializeFilter(MessageHandler messageHandler,String dataType ,ObjectSerializeConfig serializeConfig){
		String filterString = null;
		if (DataType.valueOf(dataType).equals(DataType.XML)) {
			filterString = "<filters>" + messageHandler.getParamValue(Message.HEAD_XML_PATH + "/" + "filters") + "</filters>";
		} else {
			filterString = "{\"filters\":" + messageHandler.getParamValue(Message.HEAD_JSON_PATH + "." + "filters") + "}";
		}
		
		List<PropertyFilter> filters = null;
		/*if("debug".equals(SystemConstants.CURRENT_MODEL)){
			filters = ObjectSerializer.getInstance(
					DataType.valueOf(dataType), serializeConfig).deserialize(
					List.class, filterString);
		}else{*/
			List lista = ObjectSerializer.getInstance(
					DataType.valueOf(dataType), serializeConfig).deserialize(
					List.class, filterString);
			if(null!=lista && !lista.isEmpty()){
				filters = (ArrayList<PropertyFilter>)lista.get(0);
			}else{
				filters = new ArrayList<PropertyFilter>();
			}
		/*}*/
		return filters;
	}
	
	
	  /**  
     * 通过HttpServletRequest返回IP地址  
     * @param request HttpServletRequest  
     * @return ip String  
     * @throws Exception  
     */    
    public static String getIpAddr(HttpServletRequest request) throws Exception {    
        String ip = request.getHeader("x-forwarded-for");    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("WL-Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_CLIENT_IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getRemoteAddr();    
        }    
        return ip;    
    }    
    
    
    public static String callCmd(String[] cmd) { 
        String result = ""; 
        String line = ""; 
        try { 
          Process proc = Runtime.getRuntime().exec(cmd); 
          InputStreamReader is = new InputStreamReader(proc.getInputStream()); 
          BufferedReader br = new BufferedReader (is); 
          while ((line = br.readLine ()) != null) { 
          result += line; 
          } 
        } 
        catch(Exception e) { 
          e.printStackTrace(); 
        } 
        return result; 
      }
		    
		/** 
		* 
		* @param cmd 第一个命令 
		* @param another 第二个命令 
		* @return  第二个命令的执行结果 
		*/
		public static String callCmd(String[] cmd,String[] another) { 
		  String result = ""; 
		  String line = ""; 
		  try { 
			Long currentTime = System.currentTimeMillis();
		    Runtime rt = Runtime.getRuntime(); 
		    Process proc =  proc = rt.exec(another); //rt.exec(cmd); 
		    //proc.waitFor(); //已经执行完第一个命令，准备执行第二个命令
		    System.out.println("user time is :"+(System.currentTimeMillis()-currentTime));
		   
		    InputStreamReader is = new InputStreamReader(proc.getInputStream()); 
		    BufferedReader br = new BufferedReader (is); 
		    while ((line = br.readLine ()) != null) { 
		        result += line; 
		    } 
		    
		    System.out.println("user time is :"+(System.currentTimeMillis()-currentTime));
		  } 
		  catch(Exception e) { 
		    e.printStackTrace(); 
		  } 
		  return result; 
		}
		
		/** 
		* 
		* @param ip 目标ip,一般在局域网内 
		* @param sourceString 命令处理的结果字符串 
		* @param macSeparator mac分隔符号 
		* @return mac地址，用上面的分隔符号表示 
		*/
		public static String filterMacAddress(final String ip, final String sourceString,final String macSeparator) { 
		  String result = ""; 
		  String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})"; 
		  Pattern pattern = Pattern.compile(regExp); 
		  Matcher matcher = pattern.matcher(sourceString); 
		  while(matcher.find()){ 
		    result = matcher.group(1); 
		    if(sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) { 
		        break; //如果有多个IP,只匹配本IP对应的Mac. 
		    } 
		  }
		
		  return result; 
		}
		/** 
		* 
		* @param ip 目标ip 
		* @return  Mac Address 
		* 
		*/
		public static String getMacInWindows(final String ip){ 
		  String result = ""; 
		  String[] cmd = { 
		        "cmd", 
		        "/c", 
		        "ping " + ip 
		        }; 
		  String[] another = { 
		        "cmd", 
		        "/c", 
		        "arp -a"
		        }; 
		
		  String cmdResult = callCmd(cmd,another); 
		  result = filterMacAddress(ip,cmdResult,"-"); 
		
		  return result; 
		} 

		/** 
		* @param ip 目标ip 
		* @return  Mac Address 
		* 
		*/
		public static String getMacInLinux(final String ip){ 
		  String result = ""; 
		  String[] cmd = { 
		        "/bin/sh", 
		        "-c", 
		        "ping " + ip + " -c 2 && arp -a"
		        }; 
		  String cmdResult = callCmd(cmd); 
		  result = filterMacAddress(ip,cmdResult,":"); 
		
		  return result; 
		} 
		/**
		* 获取MAC地址 
		* @return 返回MAC地址
		*/
		public static String getMacAddress(String ip){
		  String macAddress = "";
		  macAddress = getMacInWindows(ip).trim();
		  if(macAddress==null||"".equals(macAddress)){
		    macAddress = getMacInLinux(ip).trim();
		  }
		  return macAddress;
		}
		//做个测试
		public static void main(String[] args) { 
		  System.out.println(getMacAddress("111.13.101.208"));
		}
}

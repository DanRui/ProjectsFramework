package com.jst.common.shiro.interceptor;

import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jst.common.json.WriterUtil;
import com.jst.common.springmvc.MainAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.demo.login.web.LoginAction;
import com.jst.system.SystemConstants;
import com.jst.util.JsonUtil;
import com.jst.util.RequestUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ControllerInterceptor extends HandlerInterceptorAdapter implements Serializable{

	private static final long serialVersionUID = -4431222295384819382L;

	private static final String UN_LOGIN = "当前尚未登陆或会话已失效，请登陆后再进行操作";
	
	//日志
	private static final Log log = LogFactory.getLog(ControllerInterceptor.class);
	
	private static final String LACK_PERMISSION = "缺失当前操作所需权限";
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		/*super.postHandle(request, response, handler, modelAndView);*/
	
	}

	private static final String OPE_ERR_INFO = "系统异常，当前操作失败";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Subject subject = SecurityUtils.getSubject();
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Class action = handlerMethod.getBeanType();
		Method currentMethod = handlerMethod.getMethod();
		String methodName = currentMethod.getName();
		if(handlerMethod.getBean() instanceof LoginAction || handlerMethod.getBean() instanceof MainAction){
			return true;
		}else{
			String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath();
			if(!subject.isAuthenticated()){
				
				if (RequestUtil.isSynchronized(request)) {// 不需要
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html;charset=UTF-8");
	
					PrintWriter writer = response.getWriter();
	
					String script = "";
					
					script += "<script type='text/javascript'>";
					script += "window.alert('当前尚未登陆或会话已失效，请登陆后再进行操作');";
					script += "window.location.href='" + url+"/login.jsp" + "';";
					script += "</script>";
	
					writer.print(script);
					writer.flush();
	
				} else {
					// 判断是不是list页面
					if (methodName.indexOf("list") > -1 || methodName.indexOf("List") > -1) {
						JSONObject json = new JSONObject();
						json.accumulate("success", false);
						json.accumulate("message", UN_LOGIN);
						json.accumulate("total", 0);
						json.accumulate("rows", new JSONArray());
						json.accumulate("url", url+"/login.jsp");
						WriterUtil.writeJSONString(response, json.toString());
					} else {
						response.setCharacterEncoding("UTF-8");
						response.setContentType("text/html;charset=UTF-8");
	
						PrintWriter writer = response.getWriter();
	
						String script = "";
						
						script += "<script type='text/javascript'>";
						script += "window.alert('当前尚未登陆或会话已失效，请登陆后再进行操作');";
						script += "window.location.href='" + url+"/login.jsp" + "';";
						script += "</script>";
	
						writer.print(script);
						writer.flush();
					}
				}
					return false;
			}
		}
		
		
		Privilege pri = currentMethod.getAnnotation(Privilege.class);
		if(pri!=null){
			Hashtable<String, Object> loginInfo = (Hashtable<String, Object>) subject.getSession().getAttribute("LOGIN_INFO");
			if(null==loginInfo || loginInfo.isEmpty()){
				log.debug("获取用户信息失败，当前用户尚未登陆或会话已失效");
				
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				
				PrintWriter writer = response.getWriter();
				
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				
				String requestUrl = request.getRequestURL().toString();
				
				String script = "";
				
				if(basePath.length() == requestUrl.indexOf("/")){
					script += "<script type='text/javascript'>";
					script += "window.alert('当前尚未登陆或会话已失效，请登陆后再进行操作');";
					script += "window.location.href='index.jsp';";
					script += "</script>";
				}else if(basePath.length() == requestUrl.indexOf("/index.jsp")){
					script += "<script type='text/javascript'>";
					script += "window.alert('当前尚未登陆或会话已失效，请登陆后再进行操作');";
					script += "window.location.href='userLogin_logout.action';";
					script += "</script>";
				}

				writer.print(script);
				writer.flush();
				
				log.debug("此次操作拦截成功，返回登录页面");
				
				return false;
			}
			String userCode = loginInfo.get("USER_CODE").toString();
			String userName = loginInfo.get("USER_NAME").toString();
			log.debug("当前用户代码：" + userCode + ", 用户名称："+ userName);
			String modelCode = pri.modelCode();
			String prvgCode = pri.prvgCode();
			log.debug("当前访问action实例的方法对应模块：" + modelCode);
			log.debug("当前访问action实例的方法对应权限：" + prvgCode);
			
			boolean hasPrvg = subject.isPermitted(modelCode+":"+prvgCode);
			if(!hasPrvg){
				log.debug("用户权限不足，无法访问当前action实例方法");
				response.setCharacterEncoding("UTF-8");
				
				JSONObject json = new JSONObject();
				json.accumulate("success", true);
				json.accumulate("message", "权限不足");
				WriterUtil.writeJSONString(response, json.toString());
				
				log.debug("此次操作拦截成功");
				
				return false;
			}
			
		}else{
			return super.preHandle(request, response, handlerMethod);
		}
		/*Annotation [] annotations =method.getAnnotations();
		Privilege p = method.getAnnotation(Privilege.class);*/		
		return super.preHandle(request, response, handler);
	}

}

package com.jst.test.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.model.BaseModel;
import com.jst.common.service.CacheService;
import com.jst.common.utils.page.Page;
import com.jst.config.ObjectSerializeConfig;
import com.jst.constant.Message;
import com.jst.handler.MessageHandler;
import com.jst.serializer.ObjectSerializer;
import com.jst.system.util.SystemUtil;
import com.jst.test.model.TestModel;
import com.jst.test.service.TestService;
import com.jst.type.DataType;
import com.jst.util.JsonUtil;
import com.jst.util.MessageHandlerUtil;

@Service("testWebService")
public class TestWebService {
	
	private static final Log log = LogFactory.getLog(TestWebService.class);
	
	@Resource(name = "testServiceImpl")
	private TestService testService;
	
	@Resource(name = "cacheService")
	private CacheService  cacheService;
	
	/**
	 * 测试查询列表
	 * @param clientId
	 * @param clientPwd
	 * @param requestStr
	 * @param dataType
	 * @return
	 */
	@RequiresPermissions(logical= Logical.AND, value= {"M_TEST_MANAGER:QUERY"})
	public String list(String clientId, String clientPwd,String sessionInfo , String terminalInfo, String requestStr,String dataType){
		cacheService.getMenu();
		log.debug("TestWebService list is start");
		MessageHandler messageHandler = null;
		try {
			messageHandler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType), requestStr);
			
			log.debug("设置反序列化配置");
			ObjectSerializeConfig serializeConfig = SystemUtil.initQuerySerializeConfig();
			Page page = SystemUtil.serializePage(messageHandler, dataType, serializeConfig);
			List<PropertyFilter> filters = SystemUtil.serializeFilter(messageHandler, dataType, serializeConfig);
			
			//开始分页查询
			page = testService.getPage(page, filters);
			
			//将查询数据开始进行返回
			ObjectSerializeConfig serializeConfig2 = new ObjectSerializeConfig();
			messageHandler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
			messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_SUCCESS);
			messageHandler.addHeadParam(Message.RET_MSG_NAME, "数据查询成功");
			serializeConfig2.setObjectAlias(Page.class, Message.RECORD_NAME);
			serializeConfig2.setObjectAlias(TestModel.class , "testModel");
			log.debug("TestWebService_设置身体返回信息，序列化对象");
			messageHandler.addBodyParam(page, serializeConfig2);
		} catch (Exception e) {
			if(null == messageHandler){
				messageHandler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
			}
			messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_FAILURE);
			messageHandler.addHeadParam(Message.RET_MSG_NAME, "数据查询失败");
			log.error("TestWebService list is Error:"+e,e);
		}
		log.debug("TestWebService list is end");
		return messageHandler.generateResponseMessage();
	}
	
	/**
	 * 测试新增数据
	 * @param clientId
	 * @param clientPwd
	 * @param id
	 * @param name
	 * @param scription
	 * @param dataType
	 * @return
	 */
	@RequiresPermissions(logical= Logical.AND, value= {"M_TEST_MANAGER:ADD"})
	public String save(String clientId, String clientPwd ,String sessionInfo , String terminalInfo,TestModel testModel ,String dataType) {
		MessageHandler messageHandler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
		log.debug("TestWebService save is save start");
		try {
			Serializable returnId = testService.save(testModel);
			System.out.println("save id is " + returnId);
			messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_SUCCESS);
			messageHandler.addHeadParam(Message.RET_MSG_NAME, "数据插入成功");
		} catch (Exception e) {
			messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_FAILURE);
			messageHandler.addHeadParam(Message.RET_MSG_NAME, "数据插入失败");
			log.error("TestWebService save is Error:"+e,e);
		}
		log.debug("TestWebService save is end");
		return messageHandler.generateResponseMessage();
	}
	
	/**
	 * 测试修改数据
	 * @param clientId
	 * @param clientPwd
	 * @param id
	 * @param name
	 * @param scription
	 * @param dataType
	 * @return
	 */
	@RequiresPermissions(logical= Logical.AND, value= {"M_TEST_MANAGER:UPDATE"})
	public String update(String clientId, String clientPwd,String sessionInfo , String terminalInfo, TestModel testModel,String dataType){
		MessageHandler messageHandler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
		log.debug("TestWebService update is start");
		try {
			BaseModel bm = testService.update(testModel);
			//testService.awww();
			messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_SUCCESS);
			messageHandler.addHeadParam(Message.RET_MSG_NAME, "更新数据成功");
		} catch (Exception e) {
			messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_FAILURE);
			messageHandler.addHeadParam(Message.RET_MSG_NAME, "更新数据失败"+e);
			log.error("TestWebService update is Error:"+e,e);
		}
		log.debug("TestWebService update is end");
		return messageHandler.generateResponseMessage();
	}
	
	/**
	 * 测试删除数据
	 * @param clientId
	 * @param clientPwd
	 * @param id
	 * @param dataType
	 * @return
	 */
	@RequiresPermissions(logical= Logical.AND, value= {"M_TEST_MANAGER:DELETE"})
	public String delete(String clientId, String clientPwd ,String sessionInfo , String terminalInfo, int id, String dataType){
		MessageHandler messageHandler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
		log.debug("TestWebService delete is start");
		try {
			Serializable deleteId = testService.delete(id);
			messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_SUCCESS);
			messageHandler.addHeadParam(Message.RET_MSG_NAME, "删除数据成功");
		} catch (Exception e) {
			messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_FAILURE);
			messageHandler.addHeadParam(Message.RET_MSG_NAME, "删除数据失败");
			log.error("TestWebService delete is Error:"+e,e);
		}
		log.debug("TestWebService delete is end");
		return messageHandler.generateResponseMessage();
	}
	
	/**
	 * 测试查看数据
	 * @param clientId
	 * @param clientPwd
	 * @param id
	 * @param dataType
	 * @return
	 */
	public String view(String clientId, String clientPwd ,String sessionInfo , String terminalInfo, Integer id, String dataType){
		MessageHandler messageHandler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
		log.debug("TestWebService view is start");
		try {
			BaseModel baseModel = testService.get(id);
			if(null!=baseModel){
				ObjectSerializeConfig serializeConfig = new ObjectSerializeConfig();
				serializeConfig.setObjectAlias(TestModel.class, Message.RECORD_NAME);
				messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_SUCCESS);
				messageHandler.addHeadParam(Message.RET_MSG_NAME, "查看数据成功");
				messageHandler.addBodyParam(baseModel, serializeConfig);
			}else{
				messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_SUCCESS);
				messageHandler.addHeadParam(Message.RET_MSG_NAME, "测试查看数据失败");
			}
		} catch (Exception e) {
			messageHandler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_SUCCESS);
			messageHandler.addHeadParam(Message.RET_MSG_NAME, "测试查看数据失败");
			log.error("TestWebService view is Error:"+e,e);
		}
		
		log.debug("TestWebService view is end");
		return messageHandler.generateResponseMessage();
	}

	public String test(String clientId,String clientPwd,String sessionInfo , String terminalInfo,List<TestModel> list,String dataType){
		
		System.out.println("bbb");
		return "";
	}
	
	@RequiresPermissions(logical= Logical.AND ,value = {"M_TEST_MANAGER:VIEW"})
	public String test2(String clientId, String clientPwd, String sessionInfo , String terminalInfo,TestModel model ,String dataType){
		try {
			testService.update(model,"name");
		} catch (Exception e) {
			log.error("TestWebService teste is Error:"+e,e);
		}

		return "";
	}
	
}

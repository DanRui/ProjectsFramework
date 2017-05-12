package com.jst.test.webservice;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.model.BaseModel;
import com.jst.common.utils.page.Page;
import com.jst.config.ObjectSerializeConfig;
import com.jst.constant.Message;
import com.jst.handler.MessageHandler;
import com.jst.system.util.SystemUtil;
import com.jst.test.model.ClassRecord;
import com.jst.test.model.TestModel;
import com.jst.test.service.ClassRecordService;
import com.jst.type.DataType;
import com.jst.util.MessageHandlerUtil;

@Service("classRecordWebService")
public class ClassRecordWebService{
	
	private final static Log log = LogFactory.getLog(ClassRecordWebService.class);
	
	@Resource(name = "classRecordServiceImpl")
	private ClassRecordService service;
	
	
	/**
	 * 查询电子教学日志
	 * @param clientId
	 * @param clientPwd
	 * @param requestStr
	 * @param dataType
	 * @return
	 * @throws Exception
	 */
	public String list(String clientId, String clientPwd ,String sessionInfo , String terminalInfo, String requestStr,String dataType) throws Exception{
		log.debug("ClassRecordWebService list is start");
		MessageHandler handler = null;
		try {
			handler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType), requestStr);
			ObjectSerializeConfig serializeConfig = SystemUtil.initQuerySerializeConfig();
			Page page = SystemUtil.serializePage(handler, dataType, serializeConfig);
			List<PropertyFilter> filters = SystemUtil.serializeFilter(handler, dataType, serializeConfig);
			page = service.getPage(page, filters, true);
			
			//将查询数据开始进行返回
			ObjectSerializeConfig serializeConfig2 = new ObjectSerializeConfig();
			handler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
			handler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_SUCCESS);
			handler.addHeadParam(Message.RET_MSG_NAME, "数据查询成功");
			serializeConfig2.setObjectAlias(Page.class, Message.RECORD_NAME);
			serializeConfig2.setObjectAlias(ClassRecord.class , "classRecord");
			log.debug("TestWebService_设置身体返回信息，序列化对象");
			handler.addBodyParam(page, serializeConfig2);
		} catch (Exception e) {	
			if(null == handler){
				handler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
			}
			handler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_FAILURE);
			handler.addHeadParam(Message.RET_MSG_NAME, "数据查询失败");
			log.error("ClassRecordWebService list is Error:"+e,e);
		}
		log.debug("ClassRecordWebService list is end");
		return handler.generateResponseMessage();
	}
	
	/**
	 * 添加电子教学日志
	 * @param clientId
	 * @param clientPwd
	 * @param inscode
	 * @param stunum
	 * @param coachnum
	 * @param carnum
	 * @param simnum
	 * @param recnum
	 * @param subjcode
	 * @param photo1
	 * @param photot2
	 * @param photo3
	 * @param starttime
	 * @param endtime
	 * @param duration
	 * @param mileage
	 * @param avevelocity
	 * @param total
	 * @param part1
	 * @param part2
	 * @param part3
	 * @param part4
	 * @param classid
	 * @param dataType
	 * @return
	 */
	public String save(String clientId, String clientPwd, String sessionInfo , String terminalInfo, ClassRecord classrecord,String dataType) {
		MessageHandler handler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
		log.debug("ClassRecordWebService save is start");
		try {
			/**
			 * ,String inscode,String stunum,String coachnum, 
					   String carnum,String simnum,String recnum,String subjcode,String photo1,String photot2,
					   String photo3,String starttime,String endtime,Integer duration,double mileage,double avevelocity,
					   Integer total ,Integer part1,Integer part2, Integer part3,Integer part4 ,Integer classid,
			 */
		} catch (Exception e) {
			log.error("ClassRecordWebService save is Error:"+e,e);
		}
		log.debug("ClassRecordWebService save is end");
		return handler.generateResponseMessage();
	}
	
	
	/**
	 * 电子教学日志查看
	 * @param clientId
	 * @param clientPwd
	 * @param id
	 * @param dataType
	 * @return
	 */
	public String view(String clientId, String clientPwd ,String sessionInfo , String terminalInfo, Integer id, String dataType){
		MessageHandler handler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
		log.debug("ClassRecordWebService view is start");
		try {
			BaseModel classRecord = service.get(id);
			if(null!=classRecord){
				ObjectSerializeConfig serializeConfig = new ObjectSerializeConfig();
				serializeConfig.setObjectAlias(ClassRecord.class, Message.RECORD_NAME);
				handler.addBodyParam(classRecord, serializeConfig);
				handler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_SUCCESS);
				handler.addHeadParam(Message.RET_MSG_NAME, "数据查看成功");
			}else{
				handler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_FAILURE);
				handler.addHeadParam(Message.RET_MSG_NAME, "数据查看失败");
			}
		} catch (Exception e) {
			handler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_FAILURE);
			handler.addHeadParam(Message.RET_MSG_NAME, "数据查看失败");
			log.error("ClassRecordWebService view is Error:"+e,e);
		}
		log.debug("ClassRecordWebService view is end");
		return handler.generateResponseMessage();
	}
	
	public String delete(String clientId, String clientPwd ,String sessionInfo , String terminalInfo,Integer id, String dataType){
		log.debug("ClassRecordWebService delete is start");
		MessageHandler handler = MessageHandlerUtil.getMessageHandler(DataType.valueOf(dataType));
		try {
			handler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_SUCCESS);
			handler.addHeadParam(Message.RET_MSG_NAME, "删除成功");
		} catch (Exception e) {
			handler.addHeadParam(Message.RET_CODE_NAME, Message.RET_CODE_FAILURE);
			handler.addHeadParam(Message.RET_MSG_NAME, "删除数据失败");
			log.error("ClassRecordWebservice delete is Error:"+e,e);
		}
		
		log.debug("ClassRecordWebService delete is end");
		return handler.generateResponseMessage();
	}
}

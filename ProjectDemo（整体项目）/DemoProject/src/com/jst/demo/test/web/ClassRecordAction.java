package com.jst.demo.test.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.PathParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.model.BaseModel;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.common.utils.page.Page;
import com.jst.demo.test.service.ClassRecordService;
import com.jst.util.JsonUtil;
import com.jst.util.StringUtil;

/**
 * 电子教学日志操作
 * @author Administrator
 *
 */
@RequestMapping("/classrecord")
@Controller
public class ClassRecordAction extends BaseAction {
	
	
	private static final Log log = LogFactory.getLog(ClassRecordAction.class);

	
	@Resource(name = "classRecordServiceImpl")
	private ClassRecordService service;
	
	/**
	 * 电子教学日志查询
	 * @param pageNo 当前页
	 * @param pageSize  每页显示条数
	 * @param order		排序方式
	 * @param orderBy	排序字段
	 * @param starttime	开始时间
	 * @param endtime	结束时间
	 * @param carnum	车牌号码
	 * @param idcard	学员身份证号码
	 * @param part		培训部分
	 * @param project	培训项目
	 * @param coachname	教练姓名
	 * @return
	 * @
	 */
	@Privilege(modelCode  = "M_CLASS_RECORD_MANAGER", prvgCode = "QUERY" )
	@ResponseBody
	@RequestMapping(value = "list", consumes="application/x-www-form-urlencoded; charset=UTF-8")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
			   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
			   @RequestParam(value="order", defaultValue="DESC")String order, 
			   @RequestParam(value="sort", defaultValue="id")String orderBy,String inscode,String starttime,String endtime,String stuname,String carnum,String idcard,String part,String project,String carcode) {
		log.debug("ClassRecordAction list is start");
		Page page= new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		if(StringUtil.isNotEmpty(inscode)){
			filters.add(new PropertyFilter("EQS_inscode",inscode));
		}
		if(StringUtil.isNotEmpty(starttime)){
			filters.add(new PropertyFilter("GED_starttime",starttime));
		}
		
		if(StringUtil.isNotEmpty(endtime)){
			filters.add(new PropertyFilter("LED_starttime",endtime));
		}
		
		if(StringUtil.isNotEmpty(carnum)){
			filters.add(new PropertyFilter("LIKES_carnum",carnum));
		}
		
		if(StringUtil.isNotEmpty(idcard)){
			filters.add(new PropertyFilter("LIKES_idcard",idcard));
		}
		if(StringUtil.isNotEmpty(part)){
			filters.add(new PropertyFilter("EQS_part",part));
		}
		
		if(StringUtil.isNotEmpty(project)){
			filters.add(new PropertyFilter("EQS_project",project));
		}
		if(StringUtil.isNotEmpty(carcode)){
			filters.add(new PropertyFilter("LIKES_carcode",carcode));
		}
		if(StringUtil.isNotEmpty(stuname)){
			filters.add(new PropertyFilter("LIKES_stuname" , stuname));
		}		
		String returnStr = "";
		try {
			page = service.getPage(page, filters, true);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("ClassRecordAction list is Error:"+e,e);
		}
		log.debug("ClassRecordAction list is end");
		return returnStr;
	}
	
	/**
	 * 电子教学日志查看
	 * @param id
	 * @param type
	 * @return
	 */
	@Privilege(modelCode  = "M_CLASS_RECORD_MANAGER", prvgCode = "VIEW" )
	@RequestMapping("view")
	public ModelAndView view(@PathParam("id") Integer id,@PathParam(value = "type") String type) {
		String view = "CLASSRECORD.VIEW";
		log.debug("ClassRecordAction view is start");
		if(StringUtil.isNotEmpty(type)&&"update".equals(type)){
			view = "CLASSRECORD.UPDATE";
		}
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		try {
			BaseModel classRecord = service.get(id);
			mv.addObject("v", classRecord);
		} catch (Exception e) {
			log.error("ClassRecordAction view is error:"+e,e);
		}
		log.debug("ClassRecordAction view is end");
		return mv;
	}

	/**
	 * 进行逻辑删除
	 * @param id
	 * @return
	 * @
	 */
	@Privilege(modelCode = "M_CLASS_RECORD_MANAGER", prvgCode = "DELETE")
	@ResponseBody
	@RequestMapping("delete")
	public String delete(@RequestParam(value = "id") Integer id) {
		return "";
	}
}

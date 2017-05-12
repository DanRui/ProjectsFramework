package com.jst.demo.test.web;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.jst.common.hibernate.PropertyFilter;
import com.jst.common.model.BaseModel;
import com.jst.common.service.CacheService;
import com.jst.common.springmvc.BaseAction;
import com.jst.common.system.annotation.Privilege;
import com.jst.common.utils.page.Page;
import com.jst.demo.test.model.TestModel;
import com.jst.demo.test.service.TestService;
import com.jst.system.wrapper.XssRequestWrapper;
import com.jst.util.JsonUtil;
import com.jst.util.StringUtil;

@RequestMapping("/test")
@Controller
public class TestAction extends BaseAction{
	
	
	private static final Log log = LogFactory.getLog(TestAction.class);
	
	
	@Resource(name = "testServiceImpl")
	private TestService testService;
	
	@Resource(name = "cacheService")
	private CacheService cacheService;
	
	
	/**
	 * 测试进行查询数据
	 */
	@RequestMapping("list")
	//@Privilege(modelCode = "aaa" ,prvgCode = "bbb")
	@ResponseBody
	@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "QUERY")
	public String list(@RequestParam(value="page", defaultValue="1")int pageNo, 
					   @RequestParam(value="rows", defaultValue="10")Integer pageSize,
					   @RequestParam(value="order", defaultValue="DESC")String order, 
					   @RequestParam(value="sort", defaultValue="id")String orderBy, String name, String scription,HttpServletResponse response){
		/*cacheService.getMenu();*/
		List<PropertyFilter> list = new ArrayList<PropertyFilter>();
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setOrder(order);
		page.setOrderBy(orderBy);
		String returnStr = "";
		if(StringUtil.isNotEmpty(name)){
			list.add(new PropertyFilter("LIKES_name",name));
		}
		if(StringUtil.isNotEmpty(scription)){
			list.add(new PropertyFilter("LIKES_scription",scription));
		}
		try {
			page = testService.getPage(page, list, false);
			returnStr = writerPage(page);
		} catch (Exception e) {
			log.error("TestAction list is Error:" + e, e);
		}
		log.debug("TestAction list is end");
	    return returnStr;
	}
	
	
	@ResponseBody
	@RequestMapping("test_list")
	public String list(){
		
		try {
			List<TestModel> ls = testService.queryBySql();
			
			System.out.println("asdasdasd-------"+ls.size());
		} catch (Exception e) {
			log.error("e"+e,e);
		}
		
		return "ccc";
	}
	
	
	@ResponseBody
	@RequestMapping("test_list2")
	public String list2(){
		
		try {
			List<TestModel> ls = testService.queryByHql();
			
			System.out.println("asdasdasd-------"+ls.size());
		} catch (Exception e) {
			log.error("e"+e,e);
		}
		
		return "ccc";
	}
	
	/**
	 * 测试进行保存数据
	 */
	@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "ADD")
	@ResponseBody
	@RequestMapping(value = "save" )
	public String save(@RequestParam("id")Integer id,@RequestParam("name")String name, @RequestParam("scription")String scription){
		log.debug("TestAction save is start");
		TestModel testModel = new TestModel();
		testModel.setId(id);
		testModel.setName(name);
		testModel.setScription(scription);
		boolean saveOk = false;
		try {
			Serializable returnId = testService.save(testModel);
			if(null != returnId){
				saveOk = true;
			}
		} catch (Exception e) {
			log.error("TestAction save is Error:"+e,e);
		}
		
		log.debug("TestAction save is End");
		if(saveOk){
			return JsonUtil.toSuccessMsg("保存成功");
		}else
		return JsonUtil.toErrorMsg("保存失败");
	}
	
	/**
	 * 测试进行修改数据
	 */
	@Privilege(modelCode = "M_TEST_MANAGER",prvgCode = "UPDATE")
	@ResponseBody
	@RequestMapping("update")
	public String update(Integer id,String name, String scription){
		log.debug("TestAction update is start");
		TestModel testModel = new TestModel();
		testModel.setId(id);
		testModel.setName(name);
		testModel.setScription(scription);
		boolean updateOk = false;
		try {
			BaseModel bm = testService.update(testModel);
			updateOk = true;
		} catch (Exception e) {
			log.error("TestAction update is error:" + e , e);
		}
		
		log.debug("TestAction update is end");
		if(updateOk){
			return JsonUtil.toSuccessMsg("修改成功");
		}else{
			return JsonUtil.toErrorMsg("修改失败");
		}
	}
	
	/**
	 * 测试进行删除数据
	 */
	@ResponseBody
	@RequestMapping("delete")
	@Privilege(modelCode = "M_TEST_MANAGER" ,prvgCode = "DELETE")
	public String delete(Integer id) {
		log.debug("TestAction delete is start");
		boolean deleteOk = false;
		try {
			Serializable deleteId = testService.delete(id);
			deleteOk = true;
		} catch (Exception e) {
			log.error("TestAction delete is Error:"+e,e);
		}
		
		log.debug("TestAction delete is end");
		if(deleteOk){
			return JsonUtil.toSuccessMsg("删除成功");
		}else{
			return JsonUtil.toErrorMsg("删除失败");
		}
	}
	
	/**
	 * 测试进行文件上传
	 */
	@ResponseBody
	@RequestMapping("fileUpload")
	public String fileUpload(HttpServletRequest request) {
		log.debug("TestAction fileUpload is start");
		boolean fileUploadOk = false;
		try {
			ShiroHttpServletRequest shiroRequest = null;
			if(request instanceof XssRequestWrapper){
				shiroRequest = (ShiroHttpServletRequest)((XssRequestWrapper) request).getRequest();
			}
			 long  startTime=System.currentTimeMillis();
	         //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
	        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
	                request.getSession().getServletContext());
	        //检查form中是否有enctype="multipart/form-data"
	        if(multipartResolver.isMultipart(request))
	        {
	            //将request变成多部分request
	        	 CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();  
	            MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());  
	           //获取multiRequest 中所有的文件名
	            Iterator iter=multipartRequest.getFileNames();
	            while(iter.hasNext())
	            {
	                //一次遍历所有文件
	                MultipartFile file=multipartRequest.getFile(iter.next().toString());
	                if(file!=null)
	                {
	                    String path="E:/springUpload"+file.getOriginalFilename();
	                    //上传
	                    file.transferTo(new File(path));
	                }
	            }
	        }
	        long  endTime=System.currentTimeMillis();
	        System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
	        fileUploadOk = true;
		} catch (Exception e) {
			log.error("TestAction fileUpload is error:"+e,e);
		}
		log.debug("TestAction fileUpload is end");
		
		if(fileUploadOk){
			return JsonUtil.toSuccessMsg("文件上传成功！");
		}else{
			return JsonUtil.toErrorMsg("文件上传失败");
		}
        
	}
	
	/**
	 * 测试进行查看操作
	 */
	@Privilege(modelCode = "M_TEST_MANAGER", prvgCode = "VIEW")
	@RequestMapping("view")
	public ModelAndView View(@PathParam("id")Integer id, @PathParam(value = "type")String type) {
		log.debug("TestAction view is start");
		String view = "TEST.VIEW";
		if(StringUtil.isNotEmpty(type)&&"update".equals(type)){
			view = "TEST.UPDATE";
		}
		ModelAndView mv = new ModelAndView(getReturnPage(view));
		try {
			BaseModel baseModel = testService.get(id);
			mv.addObject("v",baseModel);
		} catch (Exception e) {
			log.error("TestAction view is error:" +e,e);
		}
		log.debug("TestAction view is start");
		return mv;
	}
	
	
	/**
	 * 测试进行文件下载
	 */
	@RequestMapping("fileDown")
	public String fileDown(String fileName ,HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/force-download");// 设置强制下载不打开
		   response.addHeader("Content-Disposition",
		    "attachment;fileName=" + fileName);// 设置文件名
		   File file = new File(fileName);
		   byte[] buffer = new byte[1024];
		   FileInputStream fis = null;
		   BufferedInputStream bis = null;
		   try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
				    os.write(buffer, 0, i);
				    i = bis.read(buffer);
				}
		   } catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				   } finally {
				if (bis != null) {
				    try {
				 bis.close();
				    } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				    }
				}
				if (fis != null) {
				    try {
				 fis.close();
				    } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				    }
				}
		   }
		   
		   return null;
	}
	
}

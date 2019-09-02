package com.SpringBoot.main.controller.admin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.SpringBoot.main.page.admin.Page;
import com.SpringBoot.main.pojo.admin.User;
import com.SpringBoot.main.service.admin.RoleService;
import com.SpringBoot.main.service.admin.UserService;

@Controller
@RequestMapping("admin/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("list")
	public ModelAndView list(ModelAndView model){
		Map<String, Object> query=new HashMap<String, Object>();
		
		model.addObject("roleList",roleService.findList(query));
		
		model.setViewName("user/list");
		return model;
	}
	
	@RequestMapping(value = "/list2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(Page page,
			@RequestParam(name = "username", required = false, defaultValue = "") String username,
			@RequestParam(name = "roleId", required = false) Long roleId,
			@RequestParam(name = "sex", required = false) Long sex
			) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", username);
		queryMap.put("roleId", roleId);
		queryMap.put("sex", sex);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", userService.findList(queryMap));
		ret.put("total", userService.getTotal(queryMap));
		return ret;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public Map<String, String> add(User user) {
		Map<String, String> map = new HashMap<String, String>();
		if (user == null) {
			map.put("msg", "请输入相关信息！");
			map.put("type", "error");
			return map;
		}
		if (user.getUsername().isEmpty()) {
			map.put("msg", "请输入用户名！");
			map.put("type", "error");
			return map;
		}
		
		
		if (user.getPassword().isEmpty()) {
			map.put("msg", "请输入密码！");
			map.put("type", "error");
			return map;
		}
		if (user.getRoleId()==null) {
			map.put("msg", "请选择所属角色！");
			map.put("type", "error");
			return map;
		}
		
		if(isExit(user.getUsername(), 0l)) {
			map.put("msg", "已存在的用户名！");
			map.put("type", "error");
			return map;
		}
		
		if (userService.add(user) <= 0) {
			map.put("msg", "添加失败，请联系管理员！");
			map.put("type", "error");
			return map;
		}
		map.put("msg", "添加成功！");
		map.put("type", "success");
		return map;
	}
	
	private  boolean isExit(String username,Long id) {
		User user = userService.findUserByUsername(username);
		
		if(user==null) 
			return false;
		if(user.getId().longValue()==id.longValue())
			return false;
		return true;
	}
	
	
	@RequestMapping("edit")
	@ResponseBody
	public Map<String, String> edit(User user) {
		Map<String, String> map = new HashMap<String, String>();
		if (user == null) {
			map.put("msg", "请输入相关信息！");
			map.put("type", "error");
			return map;
		}
		if (user.getUsername().isEmpty()) {
			map.put("msg", "请输入用户名！");
			map.put("type", "error");
			return map;
		}
		if (user.getRoleId()==null) {
			map.put("msg", "请选择所属角色！");
			map.put("type", "error");
			return map;
		}
		if(isExit(user.getUsername(), user.getId())) {
			map.put("msg", "已存在的用户名！");
			map.put("type", "error");
			return map;
		}
		if (userService.edit(user) <= 0) {
			map.put("msg", "修改失败，请联系管理员！");
			map.put("type", "error");
			return map;
		}
		map.put("msg", "修改成功！");
		map.put("type", "success");
		return map;
	}
	
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, String> delete(String ids) {
		Map<String, String> map = new HashMap<String, String>();
		if (ids == null) {
			map.put("msg", "请选择要删除的数据！");
			map.put("type", "error");
			return map;
		}
	if(ids.contains(",")) {
		ids=ids.substring(0,ids.length()-1);
	}
		
		if (userService.delete(ids) <= 0) {
			map.put("msg", "删除失败，请联系管理员！");
			map.put("type", "error");
			return map;
		}
		map.put("msg", "修改成功！");
		map.put("type", "success");
		return map;
	}
	
	
	@RequestMapping("upload_photo")
	@ResponseBody
	public Map<String,Object> upload(MultipartFile photo,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		if(photo==null) {
			map.put("msg", "请选择文件上传！");
			map.put("type", "error");
			return map;
		}
		if(photo.getSize()>1024*1024*10)
		{
			map.put("msg", "选择的文件不能超过10M！");
			map.put("type", "error");
			return map;
		}
		//获取文件后缀
		String suffix=photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+1,photo.getOriginalFilename().length());
		if(!"jpg,jepg,png,gif".toUpperCase().contains(suffix.toUpperCase())) {
			map.put("msg", "请上传后缀为jpg,jepg,png,gif的文件");
			map.put("type", "error");
			return map;
		}
		//保存图片的目录
		String savePath=request.getServletContext().getRealPath("/")+"/resources/upload/";
		
		
		File file=new File(savePath);
		if(!file.exists()) {
			//如果目录不存在，创建文件
			file.mkdir();
		}
		
		String fileName=UUID.randomUUID()+"."+suffix;
		try {
			photo.transferTo(new File(savePath+fileName));
		} catch (Exception e) {
			map.put("msg", "头像上传失败，请联系管理员");
			map.put("type", "error");
			e.printStackTrace();
			return map;
		} 
		map.put("msg", "头像上传成功");
		map.put("type", "success");
		map.put("filepath",request.getServletContext().getContextPath() + "/resources/upload/" + fileName );
		return map;
	}
}

package com.SpringBoot.main.interceptor.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.SpringBoot.main.pojo.admin.Menu;
import com.SpringBoot.main.utils.MenuUtil;
import com.github.pagehelper.util.StringUtil;

//import net.sf.json.JSONObject;


public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		Object admin = request.getSession().getAttribute("admin");
		
		
		if(admin==null) {
			//表示没有登录或者登录超时
			String header = request.getHeader("X-Requested-With");
			if(header!=null&&header.equals("XMLHttpRequest")) {
				Map<String, String> map=new HashMap<String, String>();
				map.put("msg", "登录超时或没有登录，请重新登录!");
				map.put("type","error");
				//response.getWriter().write(JSONObject.fromObject(admin).toString());
				return false;
			}
			response.sendRedirect(request.getContextPath()+"/system/login");
		return false;
		
		}
		String mid = request.getParameter("_mid");
		if(!StringUtil.isEmpty(mid)) {
			List<Menu> list =MenuUtil.getThirdMenu((List<Menu>)request.getSession().getAttribute("userMenus"), Long.valueOf(mid));
			request.setAttribute("thirdMenus", list);
		}
		
		return true;
	}

}

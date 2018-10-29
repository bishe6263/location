/**
 * 
 */
package com.Freerun.Contoraler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.Freerun.Entity.User;
import com.Freerun.Service.IUserService;
import com.Freerun.untilbean.Result;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
/**
 * @author wuzhiyong
 * @creation 2018楠烇拷7閺堬拷3閺冿拷
 * @DeptContoraler.java
 * @閻ц缍嶉幒褍鍩楅崳锟�
 */
@SessionAttributes(value={"user","randnumt"})
@Controller
public class UserContoraler {
	@Autowired
	IUserService userService;
	
		@RequestMapping(value="/login", produces = "text/json;charset=UTF-8")
		@ResponseBody
		public String loginCheck(User user, ModelMap model){
			User users=new User();
			Boolean rs=false;
			if(userService.login(user).size()>0) {
				users=userService.login(user).get(0);
				rs=true;
			}
			String jsonStr = null;
			if(rs){
				jsonStr = Result.getJsonObject(true, "", "登录成功", users);
				model.addAttribute("user", users);
			}else{
				jsonStr = Result.getJsonObject(false, "", "登录失败", null);
			}	
			System.out.println(jsonStr);
			return jsonStr;
		}
	
	@RequestMapping(value="/register", produces = "text/json;charset=UTF-8")//
	@ResponseBody
	public String register(User user,ModelMap model,@RequestParam("randnum") String randnum,
			@ModelAttribute("randnumt") String randnumt){
		String jsonStr = null;
		if(randnum.equals(randnumt)){
			userService.save(user);
			jsonStr = Result.getJsonObject(true, "", "注册成功", user);
		}else{
			jsonStr = Result.getJsonObject(false, "","验证码错误", null);
		}	
		System.out.println(jsonStr);
		return jsonStr;
	}
	@RequestMapping(value="/foudrandnum", produces = "text/json;charset=UTF-8")//
	@ResponseBody
	public String foudrandnum(User user,ModelMap model){
		String jsonStr = null;
		String result = GetMessage.getResult(user.getPhone());
		System.out.println(result);
		model.addAttribute("randnumt", result);
		jsonStr = Result.getJsonObject(true, "", "发送成功", null);
		System.out.println(jsonStr);
		return jsonStr;
	}
	@RequestMapping(value="/getuser", produces = "text/json;charset=UTF-8")//
	@ResponseBody
	public String getuser(HttpServletRequest request,ModelMap model){
		String jsonStr = null;
		if(request.getSession().getAttribute("user")==null) {
			jsonStr = Result.getJsonObject(false, "", "用户已过期，请重新登陆", null);
		}else{
			User users=(User) request.getSession().getAttribute("user");
			jsonStr = Result.getJsonObject(true, "", "获取成功", users);
		}
		System.out.println(jsonStr);
		return jsonStr;
	}
	@RequestMapping(value="/updateuser", produces = "text/json;charset=UTF-8") //  
	@ResponseBody
	public String updateuser(User user,HttpServletRequest request,Model model){
		System.out.println("RequestMapping allhangqing");
		String jsonStr = null;
		if(request.getSession().getAttribute("user")==null) {
			jsonStr = Result.getJsonObject(false, "", "用户已过期，请重新登陆", null);
		}else {
			User users=(User) request.getSession().getAttribute("user");
			users.setPassword(user.getPassword());
			users.setPic(user.getPic());
			users.setEmail(user.getEmail());
			users.setName(user.getName());
			userService.update(user);
			jsonStr = Result.getJsonObject(true, "", "修改成功", users);
		}
		return jsonStr;
	}
	@RequestMapping(value="/updateUser_password")//
	public String updateUser_password(HttpServletRequest request,
			@RequestParam("password1") String password1,
			@RequestParam("passwords") String password,
			@RequestParam("password2") String password2,Model model){
		if(request.getSession().getAttribute("user")==null) {
			return "user/userlogin";	
		}else {
			User user=(User) request.getSession().getAttribute("user");
			if(password==""||password1==""||password2=="") {
				model.addAttribute("message", "鐎靛棛鐖滄稉铏光敄");
			}else if(!user.getPassword().equals(password)) {
				model.addAttribute("message", "閸樼喎鐦戦惍渚�鏁婄拠锟�");
			}else if(!password1.equals(password2)) {
				model.addAttribute("message", "娑撱倖顐肩�靛棛鐖滄稉宥勭閼凤拷");
			}else {
				user.setPassword(password1);
				userService.update(user);
				model.addAttribute("message", "娣囶喗鏁肩�靛棛鐖滈幋鎰");
				model.addAttribute("user",user);
			}
			return "user/user_update_password_message";	
		}
	}
	@RequestMapping(value="/updateUser_email") //
	public String updateUser_email(User user,HttpServletRequest request,Model model){
		System.out.println("RequestMapping allhangqing");
		if(request.getSession().getAttribute("user")==null) {
			return "user/user";	
		}else {
			User users=(User) request.getSession().getAttribute("user");
			System.out.println(user);
			users.setEmail(user.getEmail());
			userService.update(users);
			model.addAttribute("user", users);
			return "user/user_update_email";
		}
	}
	@RequestMapping(value="/removeUser") //
	public String removeUser(HttpServletRequest request,SessionStatus se){
		System.out.println("RequestMapping allhangqing");
		request.getSession().removeAttribute("user");
		se.setComplete();
		return "user/userlogin";
	}
}

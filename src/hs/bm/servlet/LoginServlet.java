package hs.bm.servlet;

import hs.bm.bean.SysOrgInfo;
import hs.bm.bean.SysOrgUsrInfo;
import hs.bm.bean.SysUsrUsrInfo;
import hs.bm.control.ControlServices;
import hs.bm.dao.ContactDao;
import hs.bm.dao.StructMgrDao;
import hs.bm.dao.UserDao;
import hs.bm.util.ConfigInfo;
import hs.bm.util.GetMacAndNetCard;
import hs.bm.vo.ResObj;
import hs.bm.vo.SysUsrPassRole;
import hs.bm.vo.SysUsrUsrInfoVo;

import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public LoginServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		ResObj ro = new ResObj();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		SysUsrPassRole user=UserDao.getInstance().doLogin(username, password);
		if(user.getUsr_name()!=null&&user.getUsr_name()!=""){
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			session.setAttribute("username", username);
			session.setAttribute("manage_id", user.getDepartmentId());
			ro.setObj(1);
		}else{
			ro.setObj(0);
		}
		ro.ToJsp(response);
		return;
	}
}

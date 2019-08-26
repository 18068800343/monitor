package cn.org.hsxx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.org.hsxx.bean.BrgCardAdminId;
import cn.org.hsxx.dao.BrgCardDao;


public class BrgCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public BrgCardServlet() {
		super();
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		ArrayList<BrgCardAdminId> list = BrgCardDao.getInstance().getBrigedName();
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		jo.put("list", list);
		out.write(jo.toString());
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

package hs.bm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hs.bm.bean.BasicData;
import hs.bm.bean.TestPoint;
import hs.bm.dao.BasicDataDao;
import hs.bm.vo.ResObj;

public class MonitorIndexServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	
	public MonitorIndexServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String type = req.getParameter("type");
		ResObj ro = new ResObj();
		if(type.equals("getDataManageFl"))
		{
			List<BasicData> list = BasicDataDao.getInstance().getBasicDataByType("1");
			if (list.size()>0)
			{
				ro.setSuccess("success");
				ro.setObj(list);
				ro.setError(0);
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(resp);
		}
		if(type.equals("selectTdCodeByBrgId"))
		{
			
			String brgId = req.getParameter("brgId");
			List<TestPoint> list = BasicDataDao.getInstance().getTdCodeByBrgId(brgId);
			if (list.size()>0)
			{
				ro.setSuccess("success");
				ro.setObj(list);
				ro.setError(0);
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(resp);
		}
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req, resp);
	}
	
	
}

package hs.bm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import hs.bm.bean.BasicData;
import hs.bm.bean.TestPoint;
import hs.bm.dao.BasicDataDao;
import hs.bm.dao.BrgMonitorDao;
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
		/*
		 * req.setCharacterEncoding("UTF-8"); resp.setCharacterEncoding("UTF-8");
		 */
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

		if(type.equals("getBrgMonTypes"))
		{
			resp.setContentType("text/html;charset=utf-8");
			
			String brgId = req.getParameter("bridgeId");
			BrgMonitorDao brgMonitorDao = new BrgMonitorDao();
			List<Map> maps = new ArrayList();
			Map map = new HashMap<String, Object>();
			String json = "";
			try {
				maps = brgMonitorDao.getBrgMonitorMsgById(brgId);
				map = brgMonitorDao.getBrgMsgById(brgId);
				map.put("brgMonTypes",maps);
				json = JSONObject.toJSONString(map);
				json = new String(json.getBytes(),"UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ro.setError(1);
				ro.setSuccess("fail");
			}
			if (json!=null&&!"".equals(json))
			{
				ro.setSuccess("success");
				ro.setObj(map);
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

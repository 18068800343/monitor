package cn.org.hsxx.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.hsxx.bean.BasicData;
import cn.org.hsxx.bean.BasicDataJclx;
import cn.org.hsxx.bean.BasicDataStandard;
import cn.org.hsxx.dao.BasicDataDao;
import cn.org.hsxx.dao.TestPointDao;
import hs.bm.bean.SysOrgInfo;
import hs.bm.vo.ResObj;

public class BasicDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public BasicDataServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
		response.addHeader( "Cache-Control", "no-store");
		response.addHeader( "Cache-Control", "must-revalidate");
		ResObj ro = new ResObj();
		ro.setSuccess("fail");
		ro.setError(1);
		if (type.equals("selectAllBasicjclx")) {
			List<BasicDataJclx> ll=BasicDataDao.getInstance().selectAllBasicjclx();
			if (ll == null){
				ro.setError(2);
				ro.setSuccess("fail");
			} else if (ll.size() > 0){
				ro.setError(0);
				ro.setSuccess("success");
				ro.setObj(ll);
			} else{
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("selectBridJclx")) {
			String bridge_id = request.getParameter("bridge_id");
			List<BasicDataJclx> ll=BasicDataDao.getInstance().selectAllBasicjclx(bridge_id);
			if (ll == null){
				ro.setError(2);
				ro.setSuccess("fail");
			} else if (ll.size() > 0){
				ro.setError(0);
				ro.setSuccess("success");
				ro.setObj(ll);
			} else{
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("selectAllBasic")) {
			String bridge_id = request.getParameter("bridge_id");
			List<BasicData> ll=BasicDataDao.getInstance().selectAllBasic(bridge_id);
			if (ll == null){
				ro.setError(2);
				ro.setSuccess("fail");
			} else if (ll.size() > 0){
				ro.setError(0);
				ro.setSuccess("success");
				ro.setObj(ll);
			} else{
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("saveBasicData")) {
			String name = request.getParameter("name");
			String bdstype = request.getParameter("bdstype");
			int i = BasicDataDao.getInstance().saveBasicData(name, bdstype);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("updateBasicData")) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			int i = BasicDataDao.getInstance().updateBasicData(id, name);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("deleteBasicData")) {
			String id = request.getParameter("id");
			int i = BasicDataDao.getInstance().deleteBasicData(id);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("selectBsicData")) {
			String bdstype = request.getParameter("bdstype");
			List<BasicData> ll=BasicDataDao.getInstance().selectBsicData(bdstype);
			if (ll == null){
				ro.setError(2);
				ro.setSuccess("fail");
			} else if (ll.size() > 0){
				ro.setError(0);
				ro.setSuccess("success");
				ro.setObj(ll);
			} else{
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("saveBasicDatajclx")) {
			String name = request.getParameter("name");
			int i = BasicDataDao.getInstance().saveBasicDataJclx(name);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("selectBasicData")) {
			List<BasicData> ll=BasicDataDao.getInstance().selectAllBasic();
			if (ll == null){
				ro.setError(2);
				ro.setSuccess("fail");
			} else if (ll.size() > 0){
				ro.setError(0);
				ro.setSuccess("success");
				ro.setObj(ll);
			} else{
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("updateBasicDataJclx")) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
//			String basic_data_id = request.getParameter("basic_data_id");
			int i = BasicDataDao.getInstance().updateBasicDataJclx(id, name);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("deleteBasicDataJclx")) {
			String id = request.getParameter("id");
			int i = BasicDataDao.getInstance().deleteBasicDataJclx(id);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("saveBasicDataStandard")) {
			String basic_data_id = request.getParameter("basic_data_id");
			String bridge_id = request.getParameter("bridge_id");
			String standard = request.getParameter("standard");
			int i = BasicDataDao.getInstance().saveBasicDataStandard(bridge_id, basic_data_id, standard);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("selectBasicStandard")) {
			String bridge_id = request.getParameter("bridge_id");
			List<BasicDataStandard> ll=BasicDataDao.getInstance().selectBasicStandard(bridge_id);
			if (ll == null){
				ro.setError(2);
				ro.setSuccess("fail");
			} else if (ll.size() > 0){
				ro.setError(0);
				ro.setSuccess("success");
				ro.setObj(ll);
			} else{
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("updateBasicDataStandard")) {
			String id = request.getParameter("id");
			String standard = request.getParameter("standard");
			String basic_data_id = request.getParameter("basic_data_id");
			int i = BasicDataDao.getInstance().updateBasicDataStandard(id, standard,basic_data_id);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("deleteBasicDataStandard")) {
			String id = request.getParameter("id");
			int i = BasicDataDao.getInstance().deleteBasicDataStandard(id);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

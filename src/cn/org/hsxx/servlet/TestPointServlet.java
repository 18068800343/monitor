package cn.org.hsxx.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.SystemOutLogger;

import cn.org.hsxx.bean.BasicDataJclx;
import cn.org.hsxx.bean.TestPoint;
import cn.org.hsxx.dao.TestPointDao;
import hs.bm.bean.SysOrgInfo;
import hs.bm.vo.ResObj;

public class TestPointServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public TestPointServlet() {
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
		if (type.equals("select_Test")) {
			String bridge_id = request.getParameter("bridge_id");
			String cd_f_type_id = request.getParameter("cd_f_type_id");
			List<TestPoint> list = TestPointDao.getInstance().getTestPointData(bridge_id, cd_f_type_id);
			for (int i = 0; i < list.size(); i++) {
			}
			if (list.size()>0) {
				ro.setError(0);
				ro.setObj(list);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("save_Test")) {
			TestPoint tp = new TestPoint();
			String cd_code = request.getParameter("cd_code");
			String td_code = request.getParameter("td_code");
			String fz = request.getParameter("fz");
			String cd_describe = request.getParameter("cd_describe");
			String cypl = request.getParameter("cypl");
			String sbxh = request.getParameter("sbxh");
			BasicDataJclx bdj = new BasicDataJclx();
			bdj.setId(request.getParameter("id"));
			bdj.setName(request.getParameter("name"));
			String bridge_id = request.getParameter("bridge_id");
			String s_id = request.getParameter("s_id");
			String r_id = request.getParameter("r_id");
			String cd_span_no = request.getParameter("cd_span_no");
			String if_jihuo = request.getParameter("if_jihuo");
			String fz2 = request.getParameter("fz2");
			String xmd = request.getParameter("xmd");
			String zysc = request.getParameter("zysc");
			tp.setCd_code(cd_code);
			tp.setTd_code(td_code);
			tp.setFz(fz);
			tp.setCd_describe(cd_describe);
			tp.setCypl(cypl);
			tp.setSbxh(sbxh);
			tp.setBridge_id(bridge_id);
			tp.setBdj(bdj);
			tp.setS_id(s_id);
			tp.setR_id(r_id);
			tp.setCd_span_no(cd_span_no);
			tp.setIf_jihuo(if_jihuo);
			tp.setFz2(fz2);
			tp.setXmd(xmd);
			tp.setZysc(zysc);
			int i = TestPointDao.getInstance().saveTestPoint(tp);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("update_Test_jihuo")) {
			String id = request.getParameter("id");
			String if_jihuo = request.getParameter("if_jihuo");
			int i = TestPointDao.getInstance().updateBasicData(id, if_jihuo);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("update_TestPoint")) {
			TestPoint tp = new TestPoint();
			String id = request.getParameter("id");
			String if_bad = request.getParameter("if_bad");
			String cd_code = request.getParameter("cd_code");
			String td_code = request.getParameter("td_code");
			String jclxid = request.getParameter("jclxid");
			String fz = request.getParameter("fz");
			String cd_describe = request.getParameter("cd_describe");
			String cypl = request.getParameter("cypl");
			String sbxh = request.getParameter("sbxh");
			String cd_span_no = request.getParameter("cd_span_no");
			String yjStatus = request.getParameter("yjStatus");
			String fz2 = request.getParameter("fz2");
			String xmd = request.getParameter("xmd");
			String zysc = request.getParameter("zysc");
			tp.setId(id);
			tp.setCd_type_id(jclxid);
			tp.setCd_code(cd_code);
			tp.setTd_code(td_code);
			tp.setFz(fz);
			tp.setCd_describe(cd_describe);
			tp.setCypl(cypl);
			tp.setYjStatus(yjStatus);
			tp.setSbxh(sbxh);
			tp.setCd_span_no(cd_span_no);
			tp.setFz2(fz2);
			tp.setXmd(xmd);
			tp.setZysc(zysc);
			int i = TestPointDao.getInstance().updateTestPoint(tp);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("deleteTestPoint")) {
			String id = request.getParameter("id");
			int i = TestPointDao.getInstance().deleteTestPoint(id);
			if (i>0) {
				ro.setError(0);
				ro.setSuccess("success");
			}else {
				ro.setError(1);
				ro.setSuccess("fail");
			}
			ro.ToJsp(response);
			return;
		}else if (type.equals("getCurSensorIDS")) {
			String bridge_id = request.getParameter("bridge_id");
			Map<String, List<String>>  map = TestPointDao.getInstance().getTPData(bridge_id);
			ro.setObj(map);
			ro.ToJsp(response);
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}

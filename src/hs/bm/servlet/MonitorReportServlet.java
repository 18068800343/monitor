package hs.bm.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import hs.bm.bean.MonitorReport;
import hs.bm.dao.BrgCardDao;
import hs.bm.dao.MonitorReportDao;
import hs.bm.util.ReportUtil;
import hs.bm.vo.BrgCardInfoVO;
import hs.bm.vo.ResObj;

public class MonitorReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MonitorReportServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		ResObj ro = new ResObj();
		if(type.equals("selectMonitorReport")){
			HttpSession session = request.getSession();
			String manage_id = (String) session.getAttribute("manage_id");
			if(manage_id.equals("0000#0000#0000")){
				manage_id = "%%";
			}
			List<MonitorReport> list=MonitorReportDao.getInstanse().selectMonitorReport(manage_id);
			ro.setObj(list);
			ro.ToJsp(response);
			return;
		}
		if(type.equals("addMonitorReport")){
			String brgIdArray=request.getParameter("brgIdArray");
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			String time=sdf.format(new Date());
			int rp=0;
			for(int i=0;i<brgIdArray.split(",").length;i++){
				String id=brgIdArray.split(",")[i];
				BrgCardInfoVO vo=BrgCardDao.getInstance().getBrgCardInfoVOById(id);
				String brgName=vo.getBridge_name();
				String brgNo=vo.getBridge_no();
				String username=(String) request.getSession().getAttribute("username");
				String rNo=username+"-"+time;
				String rId=UUID.randomUUID().toString();
				
				rp=MonitorReportDao.getInstanse().addMonitorReport(rId, rNo, brgName, brgNo);
				if(rp!=0){
					ReportUtil util=new ReportUtil();
					String path=util.getReportPath(id);
					String reportName=path.split("\\\\")[path.split("\\\\").length-1];
					System.out.println(path);
					System.out.println(reportName);
					if(path!=null&&!path.equals("")){
						rp=MonitorReportDao.getInstanse().updateMonitorReport(rId, reportName, path);
					}
				}
				
			}
			ro.setObj(rp);
			ro.ToJsp(response);
			return;
		}
		if(type.equals("deleteMonitorReport")){
			String id=request.getParameter("id");
			int i=MonitorReportDao.getInstanse().deleteMonitorReport(id);
			ro.setObj(i);
			ro.ToJsp(response);
			return;
		}
		if(type.equals("downloadFile")){
			String filePath=request.getParameter("filePath");
			String fileName=request.getParameter("fileName");
			File f=new File(filePath);
			//第一步：设置响应类型
			response.setContentType("application/force-download");//应用程序强制下载
		    //第二读取文件
		    InputStream in = new FileInputStream(filePath);
		    //设置响应头，对文件进行url编码
		    fileName = URLEncoder.encode(fileName, "UTF-8");
		    response.setHeader("Content-Disposition", "attachment;filename="+fileName);   
		    response.setContentLength(in.available());
		    
		    //第三步：老套路，开始copy
		    OutputStream out = response.getOutputStream();
		    byte[] b = new byte[1024];
		    int len = 0;
		    while((len = in.read(b))!=-1){
		      out.write(b, 0, len);
		    }
		    out.flush();
		    out.close();
		    in.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

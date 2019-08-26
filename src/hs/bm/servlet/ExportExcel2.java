package hs.bm.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hs.bm.bean.BrgMonitor;
import hs.bm.bean.BrgMonitorCableforce;
import hs.bm.bean.BrgMonitorDynadisp;
import hs.bm.bean.BrgMonitorStaticdisp;
import hs.bm.bean.BrgMonitorStrainc;
import hs.bm.bean.BrgMonitorStrains;
import hs.bm.bean.BrgMonitorTemp;
import hs.bm.dao.BrgCardDao;
import hs.bm.dao.BrgMonitorDao;
import hs.bm.util.ExportData;

public class ExportExcel2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		ExportData export=new ExportData();
		String brg_id=request.getParameter("brg_id");
		String item_first=request.getParameter("item_first");
		String item_second=request.getParameter("item_second");
		String mode=request.getParameter("mode");
		String time = request.getParameter("time");
		String type=request.getParameter("type");
		String brg_no = BrgCardDao.getInstance().getBrgCardAdminIdData(brg_id).get(0).getBridge_no();
		String monitor_id=BrgMonitorDao.getIntance().getMonitor_id(item_first, item_second, brg_id);
		BrgMonitor brgMonitor = BrgMonitorDao.getIntance().getMonitor(monitor_id);
		String[] chanelNums=null;
		String startTime="";
		
		if(brgMonitor!=null&&brgMonitor.getChanelNum()!=null){
			chanelNums = brgMonitor.getChanelNum().trim().split(",");
		}
		String[] pointsNos=null;
		if(brgMonitor!=null&&brgMonitor.getPointsNo()!=null){
			pointsNos = brgMonitor.getPointsNo().trim().split(",");
		}
		if(type.equals("1")){
			List<BrgMonitorStrainc> list=null;
			if("hour".equals(mode)){
				time=time+"-00";
				try {
					long t=sdf.parse(time).getTime()-1800000;
					Date date=new Date(t);
					startTime=sdf.format(date);
					time=sdf.format(new Date(sdf.parse(time).getTime()+60000));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				 list=BrgMonitorDao.getIntance().selectStrainc(brg_no, mode,startTime,time);
			}else{
				list=BrgMonitorDao.getIntance().selectStrainc(brg_no, mode,time);
			}
			String titleName="混凝土应变";
			String titleList []=null;
			if(pointsNos.length>1){
				String []	aa={"通道号","测点号","最大值","0.95分位点值","最小值","0.05分位点值","平均值","方差值","状态"};
				titleList=aa;
			}else{
				String [] aa={"通道号","最大值","0.95分位点值","最小值","0.05分位点值","平均值","方差值","状态"};
				titleList=aa;
			}
			try {
				export.ExportHeight1(titleName, titleName, titleList, list, chanelNums, pointsNos,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(type.equals("2")){
			List<BrgMonitorStrains>list=null;
			if("hour".equals(mode)){
				time=time+"-00";
				try {
					long t=sdf.parse(time).getTime()-1800000;
					Date date=new Date(t);
					startTime=sdf.format(date);
					time=sdf.format(new Date(sdf.parse(time).getTime()+60000));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				list=BrgMonitorDao.getIntance().selectStrains(brg_no, mode,startTime,time);
			}else{
				list=BrgMonitorDao.getIntance().selectStrains(brg_no, mode,time);
			}
			String titleName="钢应变";
			String titleList []=null;
			if(pointsNos.length>1){
				String [] aa={"通道号","测点号","最大值","0.95分位点值","最小值","0.05分位点值","平均值","方差值","状态"};
				titleList=aa;
			}else{
				String [] aa={"通道号","最大值","0.95分位点值","最小值","0.05分位点值","平均值","方差值","状态"};
				titleList=aa;
			}
			try {
				export.ExportHeight2(titleName, titleName, titleList, list, chanelNums, pointsNos, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(type.equals("3")){
			List<BrgMonitorDynadisp>list=null;
			if("hour".equals(mode)){
				time=time+"-00";
				try {
					long t=sdf.parse(time).getTime()-1800000;
					Date date=new Date(t);
					startTime=sdf.format(date);
					time=sdf.format(new Date(sdf.parse(time).getTime()+60000));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				list=BrgMonitorDao.getIntance().selectDynadisp(brg_no, mode,startTime,time);
			}else{
				list=BrgMonitorDao.getIntance().selectDynadisp(brg_no, mode,time);
			}
			String titleName="动位移";
			String titleList []=null;
			if(pointsNos.length>1){
				String [] aa={"通道号","测点号","最大值","0.95分位点值","平均值","方差值","状态"};
				titleList=aa;
			}else{
				String [] aa={"通道号","最大值","0.95分位点值","平均值","方差值","状态"};
				titleList=aa;
			}
			try {
				export.ExportHeight3(titleName, titleName, titleList, list, chanelNums, pointsNos, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(type.equals("4")){
			List<BrgMonitorTemp>list=null;
			if("hour".equals(mode)){
				time=time+"-00";
				try {
					long t=sdf.parse(time).getTime()-1800000;
					Date date=new Date(t);
					startTime=sdf.format(date);
					time=sdf.format(new Date(sdf.parse(time).getTime()+60000));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				list=BrgMonitorDao.getIntance().selectTemp(brg_no, mode,startTime,time);
			}else{
				list=BrgMonitorDao.getIntance().selectTemp(brg_no, mode,time);
			}
			String titleName="温度";
			String titleList []=null;
			if(pointsNos.length>1){
				String [] aa={"通道号","测点号","平均值","状态"};
				titleList=aa;
			}else{
				String [] aa={"通道号","平均值","状态"};
				titleList=aa;
			}
			try {
				export.ExportHeight4(titleName, titleName, titleList, list, chanelNums, pointsNos, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(type.equals("5")){
			List<BrgMonitorCableforce>list=null;
			if("hour".equals(mode)){
				time=time+"-00";
				try {
					long t=sdf.parse(time).getTime()-1800000;
					Date date=new Date(t);
					startTime=sdf.format(date);
					time=sdf.format(new Date(sdf.parse(time).getTime()+60000));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				list=BrgMonitorDao.getIntance().selectCableforce(brg_no, mode,startTime,time);
			}else{
				list=BrgMonitorDao.getIntance().selectCableforce(brg_no, mode,time);
			}
			String titleName="索力";
			String titleList []=null;
			if(pointsNos.length>1){
				String [] aa={"通道号","测点号","平均值","状态"};
				titleList=aa;
			}else{
				String [] aa={"通道号","平均值","状态"};
				titleList=aa;
			}
			
			try {
				export.ExportHeight5(titleName, titleName, titleList, list, chanelNums, pointsNos, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(type.equals("6")){
			List<BrgMonitorStaticdisp>list=null;
			if("hour".equals(mode)){
				time=time+"-00";
				try {
					long t=sdf.parse(time).getTime()-1800000;
					Date date=new Date(t);
					startTime=sdf.format(date);
					time=sdf.format(new Date(sdf.parse(time).getTime()+60000));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				list=BrgMonitorDao.getIntance().selectStaticdisp(brg_no, mode,startTime,time);
			}else{
				list=BrgMonitorDao.getIntance().selectStaticdisp(brg_no, mode,time);
			}
			String titleName="静位移";
			String titleList []=null;
			if(pointsNos.length>1){
				String [] aa={"通道号","测点号","最大值","最小值","状态"};
				titleList=aa;
			}else{
				String [] aa={"通道号","最大值","最小值","状态"};
				titleList=aa;
			}
			
			try {
				export.ExportHeight6(titleName, titleName, titleList, list, chanelNums,pointsNos, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

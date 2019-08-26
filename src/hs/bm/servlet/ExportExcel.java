package hs.bm.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import hs.bm.bean.BrgWeightLoadRatio;
import hs.bm.bean.MaxGw;
import hs.bm.bean.MaxGw8;
import hs.bm.bean.MaxGwInfo;
import hs.bm.bean.MaxGwInfo8;
import hs.bm.bean.NumOvlo;
import hs.bm.bean.NumOvlo8;
import hs.bm.bean.NumVehi;
import hs.bm.bean.NumVehi8;
import hs.bm.bean.ProbOvlo;
import hs.bm.bean.ProbOvlo8;
import hs.bm.bean.RatioOvlo;
import hs.bm.bean.RatioOvlo8;
import hs.bm.dao.BrgCardDao;
import hs.bm.dao.MaxGwDao;
import hs.bm.dao.MaxGwInfoDao;
import hs.bm.dao.NumOvloDao;
import hs.bm.dao.NumVehiDao;
import hs.bm.dao.ProbOvloDao;
import hs.bm.dao.RatioOvloDao;
import hs.bm.util.ExportData;
import hs.bm.vo.ResObj;

public class ExportExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String brg_id=request.getParameter("brg_id");
		String brg_no = BrgCardDao.getInstance().getBrgCardAdminIdData(brg_id).get(0).getBridge_no();
		String type=request.getParameter("type");
		String item_second=request.getParameter("item_second");
		String mode=request.getParameter("mode");
		String time=request.getParameter("time");
		String lastTime="";
		
		if(mode.equals("hour")){
			time=time+"-00";
			try {
				long t=sdf.parse(time).getTime()-1800000;
				Date date=new Date(t);
				lastTime=sdf.format(date);
				time=sdf.format(new Date(sdf.parse(time).getTime()+60000));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		
		ExportData export=new ExportData();
		String [] titleList={"方向","车道编号","车道名","2轴车","3轴车","4轴车","5轴车","6轴车","7轴车及以上","合计"};
		
		if(item_second.equals("车辆荷载")){
			if("G25LR0040320800".equals(brg_no)||"G15320682L0100".equals(brg_no)||"G15320921L0010".equals(brg_no)){
				if(type.equals("0")){
					List<NumVehi8>list1=NumVehiDao.getInstance().selectNumVehi8(brg_no,mode,time,lastTime);
					try {
						export.ExportWithResponse1("交通量", "交通量", titleList,list1.get(0), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(type.equals("1")){
					List<MaxGw8>list2=MaxGwDao.getInstance().selectMaxGw8(brg_no,mode,time,lastTime);
					try {
						export.ExportWithResponse2("最大车重KG", "最大车重KG", titleList,list2.get(0), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(type.equals("2")){
					List<NumOvlo8>list4=NumOvloDao.getInstance().selectNumOvlo8(brg_no,mode,time,lastTime);
					try {
						export.ExportWithResponse3("超载车辆数", "超载车辆数", titleList,list4.get(0), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(type.equals("3")){
					List<RatioOvlo8>list5=RatioOvloDao.getInstance().selectRatioOvlo8(brg_no,mode,time,lastTime);
					try {
						export.ExportWithResponse4("超载比例", "超载比例", titleList,list5.get(0), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(type.equals("4")){
					List<ProbOvlo8>list6=ProbOvloDao.getInstance().selectProbOvlo8(brg_no,mode,time,lastTime);
					try {
						export.ExportWithResponse5("平均超载率", "平均超载率", titleList,list6.get(0), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(type.equals("5")){
					List<BrgWeightLoadRatio>list7=ProbOvloDao.getInstance().getOneByID(brg_no,mode,time,lastTime);
					try {
						export.ExportWithResponse11("荷载效应比", "荷载效应比",list7.get(0), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}else{
				if(type.equals("0")){
					List<NumVehi>list1=NumVehiDao.getInstance().selectNumVehi(brg_no,mode,time,lastTime);
					try {
						export.ExportWithResponse6("交通量", "交通量", titleList,list1.get(0), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(type.equals("1")){
					List<MaxGw>list2=MaxGwDao.getInstance().selectMaxGw(brg_no,mode,time,lastTime);
					try {
						export.ExportWithResponse7("最大车重KG", "最大车重KG", titleList,list2.get(0), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(type.equals("2")){
					List<NumOvlo>list4=NumOvloDao.getInstance().selectNumOvlo(brg_no,mode,time,lastTime);
					try {
						export.ExportWithResponse8("超载车辆数", "超载车辆数", titleList,list4.get(0), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(type.equals("3")){
					List<RatioOvlo>list5=RatioOvloDao.getInstance().selectRatioOvlo(brg_no,mode,time,lastTime);
					try {
						export.ExportWithResponse9("超载比例", "超载比例", titleList,list5.get(0), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(type.equals("4")){
					List<ProbOvlo>list6=ProbOvloDao.getInstance().selectProbOvlo(brg_no,mode,time,lastTime);
					try {
						export.ExportWithResponse10("平均超载率", "平均超载率", titleList,list6.get(0), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(type.equals("5")){
					List<BrgWeightLoadRatio>list7=ProbOvloDao.getInstance().getOneByID(brg_no,mode,time,lastTime);
					try {
						export.ExportWithResponse12("荷载效应比", "荷载效应比",list7.get(0), response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

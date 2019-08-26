package hs.bm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import hs.bm.bean.BrgCardAdminId;
import hs.bm.bean.BrgMonitor;
import hs.bm.bean.BrgWeightLoadRatio;
import hs.bm.bean.Connect;
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
import hs.bm.dao.AppDao;
import hs.bm.dao.BrgCardDao;
import hs.bm.dao.BrgMonitorDao;
import hs.bm.dao.GetFileSizeDao;
import hs.bm.dao.MaxGwDao;
import hs.bm.dao.MaxGwInfoDao;
import hs.bm.dao.NumOvloDao;
import hs.bm.dao.NumVehiDao;
import hs.bm.dao.ProbOvloDao;
import hs.bm.dao.RatioOvloDao;
import hs.bm.dao.UserDao;
import hs.bm.util.FileManageUtil;
import hs.bm.vo.BrgCdyc;
import hs.bm.vo.ResObj;
import hs.bm.vo.SysUsrPassRole;

/**
 * Servlet implementation class AppServlet
 */
@WebServlet("/AppServlet")
public class AppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AppServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		ResObj ro = new ResObj();
		if(type.equals("login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			SysUsrPassRole user=UserDao.getInstance().doLogin(username, password);
			if(user ==null) {
				ro.setSuccess("1");
			}else {
				ro.setSuccess("0");
			}
			
			ro.setObj(user);
			ro.ToJsp(response);
			return;
		}
		
		if (type.equals("initIndexBrgMonitorApp")) {
			String manage_id="%%";
			String mode = request.getParameter("mode");
			String tableName = Index2Servlet.getTableNameByMode(mode);
			List<Connect> lb =null;
			lb = AppDao.getInstance().initIndexModeMap(mode,tableName,manage_id);
			if (lb.size() > 0) {
				ro.setSuccess("success");
				ro.setObj(lb);
			}
			ro.ToJsp(response);
			return;
		}
		if(type.equals("selectAllBrg")){
			String manage_id = request.getParameter("manage_id");
			manage_id= manage_id.replace("\"", "");
			if(manage_id!=null&&!manage_id.equals("")){
				manage_id="%"+manage_id.split("#")[0]+"%";
			}else{
				manage_id="%%";
			}
			List<BrgCardAdminId> ll=BrgCardDao.getInstance().selectAllBrg(manage_id);
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
		}
		
		if("getWeight".equals(type)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			String brg_id=request.getParameter("brg_id");
			String brg_no = BrgCardDao.getInstance().getBrgCardAdminIdData(brg_id).get(0).getBridge_no();
			String item_first=request.getParameter("item_first");
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
			
			JSONObject obj=  new JSONObject();
			JSONArray array = new JSONArray();
			if(item_second.equals("车辆荷载")){
				if("G25LR0040320800".equals(brg_no)||"G15320682L0100".equals(brg_no)||"G15320921L0010".equals(brg_no)){
					List<NumVehi8>list1=AppDao.getInstance().selectNumVehi8(brg_no,mode,time,lastTime);
					List<MaxGw8>list2=AppDao.getInstance().selectMaxGw8(brg_no,mode,time,lastTime);
					List<MaxGwInfo8>list3=AppDao.getInstance().selectMaxGwInfo8(brg_no,mode,time,lastTime);
					List<NumOvlo8>list4=AppDao.getInstance().selectNumOvlo8(brg_no,mode,time,lastTime);
					List<RatioOvlo8>list5=AppDao.getInstance().selectRatioOvlo8(brg_no,mode,time,lastTime);
					List<ProbOvlo8>list6=AppDao.getInstance().selectProbOvlo8(brg_no,mode,time,lastTime);
					List<BrgWeightLoadRatio>list7=AppDao.getInstance().getOneByID(brg_no,mode,time,lastTime);
					/*List<BrgMonitor>list8=BrgMonitorDao.getIntance().selectBrgMonitor(item_first, item_second, brg_id);*/
					obj.put("NumVehi8", list1);
					obj.put("MaxGw8", list2);
					obj.put("MaxGwInfo8", list3);
					obj.put("NumOvlo8", list4);
					obj.put("RatioOvlo8", list5);
					obj.put("ProbOvlo8", list6);
					obj.put("LoadRadio", list7);
					/*obj.put("monitor",list8);*/
					array.add(obj);
				}else{
					List<NumVehi>list1=AppDao.getInstance().selectNumVehi(brg_no,mode,time,lastTime);
					List<MaxGw>list2=AppDao.getInstance().selectMaxGw(brg_no,mode,time,lastTime);
					List<MaxGwInfo>list3=AppDao.getInstance().selectMaxGwInfo(brg_no,mode,time,lastTime);
					List<NumOvlo>list4=AppDao.getInstance().selectNumOvlo(brg_no,mode,time,lastTime);
					List<RatioOvlo>list5=AppDao.getInstance().selectRatioOvlo(brg_no,mode,time,lastTime);
					List<ProbOvlo>list6=AppDao.getInstance().selectProbOvlo(brg_no,mode,time,lastTime);
					List<BrgWeightLoadRatio>list7=AppDao.getInstance().getOneByID(brg_no,mode,time,lastTime);
					/*List<BrgMonitor>list8=BrgMonitorDao.getIntance().selectBrgMonitor(item_first, item_second, brg_id);*/
					obj.put("NumVehi", list1);
					obj.put("MaxGw", list2);
					obj.put("MaxGwInfo", list3);
					obj.put("NumOvlo", list4);
					obj.put("RatioOvlo", list5);
					obj.put("ProbOvlo", list6);
					obj.put("LoadRadio", list7);
					/*obj.put("monitor",list8);*/
					array.add(obj);
				}
			}
			ro.setObj(array);
			ro.ToJsp(response);
			return;
		}
		if(type.equals("initYjNew")){
			String manage_id = request.getParameter("manage_id");
			manage_id= manage_id.replace("\"", "");
			if(null!=manage_id&&!"".equals(manage_id)){
				if(manage_id.startsWith("0000#")){
					manage_id="%%";
				}else{
					manage_id=manage_id.substring(0, 5)+"%";
				}
			}
			List<BrgCdyc> ycList =new ArrayList<>();
			ycList = GetFileSizeDao.getInstance().getalarmsumYcNew(manage_id);			
			if (ycList.size() > 0) {
				ro.setSuccess("success");
				ro.setObj(ycList);
			}
			ro.ToJsp(response);
			return;
		}
		if (type.equals("initDataFullNew")) {
			String manage_id = request.getParameter("manage_id");
			manage_id= manage_id.replace("\"", "");
			if(null!=manage_id&&!"".equals(manage_id)){
				if(manage_id.startsWith("0000#")){
					manage_id="%%";
				}else{
					manage_id=manage_id.substring(0, 5)+"%";
				}
			}
			String mode = request.getParameter("mode");
			List<Connect> list =new ArrayList<>();
			List<Connect> lb = GetFileSizeDao.getInstance().initDataFullIndex("brg_health_currate", manage_id);
			List<Connect> lb1 = GetFileSizeDao.getInstance().initDataFullIndex("brg_weight_currate", manage_id);
			List<Connect> lb2 = GetFileSizeDao.getInstance().initDataFullIndex("brg_gps_curyrate", manage_id);
			for(int i=0;i<lb.size();i++){
				Connect con=lb.get(i);
				list.add(con);
			}
			for(int i=0;i<lb1.size();i++){
				Connect con=lb1.get(i);
				list.add(con);
			}
			for(int i=0;i<lb2.size();i++){
				Connect con=lb2.get(i);
				list.add(con);
			}
 			Collections.sort(list,new Comparator<Connect>(){
				@Override
				public int compare(Connect o1, Connect o2) {
					if(o1.getCount()==null){
						o1.setCount("0%");
					}
					return o1.getCount().compareTo(o2.getCount());
				}}
	        );
			
	        Collections.sort(list,new Comparator<Connect>(){
				@Override
				public int compare(Connect o1, Connect o2) {
					return o1.getBridge_name().compareTo(o2.getBridge_name());
				}}
	        );
	        Iterator iterator_name=list.iterator();
	        while(iterator_name.hasNext()){
	        	Connect s=(Connect)iterator_name.next();
	            
	        }
			
			
			if (list.size() > 0) {
				ro.setSuccess("success");
				ro.setObj(list);
			}
			ro.ToJsp(response);
			return;
		}
		if (type.equals("initCdycNew")) {
			String manage_id = request.getParameter("manage_id");
			manage_id= manage_id.replace("\"", "");
			if(null!=manage_id&&!"".equals(manage_id)){
				if(manage_id.startsWith("0000#")){
					manage_id="%%";
				}else{
					manage_id=manage_id.substring(0, 5)+"%";
				}
			}
			List<BrgCdyc> ycList =new ArrayList<>();
			ycList = GetFileSizeDao.getInstance().getabnormalYcNew("brg_monitor_abnormal",manage_id);
			if (ycList.size() > 0) {
				ro.setSuccess("success");
				ro.setObj(ycList);
			}
			ro.ToJsp(response);
			return;
		}
		if(type.equals("initIndexBrgMonitorYj")){
			String manage_id = request.getParameter("manage_id");
			manage_id= manage_id.replace("\"", "");
			if(null!=manage_id&&!"".equals(manage_id)){
				if(manage_id.startsWith("0000#")){
					manage_id="%%";
				}else{
					manage_id=manage_id.substring(0, 5)+"%";
				}
			}
			List<BrgCdyc> ycList =new ArrayList<>();
			List<BrgCardAdminId> brgIdList=GetFileSizeDao.getInstance().getBrgIdByMode("s",manage_id);
			
			
			for(int i=0;i<brgIdList.size();i++){
				//索力
				String brgNo=GetFileSizeDao.getInstance().getBrgNo(brgIdList.get(i).getBridge_id());
				String brgName=brgIdList.get(i).getBridge_name();
				String yc="黄色告警";
				String yc2="红色告警";
				String monitor_id=BrgMonitorDao.getIntance().getMonitor_id("局部响应", "索力", brgIdList.get(i).getBridge_id());
				if(monitor_id!=null&&monitor_id!=""){
					BrgMonitor brgMonitor = BrgMonitorDao.getIntance().getMonitor(monitor_id);
					String[] chanelNums = brgMonitor.getChanelNum().trim().split(",");
					List<Integer> sortList=GetFileSizeDao.getInstance().getheightSort("brg_monitor_cableforce", brgNo,yc);
					if(sortList.size()>0){
						for(int j=0;j<sortList.size();j++){
							int index=sortList.get(j);
							String addr=chanelNums[index-1];
							String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
							describe=describe+"("+addr+")";
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(describe);
							cdyc.setYcStates(yc);
							ycList.add(cdyc);
						}
						/*for(int j=0;j<sortList.size();j++){
							int index=sortList.get(j);
							String addr=chanelNums[index-1];
							ycAddr=ycAddr+","+addr;
						}
						if(ycAddr.startsWith(",")){
							ycAddr=ycAddr.substring(1, ycAddr.length());
						}
						BrgCdyc cdyc=new BrgCdyc();
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(ycAddr);
						cdyc.setYcStates(yc);
						ycList.add(cdyc);*/
					}
					
					List<Integer> sortListH=GetFileSizeDao.getInstance().getheightSort("brg_monitor_cableforce", brgNo,yc2);
					if(sortListH.size()>0){
						for(int j=0;j<sortListH.size();j++){
							int index=sortListH.get(j);
							String addr=chanelNums[index-1];
							String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
							describe=describe+"("+addr+")";
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(describe);
							cdyc.setYcStates(yc2);
							ycList.add(cdyc);
						}
						/*if(ycAddr.startsWith(",")){
							ycAddr=ycAddr.substring(1, ycAddr.length());
						}
						BrgCdyc cdyc=new BrgCdyc();
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(ycAddr);
						cdyc.setYcStates(yc2);
						ycList.add(cdyc);*/
					}
					
				}
				
				//动位移
				String monitor_id2=BrgMonitorDao.getIntance().getMonitor_id("整体响应", "动位移", brgIdList.get(i).getBridge_id());
				if(monitor_id2!=null&&monitor_id2!=""){
					BrgMonitor brgMonitor2 = BrgMonitorDao.getIntance().getMonitor(monitor_id2);
					String[] chanelNums2 = brgMonitor2.getChanelNum().trim().split(",");
					List<Integer> sortList2=GetFileSizeDao.getInstance().getheightSort("brg_monitor_dynadisp", brgNo,yc);
					if(sortList2.size()>0){
						for(int j=0;j<sortList2.size();j++){
							int index=sortList2.get(j);
							String addr=chanelNums2[index-1];
							String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
							describe=describe+"("+addr+")";
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(describe);
							cdyc.setYcStates(yc);
							ycList.add(cdyc);
						}
						/*if(ycAddr.startsWith(",")){
							ycAddr=ycAddr.substring(1, ycAddr.length());
						}
						BrgCdyc cdyc=new BrgCdyc();
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(ycAddr);
						cdyc.setYcStates(yc);
						ycList.add(cdyc);*/
					}
					
					List<Integer> sortList2H=GetFileSizeDao.getInstance().getheightSort("brg_monitor_dynadisp", brgNo,yc2);
					if(sortList2H.size()>0){
						for(int j=0;j<sortList2H.size();j++){
							int index=sortList2H.get(j);
							String addr=chanelNums2[index-1];
							String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
							describe=describe+"("+addr+")";
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(describe);
							cdyc.setYcStates(yc2);
							ycList.add(cdyc);
						}
						/*if(ycAddr.startsWith(",")){
							ycAddr=ycAddr.substring(1, ycAddr.length());
						}
						BrgCdyc cdyc=new BrgCdyc();
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(ycAddr);
						cdyc.setYcStates(yc2);
						ycList.add(cdyc);*/
					}
					
				}
				
				//静位移
				String monitor_id3=BrgMonitorDao.getIntance().getMonitor_id("整体响应", "静位移", brgIdList.get(i).getBridge_id());
				if(monitor_id3!=null&&monitor_id3!=""){
					BrgMonitor brgMonitor3 = BrgMonitorDao.getIntance().getMonitor(monitor_id3);
					String[] chanelNums3 = brgMonitor3.getChanelNum().trim().split(",");
					List<Integer> sortList3=GetFileSizeDao.getInstance().getheightSort("brg_monitor_staticdisp", brgNo,yc);
					if(sortList3.size()>0){
						for(int j=0;j<sortList3.size();j++){
							int index=sortList3.get(j);
							String addr=chanelNums3[index-1];
							String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
							describe=describe+"("+addr+")";
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(describe);
							cdyc.setYcStates(yc);
							ycList.add(cdyc);
						}
						/*if(ycAddr.startsWith(",")){
							ycAddr=ycAddr.substring(1, ycAddr.length());
						}
						BrgCdyc cdyc=new BrgCdyc();
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(ycAddr);
						cdyc.setYcStates(yc);
						ycList.add(cdyc);*/
					}
					
					List<Integer> sortList3H=GetFileSizeDao.getInstance().getheightSort("brg_monitor_staticdisp", brgNo,yc2);
					if(sortList3H.size()>0){
						for(int j=0;j<sortList3H.size();j++){
							int index=sortList3H.get(j);
							String addr=chanelNums3[index-1];
							String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
							describe=describe+"("+addr+")";
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(describe);
							cdyc.setYcStates(yc2);
							ycList.add(cdyc);
						}
						/*if(ycAddr.startsWith(",")){
							ycAddr=ycAddr.substring(1, ycAddr.length());
						}
						BrgCdyc cdyc=new BrgCdyc();
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(ycAddr);
						cdyc.setYcStates(yc2);
						ycList.add(cdyc);*/
					}
					
				}
				
				//混凝土应变
				String monitor_id4=BrgMonitorDao.getIntance().getMonitor_id("局部响应", "混凝土应变", brgIdList.get(i).getBridge_id());
				if(monitor_id4!=null&&monitor_id4!=""){
					BrgMonitor brgMonitor4 = BrgMonitorDao.getIntance().getMonitor(monitor_id4);
					String[] chanelNums4 = brgMonitor4.getChanelNum().trim().split(",");
					List<Integer> sortList4=GetFileSizeDao.getInstance().getheightSort("brg_monitor_strainc", brgNo,yc);
					if(sortList4.size()>0){
						for(int j=0;j<sortList4.size();j++){
							int index=sortList4.get(j);
							String addr=chanelNums4[index-1];
							String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
							describe=describe+"("+addr+")";
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(describe);
							cdyc.setYcStates(yc);
							ycList.add(cdyc);
						}
						/*if(ycAddr.startsWith(",")){
							ycAddr=ycAddr.substring(1, ycAddr.length());
						}
						BrgCdyc cdyc=new BrgCdyc();
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(ycAddr);
						cdyc.setYcStates(yc);
						ycList.add(cdyc);*/
					}
					
					List<Integer> sortList4H=GetFileSizeDao.getInstance().getheightSort("brg_monitor_strainc", brgNo,yc2);
					if(sortList4H.size()>0){
						for(int j=0;j<sortList4H.size();j++){
							int index=sortList4H.get(j);
							String addr=chanelNums4[index-1];
							String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
							describe=describe+"("+addr+")";
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(describe);
							cdyc.setYcStates(yc2);
							ycList.add(cdyc);
						}
						/*if(ycAddr.startsWith(",")){
							ycAddr=ycAddr.substring(1, ycAddr.length());
						}
						BrgCdyc cdyc=new BrgCdyc();
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(ycAddr);
						cdyc.setYcStates(yc2);
						ycList.add(cdyc);*/
					}
					
				}
			
				//钢应变
				String monitor_id5=BrgMonitorDao.getIntance().getMonitor_id("局部响应", "钢应变", brgIdList.get(i).getBridge_id());
				if(monitor_id5!=null&&monitor_id5!=""&&!"352".equals(monitor_id5)){
					BrgMonitor brgMonitor5 = BrgMonitorDao.getIntance().getMonitor(monitor_id5);
					String[] chanelNums5 = brgMonitor5.getChanelNum().trim().split(",");
					List<Integer> sortList5=GetFileSizeDao.getInstance().getheightSort("brg_monitor_strains", brgNo,yc);
					if(sortList5.size()>0){
						for(int j=0;j<sortList5.size();j++){
							int index=sortList5.get(j);
							String addr=chanelNums5[index-1];
							String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
							describe=describe+"("+addr+")";
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(describe);
							cdyc.setYcStates(yc);
							ycList.add(cdyc);
						}
						/*if(ycAddr.startsWith(",")){
							ycAddr=ycAddr.substring(1, ycAddr.length());
						}
						BrgCdyc cdyc=new BrgCdyc();
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(ycAddr);
						cdyc.setYcStates(yc);
						ycList.add(cdyc);*/
					}
					
					List<Integer> sortList5H=GetFileSizeDao.getInstance().getheightSort("brg_monitor_strains", brgNo,yc2);
					if(sortList5H.size()>0){
						for(int j=0;j<sortList5H.size();j++){
							int index=sortList5H.get(j);
							String addr=chanelNums5[index-1];
							String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
							describe=describe+"("+addr+")";
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(describe);
							cdyc.setYcStates(yc2);
							ycList.add(cdyc);
						}
						/*if(ycAddr.startsWith(",")){
							ycAddr=ycAddr.substring(1, ycAddr.length());
						}
						BrgCdyc cdyc=new BrgCdyc();
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(ycAddr);
						cdyc.setYcStates(yc2);
						ycList.add(cdyc);*/
					}
					
				}
				
				//温度
				String monitor_id6=BrgMonitorDao.getIntance().getMonitor_id("荷载与环境", "温度", brgIdList.get(i).getBridge_id());
				if(monitor_id6!=null&&monitor_id6!=""){
					BrgMonitor brgMonitor6 = BrgMonitorDao.getIntance().getMonitor(monitor_id6);
					String[] chanelNums6 = brgMonitor6.getChanelNum().trim().split(",");
					List<Integer> sortList6=GetFileSizeDao.getInstance().getheightSort("brg_monitor_temp", brgNo,yc);
					if(sortList6.size()>0){
						for(int j=0;j<sortList6.size();j++){
							int index=sortList6.get(j);
							String addr=chanelNums6[index-1];
							String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
							describe=describe+"("+addr+") 温度";
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(describe);
							cdyc.setYcStates(yc);
							ycList.add(cdyc);
						}
						/*if(ycAddr.startsWith(",")){
							ycAddr=ycAddr.substring(1, ycAddr.length());
						}
						BrgCdyc cdyc=new BrgCdyc();
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(ycAddr);
						cdyc.setYcStates(yc);
						ycList.add(cdyc);*/
					}
					
					List<Integer> sortList6H=GetFileSizeDao.getInstance().getheightSort("brg_monitor_temp", brgNo,yc2);
					if(sortList6H.size()>0){
						for(int j=0;j<sortList6H.size();j++){
							int index=sortList6H.get(j);
							String addr=chanelNums6[index-1];
							String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
							describe=describe+"("+addr+") 温度";
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(describe);
							cdyc.setYcStates(yc2);
							ycList.add(cdyc);
						}
					/*	if(ycAddr.startsWith(",")){
							ycAddr=ycAddr.substring(1, ycAddr.length());
						}
						BrgCdyc cdyc=new BrgCdyc();
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(ycAddr);
						cdyc.setYcStates(yc2);
						ycList.add(cdyc);*/
					}
					
				}
			
			}
			if (ycList.size() > 0) {
				ro.setSuccess("success");
				ro.setObj(ycList);
			}
			ro.ToJsp(response);
			return;
		}
		
		if (type.equals("initIndexBrgMonitorCd")) {
			String manage_id = request.getParameter("manage_id");
			manage_id= manage_id.replace("\"", "");
			if(null!=manage_id&&!"".equals(manage_id)){
				if(manage_id.startsWith("0000#")){
					manage_id="%%";
				}else{
					manage_id=manage_id.substring(0, 5)+"%";
				}
			}
			List<BrgCdyc> ycList =new ArrayList<>();
			String mode="s";
			List<BrgCardAdminId> brgIdList=GetFileSizeDao.getInstance().getBrgIdByMode(mode,manage_id);
			String modew="w";
			List<BrgCardAdminId> brgIdList1=GetFileSizeDao.getInstance().getBrgIdByMode(modew,manage_id);
			/*lb = GetFileSizeDao.getInstance().initIndexModeMapCd(mode,tableName,manage_id);*/
			if(modew.equals("w")){
				for(int i=0;i<brgIdList1.size();i++){
					String chedao=GetFileSizeDao.getInstance().getWeightYcById(brgIdList1.get(i).getBridge_id());
					if(!chedao.equals("")){
						BrgCdyc cdyc=new BrgCdyc();
						String brgName=brgIdList1.get(i).getBridge_name();
						String yc="异常";
						cdyc.setBrgName(brgName);
						cdyc.setYcAddr(chedao);
						cdyc.setYcStates(yc);
						ycList.add(cdyc);
					}
				}
			}
			if(mode.equals("s")){
				for(int i=0;i<brgIdList.size();i++){
					//索力
					String brgNo=GetFileSizeDao.getInstance().getBrgNo(brgIdList.get(i).getBridge_id());
					String brgName=BrgCardDao.getInstance().getBrgName(brgIdList.get(i).getBridge_id());
					String yc="异常";
					String monitor_id=BrgMonitorDao.getIntance().getMonitor_id("局部响应", "索力", brgIdList.get(i).getBridge_id());
					if(monitor_id!=null&&monitor_id!=""){
						BrgMonitor brgMonitor = BrgMonitorDao.getIntance().getMonitor(monitor_id);
						String[] chanelNums = brgMonitor.getChanelNum().trim().split(",");
						List<Integer> sortList=GetFileSizeDao.getInstance().getheightSort("brg_monitor_cableforce", brgNo,yc);
						if(sortList.size()>0){
					//		String ycAddr="";
							for(int j=0;j<sortList.size();j++){
								int index=sortList.get(j);
								String addr=chanelNums[index-1];
								String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
								describe=describe+"("+addr+")";
								BrgCdyc cdyc=new BrgCdyc();
								cdyc.setBrgName(brgName);
								cdyc.setYcAddr(describe);
								cdyc.setYcStates(yc);
								ycList.add(cdyc);
							}
							/*if(ycAddr.startsWith(",")){
								ycAddr=ycAddr.substring(1, ycAddr.length());
							}
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(ycAddr);
							cdyc.setYcStates(yc);
							ycList.add(cdyc);*/
						}
						
					}
					
					//动位移
					String monitor_id2=BrgMonitorDao.getIntance().getMonitor_id("整体响应", "动位移", brgIdList.get(i).getBridge_id());
					if(monitor_id2!=null&&monitor_id2!=""){
						BrgMonitor brgMonitor2 = BrgMonitorDao.getIntance().getMonitor(monitor_id2);
						String[] chanelNums2 = brgMonitor2.getChanelNum().trim().split(",");
						List<Integer> sortList2=GetFileSizeDao.getInstance().getheightSort("brg_monitor_dynadisp", brgNo,yc);
						if(sortList2.size()>0){
							for(int j=0;j<sortList2.size();j++){
								int index=sortList2.get(j);
								String addr=chanelNums2[index-1];
								String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
								describe=describe+"("+addr+")";
								BrgCdyc cdyc=new BrgCdyc();
								cdyc.setBrgName(brgName);
								cdyc.setYcAddr(describe);
								cdyc.setYcStates(yc);
								ycList.add(cdyc);
							}
							/*if(ycAddr.startsWith(",")){
								ycAddr=ycAddr.substring(1, ycAddr.length());
							}
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(ycAddr);
							cdyc.setYcStates(yc);
							ycList.add(cdyc);*/
						}
						
					}
					
					//静位移
					String monitor_id3=BrgMonitorDao.getIntance().getMonitor_id("整体响应", "静位移", brgIdList.get(i).getBridge_id());
					if(monitor_id3!=null&&monitor_id3!=""){
						BrgMonitor brgMonitor3 = BrgMonitorDao.getIntance().getMonitor(monitor_id3);
						String[] chanelNums3 = brgMonitor3.getChanelNum().trim().split(",");
						List<Integer> sortList3=GetFileSizeDao.getInstance().getheightSort("brg_monitor_staticdisp", brgNo,yc);
						if(sortList3.size()>0){
							for(int j=0;j<sortList3.size();j++){
								int index=sortList3.get(j);
								String addr=chanelNums3[index-1];
								String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
								describe=describe+"("+addr+")";
								BrgCdyc cdyc=new BrgCdyc();
								cdyc.setBrgName(brgName);
								cdyc.setYcAddr(describe);
								cdyc.setYcStates(yc);
								ycList.add(cdyc);
							}
							/*if(ycAddr.startsWith(",")){
								ycAddr=ycAddr.substring(1, ycAddr.length());
							}
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(ycAddr);
							cdyc.setYcStates(yc);
							ycList.add(cdyc);*/
						}
						
					}
					
					//混凝土应变
					String monitor_id4=BrgMonitorDao.getIntance().getMonitor_id("局部响应", "混凝土应变", brgIdList.get(i).getBridge_id());
					if(monitor_id4!=null&&monitor_id4!=""){
						BrgMonitor brgMonitor4 = BrgMonitorDao.getIntance().getMonitor(monitor_id4);
						String[] chanelNums4 = brgMonitor4.getChanelNum().trim().split(",");
						List<Integer> sortList4=GetFileSizeDao.getInstance().getheightSort("brg_monitor_strainc", brgNo,yc);
						if(sortList4.size()>0){
							for(int j=0;j<sortList4.size();j++){
								int index=sortList4.get(j);
								String addr=chanelNums4[index-1];
								String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
								describe=describe+"("+addr+")";
								BrgCdyc cdyc=new BrgCdyc();
								cdyc.setBrgName(brgName);
								cdyc.setYcAddr(describe);
								cdyc.setYcStates(yc);
								ycList.add(cdyc);
							}
							/*if(ycAddr.startsWith(",")){
								ycAddr=ycAddr.substring(1, ycAddr.length());
							}
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(ycAddr);
							cdyc.setYcStates(yc);
							ycList.add(cdyc);*/
						}
						
					}
				
					//钢应变
					String monitor_id5=BrgMonitorDao.getIntance().getMonitor_id("局部响应", "钢应变", brgIdList.get(i).getBridge_id());
					if(monitor_id5!=null&&monitor_id5!=""&&!"352".equals(monitor_id5)){
						BrgMonitor brgMonitor5 = BrgMonitorDao.getIntance().getMonitor(monitor_id5);
						String[] chanelNums5 = brgMonitor5.getChanelNum().trim().split(",");
						List<Integer> sortList5=GetFileSizeDao.getInstance().getheightSort("brg_monitor_strains", brgNo,yc);
						if(sortList5.size()>0){
							for(int j=0;j<sortList5.size();j++){
								int index=sortList5.get(j);
								String addr=chanelNums5[index-1];
								String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
								describe=describe+"("+addr+")";
								BrgCdyc cdyc=new BrgCdyc();
								cdyc.setBrgName(brgName);
								cdyc.setYcAddr(describe);
								cdyc.setYcStates(yc);
								ycList.add(cdyc);
							}
							/*if(ycAddr.startsWith(",")){
								ycAddr=ycAddr.substring(1, ycAddr.length());
							}
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(ycAddr);
							cdyc.setYcStates(yc);
							ycList.add(cdyc);*/
						}
						
					}
					
					//温度
					String monitor_id6=BrgMonitorDao.getIntance().getMonitor_id("荷载与环境", "温度", brgIdList.get(i).getBridge_id());
					if(monitor_id6!=null&&monitor_id6!=""){
						BrgMonitor brgMonitor6 = BrgMonitorDao.getIntance().getMonitor(monitor_id6);
						String[] chanelNums6 = brgMonitor6.getChanelNum().trim().split(",");
						List<Integer> sortList6=GetFileSizeDao.getInstance().getheightSort("brg_monitor_temp", brgNo,yc);
						if(sortList6.size()>0){
							for(int j=0;j<sortList6.size();j++){
								int index=sortList6.get(j);
								String addr=chanelNums6[index-1];
								String describe=GetFileSizeDao.getInstance().getCdDescribe(brgIdList.get(i).getBridge_id(), addr);
								describe=describe+"("+addr+")";
								BrgCdyc cdyc=new BrgCdyc();
								cdyc.setBrgName(brgName);
								cdyc.setYcAddr(describe);
								cdyc.setYcStates(yc);
								ycList.add(cdyc);
							}
							/*if(ycAddr.startsWith(",")){
								ycAddr=ycAddr.substring(1, ycAddr.length());
							}
							BrgCdyc cdyc=new BrgCdyc();
							cdyc.setBrgName(brgName);
							cdyc.setYcAddr(ycAddr);
							cdyc.setYcStates(yc);
							ycList.add(cdyc);*/
						}
						
					}
				
				}
			}else if(mode.equals("g")){
				
			}else if(mode.equals("f")){
				
			}
			
			if (ycList.size() > 0) {
				ro.setSuccess("success");
				ro.setObj(ycList);
			}
			ro.ToJsp(response);
			return;
		}
		
		if (type.equals("initIndexBrgMonitorDataFull")) {
			String manage_id = request.getParameter("manage_id");
			manage_id= manage_id.replace("\"", "");
			if(null!=manage_id&&!"".equals(manage_id)){
				if(manage_id.startsWith("0000#")){
					manage_id="%%";
				}else{
					manage_id=manage_id.substring(0, 5)+"%";
				}
			}
			String mode = request.getParameter("mode");
			String tableName = Index2Servlet.getTableNameByMode("w");
			String tableName2 = Index2Servlet.getTableNameByMode("s");
			String tableName3 = Index2Servlet.getTableNameByMode("g");
			String tableName4 = Index2Servlet.getTableNameByMode("f");
			List<Connect> list =new ArrayList<>();
			List<Connect> lb = GetFileSizeDao.getInstance().initIndexModeMapDataFull("w",tableName,manage_id);
			List<Connect> lb2 = GetFileSizeDao.getInstance().initIndexModeMapDataFull("s",tableName2,manage_id);
			List<Connect> lb3 = GetFileSizeDao.getInstance().initIndexModeMapDataFull("g",tableName3,manage_id);
			List<Connect> lb4 = GetFileSizeDao.getInstance().initIndexModeMapDataFull("f",tableName4,manage_id);
			for(int i=0;i<lb.size();i++){
				Connect con=lb.get(i);
				list.add(con);
			}
			for(int i=0;i<lb2.size();i++){
				Connect con=lb2.get(i);
				list.add(con);
			}
			for(int i=0;i<lb3.size();i++){
				Connect con=lb3.get(i);
				list.add(con);
			}
			for(int i=0;i<lb4.size();i++){
				Connect con=lb4.get(i);
				list.add(con);
			}
 			Collections.sort(list,new Comparator<Connect>(){
				@Override
				public int compare(Connect o1, Connect o2) {
					if(o1.getMonth_count()==null){
						o1.setMonth_count("0%");
					}
					return o1.getCount().compareTo(o2.getCount());
				}}
	        );
			
	        Collections.sort(list,new Comparator<Connect>(){
				@Override
				public int compare(Connect o1, Connect o2) {
					return o1.getBridge_name().compareTo(o2.getBridge_name());
				}}
	        );
	        Iterator iterator_name=list.iterator();
	        while(iterator_name.hasNext()){
	        	Connect s=(Connect)iterator_name.next();
	            
	        }
			
			
			if (list.size() > 0) {
				ro.setSuccess("success");
				ro.setObj(list);
			}
			ro.ToJsp(response);
			return;
		}
		if (type.equals("initDataManageTable")) {
			String manage_id="%%";
			String brg_id = request.getParameter("brg_id");
			String tableName = Index2Servlet.getTableNameByMode("w");
			String tableName2 = Index2Servlet.getTableNameByMode("s");
			/*String tableName3 = Index2Servlet.getTableNameByMode("g");*/
			String tableName4 = Index2Servlet.getTableNameByMode("f");
			List<Connect> list =new ArrayList<>();
			List<Connect> lb = AppDao.getInstance().initDataManageTableNews("w",manage_id,brg_id);
			List<Connect> lb2 = AppDao.getInstance().initDataManageTableNews("s",manage_id,brg_id);
			/*List<Connect> lb3 = GetFileSizeDao.getInstance().initIndexModeMapDataFull("g",tableName3,manage_id);*/
			/*List<Connect> lb4 = GetFileSizeDao.getInstance().initDataManageTableNew("f",tableName4,manage_id,brg_id);*/
			for(int i=0;i<lb.size();i++){
				Connect con=lb.get(i);
				list.add(con);
			}
			for(int i=0;i<lb2.size();i++){
				Connect con=lb2.get(i);
				list.add(con);
			}
			/*for(int i=0;i<lb3.size();i++){
				Connect con=lb3.get(i);
				list.add(con);
			}*/
			/*for(int i=0;i<lb4.size();i++){
				Connect con=lb4.get(i);
				list.add(con);
			}*/
			
			
			
			if (list.size() > 0) {
				ro.setSuccess("success");
				ro.setObj(list);
			}
			ro.ToJsp(response);
			return;
		}
		if (type.equals("initIndexBrgMonitor")) {
			String manage_id = request.getParameter("manage_id");
			manage_id= manage_id.replace("\"", "");
			if(null!=manage_id&&!"".equals(manage_id)){
				if(manage_id.startsWith("0000#")){
					manage_id="%%";
				}else{
					manage_id=manage_id.substring(0, 5)+"%";
				}
			}
			String tableName = Index2Servlet.getTableNameByMode("w");
			String tableName2 = Index2Servlet.getTableNameByMode("s");
			String tableName3 = Index2Servlet.getTableNameByMode("g");
			String tableName4 = Index2Servlet.getTableNameByMode("f");
			List<Connect> list=new ArrayList<>();
			List<Connect> lb = GetFileSizeDao.getInstance().initIndexModeMap("w",tableName,manage_id);
			List<Connect> lb2 = GetFileSizeDao.getInstance().initIndexModeMap("s",tableName2,manage_id);
			List<Connect> lb3 = GetFileSizeDao.getInstance().initIndexModeMap("g",tableName3,manage_id);
			List<Connect> lb4 = GetFileSizeDao.getInstance().initIndexModeMap("f",tableName4,manage_id);
			for(int i=0;i<lb.size();i++){
				Connect con=lb.get(i);
				list.add(con);
			}
			for(int i=0;i<lb2.size();i++){
				Connect con=lb2.get(i);
				list.add(con);
			}
			for(int i=0;i<lb3.size();i++){
				Connect con=lb3.get(i);
				list.add(con);
			}
			for(int i=0;i<lb4.size();i++){
				Connect con=lb4.get(i);
				list.add(con);
			}
			
			
			Collections.sort(list,new Comparator<Connect>(){
				@Override
				public int compare(Connect o1, Connect o2) {
					return o1.getSort().compareTo(o2.getSort());
				}}
	        );
	       /* Collections.sort(list,new Comparator<Connect>(){
				@Override
				public int compare(Connect o1, Connect o2) {
					return o1.getBridge_name().compareTo(o2.getBridge_name());
				}}
	        );*/
			
	        Iterator iterator_name=list.iterator();
	        while(iterator_name.hasNext()){
	        	Connect s=(Connect)iterator_name.next();
	            System.out.println(s.toString());
	        }
	        List<Connect> list2 = chuliList(list);
			if (list.size() > 0) {
				ro.setSuccess("success");
				ro.setObj(list2);
			}
			ro.ToJsp(response);
			return;
		}
		
	}
		
		
	public static List<Connect> chuliList(List<Connect> list) {
		Map<String,List<Connect>> map = new LinkedHashMap();
		String bridge_id_list = "";
		int i=0;
		for(Connect connect : list){
			String bridge_id = connect.getBridge_id();
			if(i==0){
				List<Connect> list1 = new ArrayList<>();
				list1.add(connect);
				map.put(bridge_id, list1);
			}else{
				if(bridge_id_list.contains(bridge_id)){
					List<Connect> list2 = map.get(bridge_id);
					list2.add(connect);
					map.replace(bridge_id, list2);
				}else{
					List<Connect> list3 = new ArrayList<>();
					list3.add(connect);
					map.put(bridge_id, list3);
				}
			} 
			bridge_id_list+=bridge_id+",";
			i++;
		}
		List<Connect> allList = new ArrayList<>();
		for(String key :map.keySet()){
			List<Connect> connects = map.get(key);
			for(Connect connect : connects){
				allList.add(connect);
			}
		}
		return allList;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

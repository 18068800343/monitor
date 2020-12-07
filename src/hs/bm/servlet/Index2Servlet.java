package hs.bm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import com.alibaba.druid.stat.TableStat.Mode;

import hs.bm.dao.BrgCardDao;
import hs.bm.dao.BrgMonitorDao;
import hs.bm.bean.BrgCardAdminId;
import hs.bm.bean.BrgMap;
import hs.bm.bean.BrgMonitor;
import hs.bm.bean.Connect;
import hs.bm.dao.GetFileSizeDao;
import hs.bm.dao.Index2Dao;
import hs.bm.dao.StatisticsDao;
import hs.bm.dao.StructMgrDao;
import hs.bm.dao.UserDao;
import hs.bm.vo.BrgCdyc;
import hs.bm.vo.ResObj;
import hs.bm.vo.SysUsrPassRole;

public class Index2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public Index2Servlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		String log_user=(String) request.getSession().getAttribute("username");
		ResObj ro = new ResObj();
		if(type.equals("showName")){
			SysUsrPassRole user=(SysUsrPassRole) request.getSession().getAttribute("user");
			String uname="";
			if(user.getUsr_no()!=null&&!user.getUsr_no().equals("")){
				uname=UserDao.getInstance().getUserName(user.getUsr_no());
			}else{
				uname=UserDao.getInstance().getUserName(user.getUsr_id());
			}
			ro.setObj(uname);
			ro.ToJsp(response);
			return;
		}
		
		if(type.equals("loginOut")){
			request.getSession().removeAttribute("user");
			ro.ToJsp(response);
			return;
		}
		
		if(type.equals("getcdNum")){
			int num=0;
			SysUsrPassRole user=(SysUsrPassRole) request.getSession().getAttribute("user");
			String manage_id="";
			if(user.getUsr_no()!=null&&!user.getUsr_no().equals("")){
				manage_id=UserDao.getInstance().getDepartmentId(user.getUsr_no());
				manage_id=manage_id.split("#")[0]+"%";
			}else{
				manage_id="%%";
			}
			num=BrgCardDao.getInstance().getcdNum(manage_id);
			ro.setObj(num);
			ro.ToJsp(response);
			return;
		}
		
		if("getBrgDate".equals(type)){
            String highway_id=request.getParameter("highway_id");
			String manage_id=request.getParameter("manage_id");
			String section_id=request.getParameter("section_id");
			String zone_id=request.getParameter("zone_id");
			
			Map<String,List> map=Index2Dao.getInstance().getBrgDate(highway_id,manage_id,section_id,zone_id);
			if(map!=null){
				ro.setObj(map);
				ro.setSuccess("success");
				ro.setError(0);
			}
			ro.ToJsp(response);
			return;
		}
		if("getEvaDate".equals(type)){
			String highway_id=request.getParameter("highway_id");
			String manage_id=request.getParameter("manage_id");
			String section_id=request.getParameter("section_id");
			String zone_id=request.getParameter("zone_id");
			
			Map<String,List> map=Index2Dao.getInstance().getEvaDate(highway_id,manage_id,section_id,zone_id);
			if(map!=null){
				ro.setObj(map);
				ro.setSuccess("success");
				ro.setError(0);
			}
			ro.ToJsp(response);
			return;
		}
		if("initDisease".equals(type)){
			String bridgeType=request.getParameter("bridgeType");
			String componentType=request.getParameter("componentType");
			String disease=request.getParameter("disease");
			String diseaseType=request.getParameter("diseaseType");
			String highway_id=request.getParameter("highway_id");
			String manage_id=request.getParameter("manage_id");
			String section_id=request.getParameter("section_id");
			String zone_id=request.getParameter("zone_id");
			Map<String,List<Integer>> map=Index2Dao.getInstance().getDiseaseCount(bridgeType, componentType, disease, diseaseType,highway_id,manage_id,section_id,zone_id);
			ro.setObj(map);
			ro.setSuccess("success");
			ro.setError(0);
			ro.ToJsp(response);
			return;
		}
		
		if("initEvaType".equals(type)){
			String highway_id=request.getParameter("highway_id");
			String manage_id=request.getParameter("manage_id");
			String section_id=request.getParameter("section_id");
			String zone_id=request.getParameter("zone_id");
			Map<String,List> map=Index2Dao.getInstance().getEvaOf0411(highway_id,manage_id,section_id,zone_id);
			if(map!=null){
				ro.setObj(map);
				ro.setSuccess("success");
				ro.setError(0);
			}
			ro.ToJsp(response);
			return;
		}
		
		if (type.equals("initIndexBrg")) {
			String prj_id = request.getParameter("prj_id");
			String eva_type=request.getParameter("eva_type");
			String highway_id = request.getParameter("highway_id");
			String manage_id = request.getParameter("manage_id");
			String section_id = request.getParameter("section_id");
			String zone_id = request.getParameter("zone_id");
			List<BrgCardAdminId> lb =null;
			
			lb = Index2Dao.getInstance().initIndexBrg(prj_id, highway_id, manage_id, section_id,
						zone_id,eva_type);
			
			
			if (lb.size() > 0) {
				ro.setSuccess("success");
				ro.setObj(lb);
			}
			ro.ToJsp(response);
			return;
		}
		if (type.equals("initIndexBrgMonitor")) {
			HttpSession session = request.getSession();
			String manage_id = (String) session.getAttribute("manage_id");
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
		
		if (type.equals("initIndexBrgMonitorBdMap")) {
			HttpSession session = request.getSession();
			String manage_id = (String) session.getAttribute("manage_id");
			if(null!=manage_id&&!"".equals(manage_id)){
				if(manage_id.startsWith("0000#")){
					manage_id="%%";
				}else{
					manage_id=manage_id.substring(0, 5)+"%";
				}
			}
			Map<String,BrgMap> brgMap=BrgCardDao.getInstance().selectMapBrgByManageId(manage_id);
			
			String tableName = Index2Servlet.getTableNameByMode("w");
			String tableName2 = Index2Servlet.getTableNameByMode("s");
			/*String tableName3 = Index2Servlet.getTableNameByMode("g");*/
			String tableName4 = Index2Servlet.getTableNameByMode("f");
			List<Connect> lb = GetFileSizeDao.getInstance().initIndexModeMap("w",tableName,manage_id);
			List<Connect> lb2 = GetFileSizeDao.getInstance().initIndexModeMap("s",tableName2,manage_id);
			/*List<Connect> lb3 = GetFileSizeDao.getInstance().initIndexModeMap("g",tableName3,manage_id);*/
			List<Connect> lb4 = GetFileSizeDao.getInstance().initIndexModeMap("f",tableName4,manage_id);
			for(int i=0;i<lb.size();i++){
				Connect con=lb.get(i);
				if(!"1".equals(con.getBgco())){
				 BrgMap brgMap2 = brgMap.get(con.getBridge_id());
				 Map<String, String> modeMap = brgMap2.getModeStatus();
				 if(null==modeMap){
					 modeMap=new HashMap<>();
				 }
				 modeMap.put("w", con.getBgco());
				 brgMap2.setModeStatus(modeMap);
				 brgMap.replace(con.getBridge_id(), brgMap2);
				}
			}
			for(int i=0;i<lb2.size();i++){
				Connect con=lb2.get(i);
				if(!"1".equals(con.getBgco())){
					 BrgMap brgMap2 = brgMap.get(con.getBridge_id());
					 Map<String, String> modeMap = brgMap2.getModeStatus();
					 if(null==modeMap){
						 modeMap=new HashMap<>();
					 }
					 modeMap.put("s", con.getBgco());
					 brgMap2.setModeStatus(modeMap);
					 brgMap.replace(con.getBridge_id(), brgMap2);
				}
			}
			/*for(int i=0;i<lb3.size();i++){
				Connect con=lb3.get(i);
				list.add(con);
			}*/
			for(int i=0;i<lb4.size();i++){
				Connect con=lb4.get(i);
				if(!"1".equals(con.getBgco())){
					 BrgMap brgMap2 = brgMap.get(con.getBridge_id());
					 Map<String, String> modeMap = brgMap2.getModeStatus();
					 if(null==modeMap){
						 modeMap=new HashMap<>();
					 }
					 modeMap.put("f", con.getBgco());
					 brgMap2.setModeStatus(modeMap);
					 brgMap.replace(con.getBridge_id(), brgMap2);
				}
			}
			
			
			if (brgMap.size() > 0) {
				ro.setSuccess("success");
				ro.setObj(brgMap);
			}
			ro.ToJsp(response);
			return;
		}
		if(type.equals("initIndexBrgMonitorYj")){
			HttpSession session = request.getSession();
			String manage_id = (String) session.getAttribute("manage_id");
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
		if(type.equals("initYjNew")){
			HttpSession session = request.getSession();
			String manage_id = (String) session.getAttribute("manage_id");
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
		if (type.equals("initIndexBrgMonitorCd")) {
			/*String tableName = Index2Servlet.getTableNameByMode(mode);*/
			HttpSession session = request.getSession();
			String manage_id = (String) session.getAttribute("manage_id");
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
		if (type.equals("initCdycNew")) {
			HttpSession session = request.getSession();
			String manage_id = (String) session.getAttribute("manage_id");
			if(null!=manage_id&&!"".equals(manage_id)){
				if(manage_id.startsWith("0000#")){
					manage_id="%%";
				}else{
					manage_id=manage_id.substring(0, 5)+"%";
				}
			}
			List<BrgCdyc> ycList =new ArrayList<>();
			ycList = GetFileSizeDao.getInstance().getabnormalYcNew("brg_monitor_abnormal_copy",manage_id);
			if (ycList.size() > 0) {
				ro.setSuccess("success");
				ro.setObj(ycList);
			}
			ro.ToJsp(response);
			return;
		}
		if (type.equals("initIndexBrgMonitorDataFull")) {
			HttpSession session = request.getSession();
			String manage_id = (String) session.getAttribute("manage_id");
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
		
		if (type.equals("initDataFullNew")) {
			HttpSession session = request.getSession();
			String manage_id = (String) session.getAttribute("manage_id");
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
				if(!"淮安大桥".equals(con.getBridge_name())) {
					list.add(con);
				}
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
		if (type.equals("initDataFullEcharts")) {
			try {
				String manage_id="%%";
				String brg_id = request.getParameter("brg_id");
				String data_type = request.getParameter("data_type");
				Map<String, List<String>> map = GetFileSizeDao.getInstance().getDataFullMap(brg_id,data_type);
				Map<String, Object> mapResult= new HashMap<>();
				String mode ="";
				for (String key : map.keySet()) { 
					  System.out.println("Key = " + key); 
					  mode = key;
					  break;
				} 
				mapResult.put("x", GetFileSizeDao.getInstance().getDataFullTimeByTableName(brg_id, mode, data_type));
				mapResult.put("map", map);
				if (map!=null) {
					ro.setSuccess("success");
					ro.setObj(mapResult);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ro.ToJsp(response);
			return;
		}
		
		if (type.equals("initDataManageTable")) {
			String manage_id="%%";
			String brg_id = request.getParameter("brg_id");
			String tableName = Index2Servlet.getTableNameByMode("w");
			String tableName2 = Index2Servlet.getTableNameByMode("s");
			String tableName3 = Index2Servlet.getTableNameByMode("g");
			String tableName4 = Index2Servlet.getTableNameByMode("f");
			List<Connect> list =new ArrayList<>();
			List<Connect> lb = GetFileSizeDao.getInstance().initDataManageTableNew("w",tableName,manage_id,brg_id);
			List<Connect> lb2 = GetFileSizeDao.getInstance().initDataManageTableNew("s",tableName2,manage_id,brg_id);
			List<Connect> lb3 = GetFileSizeDao.getInstance().initDataManageTableNew("g",tableName3,manage_id,brg_id);
			/*List<Connect> lb4 = GetFileSizeDao.getInstance().initDataManageTableNew("f",tableName4,manage_id,brg_id);*/
			for(int i=0;i<lb.size();i++){
				Connect con=lb.get(i);
				list.add(con);
			}
			for(int i=0;i<lb2.size();i++){
				Connect con=lb2.get(i);
				list.add(con);
			}
			if(null!=lb3&&lb3.size()>0){
					Connect con=lb3.get(0);
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
		if (type.equals("getProject")) {
			HttpSession session = request.getSession();
			String userRole = (String) session.getAttribute("userRole");
			String orgid = (String) session.getAttribute("orgid");
			String norm=request.getParameter("norm");
			List<Map<String, String>> ll = new ArrayList<>();
			if ("orgAdmin".equals(userRole) || "orgCharge".equals(userRole) /*|| "superAdmin".equals(userRole)*/
					|| "orgDuty".equals(userRole) || "orgEngineer".equals(userRole)) {
				String zone= orgid.substring(0,4)+"%";
				ll = Index2Dao.getInstance().getProject2(norm,zone);
			} else {
				String manage_id=request.getParameter("manage_id");
				ll = Index2Dao.getInstance().getProject1(norm,manage_id);
			}
			ro.setSuccess("success");
			ro.setError(0);
			ro.setObj(ll);
			ro.ToJsp(response);
			return;
		}
		
		
	}
	public static String getTableNameByMode(String mode) {
		String tableName = "";
		if("w".equals(mode)){
			tableName = "brg_weight_statistic";
		}else if("s".equals(mode)){
			tableName = "brg_health_statistic";
		}else if("g".equals(mode)){
			tableName = "brg_gps_statistic";
		}else if("f".equals(mode)){
			tableName = "brg_wind_statistic";
		}
		return tableName;
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

	public static Map<String,List<Connect>> chuliListForMap(List<Connect> list) {
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
		return map;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

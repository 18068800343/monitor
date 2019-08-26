package hs.bm.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.StaticBucketMap;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

import hs.bm.bean.BrgCardAdminId;
import hs.bm.bean.BrgMonitor;
import hs.bm.bean.BrgMonitorTemp;
import hs.bm.dao.BrgMonitorDao;
import hs.bm.dao.GetFileSizeDao;
import hs.bm.vo.BrgCdyc;

public class AutoSetCdStatus implements Runnable{

	private static String getStatusByChinese(String type){
		if("正常".equals(type.trim())){
			return "0";
		}else if("异常".equals(type.trim())){
			return "1";
		}else if("黄色告警".equals(type.trim())){
			return "2";
		}else if("红色告警".equals(type.trim())){
			return "3";
		}else{
			return "0";
		}
	}
	@Override
	public void run() {
			String manage_id = "%%";
			List<BrgCdyc> ycList =new ArrayList<>();
			List<BrgCardAdminId> brgIdList=GetFileSizeDao.getInstance().getBrgIdByMode("s",manage_id);
			String yc = "正常";
			
			for(int i=0;i<brgIdList.size();i++){
				//索力
				String brgNo=GetFileSizeDao.getInstance().getBrgNo(brgIdList.get(i).getBridge_id());
				String brgName=brgIdList.get(i).getBridge_name();
				String monitor_id=BrgMonitorDao.getIntance().getMonitor_id("局部响应", "索力", brgIdList.get(i).getBridge_id());
				if(monitor_id!=null&&monitor_id!=""){
					BrgMonitor brgMonitor = BrgMonitorDao.getIntance().getMonitor(monitor_id);
					String[] chanelNums = brgMonitor.getChanelNum().trim().split(",");
					List<BrgMonitorTemp> sortList=GetFileSizeDao.getInstance().getheightSortStatus("brg_monitor_cableforce", brgNo);
					if(sortList.size()>0){
						for(int j=0;j<sortList.size();j++){
							int index=sortList.get(j).getSort();
							yc = sortList.get(j).getType();
							String status = getStatusByChinese(yc);
							String addr=chanelNums[index-1];
							GetFileSizeDao.getInstance().updateCdStatus(status, brgIdList.get(i).getBridge_id(), addr);
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
				}
				
				//动位移
				String monitor_id2=BrgMonitorDao.getIntance().getMonitor_id("整体响应", "动位移", brgIdList.get(i).getBridge_id());
				if(monitor_id2!=null&&monitor_id2!=""){
					BrgMonitor brgMonitor2 = BrgMonitorDao.getIntance().getMonitor(monitor_id2);
					String[] chanelNums2 = brgMonitor2.getChanelNum().trim().split(",");
					List<BrgMonitorTemp> sortList2=GetFileSizeDao.getInstance().getheightSortStatus("brg_monitor_dynadisp", brgNo);
					if(sortList2.size()>0){
						for(int j=0;j<sortList2.size();j++){
							int index=sortList2.get(j).getSort();
							yc = sortList2.get(j).getType();
							String status = getStatusByChinese(yc);
							String addr=chanelNums2[index-1];
							GetFileSizeDao.getInstance().updateCdStatus(status, brgIdList.get(i).getBridge_id(), addr);
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
					List<BrgMonitorTemp> sortList3=GetFileSizeDao.getInstance().getheightSortStatus("brg_monitor_staticdisp", brgNo);
					if(sortList3.size()>0){
						for(int j=0;j<sortList3.size();j++){
							int index=sortList3.get(j).getSort();
							yc = sortList3.get(j).getType();
							String status = getStatusByChinese(yc);
							String addr=chanelNums3[index-1];
							GetFileSizeDao.getInstance().updateCdStatus(status, brgIdList.get(i).getBridge_id(), addr);
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
					List<BrgMonitorTemp> sortList4=GetFileSizeDao.getInstance().getheightSortStatus("brg_monitor_strainc", brgNo);
					if(sortList4.size()>0){
						for(int j=0;j<sortList4.size();j++){
							int index=sortList4.get(j).getSort();
							yc = sortList4.get(j).getType();
							String status = getStatusByChinese(yc);
							String addr=chanelNums4[index-1];
							GetFileSizeDao.getInstance().updateCdStatus(status, brgIdList.get(i).getBridge_id(), addr);
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
					List<BrgMonitorTemp> sortList5=GetFileSizeDao.getInstance().getheightSortStatus("brg_monitor_strains", brgNo);
					if(sortList5.size()>0){
						for(int j=0;j<sortList5.size();j++){
							int index=sortList5.get(j).getSort();
							yc = sortList5.get(j).getType();
							String status = getStatusByChinese(yc);
							String addr=chanelNums5[index-1];
							GetFileSizeDao.getInstance().updateCdStatus(status, brgIdList.get(i).getBridge_id(), addr);;
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
					List<BrgMonitorTemp> sortList6=GetFileSizeDao.getInstance().getheightSortStatus("brg_monitor_temp", brgNo);
					if(sortList6.size()>0){
						for(int j=0;j<sortList6.size();j++){
							int index=sortList6.get(j).getSort();
							yc = sortList6.get(j).getType();
							String status = getStatusByChinese(yc);
							String addr=chanelNums6[index-1];
							GetFileSizeDao.getInstance().updateCdStatus(status, brgIdList.get(i).getBridge_id(), addr);
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
		}
	public static void main(String[] args) {
		new AutoSetCdStatus().run();
	}
}

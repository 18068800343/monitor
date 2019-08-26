package hs.bm.server;


import java.text.SimpleDateFormat;
import java.util.Date;

import hs.bm.util.CMDUtil;

public class AutoBackUpDataBase implements Runnable{
	
	private String user;
	
	public AutoBackUpDataBase(String user) {
		this.user = user;
	}

	@Override
	public void run() {
		System.out.println("准备运行备份命令");
		String path = CMDUtil.buildSqlBack();
	}
	
	
	public static void main(String[] args){
		//new Thread(new AutoBackUpDataBase("系统")).start();
	}
}


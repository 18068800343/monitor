package cn.org.hsxx.matlab;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import cn.org.hsxx.util.FileUtils;
import cn.org.hsxx.util.IntoDataBase;
import cn.org.hsxx.zutil.DeleteFileUtil;



public class FtpUtil implements Runnable{
    private static int i = 0;
    private static String nameList = "";
	private static boolean state=false;
	private static FTPClient ftpClient = new FTPClient();
	public boolean connect(String hostname, String username, String password) throws IOException
	{
		// 连接到FTP服务器
		ftpClient.connect(hostname);
		ftpClient.setConnectTimeout(50000);
		ftpClient.setDataTimeout(50000);
		// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode()))
		{
			if (ftpClient.login(username, password))
			{
				return true;
			}
		}
		disconnect();
		return false;
	}
	
	public void disconnect() throws IOException
	{
		if (ftpClient.isConnected())
		{
			ftpClient.disconnect();
		}
	}

	public void downloadFile(String localFile){
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"开始.....");
		
		boolean bl=false;
		try {
			bl=connect("10.154.39.197", "htbms", "htbms123");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(bl){
			System.out.println("连接ftp服务器成功");
		}else{
			System.out.println("连接ftp服务器失败");
			return;
		}
		try {
			ftpClient.changeWorkingDirectory("/"+"/");
			FTPFile[] files =ftpClient.listFiles();
			System.out.println(files);
			int j = i;
			for(FTPFile file:files){
				System.out.println(file.getName());
				
				if(file.getName().endsWith("S")){
					
					ftpClient.changeWorkingDirectory("/"+file.getName()+"/");
					FTPFile[] filess =ftpClient.listFiles();
					File f=new File(localFile+File.separator+file.getName());
					f.mkdirs();
					List<String> filessNameList =  getDirNameList(filess);
					Collections.sort(filessNameList);  
					String nearFileName ="";
					if(filessNameList.size()>=1){
						nearFileName=filessNameList.get(filessNameList.size()-1);
					}
					for(FTPFile fi:filess){
						String fileName = file.getName();//文件夹名称
						String finame = fi.getName();//文件名称
						String[] strings = finame.split("\\.");
						if(strings.length>1){
						long minite=-1;
						try {
							minite = TimeUtil.getTimeLong(strings[0]);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(i==0&&fi.getName().replace(".rar","").equals(nearFileName)){
							File localHostFile = new File(localFile+File.separator+file.getName()+File.separator+fi.getName());
							OutputStream is = new FileOutputStream(localHostFile); 
							ftpClient.enterLocalPassiveMode();
							boolean dl=ftpClient.retrieveFile(fi.getName(), is);
							is.flush();
							is.close();
							nameList+=nearFileName+",";//所有监测文件夹里面最近的一个文件名称
							i++;
						}else{
							if(fi.getName().replace(".rar","").equals(nearFileName)&&!nameList.contains((fi.getName().replace(".rar","")))){
								File localHostFile = new File(localFile+File.separator+file.getName()+File.separator+fi.getName());
								OutputStream is = new FileOutputStream(localHostFile); 
								ftpClient.enterLocalPassiveMode();
								boolean dl=ftpClient.retrieveFile(fi.getName(), is);
								is.flush();
								is.close();
								nameList+=nearFileName+",";//所有监测文件夹里面最近的一个文件名称
							 }
						}
						
						}
					}
			     }
			}  
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	public int uploadDatabase(String fileName,int fileSize,String bridge_id){
		int i=0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			String startTime=fileName.split("\\.")[0];
			String startTime2=sdf2.format(sdf.parse(startTime).getTime());
			System.out.println(startTime2);
			String endTime=sdf2.format(sdf2.parse(startTime2).getTime()+1800000);
			System.out.println(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	public static boolean uploadFile(
            String path, // FTP服务器保存目录  
            String orginfilename //   输入流文件名
    ){  
        boolean success = false;  
        FTPClient ftp = new FTPClient();  
        ftp.setControlEncoding("GBK");  
        try {  
            int reply;  
            ftp.connect("115.159.78.246");// 连接FTP服务器  
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
            ftp.login("hsxx", "hsxx123123");// 登录  
            reply = ftp.getReplyCode();  
            if (!FTPReply.isPositiveCompletion(reply)) {  //判断登录是否合法
                ftp.disconnect();  //不合法 断开连接
                System.out.println("登录失败");
                return success;  
            }
            
            File file=new File(orginfilename);
            File [] list=file.listFiles();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);  
            ftp.makeDirectory(path);
            ftp.changeWorkingDirectory(path); 
            if(list==null){
            	String filename=orginfilename.split("\\\\")[orginfilename.split("\\\\").length-1];
            	FileInputStream in = new FileInputStream(new File(orginfilename));
            	System.out.println(filename);
            	ftp.storeFile(filename, in); 
            	in.close(); 
            }else{
            	for(int i=0;i<list.length;i++){ 
            		File single = list[i];
            		if(single.isDirectory()){
            			ftp.makeDirectory(single.getName());
            			ftp.changeWorkingDirectory(single.getName());
            			File [] list2=single.listFiles();
            			for(int j=0;j<list2.length;j++){
            				File single2=list2[j];
            				String filename=single2.getName();
            				System.out.println(filename);
            				FileInputStream in = new FileInputStream(single2);
            				ftp.storeFile(filename, in);
            				in.close(); 
            				single2.delete();
            			}
            			ftp.changeToParentDirectory();
            		}else{
        				String filename=single.getName();
        				System.out.println(filename);
        				FileInputStream in = new FileInputStream(single);
        				ftp.storeFile(filename, in);  
        				in.close();
        				single.delete();
            		}
            		
            	}
            }
            ftp.logout();  
            success = true; 
            
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {   
            if (ftp.isConnected()) {  
                try {  
                    ftp.disconnect();  
                } catch (IOException ioe) {  
                }  
            }  
        }  
        System.out.println("成功");
        return success;  
    }  
	
	public static void matlabRun(String brg_no,String matlab_name,File file){
		String comd ="D:\\matlabUtil\\程序调用\\TimProcess.exe 2"+" D:\\work\\"+brg_no+" D:\\matlabUtil\\程序调用\\SHM_Mainfunc_"+matlab_name+".exe";
		try {
			if(!brg_no.equals("G25LR1660320282S")){
				if(file.length()==0){
					file.delete();
				}
				Process process = Runtime.getRuntime().exec(comd);
				process.waitFor();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> getDirNameList(FTPFile[] filess){
		List<String> nameList = new ArrayList<>();
		for(FTPFile fi:filess){
			nameList.add(fi.getName().replace(".rar", ""));
		}
		 return nameList;
	}
	
	@Override
	public void run() {
		/*Timer time=new Timer();
		time.schedule(new FtpUtil(),30000);*/
		FtpUtil f=new FtpUtil();
		f.downloadFile("D:\\work");
		//创建一个集合来接收获得的文件
		List<File> list = new ArrayList<File>();
		
		//定义一个路径，这里是拿D盘做例子
		String path="D:\\work";
		
		//调用工具类的静态方法，返回的是一个集合对象
		list = FileUtils.getAllFiles(path);

		// 获取D盘下的所有的文件（包括文件和文件夹）
		for (File file : list) {
			String filePath = file.getPath();
			String fileName = file.getName();
			
			String[] arr = filePath.split("\\\\");
			if(arr.length==4){
				String dataDirPath = arr[0]+"\\"+arr[1]+"\\"+arr[2];
				String brg_no = arr[2].replace("S","");
				if(filePath.endsWith("rar")){
					System.out.println(filePath);
					System.out.println(fileName);
					matlabRun(arr[2],brg_no,file);
					List<File> listDataDir = new ArrayList<File>();
					listDataDir = FileUtils.getAllFiles(dataDirPath);
					for(File intoDbFile : listDataDir){
						IntoDataBase.fileToDB(intoDbFile);
						if(intoDbFile.exists()){
							intoDbFile.delete();
						}
						
					}
					if(listDataDir.size()>0&&listDataDir.get(0).exists()){
						listDataDir.get(0).delete();
						DeleteFileUtil.deleteDirectory(listDataDir.get(0).getPath());
					}
					//DeleteFileUtil.deleteDirectory(dataDirPath);
				}
				//IntoDataBase.fileToDB(file);
			}
			//System.out.println(arr.toString());
		}
	}

	
}


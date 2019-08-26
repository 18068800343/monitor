
package cn.org.hsxx.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


import cn.org.hsxx.bean.BrgHealthStatistic;
import cn.org.hsxx.dao.BrgHealthStatisticDao;

public class FtpUtil extends TimerTask{
     
	private static boolean state=false;
	private static FTPClient ftpClient = new FTPClient();
	public boolean connect(String hostname, String username, String password) throws IOException
	{
		// 连接到FTP服务器
		ftpClient.connect(hostname);
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

	public void downloadFile(String localFile,String path){
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"开始.....");
		boolean bl=false;
		try {
			bl=connect("115.159.109.101", "htbms", "htbms123");
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
			for(FTPFile file:files){
				System.out.println(file.getName());
				
				if(file.getName().toUpperCase().substring(file.getName().length()-1, file.getName().length()).equals("F")){
					ftpClient.changeWorkingDirectory("/"+file.getName()+"/");
					FTPFile[] filess =ftpClient.listFiles();
					File f=new File(localFile+File.separator+file.getName());
					f.mkdirs();
					for(FTPFile fi:filess){
						File localHostFile = new File(localFile+File.separator+file.getName()+File.separator+fi.getName()); 
						OutputStream is = new FileOutputStream(localHostFile); 
						boolean dl=ftpClient.retrieveFile(fi.getName(), is);
						is.close();
						FtpUtil ff=new FtpUtil(); //保存数据库
						String brgId=file.getName().substring(0,file.getName().length()-1);
						System.out.println(brgId);
		                ff.uploadDatabase(fi.getName(), (int) fi.getSize()/1024,brgId);
		                File fileName=new File(file.getName());
		                fileName.delete();
					}
				}
			}  
			FtpUtil ff=new FtpUtil();
			ff.uploadFile(path, localFile);
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
			BrgHealthStatistic bhs=new BrgHealthStatistic();
			bhs.setR_id(UUID.randomUUID().toString());
			bhs.setBridge_id(bridge_id);
			bhs.setData_file(fileName);
			bhs.setStart_time(startTime2);
			bhs.setEnd_time(endTime);
			bhs.setFile_size(fileSize);
			bhs.setFile_time(sdf.format(new Date()));
			i=BrgHealthStatisticDao.getInstance().addBrgHealthStatistic(bhs);
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
	
	@Override
	public void run() {
		FtpUtil f=new FtpUtil();
		f.downloadFile("D:\\work","sjbf");
	}

	
	public static void main(String[] args) throws ParseException {
		Timer time=new Timer();
		time.schedule(new FtpUtil(),30000);
	}

	
}


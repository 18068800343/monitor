
package cn.org.hsxx.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class UploadFTP {
	/** 
     * Description: 向FTP服务器上传文件 
     * @Version      1.0 
     * @param url FTP服务器hostname 
     * @param username FTP登录账号 
     * @param password  FTP登录密码 
     * @param path  FTP服务器保存目录 
     * @param filename  上传到FTP服务器上的文件名 
     * @param input   输入流 
     * @return 成功返回true，否则返回false * 
     */  
    public static boolean uploadFile(
            String path, // FTP服务器保存目录  
            String orginfilename //   输入流文件名
    ){  
        boolean success = false;  
        FTPClient ftp = new FTPClient();  
        ftp.setControlEncoding("GBK");  
        try {  
            int reply;  
            ftp.connect("115.159.109.101");// 连接FTP服务器  
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
            ftp.login("htbms", "htbms123");// 登录  
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
            			}
            			ftp.changeToParentDirectory();
            		}else{
        				String filename=single.getName();
        				System.out.println(filename);
        				FileInputStream in = new FileInputStream(single);
        				ftp.storeFile(filename, in);  
        				in.close();
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
    
    public static void main(String[] args) {
		UploadFTP ftp=new UploadFTP();
		ftp.uploadFile("G15320705L0010","D:\\work");
	}

}

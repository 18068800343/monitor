package cn.org.hsxx.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IntoDataBase {
	/**
	 * 对文件/文件夹的基本操作
	 * 
	 * 有一点值得要注意的是getName和getPath、getAbsolutePath的区别
	 * getName输出的只是文件名，不包括文件路径
	 * （java里面）getPath、getAbsolutePath输出的是文件路径加文件名 
	 * */
	public static void main(String[] args) throws IOException {
		
		//创建一个集合来接收获得的文件
		List<File> list = new ArrayList<File>();
		
		//定义一个路径，这里是拿D盘做例子
		String path="E:\\health_data_2";
		
		//调用工具类的静态方法，返回的是一个集合对象
		list = FileUtils.getAllFiles(path);

		// 获取D盘下的所有的文件（包括文件和文件夹）
		for (File file : list) {
			String path1 = file.getPath();
			String[] arr = path1.split("\\\\");
			if(arr.length>5){
				System.out.println(file.getPath());
				fileToDB(file);
			}
			//System.out.println(arr.toString());
		}

	}
	//E:\abcd\G15320902L0020S\2017-11-02 11-50-57\dynadisp.txt
	//[E:,abcd,G15320902L0020S,2017-11-02 11-50-57,dynadisp.txt]
	//E:\weight_data\永安河大桥\2017-10-25 13-03-05\ratio_ovlo_com.txt
	public static void fileToDB(File file){
		String path = file.getPath();
		String name = file.getName();
		String[] arr = path.split("\\\\");
		String brg_name = arr[2];
		String date = arr[2];
			if(path.endsWith("txt")&&!name.contains("-")){
				String value =	ReadFileUtil.DateToDB(file,arr,name);
				System.out.println(value);
				
			}
	}
}

package ezhuanbing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class FtpDownload {

	/**
	 * @description:以非遍历ftp目录的方式下载文件,通过dos命令的方式直接进行文件下载,加快了下载速度
	 * @author RickyLee
	 * @time:2013-06-05 10:00
	 */

	// ftp地址
	private String ftp1Ip = null;

	// ftp用户名
	private String ftp2User = null;

	// ftp密码
	private String ftp3Pass = null;

	// ftp上文件存放的路径
	private String ftp4Dir = null;

	// ftp上的文件名
	private String ftp5FileName = null;

	// 存放到本地的文件的全路径
	private String localFullFilePath = null;

	public String getFtp1Ip() {
		return ftp1Ip;
	}

	public void setFtp1Ip(String ftp1Ip) {
		this.ftp1Ip = ftp1Ip;
	}

	public String getFtp2User() {
		return ftp2User;
	}

	public void setFtp2User(String ftp2User) {
		this.ftp2User = ftp2User;
	}

	public String getFtp3Pass() {
		return ftp3Pass;
	}

	public void setFtp3Pass(String ftp3Pass) {
		this.ftp3Pass = ftp3Pass;
	}

	public String getFtp4Dir() {
		return ftp4Dir;
	}

	public void setFtp4Dir(String ftp4Dir) {
		this.ftp4Dir = processDir(ftp4Dir);
	}

	public String getFtp5FileName() {
		return ftp5FileName;
	}

	public void setFtp5FileName(String ftp5FileName) {
		this.ftp5FileName = ftp5FileName;
	}

	public String getLocalFullFilePath() {
		return localFullFilePath;
	}

	public void setLocalFullFilePath(String localFullFilePath) {
		this.localFullFilePath = localFullFilePath;
	}

	// 开始执行下载任务,执行成功返回true,否则返回false
	public boolean run() {
		boolean b = false;
		// 根据bean的信息生成bat文件,并返回bat文件的物理路径
		String batPath = creatBat();
		// 调用bat
		b = runBat(batPath);
		return b;
	}

	// 执行bat文件
	private boolean runBat(String batPath) {
		boolean b = false;
		String command = "cmd /c start "+batPath;// exe,bat文件名OR DOS命令
		String text=null;
		try {
			Process proc = Runtime.getRuntime().exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(proc
					.getInputStream()));
			while ((text = in.readLine()) != null) {
				System.out.println(text); // 输出测试
			}
		} catch (IOException ioError) {
			ioError.printStackTrace();
			System.exit(0);
		}
		if (new File(localFullFilePath).exists())b = true;
		return b;
	}

	// 根据用户提供的信息生成bat信息
	private String creatBat() {
		String path = ApplicationResource.getValueByKey("batPath") +"FtpDownload_"+new Date().getTime()+".bat";
		File file = new File(path.substring(0, path.lastIndexOf("/")));
		if (!file.exists())
			file.mkdirs();
		try {
			FileWriter writer = new FileWriter(path, false);
			writer.write("@echo on\n");
			writer.write("set ftpfilename=autoftp.cfg\n");
			writer.write("echo open " + ftp1Ip + " >\"%ftpfilename%\"\n");
			writer.write("echo " + ftp2User + " >>\"%ftpfilename%\"\n");
			writer.write("echo " + ftp3Pass + " >>\"%ftpfilename%\"\n");
			writer.write("echo bin >>\"%ftpfilename%\"\n");
			writer.write("echo cd " + ftp4Dir + " >>\"%ftpfilename%\"\n");
			writer.write("echo get " + ftp5FileName + " " + localFullFilePath
					+ " >>\"%ftpfilename%\"\n");
			writer.write("echo bye >>\"%ftpfilename%\"\n");
			writer.write("ftp -s:\"%ftpfilename%\"\n");
			writer.write("exit");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	// 对ftp的Dir进行格式化处理
	private String processDir(String fdir) {
		fdir = fdir.replace("/", "\\").replace("\\\\", "\\");
		if (fdir.startsWith("\\"))
			fdir = fdir.substring(fdir.indexOf("\\") + 1);
		if (fdir.endsWith("\\"))
			fdir = fdir.substring(0, fdir.lastIndexOf("\\"));
		return fdir;
	}

	// 主函数,用于测试
	public static void main(String[] args) {
	/*	FtpDownload fd = new FtpDownload();
		fd.setFtp1Ip("172.18.3.3");
		fd.setFtp2User("mios");
		fd.setFtp3Pass("m92102007");
		fd.setFtp4Dir("bcbj\\zyt\\zds");
		fd.setFtp5FileName("jqyb06.txt");
		fd.setLocalFullFilePath("c:/param/jqyb06.txt");
		System.out.println(fd.run());*/
		
		FtpDownload fd = new FtpDownload();
		fd.setFtp1Ip("121.42.211.90");
		fd.setFtp2User("testupload");
	
		fd.setFtp4Dir("bcbj\\zyt\\zds");
		fd.setFtp5FileName("jqyb06.txt");
		fd.setLocalFullFilePath("c:/param/jqyb06.txt");
		System.out.println(fd.run());
		
	}
}

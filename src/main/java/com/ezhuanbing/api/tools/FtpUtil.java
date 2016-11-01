package com.ezhuanbing.api.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezhuanbing.api.conf.FtpServerConfig;

@Component
public class FtpUtil {
  private static Logger logger=Logger.getLogger(FtpUtil.class);
  
  private static FTPClient ftp;
  
  @Autowired
  private FtpServerConfig f;
  
  private static boolean flag ;

  /**
   * 获取ftp连接
   * @param f
   * @return
   * @throws Exception
   */
  public boolean connectFtp(String path) throws Exception{
    
      ftp=new FTPClient();
      ftp.setControlEncoding("UTF-8");
      int reply;
      ftp.connect(f.getIp(),f.getPort());
      ftp.login(f.getUser(), f.getPass());
      ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
      reply = ftp.getReplyCode();      
      if (!FTPReply.isPositiveCompletion(reply)) {      
            ftp.disconnect();      
            return flag;      
      }   
      
      File file1=new File(path);
     	 if (!file1.isDirectory())
     	 {
     		 ftp.makeDirectory(path);
     	 }
  	
      flag=ftp.changeWorkingDirectory(path);      
           
      return flag;
  }
  
  /**
   * 关闭ftp连接
   */
  public void closeFtp(){
      if (ftp!=null && ftp.isConnected()) {
          try {
              ftp.logout();
              ftp.disconnect();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  }
  
  /**
   * ftp上传文件
   * @param f
   * @throws Exception
   */
  public boolean upload(File f,String fileName) throws Exception{
      
  	if (f.isDirectory()) {
          ftp.makeDirectory(f.getName());
          ftp.changeWorkingDirectory(f.getName());
          String[] files=f.list();
          for(String fstr : files){
          	File  file1=new File(f.getPath()+"/"+fstr);
              if (file1.isDirectory()) {
                  upload(file1,fileName);
                  ftp.changeToParentDirectory();
              }else{
              	
                      File file2=new File(f.getPath()+"/"+fstr);
                      ftp.enterLocalPassiveMode();
                      FileInputStream input=new FileInputStream(file2);
                      flag=ftp.storeFile(new String(fileName.getBytes("UTF-8"),"iso-8859-1"),input);
                      input.close();
              	
              }
          }
      }else{
          File file2=new File(f.getPath());
          ftp.enterLocalPassiveMode();
          FileInputStream input=new FileInputStream(file2);
          flag=ftp.storeFile(new String(fileName.getBytes("UTF-8"),"iso-8859-1"),input);
          input.close();
      }
  	return flag;
  }
  
  /**
   * 下载链接配置
   * @param f
   * @param localBaseDir 本地目录
   * @param remoteBaseDir 远程目录
   * @throws Exception
   */
  public void startDown(String path,String localBaseDir,String remoteBaseDir ) throws Exception{
      if (connectFtp(path)) {
          try { 
              FTPFile[] files = null; 
              boolean changedir = ftp.changeWorkingDirectory(remoteBaseDir); 
              if (changedir) { 
                  ftp.setControlEncoding("UTF8"); 
                  files = ftp.listFiles(); 
                  for (int i = 0; i < files.length; i++) { 
                      try{ 
                          downloadFile(files[i], localBaseDir, remoteBaseDir); 
                      }catch(Exception e){ 
                          logger.error(e); 
                          logger.error("<"+files[i].getName()+">下载失败"); 
                      } 
                  } 
              } 
          } catch (Exception e) { 
              logger.error(e); 
              logger.error("下载过程中出现异常"); 
          } 
      }else{
          logger.error("链接失败！");
      }
      
  }
  
  
  /** 
   * 
   * 下载FTP文件 
   * 当你需要下载FTP文件的时候，调用此方法 
   * 根据<b>获取的文件名，本地地址，远程地址</b>进行下载 
   * 
   * @param ftpFile 
   * @param relativeLocalPath 
   * @param relativeRemotePath 
   */ 
  private void downloadFile(FTPFile ftpFile, String relativeLocalPath,String relativeRemotePath) { 
      if (ftpFile.isFile()) {
          if (ftpFile.getName().indexOf("?") == -1) { 
              OutputStream outputStream = null; 
              try { 
                  File locaFile= new File(relativeLocalPath+ ftpFile.getName()); 
                  //判断文件是否存在，存在则返回 
                  if(locaFile.exists()){ 
                      return; 
                  }else{ 
                      outputStream = new FileOutputStream(relativeLocalPath+ ftpFile.getName()); 
                      ftp.retrieveFile(ftpFile.getName(), outputStream); 
                      outputStream.flush(); 
                      outputStream.close(); 
                  } 
              } catch (Exception e) { 
                  logger.error(e);
              } finally { 
                  try { 
                      if (outputStream != null){ 
                          outputStream.close(); 
                      }
                  } catch (IOException e) { 
                     logger.error("输出文件流异常"); 
                  } 
              } 
          } 
      } else { 
          String newlocalRelatePath = relativeLocalPath + ftpFile.getName(); 
          String newRemote = new String(relativeRemotePath+ ftpFile.getName().toString()); 
          File fl = new File(newlocalRelatePath); 
          if (!fl.exists()) { 
              fl.mkdirs(); 
          } 
          try { 
              newlocalRelatePath = newlocalRelatePath + '/'; 
              newRemote = newRemote + "/"; 
              String currentWorkDir = ftpFile.getName().toString(); 
              boolean changedir = ftp.changeWorkingDirectory(currentWorkDir); 
              if (changedir) { 
                  FTPFile[] files = null; 
                  files = ftp.listFiles(); 
                  for (int i = 0; i < files.length; i++) { 
                      downloadFile(files[i], newlocalRelatePath, newRemote); 
                  } 
              } 
              if (changedir){
                  ftp.changeToParentDirectory(); 
              } 
          } catch (Exception e) { 
              logger.error(e);
          } 
      } 
  } 
    /**
     * 删除ftp上指定文件
     * @param srcFname
     * @return
     */
	public boolean removeFile(String srcFname) {
		boolean flag = false;
		if (ftp != null) {
			try {
				flag = ftp.deleteFile(srcFname);
			} catch (IOException e) {
				logger.error(e);
				e.printStackTrace();
				this.closeFtp();
			}
		}
		return flag;
	}
}

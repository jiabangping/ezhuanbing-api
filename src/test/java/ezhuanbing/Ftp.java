package ezhuanbing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class Ftp {
	private static FTPClient ftp;

	public static void main(String[] args) throws Exception {
		String path = "PatientHeader";

		ftp = new FTPClient();
		ftp.setControlEncoding("UTF-8");
		int reply;
		ftp.connect("121.42.211.90", 1016);
		ftp.login("testupload", "testupload");
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
		}

		File file1 = new File(path);
		if (!file1.isDirectory()) {
			ftp.makeDirectory(path);
		}

		//ftp.changeWorkingDirectory(path);
		
	/*String[] s = ftp.listNames() ;
	System.out.println(String.valueOf(s));*/
		startDown(path,"d://test",path);
	}
	
	
	public static void startDown(String path,String localBaseDir,String remoteBaseDir ) throws Exception{
	     
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
	                    	  e.printStackTrace();
	                      } 
	                  } 
	              } 
	          } catch (Exception e) { 
	        	  e.printStackTrace();
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
	  private static void downloadFile(FTPFile ftpFile, String relativeLocalPath,String relativeRemotePath) { 
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
	            	  e.printStackTrace();
	              } finally { 
	                  try { 
	                      if (outputStream != null){ 
	                          outputStream.close(); 
	                      }
	                  } catch (IOException e) { 
	                	  e.printStackTrace();
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
	        	  e.printStackTrace();
	          } 
	      } 
	  } 
}

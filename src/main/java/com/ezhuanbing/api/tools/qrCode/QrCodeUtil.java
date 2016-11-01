package com.ezhuanbing.api.tools.qrCode;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ezhuanbing.api.conf.ConstantConfig;
import com.ezhuanbing.api.dao.mybatis.model.Doctor;
import com.ezhuanbing.api.tools.FtpUpload;

@Component
public class QrCodeUtil {
	
	@Autowired
	FtpUpload ftpUpload;

	public JSONObject getDoctorQrCodePicUrl(HttpServletRequest request,Integer doctorId,boolean replaceExist,int width,int height,String picFormat) {
		Doctor d = new Doctor();
		d.setDoctorId(doctorId);
		JSONObject result = null;
		if(replaceExist) {//重新生成
			JSONObject json = new JSONObject();
			try {
				json.put("doctorQrCodePicUrl", generateDoctorQrCodePic(request,doctorId,width,height,picFormat));
				json.put("picWidth", width);
				json.put("picHeight", height);
				json.put("picFormat", picFormat);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = json;
		}else {
			String doctorQrCodePicUrl = d.getDoctorQrCodePicUrl();//获取医生二维码
			HttpGet get = null;
			CloseableHttpResponse resp = null;
			CloseableHttpClient client = null;
			try {
				client = HttpClients.createDefault();
				get = new HttpGet(doctorQrCodePicUrl);
				resp = client.execute(get);
				int statusCode = resp.getStatusLine().getStatusCode();
				if(statusCode == 200) {
					JSONObject json = new JSONObject();
					json.put("doctorQrCodePicUrl", doctorQrCodePicUrl);
					json.put("picWidth", width);
					json.put("picHeight", height);
					json.put("picFormat", picFormat);
					result = json;
				} else if(statusCode == 404) {
					JSONObject json = new JSONObject();
					json.put("doctorQrCodePicUrl", generateDoctorQrCodePic(request,doctorId,width,height,picFormat));
					json.put("picWidth", width);
					json.put("picHeight", height);
					json.put("picFormat", picFormat);
					result = json;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(resp!=null) resp.close();
					if(client!=null) client.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}
	
	private String generateDoctorQrCodePic(HttpServletRequest request,Integer doctorId,int width,int height,String picFormat) throws Exception {
		String path = request.getRealPath(ConstantConfig.FtpPathConst.DoctorQrCodePic.name());
		String localFilePath = path +"/"+ doctorId+ "."+ picFormat;
		
		String content = String.valueOf(doctorId);
		//生成二维码
		TwoDimensionCode handler = new TwoDimensionCode();
		handler.encoderQRCodeWithWidthHeight(content, localFilePath, picFormat,width,height);
		
		File localFile = new File(localFilePath);
		
		String imgUrl = ftpUpload.fileUpload(localFile, ConstantConfig.FtpPathConst.DoctorQrCodePic.name(), doctorId+ "."+picFormat);
		if(localFile.exists()) {
			localFile.delete();
		}
		return imgUrl;
	}
			
}

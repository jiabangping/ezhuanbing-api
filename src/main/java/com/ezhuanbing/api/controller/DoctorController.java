package com.ezhuanbing.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.model.Doctor;
import com.ezhuanbing.api.exception.HttpStatus400Exception;
import com.ezhuanbing.api.exception.HttpStatus403Exception;
import com.ezhuanbing.api.model.DoctorResponseToken;
import com.ezhuanbing.api.service.DoctorService;
import com.ezhuanbing.api.tools.ValidationTools;
import com.ezhuanbing.api.tools.qrCode.QrCodeUtil;
import com.ezhuanbing.api.vo.SyncObject;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	DoctorService doctorService;
	
	@Autowired
	QrCodeUtil qrCodeUtil;

	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value = "/getDoctorList", method = RequestMethod.GET)
	public PageInfo<Doctor> getDoctorList(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			HttpServletRequest request) throws HttpStatus403Exception {
		DoctorResponseToken doctorResponseToken = (DoctorResponseToken) request.getAttribute("doctorResponseToken");
		Integer doctorid = doctorResponseToken.getDoctorID();
		try {
			if (name != null) {
				name = URLDecoder.decode(name, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        try {
        	return new PageInfo<Doctor>(doctorService.getDoctorList(name, doctorid, pageIndex, pageSize));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw e;
        }
	}

	/**
	 * 
	* @Title: getDoctor 
	* @Description: 获取医生详情
	* @param @param doctorId
	* @param @return
	* @param @throws Exception    设定文件 
	* @return Doctor    返回类型 
	* @throws
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.PATIENT)
	@RequestMapping(value = "/{doctorId}", method = RequestMethod.GET)
	public Doctor getDoctor(@PathVariable int doctorId) throws Exception {
		Doctor d = doctorService.getDoctor(doctorId);
		if (d != null)
			return d;
		else
			throw new HttpStatus400Exception("获取医生信息", "", "获取医生信息失败，请稍后重试！", "");
	}

	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value = "/getCurrentDoctor", method = RequestMethod.GET)
	public Doctor getDoctor(HttpServletRequest request) throws Exception {
		DoctorResponseToken doctorResponseToken = (DoctorResponseToken) request.getAttribute("doctorResponseToken");
		Integer doctorid = doctorResponseToken.getDoctorID();
		return this.getDoctor(doctorid);
	}
	
	/**
	 * 将病人指定给新医生
	 * 
	 * @return
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value = "/assignNewDoctor", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String assignNewDoctor(@RequestParam(value = "pidStr", required = true) String pidStr,
			@RequestParam(value = "pNameStr", required = true) String pNameStr,
			@RequestParam(value = "tid", required = true) String tid,
			HttpServletRequest request) {
		if (pidStr != "") {
			DoctorResponseToken doctorResponseToken = (DoctorResponseToken) request.getAttribute("doctorResponseToken");
			Integer doctorid = doctorResponseToken.getDoctorID();
			String[] pidStrArr = pidStr.split(",");
			String[] pNameArr = pNameStr.split(",");
			int[] pidArr = new int[pidStrArr.length];
			for (int i = 0; i < pidStrArr.length; i++) {
				pidArr[i] = Integer.valueOf(pidStrArr[i]);
			}
			if (ValidationTools.isInt(tid)) {
				
		        try {
		        	return doctorService.assignNewDoctor(pidArr, pNameArr, doctorid, Integer.valueOf(tid));
		        } catch (Exception e) {
		            log.error(e.getMessage(),e);
		            return "分配医生时发生错误，请稍后再试！";
		        }
			} else {
				return "分配医生时发生错误，请稍后再试！";
			}
		} else {
			return "分配医生时发生错误，请稍后再试！";
		}
	}

	/**
	 * 查看分配给其他医生的患者列表
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value = "/getAssignedPatientList", method = RequestMethod.GET)
	public PageInfo<Map<String, Object>> getAssignedPatientList(
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			HttpServletRequest request) throws HttpStatus403Exception {
		DoctorResponseToken doctorResponseToken = (DoctorResponseToken) request.getAttribute("doctorResponseToken");
		Integer doctorid = doctorResponseToken.getDoctorID();
		
        try {
        	return new PageInfo<Map<String, Object>>(doctorService.getAssignedPatientList(doctorid, pageIndex, pageSize));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw e;
        }
	}
	
	
	/**
	 * 根据doctorId获取当前医生的二维码
	 * @param doctorId
	 * @param generateAgain 1:重新生成会替换原有的
	 * @param width         二维码pic宽(px)
	 * @param height		二维码pic高(px)
	 * @param picFormat		pic格式，后缀
	 * @return
	 * @throws HttpStatus400Exception
	 */
//	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value = "/qrCodePicUrl/{doctorId}", method = RequestMethod.GET)
	public JSONObject doctorQrCodePic(@PathVariable("doctorId") Integer doctorId,HttpServletRequest request,
			@RequestParam(value = "generateAgain", required = false) String generateAgain,
			@RequestParam(value = "width", required = false) Integer width,
			@RequestParam(value = "height", required = false) Integer height,
			@RequestParam(value = "picFormat", required = false) String picFormat) throws HttpStatus400Exception {
		if(doctorId == null || doctorId < 0)
		      throw new HttpStatus400Exception("获取当前医生的二维码","","doctorId不正确。","");
		boolean replaceExist = false;
		if(generateAgain != null && "1".equals(generateAgain.trim())) {
			replaceExist = true;
		}
		
		Integer _width = 92,_height=92;//95
		String _picFormat = "jpg";
		if(width != null && width > 0) {
			_width = width;
		}
		if(height != null && height > 0) {
			_height = height;
		}
		if(picFormat != null && !"".equals(picFormat)) {
			_picFormat = picFormat.trim();
		}
		
		return qrCodeUtil.getDoctorQrCodePicUrl(request,doctorId,replaceExist,_width,_height,_picFormat);
	}
	
	/**
	 * @throws Exception 
	 * 
	* @Title: doctorOnline 
	* @Description: 医生上线
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value="/online", method = RequestMethod.POST)
	public void doctorOnline(@RequestBody SyncObject doctor) throws Exception{
	  if(!doctorService.setDoctorOnline(doctor.getId())){
	    throw new HttpStatus400Exception("医生上线", "", "数据添加失败，请稍后重试。", "");
	  }
	}
	
	   /**
	   * @throws Exception 
     * 
    * @Title: doctorOnline 
    * @Description: 医生下线
    * @param     设定文件 
    * @return void    返回类型 
    * @throws
     */
	@RequestMapping(value="/offline", method = RequestMethod.POST)
	public void doctorOffline(@RequestBody SyncObject doctor) throws Exception{
	  if(!doctorService.setDoctorOffline(doctor.getId())){
        throw new HttpStatus400Exception("医生下线", "", "数据删除失败，请稍后重试。", "");
      }
	}
}

package com.ezhuanbing.api.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.ezhuanbing.api.authorize.APIAccessType;
import com.ezhuanbing.api.authorize.APIType;
import com.ezhuanbing.api.authorize.ApiControl;
import com.ezhuanbing.api.dao.mybatis.model.DoctorEducationFile;
import com.ezhuanbing.api.model.DoctorResponseToken;
import com.ezhuanbing.api.service.DoctorEducationFileService;
import com.ezhuanbing.api.service.ImageService;
import com.ezhuanbing.api.tools.CommonTools;
import com.ezhuanbing.api.tools.ConstantClass;
import com.ezhuanbing.api.tools.HttpClientUtil;
import com.ezhuanbing.api.vo.MbEducationFileVo;
//import com.ezhuanbing.api.tools.ConstantClass;
//import com.ezhuanbing.api.tools.FtpUpload;
//import com.ezhuanbing.api.tools.ConstantClass;
//import com.ezhuanbing.api.tools.FtpUpload;
import com.github.pagehelper.PageInfo;


@RestController
@RequestMapping("/mbDocEduFile")
public class DoctorEducationFileController {
	private static final Logger logger = LoggerFactory.getLogger(DoctorEducationFileController.class);
	
	@Value("${image.imageserver}")
	private String imgServer;
	public String getImgServer() {
      return imgServer;
    }
    public void setImgServer(String imgServer) {
      this.imgServer = imgServer;
    }

    @Autowired
	private DoctorEducationFileService mbDoctorEducationFileService;
	
	@Autowired
	private ImageService imageService;
	
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping("/getAllDocFiles")
	public PageInfo<DoctorEducationFile> getAllDocFiles(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
	          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,HttpServletRequest request){
		DoctorResponseToken token = (DoctorResponseToken)request.getAttribute("doctorResponseToken");
		Integer doctorId = token.getDoctorID();
		List<DoctorEducationFile> list = mbDoctorEducationFileService.getAllDocFiles(pageIndex,pageSize,doctorId);
	    return new PageInfo<DoctorEducationFile>(list);
		
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/getDocFileById/{id}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getDocFileById(@PathVariable("id") Integer id){
		DoctorEducationFile doc = mbDoctorEducationFileService.getFileById(id);
		String filePath=doc.getFileurl();
		String content = null;
		String fileName = new File(filePath).getName();
		MbEducationFileVo vo = new MbEducationFileVo();
		try {
			content = HttpClientUtil.sendGet(filePath);
			if(content==null){
				String oldImageServer = "http://image.zhongyinginfo.com/";
				content = HttpClientUtil.sendGet(oldImageServer+  "/EducationFile/post/" + fileName );//FllowupPlan
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (content != null && !content.equalsIgnoreCase("")) {
			Document html = Jsoup.parse(content);
			Element container = html.getElementsByClass("container").get(0);
			// 删除title
			Elements title = container.getElementsByClass("title");
			if (title.size() > 0) {
				title.remove();
			}
			content = container.html();
		}
		vo.setContent(content);
		vo.setProfile(doc.getProfile());
		vo.setTitle(doc.getTitle());
		vo.setId(doc.getId());
	    return JSON.toJSONString(vo);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping("/deleteDocFile/{id}")
	public String deleteDocFile(@PathVariable("id") Integer id,HttpServletRequest req) throws Exception{
		DoctorEducationFile doc = mbDoctorEducationFileService.getFileById(id);
		
		//删除文件后删除记录
		String path = req.getSession().getServletContext().getRealPath("/");
		String fileName = null;
		if(doc.getFileurl()!=null && doc.getFileurl().length()>0){
			fileName = new File(doc.getFileurl()).getName();
		}
		File file = new File(path + "/PostTemp"+File.separator+fileName);
		if(file.exists()){//删除本地文件
			file.delete();
		}
		if(fileName!=null){//删除ftp服务器上文件
			imageService.deleteFile("EducationFile/post",fileName);//FllowupPlan
		}
		//end
		int num = mbDoctorEducationFileService.deleteFileById(id);
		return String.valueOf(num);
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/addDocEduFile",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String addDocEduFile(@RequestBody MbEducationFileVo fileVo,HttpServletRequest request){
		DoctorEducationFile file = null;
		try {
			if (fileVo.getTitle().length() > 500 || fileVo.getProfile().length() > 500) {
				return "";
			}
			String path = request.getSession().getServletContext().getRealPath("/");
			String htmlFileName = new Date().getTime() + ".html";
			String content = fileVo.getContent();
			logger.debug("content:"+content);
			file = new DoctorEducationFile();
			file.setProfile(fileVo.getProfile());
			file.setTitle(fileVo.getTitle());
			DoctorResponseToken token = (DoctorResponseToken)request.getAttribute("doctorResponseToken");
			Integer doctorId = token.getDoctorID();
			file.setDoctorid(doctorId);//暂时写，以后从登录取
			if (content != null && !content.equalsIgnoreCase("")) {
				logger.debug("content:"+content);
				List<String> imgUrls = CommonTools.getImgStr(content);
				String thumbnailUrl = "";
				if (imgUrls.size() > 0) {
					thumbnailUrl = imgUrls.get(0);
				}
				file.setThumbnailurl(thumbnailUrl);
				logger.debug("图片路径是："+thumbnailUrl);
			}
			file.setStatus("1");
			
			file = mbDoctorEducationFileService.add(file);
			//		String visitCountScriptJsonp = "<script type='text/javascript'>function getVisitCount(){$.ajax({ type: 'get',url: '"
//				+ ConstantClass.BACK_SERVER
//				+ "post/'+$(\"#postId\").val()+'/info',beforeSend: function(XMLHttpRequest){},dataType : 'jsonp',jsonp: 'callback',success: function(data){$(\"#visitCount\").text(data.visitCount);},complete: function(XMLHttpRequest, textStatus){},error: function(){}});}</script>";
			
			String basePath = path + "/PostTemp/" + htmlFileName;
			writeHtml(file,basePath,content,htmlFileName);
			try{
				String fileUrl = imageService.uploadFile(htmlFileName, 2, new File(basePath));
				file.setFileurl(fileUrl);
			}catch(Exception e){
				return "";
			}
			mbDoctorEducationFileService.update(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug("新建宣教文件时出错！！！");
			logger.debug(e.getMessage());
			logger.debug("fileVo.getTitle()："+fileVo.getTitle());
			logger.debug("fileVo.getProfile()："+fileVo.getProfile());
			logger.debug("fileVo.getContent()："+fileVo.getContent());
			return "";
		}
		return JSON.toJSONString(file);
	}
	
	public void writeHtml(DoctorEducationFile file,String path,String content,String htmlFileName){
		// 加入bootstrap 样式
		Document html = Jsoup.parse(ConstantClass.POST_TEMPLETE);
		//Element body = html.getElementsByTag("body").get(0);
		Element head = html.getElementsByTag("head").get(0);
		Element continer = html.getElementsByClass("container").get(0);
		Element postid = html.getElementById("postId");
		
		String css = "<link rel='stylesheet' href='" + imgServer + "upload/post/css/bootstrap.min.css'>";
		String jqueryJs = "<script src='" + imgServer + "upload/post/js/jquery-1.9.1.min.js'></script>";
		String bootstrapJs = "<script src='" + imgServer + "upload/post/js/bootstrap.min.js'></script>";

		// 头部引用文件
		head.append(css);
		head.append(jqueryJs);
		head.append(bootstrapJs);
		// 头部JS
		// head.append(visitCountScriptJsonp);
		// 标题部分
		try {
			continer.append("<div class='title'><l id='title'>" + URLDecoder.decode(file.getTitle(), "utf-8")
					+ "</l></l>&nbsp;&nbsp;&nbsp;&nbsp;</p></div>");
			continer.append(URLDecoder.decode(content, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 内容部分
		// 设置资讯ID
		postid.val(file.getId().toString());
		
		CommonTools.string2File(html.html(), path,htmlFileName,"\\upload\\post\\");
	}
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value="/update")
	public String update(@RequestBody MbEducationFileVo fileVo,HttpServletRequest request) throws Exception{
		if (fileVo.getTitle().length() > 500 || fileVo.getProfile().length() > 500) {
			return "";
		}
		
		String path = request.getSession().getServletContext().getRealPath("/");
		
		try {
			DoctorEducationFile docFile = mbDoctorEducationFileService.getFileById(fileVo.getId());
			String filePath = docFile.getFileurl();
			String fileName = new File(filePath).getName();
			String basePath = path + "/PostTemp/" + fileName;
			docFile.setTitle(fileVo.getTitle());
			docFile.setProfile(fileVo.getProfile());
			writeHtml(docFile,basePath,fileVo.getContent(),fileName);//重新上传到本地
			imageService.uploadFile(fileName, 2, new File(basePath));//上传到ftp上，更改内容
			return JSON.toJSONString(mbDoctorEducationFileService.update(docFile));
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("修改我的宣教资料失败！");
			logger.debug(e.getMessage());
			logger.debug("fileVo.getTitle()："+fileVo.getTitle());
			logger.debug("fileVo.getProfile()："+fileVo.getProfile());
			logger.debug("fileVo.getContent()："+fileVo.getContent());
			return "";
		}
	}
	/**
	 * 图片上传
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ApiControl(apiAccessType = APIAccessType.PRIVATE_API, apiType = APIType.DOCTOR)
	@RequestMapping(value = "/postImg")
	public uploadResult upload(HttpServletRequest request,
	    @RequestParam(name = "postPhoto") MultipartFile file) throws Exception {//@RequestParam(name = "postPhoto") MultipartFile file,
		//JSONObject ret = new JSONObject();
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		response.setContentType("text/html; charset=utf-8");
//		response.setContentType("text/plain; charset=utf-8");
//		MultipartHttpServletRequest mulReq = (MultipartHttpServletRequest) request;
//		MultipartFile file = mulReq.getFile("postPhoto");
	  
	  uploadResult result = new uploadResult();
	  
		if (file == null) {
//			ret.put("status", false);
//			ret.put("message", "请选择一张图片后上传");
//			return ret.toString();
		  result.setStatus(false);
		  result.setMessage("请选择一张图片后上传");
		}
		// 真实文件名
		String name = file.getOriginalFilename();
		String suffix = name.substring(name.lastIndexOf(".") + 1);

		if (!suffix.toUpperCase().equals("JPG") && !suffix.toUpperCase().equals("JPEG")
				&& !suffix.toUpperCase().equals("PNG") && !suffix.toUpperCase().equals("GIF")) {
//			ret.put("status", false);
//			ret.put("message", "请选择图片");
          result.setStatus(false);
          result.setMessage("请选择图片");
//			return ret.toString();
		}
		String imgName = new Date().getTime() + "." + suffix;
		// 上传图片地址
//		String imgFilePath = ConstantClass.ImagePath + "\\upload\\post\\img\\" + imgName;
//		File targetFile = new File(imgFilePath);
//		if (!targetFile.exists()) {
//			targetFile.mkdirs();
//		}
		// 保存
//		try {
//			FtpUpload  ftpUpload = new FtpUpload();
//			ftpUpload.fileUpload(file,"/upload/post/img/",imgName);
////			String imgUrl = ConstantClass.ImageServer + "upload/post/img/" + imgName;
//			ret.put("status", true);
////			ret.put("imgsrc", imgUrl);
//			return ret.toString();
//		} catch (Exception e) {
//			ret.put("status", false);
//			ret.put("message", e.getMessage());
//			return ret.toString();
//		}
		
		try {
	        String imgsrc = imageService.uploadImage(imgName, 1, file);
//	        ret.put("status", true);
//	        ret.put("imgsrc", imgsrc);
//	        return ret.toString();
	          result.setStatus(true);
	          result.setImgsrc(imgsrc);
	        
	      } catch (Exception e) {
//	    	  ret.put("status", false);
//	    	  ret.put("message", "图片上传失败，请稍后重试！");
//			  return ret.toString();
//	        throw new HttpStatus400Exception("图片上传","","","");
              result.setStatus(false);
              result.setMessage("图片上传失败，请稍后重试！"); 
	      }
		
		return result;
	}
	
	class uploadResult{
	  
	  private boolean status;
	  private String imgsrc;
	  private String message;
      
	  public boolean isStatus() {
        return status;
      }
      public void setStatus(boolean status) {
        this.status = status;
      }
      public String getImgsrc() {
        return imgsrc;
      }
      public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
      }
      public String getMessage() {
        return message;
      }
      public void setMessage(String message) {
        this.message = message;
      }
	}
}

package com.zsl.web.modules.personal_web.controller;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.sun.net.httpserver.HttpContext;
import com.zsl.web.common.controller.AdminBaseController;
import com.zsl.web.modules.personal_web.model.Blog;
import com.zsl.web.modules.personal_web.model.Photo;
import com.zsl.web.modules.personal_web.model.Picture;
import com.zsl.web.modules.personal_web.model.Say;
import com.zsl.web.modules.personal_web.model.User;
/**
 * 个人主页控制器
 * @author Administrator
 *
 */
import com.zsl.web.modules.personal_web.service.IBlogService;
import com.zsl.web.modules.personal_web.service.IPhotoService;
import com.zsl.web.modules.personal_web.service.IPictureService;
import com.zsl.web.modules.personal_web.service.ISayService;
import com.zsl.web.modules.personal_web.service.IUserService;
import com.zsl.web.util.Page;
import com.zsl.web.util.UploadUtils;
/**
 * 个人主页控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/personal_page")
public class PersonalWebController extends AdminBaseController{

	private static final Logger logger = LoggerFactory.getLogger(PersonalWebController.class);
	
	@Autowired
	private IUserService userService;
	@Autowired
	private ISayService sayService;
	@Autowired
	private IBlogService blogService;
	@Autowired
	private IPictureService pictureService;
	@Autowired
	private IPhotoService photoService;
	
	private final static String upload_module ="personal_picture";
	
	/***
	 * 首页
	 * @return
	 */
	@RequestMapping(value="/index",method = {RequestMethod.GET,RequestMethod.POST})
	public String index(){
		return "/";
	}
	
	/**
	 * 登录
	 * @param modelMap
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String Login(ModelMap modelMap,User user,HttpServletRequest request){
		if(StringUtils.isNotBlank(user.getUsername()) && StringUtils.isNotBlank(user.getPassword())){
			User tempUser  = userService.findByUserNameAndPassword(user.getUsername(),DigestUtils.md5Hex(user.getPassword()));
			if(tempUser != null){
				/**将登陆信息放到session中*/
				request.getSession().setAttribute("user", user);
				modelMap.addAttribute("user", tempUser);
				return jsonPrint(1, "Login was successful", null);
			}
			else{
				return jsonPrint(0, "Account or password is not correct", null);
			}
		}else{
			return jsonPrint(0, "Please enter your account number or password ", null);
		}
	}
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/logout",method = {RequestMethod.GET,RequestMethod.POST})
	public String Logout(HttpServletRequest request){
		/**清空session中的东西*/
		request.getSession().removeAttribute("user");
		return "redirect:index";
	}
	
	/**
	 * 说说列表
	 * @param modelMap
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/Says",method = {RequestMethod.GET,RequestMethod.POST})
	public String findAllSays(ModelMap modelMap,Page<Say> page,Long userId){
		Page<Say> resultPage = sayService.findPage(page,userId);
		modelMap.addAttribute("page",resultPage);
		return "";
	}
	
	/***
	 * 日志列表
	 * @param modelMap
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/Blogs",method = {RequestMethod.GET,RequestMethod.POST})
	public String findAllBlog(ModelMap modelMap,Page<Blog> page,Long userId){
		Page<Blog> resultPage = blogService.findPage(page,userId);
		modelMap.addAttribute("page", resultPage);
		return "";
	}
	
	/**
	 * 相册列表
	 * @param modelMap
	 * @param page
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/photos",method = {RequestMethod.GET,RequestMethod.POST})
	public String findAllPhoto(ModelMap modelMap,Page<Photo> page,Long userId){
		Page<Photo> resultPage = photoService.findPage(page,userId);
		modelMap.addAttribute("page", resultPage);
		return "";
	}
	
	/***
	 * 照片列表
	 * @param modelMao
	 * @param page
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/pictures",method = {RequestMethod.GET,RequestMethod.POST})
	public String findAllPicture(ModelMap modelMap,Page<Picture> page ,Long photoId){
		page.setPageSize(30);
		Page<Picture> resultPage = pictureService.findPage(page,photoId);
		modelMap.addAttribute("page", resultPage);
		return "";
	}
	
	/**
	 * 异步删除说说
	 * @param sayId
	 * @return
	 */
	@RequestMapping(value="/deleteSay",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String deleteSay(Long sayId){
		try {
			Preconditions.checkArgument(sayId != null, "Delete error , Please try again later");
			Say say = sayService.findOne(sayId);
			Preconditions.checkArgument(say != null, "Such say does not exist or it has been removed for");
			sayService.deleteByDelFlag(say);;
			return jsonPrint(1, "Delete success", null);
		} catch (Exception e) {
			return jsonPrint(0, e.getMessage(), null);
		}
	}
	
	/**
	 * 异步删除日志
	 * @param blogId
	 * @return
	 */
	@RequestMapping(value="/deleteBlog",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String deleteBlog(Long blogId){
		try {
			Preconditions.checkArgument(blogId != null, "Delete error , Please try again later");
			Blog blog = blogService.findOne(blogId);
			Preconditions.checkArgument(blog != null, "Such blog does not exist or it has been removed for");
			blogService.deleteByDelFlag(blog);;
			return jsonPrint(1, "Delete success", null);
		} catch (Exception e) {
			return jsonPrint(0, e.getMessage(), null);
		}
	}
	/**
	 * 异步删除相册
	 * @param photoId
	 * @return
	 */
	@RequestMapping(value="/deletePhoto",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String deletePhoot(Long photoId){
		try {
			Preconditions.checkArgument(photoId != null, "Delete error , Please try again later");
			Photo photo = photoService.findOne(photoId);
			Preconditions.checkArgument(photo != null, "Such photo does not exist or it has been removed for");
			photoService.deleteByDelFlag(photo);;
			return jsonPrint(1, "Delete success", null);
		} catch (Exception e) {
			return jsonPrint(0, e.getMessage(), null);
		}
	}
	
	/**
	 * 异步删除照片
	 * @param photoId
	 * @return
	 */
	@RequestMapping(value="/deletePicture",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String deletePicture(Long pictureId){
		try {
			Preconditions.checkArgument(pictureId != null, "Delete error , Please try again later");
			Picture picture = pictureService.findOne(pictureId);
			Preconditions.checkArgument(picture != null, "Such picture does not exist or it has been removed for");
			pictureService.deleteByDelFlag(picture);;
			return jsonPrint(1, "Delete success", null);
		} catch (Exception e) {
			return jsonPrint(0, e.getMessage(), null);
		}
	}
	
	
	/**
	 * 异步上传图片
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/uploadPicture")
	@ResponseBody
	public String uploadPicture(MultipartFile file,HttpServletRequest request){
		if(file == null){
			return jsonPrint(0, "Picture upload failed , Please try again later", null);
    	}
    	long size = file.getSize();
    	if((size/(1024*1024)) > 20){
            return jsonPrint(0, "This picture's size can not be more than 20M , Please choose the right one", null);
    	}
		if(!"".equals(file.getOriginalFilename())){
			try {
				//String filePath = pictureService.uploadPictureAndsavePicture(request, file,upload_module,"yyyyMM");
				String filePath = UploadUtils.saveMartipartFile(request, file,upload_module,"yyyyMM");
				return jsonPrint(1, "Picture upload success", filePath);
				
			} catch (Exception e) {
				e.printStackTrace();
				return jsonPrint(0, "Picture upload failed , Please try again later ,Cause : "+e.getMessage(), null);
			}
		}
		return jsonPrint(0, "Picture upload failed , Please try again later", null);
		
	}
	
	/**
	 * 修改图片(只能修改图片名称)
	 * @param pictureId
	 * @return
	 */
	@RequestMapping(value="updatePicture")
	@ResponseBody
	public String updatePicture(Picture picture){
		try {
			Preconditions.checkArgument(picture.getId() != null, "Picture update failed , Please try again later");
			Picture oldPicture = pictureService.findOne(picture.getId());
			Preconditions.checkArgument(oldPicture != null, "Such picture does not exist or it has been removed for");
			pictureService.updatePicture(oldPicture,picture.getPictureName());
			return jsonPrint(1, "Rename success", null);
			
		} catch (Exception e) {
			return jsonPrint(0, e.getMessage(), null);
		}
	}
	/**
	 * 异步保存说说
	 * @param say
	 * @return
	 */
	@RequestMapping(value="/addSay",method = {RequestMethod.GET,RequestMethod.POST})	
	@ResponseBody
	public String addSay(Say say,Long userId){
		try {
			Preconditions.checkArgument(userId != null, "Save failed , Please try again later");
			Preconditions.checkArgument(say != null, "Save failed , Please try again later");
			say.setUserId(userId);
			sayService.addSay(say);
			return jsonPrint(1, "Save success", null);
		} catch (Exception e) {
			return jsonPrint(0, e.getMessage(), null);
		}
	}
	
	/**
	 * 修改说说
	 * @param say
	 * @return
	 */
	@RequestMapping(value="/updateSay")
	@ResponseBody
	public String updateSay(Say say){
		try {
			Preconditions.checkArgument(say != null, "Update failed , Please try again later");
			Preconditions.checkArgument(say.getId() != null, "Update failed , Please try again later");
			sayService.updateSay(say);
			return jsonPrint(1, "Update success", null);
		} catch (Exception e) {
			return jsonPrint(0, e.getMessage(), null);
		}
	}
	
	/**
	 * 异步保存日志
	 * @param say
	 * @return
	 */
	@RequestMapping(value="/addBlog",method = {RequestMethod.GET,RequestMethod.POST})	
	@ResponseBody
	public String addBlog(Blog blog,Long userId){
		try {
			Preconditions.checkArgument(userId != null, "Save failed , Please try again later");
			Preconditions.checkArgument(blog != null, "Save failed , Please try again later");
			blog.setUserId(userId);
			blogService.addBlog(blog);
			return jsonPrint(1, "Save success", null);
		} catch (Exception e) {
			return jsonPrint(0, e.getMessage(), null);
		}
	}
	
	/***
	 * 查看日志
	 * @param modelMap
	 * @param blogId
	 */
	@RequestMapping(value="/seeBlog")
	public String seeBlog(ModelMap modelMap,Long blogId){
		if(blogId != null){
			
			Blog blog = blogService.findOne(blogId);
			modelMap.addAttribute("blog",blog);
		}
		return "";
	}
	
	/**
	 * 修改日志
	 * @param say
	 * @return
	 */
	@RequestMapping(value="/updateBlog")
	@ResponseBody
	public String updateBlog(Blog blog){
		try {
			Preconditions.checkArgument(blog != null, "Update failed , Please try again later");
			Preconditions.checkArgument(blog.getId() != null, "Update failed , Please try again later");
			blogService.updateBlog(blog);
			return jsonPrint(1, "Update success", null);
		} catch (Exception e) {
			return jsonPrint(0, e.getMessage(), null);
		}
	}
	
	
	/**
	 * 异步保存相册
	 * @param say
	 * @return
	 */
	@RequestMapping(value="/addPhoto",method = {RequestMethod.GET,RequestMethod.POST})	
	@ResponseBody
	public String addPhoto(Photo photo,Long userId){
		try {
			Preconditions.checkArgument(userId != null, "Save failed , Please try again later");
			Preconditions.checkArgument(photo != null, "Save failed , Please try again later");
			photo.setUserId(userId);
			photoService.addPhoto(photo);
			return jsonPrint(1, "Save Photo success", null);
		} catch (Exception e) {
			return jsonPrint(0, e.getMessage(), null);
		}
	}
	
	/**
	 * 进入相册
	 * @param modelMap
	 * @param photoId
	 * @return
	 */
	@RequestMapping(value="/seePhoto")
	public String seePhoto(ModelMap modelMap ,Long photoId){
		if(photoId != null){
			Photo photo = photoService.findOne(photoId);
			modelMap.addAttribute("photo", photo);
		}
		return "";
	}
	
	/**
	 * 更新相册
	 * @param photo
	 * @return
	 */
	@RequestMapping(value="/updatePhoto")
	@ResponseBody
	public String updatePhoto(Photo photo){
		try {
			Preconditions.checkArgument(photo != null || photo.getId()!= null, "Update Photo failed , Please try again later");
			photoService.updatePhoto(photo);
			return jsonPrint(1, "Update Photo seccuss", null);
		} catch (Exception e) {
			return jsonPrint(0, e.getMessage(), null);
		}
	}
	
	/**
	 * 相册异步上传图片
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/uploadPictureInPhoto")
	@ResponseBody
	public String uploadPictureInPhoto(MultipartFile file,HttpServletRequest request,Long photoId){
		if(file == null){
			return jsonPrint(0, "Picture upload failed , Please try again later", null);
    	}
    	long size = file.getSize();
    	if((size/(1024*1024)) > 20){
            return jsonPrint(0, "This picture's size can not be more than 20M , Please choose the right one", null);
    	}
		if(!"".equals(file.getOriginalFilename())){
			try {
				String filePath = pictureService.uploadPictureAndsavePicture(request, file,upload_module,"yyyyMM",photoId);
				//String filePath = UploadUtils.saveMartipartFile(request, file,upload_module,"yyyyMM");
				return jsonPrint(1, "Picture upload success", filePath);
				
			} catch (Exception e) {
				e.printStackTrace();
				return jsonPrint(0, "Picture upload failed , Please try again later ,Cause : "+e.getMessage(), null);
			}
		}
		return jsonPrint(0, "Picture upload failed , Please try again later", null);
		
	}
	
}

package com.SpringBoot.main.controller.ueditor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.SpringBoot.main.ueditor.PublicMsg;
import com.SpringBoot.main.ueditor.Ueditor;

@Controller
@RequestMapping("ueditor")
public class UeditorController {
	@RequestMapping("/show")
	private String showPage() {
		return "redirect:/resources/admin/ueditor/index.html";
	}

	@RequestMapping(value = "/ueditor")
	@ResponseBody
	public String ueditor(HttpServletRequest request) {

		return PublicMsg.UEDITOR_CONFIG;
	}

	@RequestMapping(value = "/imgUpload")
	@ResponseBody
	public Ueditor imgUpload(MultipartFile upfile, HttpServletRequest request) throws IOException {
		Ueditor ueditor = new Ueditor();
		String suffix=upfile.getOriginalFilename().substring(upfile.getOriginalFilename().lastIndexOf(".")+1,upfile.getOriginalFilename().length());
		
		String fileName=UUID.randomUUID()+"."+suffix;
		//String fileName = upfile.getOriginalFilename();

		// 保存图片的目录
		// String
		// savePath=request.getServletContext().getRealPath("/")+"/resources/upload/image/"+new
		// SimpleDateFormat("yyyyMMdd").format(new Date())+"/";
		// String savePath="D:\\test\\"+new SimpleDateFormat("yyyyMMdd").format(new
		// Date())+"\\";
		//String savePath = "C:\\Users\\Administrator\\Pictures\\SpringBoot_News\\"
				//+ new SimpleDateFormat("yyyyMMdd").format(new Date()) + "\\";
			
		String savePath="C:\\Users\\Administrator\\Pictures\\SpringBoot_News\\";
				
		System.out.println(savePath);

		File file = new File(savePath);
		if (!file.exists()) {
			System.out.println("开始创建目录");
			// 如果目录不存在，创建文件
			file.mkdir();
		}

		System.out.println(file.getPath());
		if (!file.exists()) {
			System.out.println("目录没有创建成功");
		}

//        File file = new File(request.getServletContext().getRealPath("/")+"resources/upload",fileName);
		upfile.transferTo(new File(savePath + fileName));
		// String url =request.getServletContext().getContextPath() +
		// "/resources/upload/image/"+new SimpleDateFormat("yyyyMMdd").format(new
		// Date()) +"/"+ fileName;
		// String url="http://localhost:8080/"+new
		// SimpleDateFormat("yyyyMMdd").format(new Date())+"\\"+fileName;
		//String url = "http://148.70.102.57/" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "\\" + fileName;
		String url ="http://148.70.102.57/"+fileName;
		System.out.println(url);
		ueditor.setUrl(url);
		ueditor.setState("SUCCESS");
		ueditor.setTitle(upfile.getOriginalFilename());
		ueditor.setOriginal(upfile.getOriginalFilename());
		return ueditor;
	}
}

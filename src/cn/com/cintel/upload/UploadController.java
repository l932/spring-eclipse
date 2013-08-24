package cn.com.cintel.upload;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.com.cintel.common.BaseController;
import cn.com.cintel.common.util.Constants;

@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {

	@RequestMapping(value = "uploadpage.do", method = RequestMethod.GET)
	public ModelAndView uploadpage(ModelAndView mav) {
		mav.setViewName("upload/uploadfile");
		return mav;
	}

	@RequestMapping(value = "uploadfile.do", method = RequestMethod.POST)
	public ModelAndView uploadFile(@RequestParam() MultipartFile file, @RequestParam() MultipartFile file2, ModelAndView mav) throws Exception {
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			FileOutputStream fos = new FileOutputStream(Constants.UPLOAD_DIR + "uploadtest" + File.separator + file.getOriginalFilename()); // 上传到写死的上传路径
			fos.write(bytes); // 写入文件
			fos.flush();
			fos.close();
		}
		if (!file2.isEmpty()) {
			byte[] bytes = file2.getBytes();
			FileOutputStream fos = new FileOutputStream(Constants.UPLOAD_DIR + "uploadtest" + File.separator + file2.getOriginalFilename()); // 上传到写死的上传路径
			fos.write(bytes); // 写入文件
			fos.flush();
			fos.close();
		}

		System.out.println("name: " + file.getOriginalFilename() + "  size: " + file.getSize()); // 打印文件大小和文件名称
		System.out.println("name: " + file2.getOriginalFilename() + "  size: " + file2.getSize()); // 打印文件大小和文件名称
		File newFile = new File(Constants.UPLOAD_DIR + "uploadtest" + File.separator + file.getOriginalFilename());
		File newFile2 = new File(Constants.UPLOAD_DIR + "uploadtest" + File.separator + file2.getOriginalFilename());
		if (newFile.exists() && newFile2.exists()) {
			mav.addObject(Constants.REMSG_NAME_4_JSP, Constants.REMSG_UPLOAD_SUCCESS);
		} else {
			mav.addObject(Constants.REMSG_NAME_4_JSP, Constants.REMSG_UPLOAD_FAILE);
		}

		mav.setViewName("upload/uploadfile");
		return mav;
	}
}

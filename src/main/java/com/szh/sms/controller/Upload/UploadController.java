package com.szh.sms.controller.Upload;

import com.szh.sms.domain.Upload;
import com.szh.sms.service.Upload.UploadService;
import com.szh.sms.utils.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import static java.lang.Integer.parseInt;

/**
 * Description 上传控制层
 **/
@RestController
@RequestMapping("/api/sms/upload")
public class UploadController {
  @Autowired
  private UploadService uploadService;

  @PostMapping("/headImg")
  @ResponseBody
  public String upload(MultipartFile file, HttpServletRequest request) throws IOException {
    if (!file.isEmpty()) {
      try {
        byte[] bytes = file.getBytes();
        String url = "sms/static/img/";
        String ImgName = file.getOriginalFilename();

        if (ImgName == null || ImgName.isEmpty()) {
            return "";
        }

        String uid = UUID.randomUUID().toString();
        String str = ImgName.substring(ImgName.lastIndexOf("."));
        String newName = uid + str;

        // 图片存储地址
        String imgUrl = url + newName;
        Path path = Paths.get(imgUrl);
        // 写入文件
        Files.write(path, bytes);

        String userId = request.getParameter("id");
        int level = parseInt(request.getParameter("level"));
        Upload upload = new Upload();
        upload.setUserId(userId);
        upload.setLevel(level);
        upload.setUrl(imgUrl);
        uploadService.upload(upload);

        // url去除"sms"
        return imgUrl.substring(imgUrl.indexOf("/"));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return "";
  }

  @GetMapping("/getHeadImg")
  @UserLoginToken
  public String getAdminList (@RequestParam Map<String, Object> condition, HttpServletRequest httpServletRequest) {
    return uploadService.getHeader(condition);
  }
}

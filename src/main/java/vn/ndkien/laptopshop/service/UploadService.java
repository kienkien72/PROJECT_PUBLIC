package vn.ndkien.laptopshop.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Service
public class UploadService {

    private final ServletContext servletContext;

    public UploadService(ServletContext servletContext) {

        this.servletContext = servletContext;
    }

    public String handleSaveUploadFile(MultipartFile file, String targetFolder) {
        // Đường link dẫn đến lưu file
        String rootPath = this.servletContext.getRealPath("/resources/images");
        String finalName = " ";
        try {
            // Lấy hình ảnh dưới dạng byte
            byte[] bytes = file.getBytes();

            // Tìm nơi để lưu trữ file
            File dir = new File(rootPath + File.separator + targetFolder);
            if (!dir.exists()) // Chưa tồn tại => tạo mới
                dir.mkdirs(); // Tạo mới

            // Tạo tên file mới
            // dir.getAbsolutePath(): Đường link của thư mục
            // System.currentTimeMillis(): Sửa đổi tên file
            finalName = +System.currentTimeMillis() + "-" + file.getOriginalFilename();

            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalName);

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return finalName;
    }

}

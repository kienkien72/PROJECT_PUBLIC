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

    // cho phép bạn truy cập các thư mục và tệp tin trong ứng dụng web
    public UploadService(ServletContext servletContext) {

        this.servletContext = servletContext;
    }

    // MultipartFile:Là file được tải lên từ client
    // String targetFolder: Thư mục đích mà file sẽ được lưu
    public String handleSaveUploadFile(MultipartFile file, String targetFolder) {

        // Đường link dẫn đến lưu file
        // getRealPath trả về đường dẫn tuyệt đối trên hệ thống file của server
        // Ví dụ: C:/project/resources/images
        String rootPath = this.servletContext.getRealPath("/resources/images");

        String finalName = " ";
        try {
            // Chuyển đổi file thành mảng byte
            byte[] bytes = file.getBytes();

            // Tìm nơi để lưu trữ file
            // File.separator giúp đảm bảo dấu phân cách thư mục phù hợp với hệ điều hành,
            // (dấu / trên Linux, dấu \ trên Windows)
            File dir = new File(rootPath + File.separator + targetFolder);
            if (!dir.exists()) // Kt tồn tại hay chưa
                dir.mkdirs(); // Nếu chưa tồn tại, tạo mới

            // Tạo tên file mới
            // dir.getAbsolutePath(): Đường link của thư mục
            // System.currentTimeMillis(): Sửa đổi tên file
            finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalName);

            // BufferedOutputStream: Sử dụng lớp này để ghi mảng byte vào file trên server.
            // Lớp BufferedOutputStream giúp tối ưu hóa việc ghi dữ liệu bằng cách giữ một
            // bộ nhớ đệm (buffer) trước khi ghi ra file
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));

            // stream.write(bytes): Ghi mảng byte đã đọc từ file tải lên vào file trên
            // server
            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return finalName;
    }

}

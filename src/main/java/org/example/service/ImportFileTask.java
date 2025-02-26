package org.example.service;

import org.example.entity.DtbRttDeliveryOrderFile;
import org.example.enums.FileStatus;
import org.example.repository.DeliveryOrderFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@Service
public class ImportFileTask {


    @Autowired
    private DeliveryOrderFileRepository fileRepository;

    @Autowired
    private DeliveryOrderService deliveryOrderService;

    @Async("taskExecutor")
    @Transactional
    public void importFile(DtbRttDeliveryOrderFile file) {
        String filePath = file.getPathFile();
//        String filePath = file.getPathFile() + File.separator + file.getFileName();
        File excelFile = new File(filePath);

        if (!excelFile.exists()) {
            System.err.println("❌ File không tồn tại: " + filePath);
            return;
        }

        System.out.println("✅ Đang xử lý file: " + filePath + " trên luồng: " + Thread.currentThread().getName());

        try (FileInputStream fis = new FileInputStream(excelFile)) {
            MultipartFile multipartFile = new MockMultipartFile(
                    excelFile.getName(),
                    excelFile.getName(),
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    fis
            );

            // Import file vào hệ thống
            deliveryOrderService.importNewDeliveryOrderRtt(multipartFile,file);

            // Cập nhật trạng thái thành FINISH
            file.setStatus(FileStatus.FINISH.getValue());
            fileRepository.save(file);
            System.out.println("🎯 Đã hoàn tất import file: " + file.getFileName());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("⚠️ Lỗi khi import file: " + file.getFileName());

            // Cập nhật trạng thái thành FAILED khi gặp lỗi
//            file.setStatus(FileStatus.FAILED.getValue());
//            fileRepository.save(file);
        }
    }
}


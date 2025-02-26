package org.example.service;

import org.example.entity.DtbRttDeliveryOrderFile;
import org.example.enums.FileStatus;
import org.example.repository.DeliveryOrderFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@Service
public class ImportFileTask {


    @Autowired
    private DeliveryOrderFileRepository fileRepository;

    @Autowired
    private DeliveryOrderService deliveryOrderService;
    public void importFile(DtbRttDeliveryOrderFile file) {
        try {
            String filePath = file.getPathFile() + File.separator + file.getFileName();
            File excelFile = new File(filePath);

            if (excelFile.exists()) {
                System.out.println("Đang xử lý file: " + filePath);

                try (FileInputStream fis = new FileInputStream(excelFile)) {
                    MultipartFile multipartFile = new MockMultipartFile(
                            excelFile.getName(), excelFile.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", fis);

                    deliveryOrderService.importNewDeliveryOrderRtt(multipartFile);

                    file.setStatus(FileStatus.FINISH.getValue());
                    fileRepository.save(file);
                    System.out.println("Đã hoàn tất import file: " + file.getFileName());
                }

            } else {
                System.err.println("File không tồn tại: " + filePath);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi import file: " + file.getFileName());
        }
    }
}


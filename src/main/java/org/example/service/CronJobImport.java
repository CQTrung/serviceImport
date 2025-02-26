package org.example.service;

import org.example.entity.DtbRttDeliveryOrderFile;
import org.example.enums.FileStatus;
import org.example.repository.DeliveryOrderFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
public class CronJobImport {

    @Autowired
    private DeliveryOrderFileRepository fileRepository;

   @Autowired
   private ImportFileTask importFileTask;




    // Tự động chạy mỗi 5 phút
//    @Scheduled(cron = "0 */5 * * * *")
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void processPendingFiles() {
        System.out.println("Đang quét các file PENDING trong bảng excelDO...");

        // Lấy danh sách các file có trạng thái PENDING
        List<DtbRttDeliveryOrderFile> pendingFiles = fileRepository.findByStatus(FileStatus.PENDING.getValue());

        for (DtbRttDeliveryOrderFile file : pendingFiles) {
            try {


                String filePath = file.getPathFile() + File.separator + file.getFileName();
                File excelFile = new File(filePath);

                if (excelFile.exists()) {
                    System.out.println("Đang xử lý file: " + filePath);
                    // Cập nhật trạng thái thành PROCESSING ngay lập tức
                    file.setStatus(FileStatus.PROCESSING.getValue());
                    fileRepository.save(file);
                    System.out.println("Cập nhật trạng thái file " + file.getFileName() + " thành PROCESSING");

                    // Gọi xử lý import file (chạy ngầm)
                    importFileTask.importFile(file);
                } else {
                    System.err.println("File không tồn tại: " + filePath);
                }






























































































            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Lỗi khi cập nhật trạng thái file: " + file.getFileName());
            }
        }
    }
}

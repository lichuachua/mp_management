package cn.lichuachua.mp_management.mp_managementserver.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {
    public static void uploadFile(byte[] file, String path, String fileName) throws Exception {
        String rootPath = "C:/Users/Administrator/Desktop/Mp/mp_management/src/main/resources";
        String filePath = rootPath+path;
        File targetFile = new File(String.valueOf(filePath));
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
        File rootPath1 = new File(ResourceUtils.getURL("classpath:").getPath());
        String filePath1 = rootPath1+path;
        File targetFile1 = new File(String.valueOf(filePath1));
        if(!targetFile1.exists()){
            targetFile1.mkdirs();
        }
        FileOutputStream out1 = new FileOutputStream(filePath1+fileName);
        out1.write(file);
        out1.flush();
        out1.close();
    }

}

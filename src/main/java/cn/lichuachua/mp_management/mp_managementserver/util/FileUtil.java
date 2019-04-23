package cn.lichuachua.mp_management.mp_managementserver.util;

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
    }

}

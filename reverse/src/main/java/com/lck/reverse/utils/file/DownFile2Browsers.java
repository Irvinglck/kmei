package com.lck.reverse.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@Slf4j
public class DownFile2Browsers {
    @GetMapping("/browersFile/{pdf}")
    public void getFile2Browers(HttpServletResponse response, @PathVariable(name = "pdf") String pdf) {
        File file = new File("C:\\Users\\Administrator\\Desktop\\接口数据.doc");
        try {
            BufferedInputStream reader = null;
            FileInputStream inputStream = null;
            ServletOutputStream outw = response.getOutputStream();
            try {
                inputStream = new FileInputStream(file);
                FileInputStream inp = new FileInputStream(file);
                int i;
                byte[] buffer = new byte[1024];
                while ((i = inp.read(buffer)) != -1) {
                    outw.write(buffer, 0, i);
                }
                inp.close();
                outw.flush();
            } finally {
                if (reader != null) {
                    reader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            log.info("读取文件失败，文件名称:[{}]", pdf);
        }
    }
}

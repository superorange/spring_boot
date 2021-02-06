package com.example.sp;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

/**
 * @author tian
 * @version 1.0
 * @date 2020/10/15 16:44
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public List<Map<String, String>> hello() {
        //增加commit
        List<Map<String, String>> items = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8", "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet query = statement.executeQuery("select * from tb1");
            while (query.next()) {
                HashMap<String, String> map = new HashMap<>();
                System.out.println(query.getString("des"));
                map.put("name", query.getString("name"));
                map.put("age", String.valueOf(query.getInt("age")));
                map.put("des", query.getString("des"));
                items.add(map);
            }


        } catch (Exception throwables) {
            System.out.println("error");
            throwables.printStackTrace();
        }
//        map.put("name","李四");
        return items;
    }

    @RequestMapping("/ok")
    public Map<String, byte[]> ok() {
        String a = "HEllo World";
        File file = new File("D:\\test\\icon_close.png");
        FileInputStream fis;
        byte[] buffer = new byte[1024];
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<String, byte[]> images = new HashMap();
        images.put("images", buffer);
        return images;
    }

    @RequestMapping("upload")
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) {
        Map<String, String> error = new HashMap();
        error.put("201", "缺少body。{'file':'file'}");
        if (file.isEmpty()) {
            return error;
        }
        String fileName = file.getOriginalFilename();
        String path = System.getProperty("user.dir") + "/files/";
        File dest = new File(path + fileName);
        Map<String, String> ok = new HashMap();
        try {
            file.transferTo(dest);
            LOGGER.info("上传成功");
            ok.put("200", "上传成功");

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("上传失败");
            ok.put("202", "上传失败" + e.toString());
        }
        return ok;
    }

    @RequestMapping("uploads")
    public Map<String, String> uploads(@RequestParam("files") List<MultipartFile> files) {
        Map<String, String> error = new HashMap();
        error.put("201", "缺少body。{'file':'file'}");
        if (files.isEmpty()) return error;
        Map<String, String> ok = new HashMap();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String path = System.getProperty("user.dir") + "/files/";
            File dest = new File(path + fileName);
            try {
                file.transferTo(dest);
                LOGGER.info("上传成功");
                ok.put("200", "上传成功");

            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.info("上传失败");
                ok.put("202", "上传失败" + e.toString());
            }
        }
        return ok;
    }

    @RequestMapping("up")
    public Map<String, Object> up(HttpServletRequest request, @RequestBody Map o) {
        Map hashMap = new HashMap();
        hashMap.put("code", 200);
        hashMap.put("msg", request.getParameter("uID") + ":" + o.get("uID"));
        try {
            ServletInputStream inputStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            hashMap.put("readLine", reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hashMap;
    }

    @RequestMapping("up1")
    public Map<String, Object> up1(HttpServletRequest request) {
        Map hashMap = new HashMap();
        hashMap.put("code", 200);
        hashMap.put("msg", request.getParameter("uID"));
        try {
            ServletInputStream inputStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
            hashMap.put("readLine", sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hashMap;
    }

    @RequestMapping("up-stream")
    public Map<String, Object> upStream(String fileName, HttpServletRequest request) {
        Map result = new HashMap();
        try {
            String path = System.getProperty("user.dir") + "/files/";
            File dest = new File(path + fileName);
            ServletInputStream stream = request.getInputStream();
            FileOutputStream fos = new FileOutputStream(dest);
            byte[] bytes = new byte[1024 * 8];
            int len = 0;
            while ((len = stream.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
            fos.flush();
            fos.close();
            stream.close();
            result.put("msg", "上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            result.put("msg", e.toString());
        }
        return result;
    }
}


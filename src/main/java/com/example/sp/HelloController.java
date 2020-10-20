package com.example.sp;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String,byte[]> ok() throws FileNotFoundException {
        String a="HEllo World";
        File file=new File("D:\\test\\icon_close.png");
        FileInputStream fis;

        byte[] buffer=new byte[1024];
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
            if(file.exists()) {
                file.delete();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String,byte[]> images=new HashMap();
        images.put("images",buffer);
        return  images;
    }

    class W{
        File file;
        String name;

        public W(File file, String name) {
            this.file = file;
            this.name = name;
        }
    }

}


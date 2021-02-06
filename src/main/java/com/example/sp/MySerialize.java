package com.example.sp;

import java.io.*;

/**
 * @author tian
 * @version 1.0
 * @date 2020/10/25 12:13
 */
public class MySerialize implements Serializable {
    String name;
    int age;

    public static void main(String[] args) {
        MySerialize mySerialize = new MySerialize();
        mySerialize.name="张三";
        mySerialize.age=18;

        File file=new File("/files/");
        File file2 = null;
        if(!file.exists()){
            try {
                file.mkdirs();
                file2=new File(file,"a.txt");
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
           ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(file2));
           outputStream.writeObject(mySerialize);
           outputStream.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
        MySerialize mySerialize2 ;
        try {
            ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(file2));
            mySerialize2 = (MySerialize) inputStream.readObject();
            System.out.println("mySerialize2:"+mySerialize2.name);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

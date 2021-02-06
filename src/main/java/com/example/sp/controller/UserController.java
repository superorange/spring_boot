package com.example.sp.controller;

import com.example.sp.bean.UserData;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author tian
 * @version 1.0
 * @date 2021/2/4 17:34
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {
    // 创建线程安全的Map，模拟users信息的存储
    static Map<Long, UserData> users = Collections.synchronizedMap(new HashMap<Long, UserData>());

    @GetMapping("/")
    public List<UserData> getUsers() {
        List<UserData> r = new ArrayList<UserData>(users.values());
        return r;
    }
    @PostMapping("/")
    public UserData addUserData(@RequestPart UserData userData){
        users.put(userData.getId(), userData);
        System.out.println(userData.getId());
        return userData;
    }
    @GetMapping("/str")
    public String getStr(){
         String str ="aaaaa\\r\\nbbbb";
        return str;
    }
}

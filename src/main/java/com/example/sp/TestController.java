package com.example.sp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tian
 * @version 1.0
 * @date 2021/2/4 13:57
 */
@RestController
public class TestController {
    @RequestMapping(path = "/t3")
    public User t1() {
        User user=new User();
        user.setUserName("小明");
        user.setPassword("xxxx");
        return user;
    }
    @RequestMapping(path = "/t2",method = RequestMethod.GET)
    public String t2() {
        return "小明";
    }
    @RequestMapping(path = "/t2",method = RequestMethod.POST)
    public String t2_post() {
        return "小黄";
    }

}


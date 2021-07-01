package com.mapstructlearning.service;

import com.mapstructlearning.pojo.UserInfo;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Log
public class UserInfoService {
    // private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoService.class);
    public List<UserInfo> getAll() {
        log.info("进入getAll方法"); // 默认生成 变量名为 log的 日志对象
        return new ArrayList<>();
    }
}

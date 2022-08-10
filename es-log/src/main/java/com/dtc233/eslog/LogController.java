package com.dtc233.eslog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dtc
 */
@Controller
@RequestMapping("log")
@Slf4j
public class LogController {
    @RequestMapping("add")
    public Object add()
    {
        Message build = Message.builder().id(23).title("123123").build();
        log.info("hahahahhha,11232,33");
        return new Object();
    }
}

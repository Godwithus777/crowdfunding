package com.gwwd.crowd.mvc.handler;

import com.gwwd.crowd.service.api.AdminService;
import com.gwwd.crowd.util.CrowdUtil;
import com.gwwd.crowd.util.ResultEntity;
import com.gwwd.entity.Admin;
import com.gwwd.entity.test.ParamData;
import com.gwwd.entity.test.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(TestHandler.class);


    @ResponseBody
    @RequestMapping("send/compose/object.json")
    public ResultEntity<Student> testReceiveComponseObject(@RequestBody Student student, HttpServletRequest request) {

        boolean judgeResult = CrowdUtil.judgeRequestType(request);

        logger.info("judgeResult=" + judgeResult);

        logger.info(student.toString());
        //[11:43:02.685] [INFO ] [http-nio-8080-exec-7] [com.gwwd.crowd.mvc.handler.TestHandler] [judgeResulttrue]
        //[11:43:02.685] [INFO ] [http-nio-8080-exec-7] [com.gwwd.crowd.mvc.handler.TestHandler] [Student{stuId=null, stuName='tom', address=Address{province='广东', city='深圳', street='后端'}, subjectList=[Subject{subjectName='JavaSE', subjectScore=100}, Subject{subjectName='SSM', subjectScore=99}], map={k1=v1, k2=v2}}]

        // 将"查询"到的student对象封装到ResultEntity中返回
        ResultEntity<Student> resultEntity = ResultEntity.successWithData(student);

//        String a = null;
//
//        System.out.println(a.length());

        return resultEntity;
    }

    @ResponseBody
    @RequestMapping("/send/array/three.html")
    public String testReceiveArrayThree(@RequestBody List<Integer> array) {

        for (Integer number : array) {
            logger.info("number=" + number);
        }
        return "success";

    }


    @ResponseBody
    @RequestMapping("/send/array/two.html")
    public String testReceiveArrayTwo(ParamData paramData) {
        List<Integer> array = paramData.getArray();

        for (Integer number : array) {
            System.out.println("number=" + number);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/compose/object.html")
    public String testReceiveComposeObject(@RequestBody Student student) {

        logger.info(student.toString());
        return "success";
    }


    @ResponseBody
    @RequestMapping("/send/array/one.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array) {
        for (Integer number : array) {
            System.out.println("number=" + number);
        }

        return "success";
    }


    @RequestMapping("/test/ssm.html")
    public String testssm(ModelMap modelMap, HttpServletRequest request) {

        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        logger.info("judgeResult=" + judgeResult);

        List<Admin> adminList = adminService.getAll();

        modelMap.addAttribute("adminList", adminList);

        String a = null;

        System.out.println(a.length());
//        System.out.println(10 / 0);
        return "target";
    }


}

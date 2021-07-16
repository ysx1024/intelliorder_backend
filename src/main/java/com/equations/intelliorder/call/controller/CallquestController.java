package com.equations.intelliorder.call.controller;


import com.equations.intelliorder.call.service.ICallquestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author equations
 * @since 2021-07-16
 */

@Controller
@RestController
@CrossOrigin
@RequestMapping("/call/callquest")
@Api(tags = "呼叫模块的Controller")


public class CallquestController {

    @Autowired
    private ICallquestService callquestService;

//    @RequestMapping(value = "/getStaffById", method = RequestMethod.GET)//url地址和请求方法类型
//    //这里是swagger自动生成api文档的相关注解
//    @ResponseBody
//    @ApiOperation(value = "根据员工ID检索员工列表", notes = "输入员工ID必须是有效的数字")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "staffId", value = "员工ID", required = true, dataType = "Integer")
//    })
//    @ApiResponses({
//            @ApiResponse(code = 404, message = "请求失败"),
//            @ApiResponse(code = 200, message = "请求成功")
//    })

}

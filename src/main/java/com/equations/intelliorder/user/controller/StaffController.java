package com.equations.intelliorder.user.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.equations.intelliorder.user.entity.Staff;
import com.equations.intelliorder.user.service.IStaffService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 * 前端控制器,对接前端的逻辑操作在这里进行，接口文档在这里生成
 * </p>
 *
 * @author equations
 * @since 2021-07-10
 */
@Controller
@RestController
@CrossOrigin
@RequestMapping("/user/staff")
@Api(tags = "员工信息管理的Controller")

public class StaffController {

    @Autowired
    private IStaffService staffService;//通过字段注入自动创建业务类，调用IstaffServer类
    //这里体现的是spring的三层架构，
    // controller（前端控制器）层调用service（业务逻辑）层，service调用Mapper（数据）层


    @RequestMapping(value = "/getStaffById", method = RequestMethod.POST)//url地址和请求方法类型
    //这里是swagger自动生成api文档的相关注解
    @ResponseBody
    @ApiOperation(value = "根据员工ID检索员工列表", notes = "输入员工ID必须是有效的数字")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staffId", value = "员工ID", required = true, dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String getStaffById(int staffId) {
        //对接前端，方法主要返回相应的状态参数
        Map<String, Object> map = new HashMap<>();//创建hashmap用来存储返回实体类或者列表并转成json数据
        try {
            Staff staff = staffService.getStaffById(staffId);
            map.put("status", "200");
            map.put("data", staff);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/getStaffByName", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据员工姓名模糊检索员工列表", notes = "需要输入中文姓名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String getStaffByName(String name) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Staff> staffList = staffService.getStaffByName(name);
            map.put("status", "200");
            map.put("data", staffList);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/showStaffList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "页面渲染时返回所有员工列表", notes = "渲染时即返回")
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String showStaffList() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Staff> staffList = staffService.showStaffList();
            map.put("status", "200");
            map.put("data", staffList);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/getStaffByType", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据员工类别检索员工列表", notes = "需要输入员工类别{服务员，后厨，前台}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staffType", value = "员工类别", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String getStaffByType(String staffType) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Staff> staffList = staffService.getStaffByType(staffType);
            map.put("status", "200");
            map.put("data", staffList);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/updateStaff", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据员工Id修改员工各项信息", notes = "需要输入员工数字ID与其余信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staffId", value = "员工ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String"),
            @ApiImplicitParam(name = "account", value = "账号", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String"),
            @ApiImplicitParam(name = "staffType", value = "员工类型", dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 304, message = "信息未修改"),
            @ApiResponse(code = 200, message = "更新成功"),
            @ApiResponse(code = 404, message = "更新失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String updateStaff(
            String staffId, String phone, String account,
            String password, String staffType) {
        Map<String, Object> map = new HashMap<>();
        try {
            Staff staff = staffService.getStaffById(Integer.parseInt(staffId));
            AtomicBoolean flag = new AtomicBoolean(true);
            if (!Objects.equals(staff.getPhone(), phone)) flag.set(false);
            else if (!Objects.equals(staff.getAccount(), account)) flag.set(false);
            else if (!Objects.equals(staff.getPassword(), password)) flag.set(false);
            else if (!Objects.equals(staff.getStaffType(), staffType)) flag.set(false);
            if (flag.get()) {
                map.put("status", "304");
                map.put("msg", "信息未修改");
            } else {
                int result = staffService.updateStaff(Integer.parseInt(staffId), phone, account, password, staffType);
                if (result == 1) {
                    map.put("status", "200");
                    map.put("msg", "更新成功");
                } else {
                    map.put("status", "404");
                    map.put("msg", "更新失败");
                }
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "/addStaff", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "增加员工与其各项信息", notes = "需要输入员工姓名与其余信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "staffType", value = "员工类型", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "添加成功"),
            @ApiResponse(code = 404, message = "添加失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String addStaff(String name, String phone, String staffType) {
        Map<String, Object> map = new HashMap<>();
        try {
            int result = staffService.addStaff(name, phone, staffType);
            if (result == 1) {
                map.put("status", "200");
                map.put("msg", "添加成功");
            } else {
                map.put("status", "404");
                map.put("msg", "添加失败");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除员工", notes = "需要输入员工ID，必须是有效的数字")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staffId", value = "员工ID", required = true, dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除成功"),
            @ApiResponse(code = 404, message = "删除失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String deleteById(Integer staffId) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(staffId);
        try {
            int result = staffService.deleteById(staffId);
            if (result == 1) {
                map.put("status", "200");
                map.put("msg", "删除成功");
            } else {
                map.put("status", "404");
                map.put("msg", "没有找到数据");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "员工登录", notes = "需要输入员工账号、密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "登录成功"),
            @ApiResponse(code = 404, message = "账号或密码错误"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String login(String account, String password) {
        Map<String, Object> map = new HashMap<>();
        try {
            Staff staff = staffService.login(account, password);
            if (!ObjectUtils.isEmpty(staff)) {
                //保持登录状态，将登录id存放在session中
                //session   由前端完成
                map.put("status", "200");
                map.put("data", staff);
            } else {
                map.put("status", "404");
                map.put("msg", "账号或密码错误");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "员工个人修改密码", notes = "输入原密码与新密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "staffId", value = "员工Id", required = true, dataType = "Int")
    })
    @ApiResponses({
            @ApiResponse(code = 304, message = "修改密码失败，原密码错误"),
            @ApiResponse(code = 200, message = "修改密码成功"),
            @ApiResponse(code = 404, message = "修改密码失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String changePassword(String oldPassword, String newPassword, int staffId) {
        Map<String, Object> map = new HashMap<>();
        try {

            Staff staff = staffService.getStaffById(staffId);
            if (!oldPassword.equals(staff.getPassword())) {
                map.put("status", "304");
                map.put("msg", "修改密码失败，原密码错误");
            } else {
                int result = staffService.changePassword(staffId, oldPassword, newPassword);
                Staff staff1 = staffService.getStaffById(staffId);
                if (result == 1) {
                    map.put("status", "200");
                    map.put("password", staff1.getPassword());
                } else {
                    map.put("status", "404");
                    map.put("msg", "修改密码失败");
                }
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/changeAccount", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "员工个人修改账号", notes = "输入新账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "新账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "staffId", value = "员工Id", required = true, dataType = "Int")
    })
    @ApiResponses({
            @ApiResponse(code = 304, message = "信息未修改"),
            @ApiResponse(code = 200, message = "修改成功"),
            @ApiResponse(code = 404, message = "修改失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String changeAccount(String account, int staffId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Staff staff = staffService.getStaffById(staffId);
            if (staff.getAccount().equals(account)) {
                map.put("status", "304");
                map.put("msg", "信息未修改");
            } else {
                int result = staffService.changeAccount(staffId, account);
                if (result == 1) {
                    map.put("status", "200");
                    map.put("msg", "修改成功");
                } else {
                    map.put("status", "404");
                    map.put("msg", "修改失败");
                }
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/changePhone", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "员工个人修改手机号", notes = "输入新手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "新手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "staffId", value = "员工Id", required = true, dataType = "Int")
    })
    @ApiResponses({
            @ApiResponse(code = 304, message = "信息未修改"),
            @ApiResponse(code = 200, message = "修改成功"),
            @ApiResponse(code = 404, message = "修改失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String changePhone(String phone, int staffId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Staff staff = staffService.getStaffById(staffId);
            if (staff.getPhone().equals(phone)) {
                map.put("status", "304");
                map.put("msg", "信息未修改");
            } else {
                int result = staffService.changePhone(staffId, phone);
                if (result == 1) {
                    map.put("status", "200");
                    map.put("msg", "修改成功");
                } else {
                    map.put("status", "404");
                    map.put("msg", "修改失败");
                }
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

        @RequestMapping(value = "/showStaffInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "登录后查看员工个人信息", notes = "需要登录查看个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staffId", value = "员工Id", required = true, dataType = "Int")
    })
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String showStaffInfo(String staffId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Staff staff = staffService.showStaffInfo(Integer.parseInt(staffId));
            map.put("status", "200");
            map.put("data", staff);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }
}

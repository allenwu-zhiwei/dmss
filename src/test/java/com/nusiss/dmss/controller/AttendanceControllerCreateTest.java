package com.nusiss.dmss.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nusiss.dmss.dao.PermissionRepository;
import com.nusiss.dmss.entity.AttendanceRecord;
import com.nusiss.dmss.filter.TokenAuthenticationFilter;
import com.nusiss.dmss.service.AttendanceOperation;
import com.nusiss.dmss.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AttendanceController.class)
class AttendanceControllerCreateTest {

    @Autowired
    private MockMvc mockMvc; // 模拟 MVC 的 HTTP 请求与响应

    @MockBean(name = "createAttendanceStrategy")
    private AttendanceOperation createAttendanceStrategy; // Mock 考勤创建策略

    @MockBean
    private PermissionRepository permissionRepository; // Mock 权限存储库

    @MockBean
    private UserService userService; // Mock 用户服务

    @MockBean
    private TokenAuthenticationFilter tokenAuthenticationFilter; // Mock Token 认证过滤器

    private ObjectMapper objectMapper; // JSON 序列化/反序列化工具

    // 在每个测试之前执行的初始化方法
    @BeforeEach
    void setUp() throws ServletException, IOException {
        objectMapper = new ObjectMapper(); // 初始化 ObjectMapper
        MockitoAnnotations.openMocks(this); // 初始化 Mockito 的 Mock 对象

        // Mock TokenAuthenticationFilter 的 doFilter 方法
        doAnswer((Answer<Void>) invocation -> {
            ServletRequest request = invocation.getArgument(0); // 获取请求
            ServletResponse response = invocation.getArgument(1); // 获取响应
            FilterChain chain = invocation.getArgument(2); // 获取过滤链
            chain.doFilter(request, response); // 模拟调用过滤链的下一个过滤器
            return null; // 方法无返回值
        }).when(tokenAuthenticationFilter).doFilter(any(), any(), any());
    }

    // 测试创建考勤记录的控制器逻辑
    @Test
    void testCreateAttendanceRecord() throws Exception {
        // 准备测试用的考勤记录对象
        AttendanceRecord newRecord = new AttendanceRecord();
        newRecord.setStudentId(1); // 设置学生 ID
        newRecord.setCourseId(101); // 设置课程 ID
        newRecord.setStatus("Present"); // 设置出勤状态
        newRecord.setRemarks("On time"); // 设置备注信息
        newRecord.setCreateUser("Teacher1"); // 设置创建用户
        newRecord.setTeacherId(1); // 设置教师 ID

        // Mock createAttendanceStrategy 的行为
        doNothing().when(createAttendanceStrategy).createAttendanceRecord(1, 101, newRecord);

        // 模拟发送 POST 请求到控制器的考勤创建接口
        mockMvc.perform(post("/api/v1/attendance/add/{studentId}/{courseId}", 1, 101)
                        .contentType(MediaType.APPLICATION_JSON) // 设置请求类型为 JSON
                        .content(objectMapper.writeValueAsString(newRecord))) // 将考勤记录对象转换为 JSON 字符串
                .andExpect(status().isOk()) // 断言响应状态为 200 OK
                .andExpect(content().string("Attendance record added successfully!")); // 断言响应内容为期望的消息
    }

    @Test
    void testCreateAttendanceRecord_RuntimeException() throws Exception {
        // 准备测试用的考勤记录对象
        AttendanceRecord newRecord = new AttendanceRecord();
        newRecord.setStudentId(1); // 设置学生 ID
        newRecord.setCourseId(101); // 设置课程 ID
        newRecord.setStatus("Present"); // 设置出勤状态
        newRecord.setRemarks("On time"); // 设置备注信息
        newRecord.setCreateUser("Teacher1"); // 设置创建用户
        newRecord.setTeacherId(1); // 设置教师 ID

        // 模拟 createAttendanceStrategy 抛出异常
        doThrow(new RuntimeException("Test Exception"))
                .when(createAttendanceStrategy)
                .createAttendanceRecord(eq(1), eq(101), any(AttendanceRecord.class));

        // 模拟发送 POST 请求到控制器的考勤创建接口
        mockMvc.perform(post("/api/v1/attendance/add/{studentId}/{courseId}", 1, 101)
                        .contentType(MediaType.APPLICATION_JSON) // 设置请求类型为 JSON
                        .content(objectMapper.writeValueAsString(newRecord))) // 将考勤记录对象转换为 JSON 字符串
                .andExpect(status().isBadRequest()) // 断言响应状态为 400 Bad Request
                .andExpect(content().string("Test Exception")); // 断言响应内容为期望的异常消息
    }

}

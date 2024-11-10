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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AttendanceController.class)
class AttendanceControllerUpdateTest {

    @Autowired
    private MockMvc mockMvc; // 模拟 MVC 的 HTTP 请求与响应

    @MockBean(name = "updateAttendanceStrategy")
    private AttendanceOperation updateAttendanceStrategy;// Mock 考勤创建策略

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

    @Test
    void testUpdateAttendanceRecord() throws Exception {
        AttendanceRecord updatedRecord = new AttendanceRecord();
        updatedRecord.setStudentId(1);
        updatedRecord.setCourseId(101);
        updatedRecord.setStatus("Absent");
        updatedRecord.setRemarks("Late");
        updatedRecord.setUpdateUser("Teacher2");

        doNothing().when(updateAttendanceStrategy).updateAttendanceRecord(1, 101, updatedRecord);

        mockMvc.perform(put("/api/v1/attendance/update/{studentId}/{courseId}", 1, 101)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecord)))
                .andExpect(status().isOk())
                .andExpect(content().string("Attendance record updated successfully!"));
    }
}

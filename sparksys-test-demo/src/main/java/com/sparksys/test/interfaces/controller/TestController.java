package com.sparksys.test.interfaces.controller;

import com.sparksys.authority.proto.UserGetRequest;
import com.sparksys.authority.proto.UserGetResponse;
import com.sparksys.authority.proto.UserServiceGrpc;
import com.sparksys.log.annotation.WebLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author: zhouxinlei
 * @date: 2020-09-04 15:08:58
 */
@RestController
@RequestMapping("/user")
@WebLog
@Api(tags = "测试管理")
public class TestController {

    @GrpcClient("sparksys-authority-server")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @GetMapping("/test")
    @ApiOperation("test grpc")
    public String testDubbo(@RequestParam("userId") Long userId) {
        // 创建请求
        UserGetRequest request = UserGetRequest.newBuilder().setId(userId).build();
        // 执行 gRPC 请求
        UserGetResponse userGetResponse = userServiceBlockingStub.get(request);
        return userGetResponse.getAccount();
    }
}

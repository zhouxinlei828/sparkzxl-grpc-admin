package com.sparksys.authority.infrastructure.grpc;

import com.google.protobuf.Timestamp;
import com.sparksys.authority.domain.repository.IAuthUserRepository;
import com.sparksys.authority.infrastructure.entity.AuthUser;
import com.sparksys.authority.proto.UserGetRequest;
import com.sparksys.authority.proto.UserGetResponse;
import com.sparksys.authority.proto.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description: grpc 用户实现类
 *
 * @author: zhouxinlei
 * @date: 2020-09-04 13:35:39
 */
@GrpcService
public class UserServiceImpl extends  UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private IAuthUserRepository userRepository;

    @Override
    public void get(UserGetRequest request, StreamObserver<UserGetResponse> responseObserver) {
        AuthUser authUser = userRepository.selectById(request.getId());
        // 创建响应对象
        UserGetResponse.Builder builder = UserGetResponse.newBuilder();
        builder.setId(request.getId())
                .setAccount(authUser.getAccount())
                .setAvatar(authUser.getAvatar())
                .setEducation(false)
                .setLastLoginTime(Timestamp.newBuilder().setNanos(authUser.getLastLoginTime().getNano()).build())
        .setName(authUser.getName())
        .setMobile(authUser.getMobile());
        // 返回响应
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}

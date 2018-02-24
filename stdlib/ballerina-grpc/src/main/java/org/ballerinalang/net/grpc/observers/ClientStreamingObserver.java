/*
 * Copyright (c) 2018, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ballerinalang.net.grpc.observers;

import com.google.protobuf.Message;
import io.grpc.stub.StreamObserver;
import org.ballerinalang.connector.api.Resource;
import org.ballerinalang.connector.api.Service;
import org.ballerinalang.net.grpc.builder.BalGenerate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.ballerinalang.net.grpc.MessageConstants.ON_COMPLETE_RESOURCE;
import static org.ballerinalang.net.grpc.MessageConstants.ON_ERROR_RESOURCE;
import static org.ballerinalang.net.grpc.MessageConstants.ON_MESSAGE_RESOURCE;

/**
 * This is Server Streaming Method Implementation for gRPC Service Call.
 */
public class ClientStreamingObserver implements StreamObserver<com.google.protobuf.Message> {
    private static final Logger LOG = LoggerFactory.getLogger(BalGenerate.class);
    private Map<String, Resource> resourceMap = new HashMap<>();
    private io.grpc.stub.StreamObserver<com.google.protobuf.Message> responseObserver;
    
    public ClientStreamingObserver(Service service) {
        for (Resource resource : service.getResources()) {
            resourceMap.put(resource.getName(), resource);
        }
        
    }
    
    @Override
    public void onNext(com.google.protobuf.Message value) {
        LOG.info(value.toString());
        Resource resource = resourceMap.get(ON_MESSAGE_RESOURCE);

//        BallerinaGrpcServerConnector grpcServerConnector = (BallerinaGrpcServerConnector) ConnectorUtils.
//                getBallerinaServerConnector(context, "ballerina.net.grpc");
    }
    
    @Override
    public void onError(Throwable t) {
        LOG.info("Err 3");
        Resource resource = resourceMap.get(ON_ERROR_RESOURCE);
    }
    
    @Override
    public void onCompleted() {
        LOG.info("Done...3");
        Resource resource = resourceMap.get(ON_COMPLETE_RESOURCE);
    }
    
    public void setResponseObserver(StreamObserver<Message> responseObserver) {
        this.responseObserver = responseObserver;
    }
}

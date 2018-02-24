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

import io.grpc.stub.StreamObserver;
import org.ballerinalang.connector.api.ConnectorUtils;
import org.ballerinalang.connector.api.Executor;
import org.ballerinalang.connector.api.ParamDetail;
import org.ballerinalang.connector.api.Resource;
import org.ballerinalang.connector.api.Service;
import org.ballerinalang.model.types.BStructType;
import org.ballerinalang.model.types.BType;
import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BFloat;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BRefType;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.net.grpc.Message;
import org.ballerinalang.net.grpc.MessageConstants;
import org.ballerinalang.net.grpc.MessageRegistry;
import org.ballerinalang.net.grpc.builder.BalGenerate;
import org.ballerinalang.net.grpc.exception.UnsupportedFieldTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ballerinalang.net.grpc.MessageConstants.ON_ERROR_RESOURCE;

/**
 * This is Client Streaming Method Implementation for gRPC Service Call.
 */
public class ServerStreamingObserver implements StreamObserver<com.google.protobuf.Message> {
    public static final Logger LOG = LoggerFactory.getLogger(BalGenerate.class);
    private String resourceName;
    private Map<String, Resource> resourceMap = new HashMap<>();
    
    public ServerStreamingObserver(Service service, String resourceName) {
        this.resourceName = resourceName;
        for (Resource resource : service.getResources()) {
            resourceMap.put(resource.getName(), resource);
        }
    }
    
    @Override
    public void onNext(com.google.protobuf.Message value) {
        LOG.info(value.toString());
        Resource resource = resourceMap.get(this.resourceName);
        List<ParamDetail> paramDetails = resource.getParamDetails();
        BValue[] signatureParams = new BValue[paramDetails.size()];
        //signatureParams[0] = getConnectionParameter(responseObserver);
        BValue requestParam = getRequestParameter((Message) value, resource);
        if (requestParam != null) {
            signatureParams[0] = requestParam;
        }
        Executor.execute(resource, null, signatureParams);
//        BallerinaGrpcServerConnector grpcServerConnector = (BallerinaGrpcServerConnector) ConnectorUtils.
//                getBallerinaServerConnector(context, "ballerina.net.grpc");
//        grpcServerConnector.
//        grpcServerConnector.serviceRegistered(null);
    }
    
    @Override
    public void onError(Throwable t) {
        Resource resource = resourceMap.get(ON_ERROR_RESOURCE);
        LOG.info("Err 2");
    }
    
    @Override
    public void onCompleted() {
        LOG.info("Done...2");
        Resource onCompleted = resourceMap.get(MessageConstants.ON_COMPLETE_RESOURCE);
        List<ParamDetail> paramDetails = onCompleted.getParamDetails();
        BValue[] signatureParams = new BValue[paramDetails.size()];
        //yavanna ooni observer
        //signatureParams[0] = getConnectionParameter(responseObserver);
        Executor.execute(onCompleted, null, signatureParams);
    }
    
    // TODO: 2/23/18 common util
    private BValue getRequestParameter(Message requestMessage, Resource resource) {
        if (resource == null || resource.getParamDetails() == null || resource.getParamDetails().size() > 2) {
            throw new RuntimeException("Invalid resource input arguments. arguments must not be greater than two");
        }
        
        if (resource.getParamDetails().size() == 2) {
            BType requestType = resource.getParamDetails().get(MessageConstants.REQUEST_MESSAGE_INDEX)
                    .getVarType();
            String requestName = resource.getParamDetails().get(MessageConstants.REQUEST_MESSAGE_INDEX).getVarName();
            
            return generateRequestStruct(requestMessage, requestName, requestType, resource);
        } else {
            return null;
        }
    }
    
    private BValue generateRequestStruct(Message request, String fieldName, BType structType, Resource resource) {
        BValue bValue = null;
        int stringIndex = 0;
        int intIndex = 0;
        int floatIndex = 0;
        int boolIndex = 0;
        int refIndex = 0;
        
        if (structType instanceof BStructType) {
            BStruct requestStruct = ConnectorUtils.createStruct(resource, structType.getPackagePath(), structType
                    .getName());
            for (BStructType.StructField structField : ((BStructType) structType).getStructFields()) {
                String structFieldName = structField.getFieldName();
                if (structField.getFieldType() instanceof BRefType) {
                    BType bType = structField.getFieldType();
                    if (MessageRegistry.getInstance().getMessageDescriptorMap().containsKey(bType.getName())) {
                        Message message = (Message) request.getFields().get(structFieldName);
                        requestStruct.setRefField(refIndex++, (BRefType) generateRequestStruct(message,
                                structFieldName, structField.getFieldType(), resource));
                    }
                } else {
                    if (request.getFields().containsKey(structFieldName)) {
                        String fieldType = structField.getFieldType().getName();
                        switch (fieldType) {
                            case "string": {
                                requestStruct.setStringField(stringIndex++, (String) request.getFields().get
                                        (structFieldName));
                                break;
                            }
                            case "int": {
                                requestStruct.setIntField(intIndex++, (Long) request.getFields().get
                                        (structFieldName));
                                break;
                            }
                            case "float": {
                                Float value = (Float) request.getFields().get(structFieldName);
                                if (value != null) {
                                    requestStruct.setFloatField(floatIndex++, Double.parseDouble(value.toString()));
                                }
                                break;
                            }
                            case "double": {
                                Double value = (Double) request.getFields().get(structFieldName);
                                if (value != null) {
                                    requestStruct.setFloatField(floatIndex++, Double.parseDouble(value.toString()));
                                }
                                break;
                            }
                            case "boolean": {
                                requestStruct.setBooleanField(boolIndex++, (Integer) request.getFields().get
                                        (structFieldName));
                                break;
                            }
                            default: {
                                throw new UnsupportedFieldTypeException("Error while generating request struct. Field" +
                                        " type is not supported : " + fieldType);
                            }
                        }
                    }
                }
            }
            bValue = requestStruct;
        } else {
            Map<String, Object> fields = request.getFields();
            if (fields.size() == 1 && fields.containsKey("value")) {
                fieldName = "value";
            }
            if (request.getFields().containsKey(fieldName)) {
                String fieldType = structType.getName();
                switch (fieldType) {
                    case "string": {
                        bValue = new BString((String) request.getFields().get(fieldName));
                        break;
                    }
                    case "int": {
                        bValue = new BInteger((Long) request.getFields().get(fieldName));
                        break;
                    }
                    case "float": {
                        Float value = (Float) request.getFields().get(fieldName);
                        if (value != null) {
                            bValue = new BFloat(Double.parseDouble(value.toString()));
                        }
                        break;
                    }
                    case "double": {
                        Double value = (Double) request.getFields().get(fieldName);
                        if (value != null) {
                            bValue = new BFloat(Double.parseDouble(value.toString()));
                        }
                        break;
                    }
                    case "boolean": {
                        bValue = new BBoolean((Boolean) request.getFields().get(fieldName));
                        break;
                    }
                    default: {
                        throw new UnsupportedFieldTypeException("Error while generating request struct. Field " +
                                "type is not supported : " + fieldType);
                    }
                }
            }
        }
        
        return bValue;
    }
}

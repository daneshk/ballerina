/*
 * Copyright (c) 2017, WSO2 Inc. (http://wso2.com) All Rights Reserved.
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
package org.ballerinalang.net.grpc;

import io.grpc.stub.ServerCalls.UnaryMethod;
import io.grpc.stub.StreamObserver;
import org.ballerinalang.connector.api.ConnectorUtils;
import org.ballerinalang.connector.api.Executor;
import org.ballerinalang.connector.api.Resource;
import org.ballerinalang.connector.api.Service;
import org.ballerinalang.model.types.BStructType;
import org.ballerinalang.model.types.BType;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;


/**
 * This is Unary Method Implementation for gRPC Service Call.
 */
public class UnaryMethodInvoker implements UnaryMethod<Object, Object> {

    private final Service service;
    private final Resource resource;

    public UnaryMethodInvoker(Service service, Resource resource) {
        this.service = service;
        this.resource = resource;
    }
    @Override
    public void invoke(Object request, StreamObserver<Object> responseObserver) {
        try {
            BStruct requestStruct = ConnectorUtils.createStruct(resource, service.getPackage(),
                    resource.getParamDetails().get(0).getVarType().getName());
            BStruct responseStruct = ConnectorUtils.createStruct(resource, service.getPackage(),
                    resource.getParamDetails().get(1).getVarType().getName());
/*            Object paramObject;
            List<Descriptors.FieldDescriptor> fieldDescriptors = methodDescriptor.getInputType().getFields();

            if (paramType.isAnnotationPresent(XmlRootElement.class)) {
                paramObject = AccessController.doPrivileged(new InputRequestElementMapper(request, fieldDescriptors));

            } else {
                if (fieldDescriptors.size() > 1) {
                    throw new RuntimeException("Service method " + method.getName() + " not handle multiple input " +
                            "parameters. ");
                }
                paramObject = AccessController.doPrivileged(new InputRequestFieldMapper(request, fieldDescriptors));

            }

            Object returnObj = method.invoke(serviceToInvoke, paramObject);
            Method returnMethod = Class.forName(methodDescriptor.getOutputType().getFullName()).getMethod
                    ("newBuilder");
            Object builderObject = returnMethod.invoke(null);
            if (returnType.isAnnotationPresent(XmlRootElement.class)) {
                AccessController.doPrivileged(new OutputResponseElementMapper(returnObj, builderObject));

            } else {
                AccessController.doPrivileged(new OutputResponseFieldMapper(returnObj, builderObject));

            }*/
            BType requestType = resource.getParamDetails().get(ServiceProtoConstants.REQUEST_MESSAGE_INDEX)
                    .getVarType();
            BType responseType = resource.getParamDetails().get(ServiceProtoConstants.RESPONSE_MESSAGE_INDEX)
                    .getVarType();
            int stringIndex = 0;
            int intIndex = 0;
            int floatIndex = 0;
            int boolIndex = 0;
            for (BStructType.StructField structField : ((BStructType) requestType).getStructFields()) {
                if (((Message) request).getFields().containsKey(structField.getFieldName())) {
                    switch (structField.getFieldType().toString()) {
                        case "string": {
                            requestStruct.setStringField(stringIndex++, (String) ((Message) request).getFields().get
                                    (structField
                                    .getFieldName()));
                            break;
                        }
                        case "int" : {
                            requestStruct.setIntField(intIndex++, (Long) ((Message) request).getFields().get
                                    (structField
                                    .getFieldName()));
                            break;
                        }
                        case "float" : {
                            Float value = (Float)((Message) request).getFields().get(structField.getFieldName());
                            if (value != null) {
                                requestStruct.setFloatField(floatIndex++, Double.parseDouble(value.toString()));
                            }
                            break;
                        }
                        case "boolean": {
                            requestStruct.setBooleanField(boolIndex++, (Integer) ((Message) request).getFields().get
                                    (structField.getFieldName()));
                        }
                        default: {
                            System.out.println("Field Types: " + structField.getFieldType().toString());
                            break;
                        }
                    }
                }
            }
            BValue[] bValues = new BValue[] {requestStruct, responseStruct};
//            Map<String, Object> properties = null;
            Executor.execute(resource, null, bValues);
//            Message.Builder responseBuilder = Message.newBuilder(resource.getParamDetails().get(1).getVarType().getName());

            BStruct bValue = (BStruct) bValues[1];
/*            stringIndex = 0;
            intIndex = 0;
            floatIndex = 0;
            boolIndex = 0;
            for (BStructType.StructField structField : ((BStructType) responseType).getStructFields()) {
                switch (structField.getFieldType().toString()) {
                    case "string": {
                        String value = bValue.getStringField(stringIndex++);
                        responseBuilder.addField(structField.getFieldName(), value);
                        break;
                    }
                    case "int" : {
                        long value = bValue.getIntField(intIndex++);
                        responseBuilder.addField(structField.getFieldName(), value);
                        break;
                    }
                    case "float" : {
                        double value = bValue.getFloatField(floatIndex++);
                        responseBuilder.addField(structField.getFieldName(), value);
                        break;
                    }
                    case "boolean": {
                        int value = bValue.getBooleanField(boolIndex++);
                        responseBuilder.addField(structField.getFieldName(), value != 0);
                        break;
                    }
                    default: {
                        if ()
                        System.out.println("Field Types: " + structField.getFieldType().toString());
                        break;
                    }
                }
            }*/

            responseObserver.onNext(generateProtoMessage(bValue, (BStructType) responseType));
        } finally {
            responseObserver.onCompleted();
        }
    }

    private com.google.protobuf.Message generateProtoMessage(BStruct bValue, BStructType structType) {
        Message.Builder responseBuilder = Message.newBuilder(structType.getName());
        int stringIndex = 0;
        int intIndex = 0;
        int floatIndex = 0;
        int boolIndex = 0;
        int refIndex = 0;
        for (BStructType.StructField structField : structType.getStructFields()) {
            switch (structField.getFieldType().toString()) {
                case "string": {
                    String value = bValue.getStringField(stringIndex++);
                    responseBuilder.addField(structField.getFieldName(), value);
                    break;
                }
                case "int" : {
                    long value = bValue.getIntField(intIndex++);
                    responseBuilder.addField(structField.getFieldName(), value);
                    break;
                }
                case "float" : {
                    double value = bValue.getFloatField(floatIndex++);
                    responseBuilder.addField(structField.getFieldName(), value);
                    break;
                }
                case "boolean": {
                    int value = bValue.getBooleanField(boolIndex++);
                    responseBuilder.addField(structField.getFieldName(), value != 0);
                    break;
                }
                default: {
                    if (MessageRegistry.getInstance().getMessageDescriptorMap().containsKey(structField.fieldType
                            .getName())) {
                        BStruct value = (BStruct)bValue.getRefField(refIndex++).value();
                        responseBuilder.addField(structField.getFieldName(), generateProtoMessage(value,
                                (BStructType) structField.getFieldType()));
                    }
                    System.out.println("Field Types: " + structField.getFieldType().toString());
                    break;
                }
            }
        }
        return responseBuilder.build();
    }

/*    private static String capitalize(String name) {
        if (name != null && name.length() != 0) {
            char[] chars = name.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        } else {
            return name;
        }
    }*/

/*    private class InputRequestElementMapper implements PrivilegedExceptionAction<Object> {

        final Object request;
        final List<Descriptors.FieldDescriptor> fieldDescriptors;

        InputRequestElementMapper(Object request, List<Descriptors.FieldDescriptor> fieldDescriptors) {
            this.request = request;
            this.fieldDescriptors = fieldDescriptors;
        }

        @Override
        public Object run() throws GrpcServerException {
            try {
                Object paramObject = paramType.newInstance();
                for (Descriptors.FieldDescriptor fieldDescriptor : fieldDescriptors) {
                    String fieldName = fieldDescriptor.getName();
                    if (fieldName != null) {
                        Field field = request.getClass().getDeclaredField(fieldName + FIELD_SUFFIX);
                        field.setAccessible(true);
                        Object value = field.get(request);
                        field.setAccessible(false);
                        Field matchedField = paramType.getDeclaredField(fieldName);
                        matchedField.setAccessible(true);
                        matchedField.set(paramObject, value);
                        matchedField.setAccessible(false);
                    }
                }
                return paramObject;
            } catch (NoSuchFieldException | InstantiationException | IllegalAccessException e) {
                throw new GrpcServerException("Error while mapping request inputs to service method " +
                        "params", e);
            }
        }
    }

    private static class InputRequestFieldMapper implements PrivilegedExceptionAction<Object> {

        final Object request;
        final List<Descriptors.FieldDescriptor> fieldDescriptors;

        InputRequestFieldMapper(Object request, List<Descriptors.FieldDescriptor> fieldDescriptors) {
            this.request = request;
            this.fieldDescriptors = fieldDescriptors;
        }

        @Override
        public Object run() throws GrpcServerException {
            try {
                String fieldName = fieldDescriptors.get(0).getName();
                Field field = request.getClass().getDeclaredField(fieldName + FIELD_SUFFIX);
                field.setAccessible(true);
                Object paramObject =  field.get(request);
                field.setAccessible(false);
                return paramObject;
            } catch (NoSuchFieldException | IllegalAccessException  e) {
                throw new GrpcServerException("Error while mapping request inputs to service method " +
                        "params", e);
            }
        }
    }

    private class OutputResponseElementMapper implements PrivilegedExceptionAction<Object> {
        final Object returnObject;
        final Object builderObject;

        OutputResponseElementMapper(Object returnObject, Object builderObject) {
            this.returnObject = returnObject;
            this.builderObject = builderObject;
        }

        @Override
        public Object run() throws GrpcServerException {
            try {
                Field[] responseFields = returnType.getDeclaredFields();
                for (Field field : responseFields) {
                    Method setterMethod = builderObject.getClass().getMethod("set" + capitalize(field
                            .getName()), field.getType());
                    field.setAccessible(true);
                    setterMethod.invoke(builderObject, field.get(returnObject));
                    field.setAccessible(false);
                }
                return builderObject;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new GrpcServerException("Error while mapping response types to service response" , e);
            }

        }
    }

    private static class OutputResponseFieldMapper implements PrivilegedExceptionAction<Object> {
        final Object returnObject;
        final Object builderObject;

        OutputResponseFieldMapper(Object returnObject, Object builderObject) {
            this.returnObject = returnObject;
            this.builderObject = builderObject;
        }

        @Override
        public Object run() throws GrpcServerException {
            try {
                Field[] returnFields = builderObject.getClass().getDeclaredFields();
                for (Field field : returnFields) {
                    String fieldName = field.getName();
                    if (fieldName != null && fieldName.endsWith("_")) {
                        String paramName = fieldName.substring(0, fieldName.lastIndexOf('_'));
                        field.setAccessible(true);
                        Method setterMethod = builderObject.getClass().getMethod("set" + capitalize
                                (paramName), field.get(builderObject).getClass());
                        setterMethod.invoke(builderObject, returnObject);
                    }
                }
                return builderObject;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new GrpcServerException("Error while mapping response types to service response" , e);
            }
        }
    }*/
}

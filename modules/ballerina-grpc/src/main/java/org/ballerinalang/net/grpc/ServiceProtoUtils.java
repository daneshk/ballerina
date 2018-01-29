package org.ballerinalang.net.grpc;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import org.ballerinalang.connector.api.ConnectorUtils;
import org.ballerinalang.connector.api.Resource;
import org.ballerinalang.model.types.BStructType;
import org.ballerinalang.model.types.BType;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.net.grpc.definition.Field;
import org.ballerinalang.net.grpc.definition.File;
import org.ballerinalang.net.grpc.definition.Message;
import org.ballerinalang.net.grpc.definition.Method;
import org.ballerinalang.net.grpc.definition.Service;
import org.ballerinalang.net.grpc.exception.GrpcServerException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by daneshk on 1/18/18.
 */
public class ServiceProtoUtils {

    public static File generateServiceDefinition(org.ballerinalang.connector.api.Service service) {
        File.Builder fileBuilder = File.newBuilder(service.getName() + ServiceProtoConstants.PROTO_FILE_EXTENSION)
                .setSyntax(ServiceProtoConstants.PROTOCOL_SYNTAX).setPackage(service.getPackage());
        Service.Builder serviceBuilder = Service.newBuilder(service.getName());

        for (Resource resource : service.getResources()) {
            BStruct requestStruct = ConnectorUtils.createStruct(resource, service.getPackage(), resource
                    .getParamDetails().get(ServiceProtoConstants.REQUEST_MESSAGE_INDEX).getVarType().getName());
            BStruct responseStruct = ConnectorUtils.createStruct(resource, service.getPackage(), resource
                    .getParamDetails().get(ServiceProtoConstants.RESPONSE_MESSAGE_INDEX).getVarType().getName());
            BType requestType = resource.getParamDetails().get(ServiceProtoConstants.REQUEST_MESSAGE_INDEX)
                    .getVarType();
            BType responseType = resource.getParamDetails().get(ServiceProtoConstants.RESPONSE_MESSAGE_INDEX)
                    .getVarType();
            // request message build logic
            Message.Builder requestMsgBuilder = Message.newBuilder(requestStruct.getType().getName());
            int fieldIndex = 1;
            for (BStructType.StructField structField : ((BStructType) requestType).getStructFields()) {
                Field messageField = Field.newBuilder(structField.getFieldName())
                        .setIndex(fieldIndex++)
                        .setType(structField.getFieldType().getName()).build();
                requestMsgBuilder.addFieldDefinition(messageField);
            }
            Message requestMessage = requestMsgBuilder.build();
            if (!fileBuilder.getRegisteredMessages().contains(requestMessage.getDescriptorProto())) {
                fileBuilder.setMessage(requestMessage);
            }

            //response message build logic
            Message.Builder responseMsgBuilder = Message.newBuilder(responseStruct.getType().getName());
            fieldIndex = 1;
            for (BStructType.StructField structField : ((BStructType) responseType).getStructFields()) {
                Field messageField = Field.newBuilder(structField.getFieldName())
                        .setIndex(fieldIndex++)
                        .setType(structField.getFieldType().getName()).build();
                responseMsgBuilder.addFieldDefinition(messageField);
            }
            Message responseMessage = responseMsgBuilder.build();
            if (!fileBuilder.getRegisteredMessages().contains(responseMessage.getDescriptorProto())) {
                fileBuilder.setMessage(responseMessage);
            }

            Method resourceMethod = Method.newBuilder(resource.getName())
                    .setClientStreaming(false)
                    .setServerStreaming(false)
                    .setInputType(requestType.getPackagePath() + ServiceProtoConstants.CLASSPATH_SYMBOL + requestType
                            .getName())
                    .setOutputType(responseType.getPackagePath() + ServiceProtoConstants.CLASSPATH_SYMBOL +
                            responseType.getName())
                    .build();
            serviceBuilder.addMethod(resourceMethod);
        }
        Service grpcService = serviceBuilder.build();
        fileBuilder.setService(grpcService);
        return fileBuilder.build();
    }

    public static int getFieldWireType(Descriptors.FieldDescriptor.Type fieldType) {
        if (fieldType == null) {
            return -1;
        }
        Integer wireType = ServiceProtoConstants.WIRE_TYPE_MAP.get(fieldType.toProto());
        if (wireType != null) {
            return wireType;
        } else {
            return 2;
        }
    }

    public static void writeConfigurationFile(File protoFileDefinition, String filename) throws GrpcServerException {
        java.io.File configDir = new java.io.File(ServiceProtoConstants.PROTO_BUF_DIRECTORY);

        // create protobuf contract directory.
        if (!configDir.exists() && !configDir.mkdirs()) {
            throw new GrpcServerException("Error while creating protobuf contract directory.");
        }

        // write the yaml string to the configuration file in config directory
        try (PrintWriter out = new PrintWriter(new java.io.File(configDir.getPath(), filename
                + ServiceProtoConstants.PROTO_FILE_EXTENSION), ServiceProtoConstants.UTF_8_CHARSET)) {
            out.println(protoFileDefinition.getFileDefinition());
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new GrpcServerException("Error while creating new contract file.", e);
        }
    }
}

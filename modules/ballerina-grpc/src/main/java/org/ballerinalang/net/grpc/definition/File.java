package org.ballerinalang.net.grpc.definition;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import org.ballerinalang.net.grpc.exception.GrpcServerException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by daneshk on 1/17/18.
 */
public class File {
    private DescriptorProtos.FileDescriptorProto fileDescriptorProto;
    private List<Message> messageList = new ArrayList<>();
    private List<Service> serviceList = new ArrayList<>();

    private File(DescriptorProtos.FileDescriptorProto fileDescriptorProto) {
        this.fileDescriptorProto = fileDescriptorProto;
    }

    protected void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    protected void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    public static File.Builder newBuilder(String fileName) {
        return new File.Builder(fileName);
    }

    public DescriptorProtos.FileDescriptorProto getFileDescriptorProto() {
        return fileDescriptorProto;
    }

    public Descriptors.FileDescriptor getFileDescriptor() throws GrpcServerException {
        try {
            return Descriptors.FileDescriptor.buildFrom(fileDescriptorProto, new Descriptors.FileDescriptor[] {});
        } catch (Descriptors.DescriptorValidationException e) {
            throw new GrpcServerException(e);
        }
    }

    public String getFileDefinition() {
        StringBuilder fileDefinition = new StringBuilder();

        fileDefinition.append("syntax = \"").append(fileDescriptorProto.getSyntax()).append("\";\n");
        fileDefinition.append("package ").append(fileDescriptorProto.getPackage()).append(";\n");
        for (Service service : serviceList) {
            fileDefinition.append(service.getServiceDefinition());
        }
        for (Message message : messageList) {
            fileDefinition.append(message.getMessageDefinition());
        }
        return fileDefinition.toString();
    }

    /**
     * FieldDefinition.Builder
     */
    public static class Builder {

        public File build() {
            File file = new File(fileBuilder.build());
            file.setMessageList(messageList);
            file.setServiceList(serviceList);
            return file;
        }

        public Builder setPackage(String packageName) {
            fileBuilder.setPackage(packageName);
            return this;
        }

        public Builder setSyntax(String syntax) {
            fileBuilder.setSyntax(syntax);
            return this;
        }

        public Builder setService(Service serviceDefinition) {
            fileBuilder.addService(serviceDefinition.getServiceDescriptor());
            serviceList.add(serviceDefinition);
            return this;
        }

        public Builder setMessage(Message messageDefinition) {
            fileBuilder.addMessageType(messageDefinition.getDescriptorProto());
            messageList.add(messageDefinition);
            return this;
        }

        public List<DescriptorProtos.DescriptorProto> getRegisteredMessages() {
            return Collections.unmodifiableList(fileBuilder.getMessageTypeList());
        }

        private Builder(String fileName) {
            fileBuilder = DescriptorProtos.FileDescriptorProto.newBuilder();
            fileBuilder.setName(fileName);
        }

        private DescriptorProtos.FileDescriptorProto.Builder fileBuilder;
        private List<Message> messageList = new ArrayList<>();
        private List<Service> serviceList = new ArrayList<>();
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
        throw new java.io.NotSerializableException( getClass().getName() );
    }

    private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException {
        throw new java.io.NotSerializableException( getClass().getName() );
    }
}

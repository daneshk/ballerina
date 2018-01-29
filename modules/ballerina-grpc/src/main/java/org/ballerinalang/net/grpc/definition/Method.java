package org.ballerinalang.net.grpc.definition;

import com.google.protobuf.DescriptorProtos;

/**
 * Created by daneshk on 1/17/18.
 */
public class Method {
    private DescriptorProtos.MethodDescriptorProto methodDescriptor;

    private Method(DescriptorProtos.MethodDescriptorProto methodDescriptor) {
        this.methodDescriptor = methodDescriptor;
    }

    public static Method.Builder newBuilder(String serviceName) {
        return new Method.Builder(serviceName);
    }

    public DescriptorProtos.MethodDescriptorProto getMethodDescriptor() {
        return methodDescriptor;
    }

    public String getMethodDefinition() {
        StringBuilder methodDefinition = new StringBuilder();
        methodDefinition.append("\trpc ").append(methodDescriptor.getName()).append("(").append
                (methodDescriptor.getInputType()).append(") returns (").append(methodDescriptor.getOutputType())
                .append(")").append(";\n");
        return methodDefinition.toString();
    }

    /**
     * MethodDefinition.Builder
     */
    public static class Builder {

        public Method build() {
            return new Method(methodBuilder.build());
        }

        public Builder setClientStreaming(boolean flag) {
            methodBuilder.setClientStreaming(flag);
            return this;
        }

        public Builder setServerStreaming(boolean flag) {
            methodBuilder.setServerStreaming(flag);
            return this;
        }

        public Builder setInputType(String inputType) {
            methodBuilder.setInputType(inputType);
            return this;
        }

        public Builder setOutputType(String outputType) {
            methodBuilder.setOutputType(outputType);
            return this;
        }

        private Builder(String methodName) {
            methodBuilder = DescriptorProtos.MethodDescriptorProto.newBuilder();
            methodBuilder.setName(methodName);
        }


        private DescriptorProtos.MethodDescriptorProto.Builder methodBuilder;
    }
}

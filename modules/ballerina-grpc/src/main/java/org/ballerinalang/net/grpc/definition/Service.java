package org.ballerinalang.net.grpc.definition;

import com.google.protobuf.DescriptorProtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daneshk on 1/17/18.
 */
public class Service {
    private DescriptorProtos.ServiceDescriptorProto serviceDescriptor;
    private List<Method> methodList = new ArrayList<>();

    private Service(DescriptorProtos.ServiceDescriptorProto serviceDescriptor) {
        this.serviceDescriptor = serviceDescriptor;
    }

    public static Builder newBuilder(String serviceName) {
        return new Builder(serviceName);
    }

    public DescriptorProtos.ServiceDescriptorProto getServiceDescriptor() {
        return serviceDescriptor;
    }

    public String getServiceDefinition() {
        StringBuilder serviceDefinition = new StringBuilder();
        serviceDefinition.append("service ").append(serviceDescriptor.getName()).append(" {\n");
/*        for (DescriptorProtos.MethodDescriptorProto methodDescriptor : serviceDescriptor.getMethodList()) {
            serviceDefinition.append("\trpc ").append(methodDescriptor.getName()).append(" (").append
                    (methodDescriptor.getInputType()).append(") returns (").append(methodDescriptor.getOutputType())
                    .append(")").append(";\n");
        }*/

        for (Method method : methodList) {
            serviceDefinition.append("\t").append(method.getMethodDefinition());
        }
        serviceDefinition.append("}\n");
        return serviceDefinition.toString();
    }

    /**
     * ServiceDefinition.Builder
     */
    public static class Builder {
        private DescriptorProtos.ServiceDescriptorProto.Builder serviceBuilder;
        private List<Method> methodList = new ArrayList<>();

        public Builder addMethod(Method methodDefinition) {
            methodList.add(methodDefinition);
            serviceBuilder.addMethod(methodDefinition.getMethodDescriptor());
            return this;
        }

        public Service build() {
            Service service = new Service(serviceBuilder.build());
            service.methodList = methodList;
            return service;
        }


        private Builder(String serviceName) {
            serviceBuilder = DescriptorProtos.ServiceDescriptorProto.newBuilder();
            serviceBuilder.setName(serviceName);
        }
    }
}

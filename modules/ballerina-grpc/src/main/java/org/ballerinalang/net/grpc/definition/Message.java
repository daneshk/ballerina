package org.ballerinalang.net.grpc.definition;

import com.google.protobuf.DescriptorProtos.DescriptorProto;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by daneshk on 1/17/18.
 */
public class Message {
    private DescriptorProto descriptorProto;
    private List<Field> fieldList = new ArrayList<>();

    private Message(DescriptorProto descriptorProto) {
        this.descriptorProto = descriptorProto;
    }

    public static Message.Builder newBuilder(String messageName) {
        return new Message.Builder(messageName);
    }

    public DescriptorProto getDescriptorProto() {
        return descriptorProto;
    }

    public String getMessageDefinition() {
        StringBuilder msgDefinition = new StringBuilder();
        msgDefinition.append("message ").append(descriptorProto.getName()).append(" {\n");
/*        for (DescriptorProtos.FieldDescriptorProto fieldDescriptor : descriptorProto.getFieldList()) {
            String fieldType = null;
            if (!fieldDescriptor.getTypeName().isEmpty()) {
                fieldType = fieldDescriptor.getTypeName();
            } else if (ServiceProtoConstants.FIELD_TYPE_MAP.containsKey(fieldDescriptor.getType().getNumber())) {
                fieldType = ServiceProtoConstants.FIELD_TYPE_MAP.get(fieldDescriptor.getType().getNumber());
            }
            
            msgDefinition.append("\t").append(fieldType).append(" ").append(fieldDescriptor
                    .getName()).append(" = ").append(fieldDescriptor.getNumber()).append(";\n");
        }*/
        for (Field field : fieldList) {
            msgDefinition.append("\t").append(field.getFieldDefinition());
        }
        msgDefinition.append("}\n");
        return msgDefinition.toString();

    }

    /**
     * MessageDefinition.Builder
     */
    public static class Builder {
        private DescriptorProto.Builder messageDescriptorBuilder;
        private List<Field> fieldList = new ArrayList<>();

        public Message build() {
            Message message = new Message(messageDescriptorBuilder.build());
            message.fieldList = fieldList;
            return message;
        }

        private Builder(String methodName) {
            messageDescriptorBuilder = DescriptorProto.newBuilder();
            messageDescriptorBuilder.setName(methodName);
        }

        public Builder addMessageDefinition(Message messageDefinition) {
            messageDescriptorBuilder.addNestedType(messageDefinition.getDescriptorProto());
            return this;
        }

        public Builder addFieldDefinition(Field fieldDefinition) {
            fieldList.add(fieldDefinition);
            messageDescriptorBuilder.addField(fieldDefinition.getFieldDescriptorProto());
            return this;
        }
    }
}

package org.ballerinalang.net.grpc.definition;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import org.ballerinalang.net.grpc.ServiceProtoConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daneshk on 1/17/18.
 */
public class Field {
    private DescriptorProtos.FieldDescriptorProto fieldDescriptorProto;
    private String fieldType;

    private Field(DescriptorProtos.FieldDescriptorProto descriptorProto) {
        this.fieldDescriptorProto = descriptorProto;
    }

    public static Field.Builder newBuilder(String fieldName) {
        return new Field.Builder(fieldName);
    }

    public DescriptorProtos.FieldDescriptorProto getFieldDescriptorProto() {
        return fieldDescriptorProto;
    }

    public String getFieldDefinition() {
        StringBuilder fieldDefinition = new StringBuilder();
        fieldDefinition.append(fieldType).append(" ").append(fieldDescriptorProto
                .getName()).append(" = ").append(fieldDescriptorProto.getNumber()).append(";\n");
        return fieldDefinition.toString();

    }

    /**
     * FieldDefinition.Builder
     */
    public static class Builder {
        private DescriptorProtos.FieldDescriptorProto.Builder fieldDescriptorBuilder;
        private String fieldType;

        public Field build() {
            Field field = new Field(fieldDescriptorBuilder.build());
            field.fieldType = fieldType;
            return field;
        }

        private Builder(String methodName) {
            fieldDescriptorBuilder = DescriptorProtos.FieldDescriptorProto.newBuilder();
            fieldDescriptorBuilder.setName(methodName);
        }

        public Builder setLabel(String label) {
            DescriptorProtos.FieldDescriptorProto.Label protoLabel = sLabelMap.get(label);
            if (protoLabel == null) {
                throw new IllegalArgumentException("Illegal label: " + label);
            }
            fieldDescriptorBuilder.setLabel(protoLabel);
            return this;
        }

        public Builder setType(String type) {
            fieldType = type;
            DescriptorProtos.FieldDescriptorProto.Type primType = sTypeMap.get(type);
            if (primType != null) {
                fieldDescriptorBuilder.setType(primType);
            } else {
                fieldDescriptorBuilder.setTypeName(type);
            }
            return this;
        }

        public Builder setIndex(int index) {
            fieldDescriptorBuilder.setNumber(index);
            return this;
        }

        public Builder setDefaultValue(String defaultValue) {
            if (defaultValue != null) {
                fieldDescriptorBuilder.setDefaultValue(defaultValue);
            }
            return this;
        }
    }


    private static Map<String,DescriptorProtos.FieldDescriptorProto.Type> sTypeMap;
    private static Map<String,DescriptorProtos.FieldDescriptorProto.Label> sLabelMap;

    static {
        sTypeMap = new HashMap<>();
        sTypeMap.put("double", DescriptorProtos.FieldDescriptorProto.Type.TYPE_DOUBLE);
        sTypeMap.put("float", DescriptorProtos.FieldDescriptorProto.Type.TYPE_FLOAT);
        sTypeMap.put("int32", DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32);
        sTypeMap.put("int64", DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT64);
        sTypeMap.put("uint32", DescriptorProtos.FieldDescriptorProto.Type.TYPE_UINT32);
        sTypeMap.put("uint64", DescriptorProtos.FieldDescriptorProto.Type.TYPE_UINT64);
        sTypeMap.put("sint32", DescriptorProtos.FieldDescriptorProto.Type.TYPE_SINT32);
        sTypeMap.put("sint64", DescriptorProtos.FieldDescriptorProto.Type.TYPE_SINT64);
        sTypeMap.put("fixed32", DescriptorProtos.FieldDescriptorProto.Type.TYPE_FIXED32);
        sTypeMap.put("fixed64", DescriptorProtos.FieldDescriptorProto.Type.TYPE_FIXED64);
        sTypeMap.put("sfixed32", DescriptorProtos.FieldDescriptorProto.Type.TYPE_SFIXED32);
        sTypeMap.put("sfixed64", DescriptorProtos.FieldDescriptorProto.Type.TYPE_SFIXED64);
        sTypeMap.put("bool", DescriptorProtos.FieldDescriptorProto.Type.TYPE_BOOL);
        sTypeMap.put("string", DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING);
        sTypeMap.put("bytes", DescriptorProtos.FieldDescriptorProto.Type.TYPE_BYTES);

        sLabelMap = new HashMap<>();
        sLabelMap.put("optional", DescriptorProtos.FieldDescriptorProto.Label.LABEL_OPTIONAL);
        sLabelMap.put("required", DescriptorProtos.FieldDescriptorProto.Label.LABEL_REQUIRED);
        sLabelMap.put("repeated", DescriptorProtos.FieldDescriptorProto.Label.LABEL_REPEATED);
    }
}

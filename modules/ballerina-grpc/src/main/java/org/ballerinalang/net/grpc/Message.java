package org.ballerinalang.net.grpc;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.MessageLite;
import org.ballerinalang.net.grpc.exception.GrpcServerException;

import java.util.HashMap;
import java.util.Map;


public class Message extends GeneratedMessageV3 {
    private static final long serialVersionUID = 0L;
    private Map<String, Object> fields_ ;
    private String messageName;

    // Use Message.newBuilder() to construct.
    protected Message(Builder builder) {
        super(builder);
        this.messageName = builder.messageName;
    }
    protected Message(String messageName) {
        fields_ = new HashMap<>();
        this.messageName = messageName;
    }

    protected void setFieldValues(Map fieldValues) {
        this.fields_ = fieldValues;
    }

    public Map<String, Object> getFields() {
        return fields_;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    public Message(
            String messageName,
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
        this(messageName);

        Descriptors.Descriptor messageDescriptor = getDescriptor();
        Map<Integer, Descriptors.FieldDescriptor> fields = new HashMap<>();
        for(Descriptors.FieldDescriptor fieldDescriptor : messageDescriptor.getFields()) {
            String name = fieldDescriptor.getName();
            Descriptors.FieldDescriptor.Type fieldType = fieldDescriptor.getType();
            int number = fieldDescriptor.getNumber();
            int byteCode = ((number << 3) + ServiceProtoUtils.getFieldWireType(fieldType));

            System.out.println("Field Description {" +
                    "name=" + name +
                    ", type='" + fieldType.toString() + '\'' +
                    ", number='" + number + '\'' +
                    ", bytecode='" + byteCode + '\'' +
                    '}');
            fields.put(byteCode, fieldDescriptor);
        }


        int mutable_bitField0_ = 0;
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                com.google.protobuf.UnknownFieldSet.newBuilder();
        try {
            boolean done = false;
            while (!done) {
                int tag = input.readTag();
                if (tag == 0) {
                    done = true;
                } else if (fields.containsKey(tag)) {
                    Descriptors.FieldDescriptor fieldDescriptor = fields.get(tag);
                    String name = fieldDescriptor.getName();
                    switch (fieldDescriptor.getType().toProto().getNumber()) {
                        case DescriptorProtos.FieldDescriptorProto.Type.TYPE_DOUBLE_VALUE: {
                            double value = input.readDouble();
                            fields_.put(name, value);
                            break;
                        }
                        case DescriptorProtos.FieldDescriptorProto.Type.TYPE_FLOAT_VALUE: {
                            float value = input.readFloat();
                            fields_.put(name, value);
                            break;
                        }
                        case DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT64_VALUE: {
                            long value = input.readInt64();
                            fields_.put(name, value);
                            break;
                        }
                        case DescriptorProtos.FieldDescriptorProto.Type.TYPE_UINT64_VALUE: {
                            long value = input.readUInt64();
                            fields_.put(name, value);
                            break;
                        }
                        case DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32_VALUE: {
                            int value = input.readInt32();
                            fields_.put(name, value);
                            break;
                        }
                        case DescriptorProtos.FieldDescriptorProto.Type.TYPE_FIXED64_VALUE: {
                            long value = input.readFixed64();
                            fields_.put(name, value);
                            break;
                        }
                        case DescriptorProtos.FieldDescriptorProto.Type.TYPE_FIXED32_VALUE: {
                            int value = input.readFixed32();
                            fields_.put(name, value);
                            break;
                        }
                        case DescriptorProtos.FieldDescriptorProto.Type.TYPE_BOOL_VALUE: {
                            boolean value = input.readBool();
                            fields_.put(name, value);
                            break;
                        }
                        case DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING_VALUE: {
                            String value = input.readStringRequireUtf8();
                            fields_.put(name, value);
                            break;
                        }
                        default: {
                            System.out.println("Type value is: " + fieldDescriptor.getType().toProto().getNumber());
                            break;
                        }
                    }
                } else {
                    if (!parseUnknownFieldProto3(
                            input, unknownFields, extensionRegistry, tag)) {
                        done = true;
                    }
                }
            }
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(this);
        } catch (java.io.IOException e) {
            throw new com.google.protobuf.InvalidProtocolBufferException(
                    e).setUnfinishedMessage(this);
        } finally {
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }
    }

    public com.google.protobuf.Descriptors.Descriptor getDescriptor() {
        try {
            return MessageRegistry.getInstance().getMessageDecriptor(messageName);
        } catch (GrpcServerException e) {
            throw new RuntimeException("Error while retrieving message descrptor ", e);
        }
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
/*        return new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                getDescriptor(), new String[] {"Name",});*/
        throw new UnsupportedOperationException("Operation is not supported");
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
        byte isInitialized = memoizedIsInitialized;
        if (isInitialized == 1) return true;
        if (isInitialized == 0) return false;

        memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
            throws java.io.IOException {
        Descriptors.Descriptor messageDescriptor = getDescriptor();
        for (Descriptors.FieldDescriptor fieldDescriptor : messageDescriptor.getFields()) {
            if (fields_.containsKey(fieldDescriptor.getName())) {
                switch (fieldDescriptor.getType().toProto().getNumber()) {
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_DOUBLE_VALUE: {
                        output.writeDouble(fieldDescriptor.getNumber(), (Double) fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_FLOAT_VALUE: {
                        output.writeFloat(fieldDescriptor.getNumber(),Float.parseFloat(fields_.get(fieldDescriptor
                                .getName()).toString()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT64_VALUE: {
                        output.writeInt64(fieldDescriptor.getNumber(), (Long) fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_UINT64_VALUE: {
                        output.writeUInt64(fieldDescriptor.getNumber(), (Long) fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32_VALUE: {
                        output.writeInt32(fieldDescriptor.getNumber(), Integer.parseInt(fields_.get(fieldDescriptor
                                .getName()).toString()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_FIXED64_VALUE: {
                        output.writeFixed64(fieldDescriptor.getNumber(), (Long) fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_FIXED32_VALUE: {
                        output.writeFixed32(fieldDescriptor.getNumber(), Integer.parseInt(fields_.get(fieldDescriptor
                                .getName()).toString()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_BOOL_VALUE: {
                        output.writeBool(fieldDescriptor.getNumber(), (Boolean) fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING_VALUE: {
                        com.google.protobuf.GeneratedMessageV3.writeString(output, fieldDescriptor.getNumber(),
                                fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_MESSAGE_VALUE: {
                        output.writeMessage(fieldDescriptor.getNumber(), (MessageLite) fields_.get(fieldDescriptor
                                .getName()));
                        break;
                    }
                    default: {
                        System.out.println("Type value is: " + fieldDescriptor.getType().toProto().getNumber());
                        break;
                    }
                }

            }
        }
        unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = memoizedSize;
        if (size != -1) return size;

        size = 0;
        Descriptors.Descriptor messageDescriptor = getDescriptor();
        for (Descriptors.FieldDescriptor fieldDescriptor : messageDescriptor.getFields()) {
            if (fields_.containsKey(fieldDescriptor.getName())) {
                switch (fieldDescriptor.getType().toProto().getNumber()) {
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_DOUBLE_VALUE: {
                        size += com.google.protobuf.CodedOutputStream.computeDoubleSize(fieldDescriptor.getNumber(),
                                (Double) fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_FLOAT_VALUE: {
                        size += com.google.protobuf.CodedOutputStream.computeFloatSize(fieldDescriptor.getNumber
                                (), Float.parseFloat(fields_.get(fieldDescriptor.getName()).toString()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT64_VALUE: {
                        size += com.google.protobuf.CodedOutputStream.computeInt64Size(fieldDescriptor.getNumber
                                (), (Long) fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_UINT64_VALUE: {
                        size += com.google.protobuf.CodedOutputStream.computeUInt64Size(fieldDescriptor.getNumber
                                (), (Long) fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_INT32_VALUE: {
                        size += com.google.protobuf.CodedOutputStream.computeInt32Size(fieldDescriptor.getNumber
                                (), Integer.parseInt(fields_.get(fieldDescriptor.getName()).toString()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_FIXED64_VALUE: {
                        size += com.google.protobuf.CodedOutputStream.computeFixed64Size(fieldDescriptor
                                .getNumber(), (Long) fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_FIXED32_VALUE: {
                        size += com.google.protobuf.CodedOutputStream.computeFixed32Size(fieldDescriptor
                                .getNumber(), Integer.parseInt(fields_.get(fieldDescriptor.getName()).toString()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_BOOL_VALUE: {
                        size += com.google.protobuf.CodedOutputStream.computeBoolSize(fieldDescriptor.getNumber()
                                , (Boolean) fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_STRING_VALUE: {
                        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(fieldDescriptor
                                .getNumber(), fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    case DescriptorProtos.FieldDescriptorProto.Type.TYPE_MESSAGE_VALUE: {
                        size += com.google.protobuf.CodedOutputStream.computeMessageSize(fieldDescriptor
                                .getNumber(), (MessageLite) fields_.get(fieldDescriptor.getName()));
                        break;
                    }
                    default: {
                        System.out.println("Type value is: " + fieldDescriptor.getType().toProto().getNumber());
                        break;
                    }
                }
            }
        }
        size += unknownFields.getSerializedSize();
        memoizedSize = size;
        return size;
    }

/*    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Message)) {
            return super.equals(obj);
        }
        Message other = (Message) obj;

        boolean result = true;
        result = result && getName()
                .equals(other.getName());
        result = result && unknownFields.equals(other.unknownFields);
        return result;
    }

    @java.lang.Override
    public int hashCode() {
        if (memoizedHashCode != 0) {
            return memoizedHashCode;
        }
        int hash = 41;
        hash = (19 * hash) + getDescriptor().hashCode();
        hash = (37 * hash) + NAME_FIELD_NUMBER;
        hash = (53 * hash) + getName().hashCode();
        hash = (29 * hash) + unknownFields.hashCode();
        memoizedHashCode = hash;
        return hash;
    }*/

    public Builder newBuilderForType() {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    public static Builder newBuilder(String messageName) {
        return new Message.Builder(messageName);
    }

    public Builder toBuilder() {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    @java.lang.Override
    protected Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        throw new UnsupportedOperationException("This method is not supported.");
    }
    /**
     * <pre>
     * The request message containing the user's name.
     * </pre>
     *
     * Protobuf type {@code org.ballerinalang.net.grpc.Message}
     */
    public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder> {

        private Map<String, Object> fields = new HashMap<>();
        private final String messageName;

        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
/*            return new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                    getDescriptor(), new String[] {"Name",});*/
            throw new UnsupportedOperationException("Operation is not supported");
        }

        // Construct using org.ballerinalang.net.grpc.Message.newBuilder()
        private Builder(String messageName) {
            this.messageName = messageName;
        }

        private Descriptors.Descriptor getDescriptor() {
            try {
                return MessageRegistry.getInstance().getMessageDecriptor(messageName);
            } catch (GrpcServerException e) {
                throw new RuntimeException("Error while retrieving Message Descriptor. ", e);
            }
        }

        public Builder clear() {
            super.clear();
            fields.clear();

            return this;
        }

        public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
            return getDescriptor();
        }

        public Message getDefaultInstanceForType() {
            return new Message(messageName);
        }

        public com.google.protobuf.Message build() {
            com.google.protobuf.Message result = buildPartial();
            if (!result.isInitialized()) {
                throw newUninitializedMessageException(result);
            }
            return result;
        }

        public com.google.protobuf.Message buildPartial() {
            Message result = new Message(this);
            if (!fields.isEmpty()) {
                result.setFieldValues(fields);
            }
            onBuilt();
            return result;
        }

        public Map<String, Object> getFields() {
            return fields;
        }

        public Builder addField(String name, Object value) {
            fields.put(name, value);
            return this;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "fields=" + fields +
                    ", messageName='" + messageName + '\'' +
                    '}';
        }
    }

    // @@protoc_insertion_point(class_scope:org.ballerinalang.net.grpc.Message)
/*    private static final Message DEFAULT_INSTANCE;
    static {
        DEFAULT_INSTANCE = Message.newBuilder().setDescriptor();
    }*/

/*    public static Message getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }*/

/*    private static final com.google.protobuf.Parser<Message> PARSER = new com.google.protobuf.AbstractParser<Message>() {
                public Message parsePartialFrom(
                        com.google.protobuf.CodedInputStream input,
                        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws com.google.protobuf.InvalidProtocolBufferException {
                    return new Message(input, extensionRegistry);
                }
            };*/

    @java.lang.Override
    public com.google.protobuf.Parser<Message> getParserForType() {
        return new MessageParser(messageName);
    }

    public Message getDefaultInstanceForType() {
        throw new UnsupportedOperationException("Default instance is not supported.");
    }

}

package org.ballerinalang.net.grpc;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created by daneshk on 1/23/18.
 */
public class MessageParser extends com.google.protobuf.AbstractParser<Message> {

    private final String messageName;

    MessageParser(String messageName) {
        this.messageName = messageName;
    }

    @Override
    public Message parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return new Message(messageName, input, extensionRegistry);
    }
}

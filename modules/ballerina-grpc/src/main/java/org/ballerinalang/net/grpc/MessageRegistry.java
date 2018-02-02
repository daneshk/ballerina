package org.ballerinalang.net.grpc;

import com.google.protobuf.Descriptors;
import org.ballerinalang.net.grpc.exception.GrpcServerException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daneshk on 1/22/18.
 */
public class MessageRegistry {

    private Map<String, Descriptors.Descriptor> messageDescriptors = new HashMap<>();

    private static volatile MessageRegistry messageRegistry = new MessageRegistry();

    private MessageRegistry() {}

    public static MessageRegistry getInstance() {
        return messageRegistry;
    }

    public void addMessageDescriptor(String messageName, Descriptors.Descriptor messageDescriptor) throws
            GrpcServerException {
        if (messageName == null) {
            throw new GrpcServerException("Message name cannot be null");
        }
        if (messageDescriptor == null) {
            throw new GrpcServerException("Message Descriptor cannot be null");
        }
        messageDescriptors.put(messageName, messageDescriptor);
    }

    public Descriptors.Descriptor getMessageDecriptor(String messageName) throws GrpcServerException {
        if (messageName == null) {
            throw new GrpcServerException("Message name cannot be null");
        }
        return messageDescriptors.get(messageName);
    }

    public Map<String, Descriptors.Descriptor> getMessageDescriptorMap() {
        return Collections.unmodifiableMap(messageDescriptors);
    }
}

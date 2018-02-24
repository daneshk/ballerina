package org.ballerinalang.net.grpc.actions;

import io.grpc.MethodDescriptor;
import org.ballerinalang.bre.Context;
import org.ballerinalang.connector.api.AbstractNativeAction;
import org.ballerinalang.connector.api.ConnectorFuture;
import org.ballerinalang.model.types.BStructType;
import org.ballerinalang.model.types.BType;
import org.ballerinalang.model.types.BTypes;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaAction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.util.codegen.PackageInfo;
import org.ballerinalang.util.codegen.StructInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code Get} is the GET action implementation of the HTTP Connector.
 */
@BallerinaAction(
        packageName = "ballerina.net.grpc",
        actionName = "execute",
        connectorName = "GRPCConnector",
        args = {
                @Argument(name = "conn", type = TypeKind.STRUCT, structType = "ClientConnection", structPackage =
                        "ballerina.net.grpc"),
                @Argument(name = "payload", type = TypeKind.ANY),
                @Argument(name = "methodID", type = TypeKind.INT)
        },
        returnType = {
                @ReturnType(type = TypeKind.ANY),
                @ReturnType(type = TypeKind.STRUCT, structType = "ConnectorError",
                        structPackage = "ballerina.net.grpc"),
        },
        connectorArgs = {
                @Argument(name = "host", type = TypeKind.STRING),
                @Argument(name = "port", type = TypeKind.INT)
        }
)
public class Execute2 extends AbstractNativeAction {
    
    private static final Logger logger = LoggerFactory.getLogger(Execute2.class);
    
    @Override
    public ConnectorFuture execute(Context context) {
    
//        ClientConnectorFuture ballerinaFuture = new ClientConnectorFuture();
//        BStruct outboundError = createStruct(context);
//        BValue payloadBValue;
//        BStruct connectionStub;
//        Integer methodId;
//        try {
//            // todo: 2/15/18 Check type and cast
//            payloadBValue = getRefArgument(context, 2);
//            connectionStub = (BStruct) getRefArgument(context, 1);
//            methodId = getIntArgument(context, 0);
//        } catch (ArrayIndexOutOfBoundsException e) {
//            outboundError.setStringField(0, "gRPC Connector Error :" + e.getMessage());
//            ballerinaFuture.notifyReply(null, outboundError);
//            return ballerinaFuture;
//        }
//        String methodName = GRPCClientStub.getGrpcServiceProto().getSet().getService(0)
//                .getMethodList().get(methodId).getName();
//        com.google.protobuf.Descriptors.MethodDescriptor methodDescriptor = GRPCClientStub.getMethodDescriptorMap
//                (methodName);
//        com.google.protobuf.Message message = MessageUtil.generateProtoMessage
//                (payloadBValue, methodDescriptor.getOutputType());
//        try {
//            Message messageRes = null;
//            if (connectionStub.getNativeData("stub") instanceof GRPCBlockingStub) {
//                GRPCBlockingStub grpcBlockingStub = (GRPCBlockingStub)
//                        connectionStub.getNativeData("stub");
//                if (getMethodType(methodId).equals(MethodDescriptor.MethodType.UNARY)) {
//                    messageRes = (Message) grpcBlockingStub.executeUnary(message, methodId);
//                } else if ((getMethodType(methodId).equals(MethodDescriptor.MethodType.SERVER_STREAMING))) {
//                    messageRes = (Message) grpcBlockingStub.executeServerStreaming(message, methodId);
//                }
//            } else if (connectionStub.getNativeData("stub") instanceof GRPCNonBlockingStub) {
//                GRPCNonBlockingStub grpcNonBlockingStub = (GRPCNonBlockingStub)
//                        connectionStub.getNativeData("stub");
//                if (getMethodType(methodId).equals(MethodDescriptor.MethodType.UNARY)) {
//                    grpcNonBlockingStub.executeUnary(message, new UnaryMethodObserver(context), methodId);
//                } else if ((getMethodType(methodId).equals(MethodDescriptor.MethodType.SERVER_STREAMING))) {
//                    grpcNonBlockingStub.executeServerStreaming(message, new ClientStreamingObserver(context),
// methodId);
//                } else if ((getMethodType(methodId).equals(MethodDescriptor.MethodType.CLIENT_STREAMING))) {
//                    grpcNonBlockingStub.executeServerStreaming(message, new ServerStreamingObserver(context),
// methodId);
//                }
//            } else if (connectionStub.getNativeData("stub") instanceof GRPCFutureStub) {
//                // TODO: 2/22/18
//            } else {
//                throw new RuntimeException("Unsupported stub type.");
//            }
//
//            DescriptorProtos.MethodDescriptorProto methodDescriptorProto = Connect.getServiceProto().getSet()
//                    .getService(0).getMethod(methodId);
//            String resMessageName = methodDescriptorProto.getOutputType().split("\\.")[methodDescriptorProto
//                    .getOutputType().split("\\.").length - 1];
//            BValue bValue = MessageUtil.generateRequestStruct(messageRes, resMessageName,
//                    getBalType(resMessageName, context, resMessageName), context);
//            ballerinaFuture.notifyReply(bValue, null);
//            return ballerinaFuture;
//        } catch (RuntimeException e) {
//            outboundError.setStringField(0, "gRPC Connector Error :" + e.getMessage());
//            ballerinaFuture.notifyReply(null, outboundError);
//            return ballerinaFuture;
//        }
        
        return null;
    }
    
    /**
     * todo.
     *
     * @param protoType .
     * @return .
     */
    private BType getBalType(String protoType, Context context, String reqMessageName) {
        
        if (protoType.equalsIgnoreCase("DoubleValue") || protoType
                .equalsIgnoreCase("FloatValue")) {
            return BTypes.typeFloat;
        } else if (protoType.equalsIgnoreCase("Int32Value") || protoType
                .equalsIgnoreCase("Int64Value") || protoType
                .equalsIgnoreCase("UInt32Value") || protoType
                .equalsIgnoreCase("UInt64Value")) {
            return BTypes.typeInt;
        } else if (protoType.equalsIgnoreCase("BoolValue")) {
            return BTypes.typeBoolean;
        } else if (protoType.equalsIgnoreCase("StringValue")) {
            return BTypes.typeString;
        } else if (protoType.equalsIgnoreCase("BytesValue")) {
            return BTypes.typeBlob;
        } else {
            // TODO: 2/22/18 enum
            return context.getProgramFile().getEntryPackage().getStructInfo(reqMessageName).getType();
        }
    }
    
    public static MethodDescriptor.MethodType getMethodType(int methodID) {
        com.google.protobuf.DescriptorProtos.MethodDescriptorProto proto = Connect.getServiceProto().getSet()
                .getService(0).getMethodList().get(methodID);
        if (proto.getClientStreaming() && proto.getServerStreaming()) {
            return MethodDescriptor.MethodType.BIDI_STREAMING;
        } else if (!(proto.getClientStreaming() || proto.getServerStreaming())) {
            return MethodDescriptor.MethodType.UNARY;
        } else if (proto.getServerStreaming()) {
            return MethodDescriptor.MethodType.SERVER_STREAMING;
        } else if (proto.getClientStreaming()) {
            return MethodDescriptor.MethodType.CLIENT_STREAMING;
        } else {
            return MethodDescriptor.MethodType.UNKNOWN;
        }
    }
//    /**
//     * .
//     *
//     * @param list .
//     * @return .
//     */
//    private Map<String, DescriptorProtos.DescriptorProto> attrybuteList(java.util.List<DescriptorProtos
//            .DescriptorProto> list) {
//
//        Map<String, DescriptorProtos.DescriptorProto> stringObjectMap = new HashMap<>();
//        for (DescriptorProtos.DescriptorProto proto : list) {
//            stringObjectMap.put(proto.getName(), proto);
//        }
//        return stringObjectMap;
//    }
    
    
    private BStruct createStruct(Context context) {
        
        PackageInfo httpPackageInfo = context.getProgramFile()
                .getPackageInfo("ballerina.net.grpc");
        StructInfo structInfo = httpPackageInfo.getStructInfo("ConnectorError");
        BStructType structType = structInfo.getType();
        return new BStruct(structType);
    }
    
}

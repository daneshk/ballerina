package org.ballerinalang.net.grpc.actions;

import com.google.protobuf.Message;
import io.grpc.MethodDescriptor;
import io.grpc.stub.StreamObserver;
import org.ballerinalang.bre.Context;
import org.ballerinalang.connector.api.AbstractNativeAction;
import org.ballerinalang.connector.api.ConnectorFuture;
import org.ballerinalang.connector.api.Service;
import org.ballerinalang.model.types.BStructType;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BConnector;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.nativeimpl.actions.ClientConnectorFuture;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaAction;
import org.ballerinalang.natives.annotations.ReturnType;
import org.ballerinalang.net.grpc.BallerinaGrpcServerConnector;
import org.ballerinalang.net.grpc.GRPCClientStub;
import org.ballerinalang.net.grpc.observers.ClientStreamingObserver;
import org.ballerinalang.net.grpc.observers.ServerStreamingObserver;
import org.ballerinalang.net.grpc.observers.UnaryMethodObserver;
import org.ballerinalang.net.grpc.stubs.GRPCNonBlockingStub;
import org.ballerinalang.util.codegen.PackageInfo;
import org.ballerinalang.util.codegen.StructInfo;

import static org.ballerinalang.net.grpc.BallerinaGrpcServerConnector.PACKAGE_NAME_SEPERATOR;
import static org.ballerinalang.net.grpc.MessageConstants.PROTOCOL_PACKAGE_GRPC;
/**.
 * .
 */
@BallerinaAction(
        packageName = "ballerina.net.grpc",
        actionName = "connect",
        connectorName = "GRPCConnector",
        args = {
                @Argument(name = "observerName", type = TypeKind.STRING),
                @Argument(name = "methodId", type = TypeKind.INT)
        },
        returnType = {
                @ReturnType(type = TypeKind.STRUCT, structType = "ClientConnection",
                        structPackage = "ballerina.net.grpc"),
                @ReturnType(type = TypeKind.STRUCT, structType = "ConnectorError",
                        structPackage = "ballerina.net.grpc"),
        },
        connectorArgs = {
                @Argument(name = "host", type = TypeKind.STRING),
                @Argument(name = "port", type = TypeKind.INT)
        }
)
public class AddObserver extends AbstractNativeAction {
    
    @Override
    public ConnectorFuture execute(Context context) {
        int methodId;
        String observerName;
        BStruct outboundError = createStruct(context, "ConnectorError");
        ClientConnectorFuture ballerinaFuture = new ClientConnectorFuture();
        try {
            BConnector bConnector = (BConnector) getRefArgument(context, 0);
            observerName = bConnector.getStringField(0);
            Service service = BallerinaGrpcServerConnector.getregisteredService(PROTOCOL_PACKAGE_GRPC +
                    PACKAGE_NAME_SEPERATOR + observerName);
            methodId = (int) bConnector.getIntField(0);
            String methodName = GRPCClientStub.getGrpcServiceProto().getSet().getService(0)
                    .getMethodList().get(methodId).getName();
            StreamObserver<Message> observer = null;
            
            io.grpc.stub.AbstractStub<GRPCNonBlockingStub> grpcNonBlockingStub = null;
            if (getMethodType(methodId).equals(MethodDescriptor.MethodType.UNARY)) {
                observer = new UnaryMethodObserver(context);
            } else if ((getMethodType(methodId).equals(MethodDescriptor.MethodType.SERVER_STREAMING))) {
                observer = new ClientStreamingObserver(service);
            } else if ((getMethodType(methodId).equals(MethodDescriptor.MethodType.CLIENT_STREAMING))) {
                observer = new ServerStreamingObserver(service, methodName);
            }
            BStruct outboundResponse = createStruct(context, "ClientConnection");
            outboundResponse.addNativeData("observer", observer);
            ballerinaFuture.notifyReply(outboundResponse, null);
            return ballerinaFuture;
        } catch (ArrayIndexOutOfBoundsException e) {
            outboundError.setStringField(0, "gRPC Connector Error :" + e.getMessage());
            ballerinaFuture.notifyReply(null, outboundError);
            return ballerinaFuture;
        }
    }
    
    private BStruct createStruct(Context context, String structName) {
        PackageInfo httpPackageInfo = context.getProgramFile()
                .getPackageInfo("ballerina.net.grpc");
        StructInfo structInfo = httpPackageInfo.getStructInfo(structName);
        BStructType structType = structInfo.getType();
        return new BStruct(structType);
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
}

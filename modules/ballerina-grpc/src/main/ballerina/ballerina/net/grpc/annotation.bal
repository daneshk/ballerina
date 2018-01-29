package ballerina.net.grpc;

@Description { value:"gRPC protobuf annotations"}
@Field {value:"fieldType: Host of the service"}
@Field {value:"index: Port number of the service"}
@Field {value:"name: HTTPS port number of service"}
public annotation protobuf attach field {
    string fieldType;
    int index;
    int name;
}
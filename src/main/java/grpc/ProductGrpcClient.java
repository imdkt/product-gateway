package grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Component;
import product.GetProductRequest;
import product.GetProductResponse;
import product.ProductServiceGrpc;

@Component
public class ProductGrpcClient {

    private final ProductServiceGrpc.ProductServiceBlockingStub stub;

    public ProductGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("product-service", 50051) // Docker Compose service name
                .usePlaintext()
                .build();
        stub = ProductServiceGrpc.newBlockingStub(channel);
    }

    public GetProductResponse getProduct(String id) {
        try {
            GetProductRequest request = GetProductRequest.newBuilder()
                    .setId(id)
                    .build();
            return stub.getProduct(request);
        } catch (StatusRuntimeException e) {
            throw e;
        }
    }
}

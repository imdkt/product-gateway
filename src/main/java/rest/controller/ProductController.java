package rest.controller;


import com.example.productgateway.grpc.ProductGrpcClient;
import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.GetProductResponse;
import product.Product;

import java.util.Map;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductGrpcClient grpcClient;

    public ProductController(ProductGrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id) {
        try {
            GetProductResponse response = grpcClient.getProduct(id);
            Product product = response.getProduct();

            Map<String, Object> json = Map.of(
                    "id", product.getId(),
                    "name", product.getName(),
                    "price", product.getPriceInCents() / 100.0,
                    "availability_status", switch (product.getStatus()) {
                        case AVAILABLE -> "Available";
                        case OUT_OF_STOCK -> "Out of Stock";
                        default -> "Status Unknown";
                    }
            );

            return ResponseEntity.ok(json);

        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode().name().equals("NOT_FOUND")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "product with ID '" + id + "' not found"));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unable to connect to gRPC service"));
        }
    }
}

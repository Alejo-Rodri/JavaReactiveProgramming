package sec11.client;

import common.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClientResponse;
import sec09.assignment.Product;

public class ExternalServiceClient extends AbstractHttpClient {
    public Mono<String> getProductName(int id) {
        return get("/demo06/product/" + id);
    }

    public Mono<String> getCountry() {
        return get("/demo06/country");
    }

    private Mono<String> get(String path) {
        return this.httpClient.get()
                .uri(path)
                // we use this to handle the 400 and 500 errors, netty do NOT handle them automatically
                .response(this::toResponse)
                .next();
    }

    private Flux<String> toResponse(HttpClientResponse httpClientResponse, ByteBufFlux byteBufFlux) {
        return switch (httpClientResponse.status().code()) {
            case 200 -> byteBufFlux.asString();
            case 400 -> Flux.error(new ClientError());
            default -> Flux.error(new ServerError());
        };
    }
}

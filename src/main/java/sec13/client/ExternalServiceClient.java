package sec13.client;

import common.AbstractHttpClient;
import reactor.core.publisher.Mono;

public class ExternalServiceClient extends AbstractHttpClient {
    public Mono<String> getBook() {
        return this.httpClient.get()
                .uri("/demo07/book")
                .responseContent()
                .asString()
                .contextWrite(UserService.userCategoryContext())
                .next();
                //.subscribe();
    }
}

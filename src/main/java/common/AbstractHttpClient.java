package common;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

public abstract class AbstractHttpClient {
    private static final String BASE_URL = "http://localhost:8080";
    protected final HttpClient httpClient;

    public AbstractHttpClient() {
        var loopResources = LoopResources.create("ale", 1, true);
        this.httpClient = HttpClient.create().runOn(loopResources).baseUrl(BASE_URL);
    }
}

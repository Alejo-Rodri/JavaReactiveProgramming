package sec02;

import reactor.core.publisher.Mono;

public interface FileService {
    Mono<String> read(String filename);
    Mono<String> write(String filename, String content);
    Mono<String> delete(String filename);
}

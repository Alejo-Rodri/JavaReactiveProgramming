package sec10.assignment.window;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;

public class LogService {
    private static final Logger log = LoggerFactory.getLogger(LogService.class);
    private static int counter = 1;
    private static final String BASE_PATH = "src/main/resources/sec10";

    public static Flux<Void> processData() {
        return data()
                .window(Duration.ofSeconds(1))
                .flatMap(LogService::writeData);
    }

    private static Mono<Void> writeData(Flux<String> data) {
        return data
                .doOnNext(string -> {
                    Path path = Paths.get(BASE_PATH, "log" + counter);
                    if (Files.notExists(path)) {
                        try {
                            Files.createFile(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    try {
                        Files.writeString(path, string, StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .doOnComplete(() -> {
                    log.info("log{} file written successfully", counter++);
                })
                .then();
    }

    private static Flux<String> data() {
        return Flux.interval(Duration.ofMillis(100))
                .map(i -> Util.getFaker().elderScrolls().creature() + "\n");
    }
}

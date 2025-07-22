package sec02;

import common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileAssignment implements FileService {
    private static final Logger log = LoggerFactory.getLogger(FileAssignment.class);
    private final String BASE_PATH = "src/main/resources/sec02";

    public static void main(String[] args) {
        var fileAssignment = new FileAssignment();
        fileAssignment.read("suavemente.txt").subscribe(Util.subscriber("read_assignment"));

        fileAssignment.write(
                "algo_contigo.txt",
                "y hace falta que te diga que me muero por tener algo contigo?"
                )
                .subscribe(Util.subscriber("write_assignment"));

        fileAssignment.delete("algo_contigo.txt").subscribe(Util.subscriber("delete_assignment"));
    }

    @Override
    public Mono<String> read(String filename) {
        Path path = Paths.get(BASE_PATH, filename);

        return Mono.fromCallable(() -> {
            log.info("reading file...");
            return Files.readString(path);
        });
    }

    @Override
    public Mono<String> write(String filename, String content) {
        Path path = Paths.get(BASE_PATH, filename);

        return Mono.fromCallable(() -> {
            log.info("writing into file...");

            if (Files.notExists(path)) Files.createFile(path);

            Files.writeString(path, content, StandardOpenOption.APPEND);
            return Files.readString(path);
        });
    }

    @Override
    public Mono<String> delete(String filename) {
        Path path = Paths.get(BASE_PATH, filename);

        return Mono.fromCallable(() -> {
            log.info("deleting file...");
            var content = Files.readString(path);
            Files.delete(path);
            return content;
        });
    }
}

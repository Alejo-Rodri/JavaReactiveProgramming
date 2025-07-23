package sec04.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileReaderServiceImpl implements FileReaderService{
    private static final Logger log = LoggerFactory.getLogger(FileReaderServiceImpl.class);
    @Override
    public Flux<String> read(Path path) {
        return generate(path);
    }

    // cual es la diferencia entre create y generate y cuando deberia usar cada uno?

    private Flux<String> create(Path path) {
        return Flux.<String>create(fluxSink -> {
            fluxSink.onRequest(request -> {
                try(BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                    String line = reader.readLine();

                    for (int i = 0; i < request && line != null && !fluxSink.isCancelled(); i++) {
                        fluxSink.next(line);
                        line = reader.readLine();
                    }

                } catch (IOException e) {
                    log.error("error", e);
                    fluxSink.error(e);
                }

                if (!fluxSink.isCancelled()) fluxSink.complete();
            });
        });
    }

    private Flux<String> generate(Path path) {
        return Flux.generate(
                () -> openFile(path),
                this::readFile,
                this::closeFile
        );
    }

    private BufferedReader openFile(Path path) throws IOException {
        log.info("opening file...");
        return Files.newBufferedReader(path);
    }

    private BufferedReader readFile(BufferedReader bufferedReader, SynchronousSink<String> sink) {
        try {
            var line = bufferedReader.readLine();

            log.info("reading file...");
            if (Objects.isNull(line)) sink.complete();
            else sink.next(line);
        } catch (IOException e) {
            sink.error(e);
            log.error("error", e);
        }

        return bufferedReader;
    }

    private void closeFile(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            log.error("error",e);
        }
    }
}

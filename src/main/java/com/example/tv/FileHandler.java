package com.example.tv;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.*;

public class FileHandler {

    public int test() {
        final Path path = FileSystems.getDefault().getPath(System.getProperty("user.home"), "Desktop");
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            final WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                WatchKey wk = null;
                try {
                    wk = watchService.take();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                for (WatchEvent<?> event : wk.pollEvents()) {
                    //we only register "ENTRY_MODIFY" so the context is always a Path.
                    final Path changed = (Path) event.context();
                    if (changed.endsWith("testFile.txt")) {
                        System.out.println("My file has changed");
                        return 1;
                    }
                }
                // reset the key
                boolean valid = wk.reset();
                if (!valid) {
                    System.out.println("Key has been unregisterede");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

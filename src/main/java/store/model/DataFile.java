package store.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DataFile {

    private final List<String> lines;

    public DataFile() {
        this.lines = new ArrayList<>();
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public String getHead() {
        return lines.getFirst();
    }

    public List<String> getLines() {
        return lines.subList(1, lines.size());
    }

    public void forEach(Consumer<String> consumer) {
        lines.subList(1, lines.size())
                .forEach(consumer);
    }
}

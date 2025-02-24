package util;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileLoader<T> {
    private Function<String[], T> constructor;

    public FileLoader(Function<String[], T> constructor) {
        this.constructor = constructor;
    }

    public List<T> load (String filePath){
        List<T> items = new ArrayList<>();
        try(BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            items.addAll(br.lines()
                    .map(line -> constructor.apply(line.split(",")))
                            .collect(Collectors.toList()));
        }
        catch (Exception e) {
            System.err.println("Файл + " + filePath + " не найден");
        }
        return items;
    }
}

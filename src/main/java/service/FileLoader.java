package service;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FileLoader<T> {
    private Function<String[], T> constructor;

    public FileLoader(Function<String[], T> constructor) {
        this.constructor = constructor;
    }

    public List<T> load (String filePath){
        List<T> items = new ArrayList<>();
        try(BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine())!= null){
                String[] values = line.split(",");
                items.add(constructor.apply(values));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}

package springintroex.springintro.util;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileUtilImpl implements FileUtil {
    @Override
    public String[] fileContent(String path) throws IOException {
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        List<String> fileInfo = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null){
            fileInfo.add(line);
        }

        return fileInfo.stream()
                .filter(l -> !l.equals(""))
                .toArray(String[]::new);
    }
}

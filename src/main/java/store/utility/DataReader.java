package store.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import store.config.constant.ErrorMessage;
import store.config.system.DataPath;
import store.exception.FileInputException;
import store.model.DataFile;

public class DataReader {

    public static DataFile readData(DataPath path) {
        DataFile file = new DataFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(path.getValue()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                file.addLine(line);
            }
        } catch (Exception e) {
            throw new FileInputException(ErrorMessage.FILE_FORMAT_INVALID);
        }

        return file;
    }

}

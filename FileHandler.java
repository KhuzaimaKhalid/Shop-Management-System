import java.util.List;

public abstract class FileHandler {
    public abstract List<?> readFile(String filename);
    public abstract void writeFile(String filename, List<?> data);
}
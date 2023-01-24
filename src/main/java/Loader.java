import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Loader {
    public static List<List<String>> loadBinaryPuzzle(String path) throws FileNotFoundException {
        List<List<String>> result = new ArrayList<>();
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String line = scanner.next();
            result.add(Arrays.asList(line.split("")));
        }
        return result;
    }

    public static List<List<String>> loadFutoshikiPuzzle(String path) throws FileNotFoundException {
        List<List<String>> result = new ArrayList<>();
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String line = scanner.next();
            if(result.size() % 2 != 0) {
                List<String> x = Arrays.asList(line.split(""));
                line = "";
                for (String s: x) {
                    line += s + "-";
                }
                line = line.substring(0, line.length() - 1);
                result.add(Arrays.asList(line.split("")));
            }
            else
                result.add(Arrays.asList(line.split("")));
        }
        return result;
    }

    public static List<List<String>> loadFutoshikiPuzzleConstraints(String path) throws FileNotFoundException {
        List<List<String>> result = new ArrayList<>();
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String line = scanner.next();
            if(result.size() % 2 == 0) {
                List<String> x = Arrays.asList(line.split(""));
                line = "";
                for (String s: x) {
                    if(!s.equals("x"))
                        line += s;
                }
                result.add(Arrays.asList(line.split("")));
            }
            else
                result.add(Arrays.asList(line.split("")));
        }
        return result;
    }
}

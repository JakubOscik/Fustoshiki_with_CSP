import org.checkerframework.checker.units.qual.A;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void a(List<Integer> grid){
        grid.add(2);
    }

    public static void main(String[] args) throws FileNotFoundException {
        long start = System.currentTimeMillis();

//        for(int i: List.of(6, 8, 10)) { //another game print
//            List<List<String>> x = Loader.loadBinaryPuzzle("src/main/resources/binary_" + i + "x" + i);
//            BinaryPuzzle binaryPuzzle = new BinaryPuzzle(x);
//            System.out.println("Dziedzina: " + binaryPuzzle.domain);
//            binaryPuzzle.doo(x, 0, 0);
//            System.out.println("Unikalnych rozwiazan: " + binaryPuzzle.solutions.size() + "\n\n");
//        }

        List<List<String>> y = Loader.loadFutoshikiPuzzle("src/main/resources/futoshiki_4x4");
        List<List<String>> z = Loader.loadFutoshikiPuzzleConstraints("src/main/resources/futoshiki_4x4");
        System.out.println(y);
        System.out.println(z);
        Futoshiki f = new Futoshiki(y, z);
        f.getShortestDomain(f.domain);
        f.forwardCheck(f.domain, f.visited, 5, 4);
        System.out.println(f.visited);
        System.out.println(f.solutions.size());
        f.doo(y, 0, 0);
        System.out.println(y);
        FutoshikiPuzzle futoshikiPuzzle = new FutoshikiPuzzle(y);
        futoshikiPuzzle.forw(y, 0, 0);
        futoshikiPuzzle.doo(y, 0, 0);
        System.out.println("Dziedzina: " + futoshikiPuzzle.domain);
        System.out.println("Unikalnych rozwiazan: " + futoshikiPuzzle.solutions.size());
        long after = System.currentTimeMillis();
        System.out.println((after - start) / 1000.0 + "s.");
    }
}

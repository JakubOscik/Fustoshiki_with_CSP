import com.google.errorprone.annotations.Var;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Solver {
    private final List<List<String>> board;
    public final List<String> domain;
    public final Set<String> solutions;
//    public final HashMap<Pair<Integer, Integer>, List<String>> dom;
    public Solver(List<List<String>> board, Set<String> solutions, List<String> domain) {
        this.board = board;
        this.solutions = solutions;
        this.domain = domain;
    }

    public String rep(List<List<String>> grid){
        List<String> domain = new ArrayList<>(List.of("0", "1"));
        List<List<Variable>> temp = new ArrayList<>(grid.size());
        for(int i = 0; i < grid.size(); i++){
            temp.add(new ArrayList<Variable>());
            for(int j = 0; j < grid.size(); j++){
                Variable v = new Variable(domain, grid.get(i).get(j));
                temp.get(i).add(v);
            }
        }
        return temp.toString();
    }

    private String gridToString(List<List<String>> grid) {
        StringBuilder gridRepresentation = new StringBuilder("");
        for (List<String> row : grid) {
            for (int j = 0; j < row.size(); j++) {
                gridRepresentation.append(row.get(j)).append(" ");
            }
            gridRepresentation.append("\n");
        }
        return gridRepresentation.toString();
    }

    @Override
    public String toString() {
        return gridToString(this.board);
    }

    public void backtrack(List<List<String>> grid, int i, int j, Constraints constraints) {
        List<List<String>> temp = new ArrayList<>(grid.size());
        for(int d = 0; d < grid.size(); d++) {
            temp.add(new ArrayList<>(grid.get(d)));
        }

        if(i == temp.size()) {
            if(constraints.checkValidity(temp, grid.size() - 1, grid.size() - 1, this.domain)) {
                String gridRepresentation = gridToString(grid);
                if(!solutions.contains(gridRepresentation)) {
                    solutions.add(gridRepresentation);
                    System.out.println(gridRepresentation);
                }
                return;
            }
        }
        else {
            if(!grid.get(i).get(j).equals("x")) {
                if(j == grid.size() - 1)
                    backtrack(temp, i + 1, 0, constraints);
                else
                    backtrack(temp, i, j + 1, constraints);
            }
            else {
                for(String k: this.domain) {
                    temp.get(i).set(j, String.valueOf(k));
                    if(constraints.checkValidity(temp, i, j, this.domain)) {
                        if(j == grid.size() - 1)
                            backtrack(temp, i + 1, 0, constraints);
                        else
                            backtrack(temp, i, j + 1, constraints);
                    }
                }
            }
        }
    }

    public List<String> getColumn(List<List<String>> grid, int col){
        List<String> temp = new ArrayList<>();
        for(int i = 0; i < grid.size(); i++){
            for(int j = 0; j < grid.size(); j++) {
                temp.add(grid.get(i).get(col));
            }
        }
        return temp;
    }

    public void forward(List<List<String>> grid, int x, int y, Constraints constraints, List<String> domain){
        List<List<Variable>> temp = new ArrayList<>(grid.size());
        for(int i = 0; i < grid.size(); i++){
            temp.add(new ArrayList<>());
            for(int j = 0; j < grid.size(); j++){
                Variable v = new Variable(domain, grid.get(i).get(j));
                temp.get(i).add(v);
            }
        }
        for(List<Variable> a: temp){
           System.out.println(a);
        }
        System.out.println("kol 2" + getColumn(grid, 2));
    }

}

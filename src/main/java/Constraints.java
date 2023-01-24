import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Constraints {
    String problem;

    public Constraints(String problem){
        this.problem = problem;
    }

    private boolean uniqueNums(List<String> row, List<String> column, List<String> domain){
        for (String digit : domain) {
            if (row.stream().filter(ch -> ch.equals(digit)).count() > 1 ||
                    column.stream().filter(ch -> ch.equals(digit)).count() > 1)
                return false;
        }
        return true;
    }

    private boolean columnInequality(List<String> column){
        for (int j = 0; j < column.size(); j++) {
            if (column.get(j).equals(">")) {
                String leftChar = column.get(j - 1);
                String rightChar = column.get(j + 1);
                if (!leftChar.equals("x") && !rightChar.equals("x")) {
                    int left = Integer.parseInt(leftChar);
                    int right = Integer.parseInt(rightChar);
                    if (left < right)
                        return false;
                }
            }

            if (column.get(j).equals("<")) {
                String leftChar = column.get(j - 1);
                String rightChar = column.get(j + 1);
                if (!leftChar.equals("x") && !rightChar.equals("x")) {
                    int left = Integer.parseInt(leftChar);
                    int right = Integer.parseInt(rightChar);
                    if (left > right)
                        return false;
                }
            }
        }
        return true;
    }

    private boolean rowInequality(List<String> row){
        for (int j = 0; j < row.size(); j++) {
            if (row.get(j).equals(">")) {
                String leftChar = row.get(j - 1);
                String rightChar = row.get(j + 1);
                if (!leftChar.equals("x") && !rightChar.equals("x")) {
                    int left = Integer.parseInt(leftChar);
                    int right = Integer.parseInt(rightChar);
                    if (left < right)
                        return false;
                }
            }

            if (row.get(j).equals("<")) {
                String leftChar = row.get(j - 1);
                String rightChar = row.get(j + 1);
                if (!leftChar.equals("x") && !rightChar.equals("x")) {
                    int left = Integer.parseInt(leftChar);
                    int right = Integer.parseInt(rightChar);
                    if (left > right)
                        return false;
                }
            }
        }
        return true;
    }

    public boolean checkValidity(List<List<String>> grid, int rowNum, int colNum, List<String> domain) {
        if(this.problem.equals("Futoshiki")) {
            List<String> row = grid.get(rowNum);
            List<String> column = new ArrayList<>();
            for (int i = 0; i < grid.size(); i++) {
                column.add(grid.get(i).get(colNum));
            }

            return uniqueNums(row, column, domain) && columnInequality(column) && rowInequality(row);

        }
        else if (this.problem.equals("binary")){
            HashSet<String> rows = new HashSet<>();
            HashSet<String> cols = new HashSet<>();
            for(int i = 0; i < grid.size(); i++) {
                StringBuilder rowBuilder = new StringBuilder("");
                StringBuilder columnBuilder = new StringBuilder("");
                for(int j = 0; j < grid.size(); j++) {
                    rowBuilder.append(grid.get(i).get(j));
                    columnBuilder.append(grid.get(j).get(i));
                }
                String row = rowBuilder.toString();
                String column = columnBuilder.toString();

                if(!row.contains("x")) {
                    if(rows.contains(row))
                        return false;
                    rows.add(row);
                    if(row.chars().filter(z -> z == '0').count() != grid.size() / 2) //czy tyle samo 0 co 1
                        return false;
                }

                if(!column.contains("x")) {
                    if(cols.contains(column))
                        return false;
                    cols.add(column);
                    if(column.chars().filter(z -> z == '0').count() != grid.size() / 2)
                        return false;
                }
                sequence(grid);
            }
        }
        return true;
    }

    private boolean sequence(List<List<String>> grid){
        for(int i = 0; i < grid.size(); i++) {
            StringBuilder rowBuilder = new StringBuilder("");
            StringBuilder columnBuilder = new StringBuilder("");
            for(int j = 0; j < grid.size(); j++) {
                rowBuilder.append(grid.get(i).get(j));
                columnBuilder.append(grid.get(j).get(i));
            }
            String row = rowBuilder.toString();
            String column = columnBuilder.toString();
            if(row.contains("111") || row.contains("000") || column.contains("111") || column.contains("000"))
                return false;
        }
        return true;
    }

    private boolean uniqueRow(List<List<String>> grid){
        HashSet<String> rows = new HashSet<>();
        for(int i = 0; i < grid.size(); i++) {
            StringBuilder rowBuilder = new StringBuilder("");
            for (int j = 0; j < grid.size(); j++) {
                rowBuilder.append(grid.get(i).get(j));
            }
            String row = rowBuilder.toString();

            if (!row.contains("x")) {
                if (rows.contains(row))
                    return false;
                rows.add(row);
            }
        }
        return true;
    }

    private boolean uniqueColumn(List<List<String>> grid){
        HashSet<String> columns = new HashSet<>();
        for(int i = 0; i < grid.size(); i++) {
            StringBuilder columnBuilder = new StringBuilder("");
            for (int j = 0; j < grid.size(); j++) {
                columnBuilder.append(grid.get(i).get(j));
            }
            String column = columnBuilder.toString();

            if (!column.contains("x")) {
                if (columns.contains(column))
                    return false;
                columns.add(column);
            }
        }
        return true;
    }
}

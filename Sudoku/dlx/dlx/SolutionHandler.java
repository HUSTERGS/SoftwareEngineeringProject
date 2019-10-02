package dlx;

import dlx.DancingLinks.*;
import java.util.*;

public interface SolutionHandler{
    int[][] handleSolution(List<DancingNode> solution);
}

class SudokuHandler implements SolutionHandler{
    int size = 9;

    public int[][] handleSolution(List<DancingNode> answer){
        int[][] result = parseBoard(answer);
//        AbstractSudokuSolver.printSolution(result);
        return result;
    }

    private int[][] parseBoard(List<DancingNode> answer){
        int[][] result = new int[size][size];
        for(DancingNode n : answer){
            DancingNode rcNode = n;
            int min = Integer.parseInt(rcNode.C.name);
            for(DancingNode tmp = n.R; tmp != n; tmp = tmp.R){
                int val = Integer.parseInt(tmp.C.name);
                if (val < min){
                    min = val;
                    rcNode = tmp;
                }
            }
            int ans1 = Integer.parseInt(rcNode.C.name);
            int ans2 = Integer.parseInt(rcNode.R.C.name);
            int r = ans1 / size;
            int c = ans1 % size;
            int num = (ans2 % size) + 1;
            result[r][c] = num;
        }
        return result;
    }



    public SudokuHandler(int boardSize){
        size = boardSize;
    }

}
// Author: Rafal Szymanski <me@rafal.io>

// Implementation of the Dancing Links algorithm for exact cover.

package com.example.sudoku.dlx;

import java.util.*;

public class DancingLinks{
    
//    static final boolean verbose = true;

    private ColumnNode header;
    public int solutions = 0;
    private SudokuHandler handler;
    private List<DancingNode> answer;
    public int [][] solutionBoard;

    class DancingNode{
        DancingNode L, R, U, D;
        ColumnNode C;

        DancingNode hookDown(DancingNode n1){
            assert (this.C == n1.C);
            n1.D = this.D;
            n1.D.U = n1;
            n1.U = this;
            this.D = n1;
            return n1;
        }

        DancingNode hookRight(DancingNode n1){
            n1.R = this.R;
            n1.R.L = n1;
            n1.L = this;
            this.R = n1;
            return n1;
        }

        void unlinkLR(){
            this.L.R = this.R;
            this.R.L = this.L;
        }

        void relinkLR(){
            this.L.R = this.R.L = this;
        }

        void unlinkUD(){
            this.U.D = this.D;
            this.D.U = this.U;
        }

        void relinkUD(){
            this.U.D = this.D.U = this;
        }

        public DancingNode(){
            L = R = U = D = this;
        }

        public DancingNode(ColumnNode c){
            this();
            C = c;
        }
    }

    class ColumnNode extends DancingNode{
        int size; // number of ones in current column
        String name;

        public ColumnNode(String n){
            super();
            size = 0;
            name = n;
            C = this;
        }

        void cover(){
            unlinkLR();
            for(DancingNode i = this.D; i != this; i = i.D){
                for(DancingNode j = i.R; j != i; j = j.R){
                    j.unlinkUD();
                    j.C.size--;
                }
            }
            header.size--;
        }

        void uncover(){
            for(DancingNode i = this.U; i != this; i = i.U){
                for(DancingNode j = i.L; j != i; j = j.L){
                    j.C.size++;
                    j.relinkUD();
                }
            }
            relinkLR();
            header.size++;
        }
    }



    private void search(int k){
        if (header.R == header){
            solutionBoard = handler.handleSolution(answer);
            solutions++;
        } else{
            ColumnNode c = selectColumnNodeHeuristic();
            c.cover();

            for(DancingNode r = c.D; r != c; r = r.D){
                answer.add(r);

                for(DancingNode j = r.R; j != r; j = j.R){
                    j.C.cover();
                }

                search(k + 1);

                r = answer.remove(answer.size() - 1);
                c = r.C;

                for(DancingNode j = r.L; j != r; j = j.L){
                    j.C.uncover();
                }
            }
            c.uncover();
        }
    }

    private ColumnNode selectColumnNodeHeuristic(){
        int min = Integer.MAX_VALUE;
        ColumnNode ret = null;
        for(ColumnNode c = (ColumnNode) header.R; c != header; c = (ColumnNode) c.R){
            if (c.size < min){
                min = c.size;
                ret = c;
            }
        }
        return ret;
    }

    private ColumnNode makeDLXBoard(int[][] grid){
        final int COLS = grid[0].length;
        final int ROWS = grid.length;

        ColumnNode headerNode = new ColumnNode("header");
        ArrayList<ColumnNode> columnNodes = new ArrayList<ColumnNode>();

        for(int i = 0; i < COLS; i++){
            ColumnNode n = new ColumnNode(Integer.toString(i));
            columnNodes.add(n);
            headerNode = (ColumnNode) headerNode.hookRight(n);
        }
        headerNode = headerNode.R.C;

        for(int i = 0; i < ROWS; i++){
            DancingNode prev = null;
            for(int j = 0; j < COLS; j++){
                if (grid[i][j] == 1){
                    ColumnNode col = columnNodes.get(j);
                    DancingNode newNode = new DancingNode(col);
                    if (prev == null)
                        prev = newNode;
                    col.U.hookDown(newNode);
                    prev = prev.hookRight(newNode);
                    col.size++;
                }
            }
        }

        headerNode.size = COLS;
        
        return headerNode;
    }
    


    public DancingLinks(int[][] grid, SudokuHandler h){
        header = makeDLXBoard(grid);
        handler = h;
    }

    public void runSolver(){
        solutions = 0;
        answer = new LinkedList<DancingNode>();
        search(0);
    }

}

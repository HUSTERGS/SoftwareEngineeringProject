类说明

`DancingLinks`

用于解决**exact cover problem**

调用传入的`SudokuHandler`将解出来的答案(如果有的话)转化为数独格局，并返回



`SudokuHandler`

纯粹的方法类，用于辅助`DancingLinks`



`SudokuDLX(int gridLength, int side)`

利用`DancingLinks`算法来解决

`gridLength`为棋盘宽度，`side`为一个block的宽度，九宫格分别为`9`和`3`

实例属性：

`solutions`解的个数

`solutionBoard`解出来的棋盘

静态方法：

* `printBoard(int[][])`
* `copyBoard(int[][] board, int[][] newBoard)`，将`board`复制到`newBoard`

实例方法：

* `solve(int[][] sudoku)`解决输入的数独



`GenerateBoard`

1. `init`
2. `run(int level)`表示挖掉的个数 9宫不要超过50个，4宫不要超过12个
3. 通过`.board`方法得到生成的数独
4. 通过`.anwser`方法得到答案（也可以重新算一遍）
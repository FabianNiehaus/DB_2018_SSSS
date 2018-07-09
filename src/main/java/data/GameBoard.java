package data;

import exceptions.BuzzwordNotOnGameBoardException;

import java.util.Collections;
import java.util.LinkedList;

public class GameBoard {

    private SingleCell cellMatrix[][] = new SingleCell[5][5];

    public void setCellMatrix (SingleCell[][] cellMatrix) {
        this.cellMatrix = cellMatrix;
    }

    public SingleCell[][] getCellMatrix () {
        return cellMatrix;
    }

    public void setGameBoard(LinkedList<Buzzword> buzzwords) {
        int buzzwordIndex = 0;

        Collections.shuffle(buzzwords);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!(i == 2 && j == 2)) {
                    cellMatrix[i][j] = new SingleCell(buzzwords.get(buzzwordIndex));
                    buzzwordIndex++;
                }
            }
        }
    }

    public LinkedList<String> getBuzzwords() {

        LinkedList<String> buzzwords = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!(i == 2 && j == 2)) {
                    buzzwords.add(cellMatrix[i][j].getBuzzword().get());
                }
            }
        }

        return buzzwords;
    }

    public Buzzword setSingleCellMarked(int[] cellPosition) throws ArrayIndexOutOfBoundsException {

        cellMatrix[cellPosition[0]][cellPosition[1]].setMarked(true);
        return cellMatrix[cellPosition[0]][cellPosition[1]].getBuzzword();

    }

    public int[] getBuzzwordPosition(Buzzword buzzword) throws BuzzwordNotOnGameBoardException {
        int buzzwordPosition[] = new int[2];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!(i == 2 && j == 2) && cellMatrix[i][j].getBuzzword().get().equals(buzzword.get())) {
                    buzzwordPosition[0] = i;
                    buzzwordPosition[1] = j;
                    return buzzwordPosition;
                }
            }
        }

        throw new BuzzwordNotOnGameBoardException();
    }

    public boolean checkWinState() {
        boolean fullRow = false;
        boolean fullColumn = false;

        SingleCell previousCellInRow = null;
        SingleCell previousCellInColumn = null;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                // Go through Rows
                if (previousCellInRow != null && !previousCellInRow.isMarked()) {
                    fullRow = false;
                } else if (cellMatrix[i][j].isMarked()) {
                    fullRow = true;
                }
                previousCellInRow = cellMatrix[i][j];

                // Go through columns
                if (previousCellInColumn != null && !previousCellInColumn.isMarked()) {
                    fullRow = false;
                } else if (cellMatrix[j][i].isMarked()) {
                    fullRow = true;
                }
                previousCellInColumn = cellMatrix[j][i];

            }

            if (fullRow || fullColumn) return true;

            fullRow = false;
            fullColumn = false;
            previousCellInRow = null;
            previousCellInColumn = null;
        }

        return false;
    }

     public class SingleCell {
        boolean marked = false;
        Buzzword buzzword;

        public SingleCell(Buzzword buzzword) {

            this.buzzword = buzzword;
        }

        public boolean isMarked() {
            return marked;
        }

        public void setMarked(boolean marked) {
            this.marked = marked;
        }

         public void setBuzzword(Buzzword buzzword) {

             this.buzzword = buzzword;

         }

        public Buzzword getBuzzword() {

            return buzzword;
        }
    }
}

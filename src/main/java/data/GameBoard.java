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
                } else {
                    cellMatrix[i][j] = new SingleCell(new Buzzword("!!Placeholder!!"));
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

        for (int i = 0; i < 5; i++) {
            if(cellMatrix[i][0].isMarked()
                    && cellMatrix[i][1].isMarked()
                    && cellMatrix[i][2].isMarked()
                    && cellMatrix[i][3].isMarked()
                    && cellMatrix[i][4].isMarked())
                return true;
            if(cellMatrix[0][i].isMarked()
                    && cellMatrix[1][i].isMarked()
                    && cellMatrix[2][i].isMarked()
                    && cellMatrix[3][i].isMarked()
                    && cellMatrix[4][i].isMarked())
                return true;
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

package data;

import exceptions.BuzzwordNotOnGameBoardException;

public class GameBoard {

    class SingleCell {
        public boolean isMarked() {
            return marked;
        }

        public void setMarked(boolean marked) {
            this.marked = marked;
        }

        public Buzzword getBuzzword() {

            return buzzword;
        }

        public SingleCell(Buzzword buzzword) {

            this.buzzword = buzzword;
        }

        boolean marked = false;
        Buzzword buzzword;
    }

    private SingleCell cellMatrix[][] = new SingleCell[5][5];

    public void setGameBoard(Buzzword[][] inputMatrix){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                cellMatrix[i][j] = new SingleCell(inputMatrix[i][j]);
            }
        }
    }

    public boolean setSingleCellMarked(int[] cellPosition){
        try{
            cellMatrix[cellPosition[0]][cellPosition[1]].setMarked(true);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public int[] getBuzzwordPosition(Buzzword buzzword) throws BuzzwordNotOnGameBoardException{
        int buzzwordPosition[] = new int[2];

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if(cellMatrix[i][j].getBuzzword().get().equals(buzzword.get()) ){
                    buzzwordPosition[0] = i;
                    buzzwordPosition[1] = j;
                    return buzzwordPosition;
                }
            }
        }

        throw new BuzzwordNotOnGameBoardException();
    }

    public boolean checkWinState(){
        boolean fullRow = false;
        boolean fullColumn = false;

        SingleCell previousCellInRow = null;
        SingleCell previousCellInColumn = null;

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){

                // Go through Rows
                if(previousCellInRow != null && !previousCellInRow.isMarked()){
                    fullRow = false;
                } else if(cellMatrix[i][j].isMarked()){
                    fullRow = true;
                }
                previousCellInRow = cellMatrix[i][j];

                // Go through columns
                if(previousCellInColumn != null && !previousCellInColumn.isMarked()){
                    fullRow = false;
                } else if(cellMatrix[j][i].isMarked()){
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
}

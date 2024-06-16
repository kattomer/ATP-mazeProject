package algorithms.search;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {
    private BestFirstSearch bestFirstSearch;

    @BeforeEach
    public void setUp() {
        this.bestFirstSearch = new BestFirstSearch();
    }

    @Test
    public void getName() {
        assertEquals("Best First Search", bestFirstSearch.getName());
    }

    @Test
    public void getWrongName() {
        assertNotEquals("Depth First Search", bestFirstSearch.getName());
    }


    @Test
    public void givenNullISearchable_WhenSolve_ThenReturnNull() {
        SearchableMaze searchableMaze = null;
        Solution sol = bestFirstSearch.solve(searchableMaze);
        assertNull(sol);
    }

    @Test
    public void givenNullMaze_WhenNewSearchableMaze_ThenThrowIllegalArgumentException() {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(0, 0);
        IllegalArgumentException invalidMaze = assertThrows(IllegalArgumentException.class, () -> new SearchableMaze(maze));
        assertEquals("null maze", invalidMaze.getMessage());
    }

    @Test
    public void givenValidMaze_WhenSolve_ThenReturnSolutionPath() {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(30, 30);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        Solution solution = bestFirstSearch.solve(searchableMaze);
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        assertNotNull(solutionPath);
    }
}
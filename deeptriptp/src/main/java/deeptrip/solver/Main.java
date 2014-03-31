package deeptrip.solver;

import au.com.bytecode.opencsv.CSVReader;
import deeptrip.ai.engine.DeeptripAIEngine;
import deeptrip.ai.enumerators.HeuristicType;
import deeptrip.ai.heuristics.Heuristic;
import deeptrip.ai.problem.DeeptripAIProblem;
import deeptrip.ai.states.DeeptripAIState;
import deeptrip.game.Board;
import gps.SearchStrategy;
import gps.api.GPSProblem;
import gps.exception.SolutionNotFoundException;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

	private static final Logger logger = Logger.getLogger(Main.class);
	private static final CommandLineParser lineParser = new BasicParser();
	private static final Options options = new Options();
	private final Board start;
	private final DeeptripAIState startState;
	private final DeeptripAIState endState;
	private GPSProblem problem;
	private final DeeptripAIEngine engine;

	static {
		options.addOption(OptionBuilder
				.withArgName("file")
				.withDescription("Archivo que contiene el tablero a cargar.")
				.isRequired()
				.hasArg()
				.create('f'));
		options.addOption(OptionBuilder
				.withArgName("heuristic")
				.withDescription("Heurística a utilizar para en los métodos informados. Requerido si se utiliza un método informado.")
				.isRequired(false)
				.hasArg()
				.create('h'));
		options.addOption(OptionBuilder
				.withArgName("algorithm")
				.withDescription("Algoritmo a utilizar para resolver el tablero.")
				.isRequired(true)
				.hasArgs()
				.create('a'));

	}

	public Main(List<List<Integer>> startBoard, Heuristic heuristic) {
		start = new Board(startBoard);
		startState = new DeeptripAIState(start);
		endState = new DeeptripAIState(Main.getEndBoard(start));
		problem = new DeeptripAIProblem(startState, endState, heuristic);
		engine = new DeeptripAIEngine();
		logger.info("Tablero Inicial: " + start.toString());
	}

	public static Board getEndBoard(Board board) {

		int rows = board.getRowsSize();
		int columns = board.getColumnsSize();
		List<List<Integer>> end = new ArrayList<>(rows);
		for (int i = 0; i < rows; i++) {
			List<Integer> row = new ArrayList<>(columns);
			for (int j = 0; j < columns; j++) {
				row.add(0);
			}
			end.add(row);
		}
		return new Board(end);
	}

	public static void main(String[] args) {

		CommandLine line = null;
		try {
			line = lineParser.parse(options, args);
		} catch (ParseException e) {
			System.out.println("Hubo un error en la interpretación de los argumentos.");
			return;
		}

		List<List<Integer>> startBoard = null;
		try {
			startBoard = parseBoard(line.getOptionValue('f'));
		} catch (NumberFormatException nfe) {
			System.out.println("Error de formato en el tablero.");
			return;
		} catch (FileNotFoundException fnfe) {
			System.out.println("No se encontró el archivo especificado.");
			return;
		} catch (IOException ioe) {
			System.out.println("Hubo un error al leer el archivo.");
			return;
		}

		SearchStrategy searchStrategy = SearchStrategy.getSearchStrategy(line.getOptionValue('a'));
		Heuristic heuristic = line.hasOption('h') ?
				HeuristicType.getHeuristicType(line.getOptionValue('h')).getHeuristic() : null;

		switch (searchStrategy) {
		case IDDFS:
			new Main(startBoard, heuristic).solveIterativeDeepening();
		case Greedy:
		case AStar:
			if (heuristic == null) {
				throw new IllegalArgumentException("Se necesita una heurística para este algoritmo.");
			}
		case BFS:
		case DFS:
			new Main(startBoard, heuristic).solve(searchStrategy);
			break;
		default:
			throw new IllegalArgumentException();
		}

	}

	private static List<List<Integer>> parseBoard(String filepath) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(filepath));
		String[] line = null;
		List<List<Integer>> board = new LinkedList<>();
		while ((line = reader.readNext()) != null) {
			List<Integer> lLine = new LinkedList<>();
			for(String s : line) {
				lLine.add(Integer.parseInt(s));
			}
			board.add(lLine);
		}
		return board;  //To change body of created methods use File | Settings | File Templates.
	}

	private void solve(SearchStrategy searchStrategy) {
		long timeInit = System.currentTimeMillis();
		try {
			engine.engine(problem, searchStrategy);
		} catch (Exception e) {

		}
		System.out.println("Elapsed time: "
				+ (System.currentTimeMillis() - timeInit) + " milliseconds");
	}

	public void solveIterativeDeepening() {
		long timeInit = System.currentTimeMillis();
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			System.out
					.println("==== Starting Iterative Deepening DFS with a maximum depth of "
							+ i + " ====");
			try {
				engine.engine(problem, SearchStrategy.DFS, i);
				break;
			} catch (SolutionNotFoundException snfe) { /* Do nothing */
			}
		}
		System.out.println("Elapsed time: "
				+ (System.currentTimeMillis() - timeInit) + " milliseconds");
	}

}

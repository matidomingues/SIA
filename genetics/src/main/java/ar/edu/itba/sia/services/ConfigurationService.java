package ar.edu.itba.sia.services;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.cutcondition.impl.*;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeBuilder;
import ar.edu.itba.sia.genetics.fenotypes.FenotypeSplitter;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;
import ar.edu.itba.sia.genetics.fenotypes.impl.NeuralNetworkFenotypeBuilder;
import ar.edu.itba.sia.genetics.fenotypes.impl.NeuralNetworkFenotypeSplitter;
import ar.edu.itba.sia.genetics.fenotypes.impl.PerceptronNetworkFitnessFunction;
import ar.edu.itba.sia.genetics.operators.backpropagation.Backpropagator;
import ar.edu.itba.sia.genetics.operators.crossers.Crossover;
import ar.edu.itba.sia.genetics.operators.crossers.impl.AnularCrossover;
import ar.edu.itba.sia.genetics.operators.crossers.impl.OnePointCrossover;
import ar.edu.itba.sia.genetics.operators.crossers.impl.TwoPointCrossover;
import ar.edu.itba.sia.genetics.operators.crossers.impl.UniformCrossover;
import ar.edu.itba.sia.genetics.operators.mutators.Mutator;
import ar.edu.itba.sia.genetics.operators.mutators.impl.ClassicMutator;
import ar.edu.itba.sia.genetics.operators.mutators.impl.ModernMutator;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.genetics.replacers.impl.ReplacementAlgorithmOne;
import ar.edu.itba.sia.genetics.replacers.impl.ReplacementAlgorithmThree;
import ar.edu.itba.sia.genetics.replacers.impl.ReplacementAlgorithmTwo;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;
import ar.edu.itba.sia.genetics.selectors.impl.*;
import ar.edu.itba.sia.genetics.utils.CachingFenotypeComparator;
import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.backpropagation.BackpropagationAlgorithm;
import ar.edu.itba.sia.perceptrons.backpropagation.DeltaCalculator;
import ar.edu.itba.sia.perceptrons.backpropagation.impl.GradientDescentDeltaCalculator;
import ar.edu.itba.sia.perceptrons.backpropagation.impl.TanhDMatrixFunction;
import ar.edu.itba.sia.perceptrons.backpropagation.impl.TanhMatrixFunction;
import ar.edu.itba.sia.perceptrons.utils.EpochsCutCondition;
import ar.edu.itba.sia.perceptrons.utils.ErrorCutCondition;
import ar.edu.itba.sia.utils.MatrixFunction;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.base.Strings;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.jblas.DoubleMatrix;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ConfigurationService {

	private static ConfigurationService instance;
	private static final Object LOCKER = new Object();

	private Configuration configuration;

	private int[] architecture;
	private List<Pattern> patterns;
	private int testPatternsQty;
	private int population;

	private List<MatrixFunction> transferenceFunctions;
	private FitnessFunction fitnessFunction;

	private Comparator<Fenotype> fenotypeComparator;
	private BackpropagationAlgorithm backpropagation;
	private ar.edu.itba.sia.perceptrons.backpropagation.CutCondition backpropagationCutCondition;

	private DeltaCalculator deltaCalculator;
	private ReplacementAlgorithm replacementAlgorithm;
	private FenotypeSelector selectionSelector;
	private FenotypeSelector replacementSelector;
	private Mutator mutator;
	private Crossover crosser;
	private Backpropagator backpropagator;
	private CutCondition geneticCutCondition;
	private FenotypeBuilder fenotypeBuilder;

	private Path exportPath;

	private ConfigurationService() {
		try {
			configuration = new XMLConfiguration("config.xml");

			architecture = getArchitecture();
			patterns = getPatterns();
			testPatternsQty = getTestPatternsQty();

			backpropagation = getBackpropagation();

			fenotypeBuilder = getFenotypeBuilder();
			fenotypeComparator = getFenotypeComparator();
			fitnessFunction = getFitnessFunction();
			population = getPopulation();
			selectionSelector = getSelectionSelector();
			replacementSelector = getReplacementSelector();
			mutator = getMutator();
			crosser = getCrosser();
			geneticCutCondition = getGeneticCutCondition();
			backpropagator = getBackpropagator();
			replacementAlgorithm = getReplacementAlgorithm();

			exportPath = getExportPath();
		} catch (ConfigurationException e) {
			throw new Error("Unable to load configuration file! Aborting");
		}
	}

	public  int[] getArchitecture() {
		if (architecture == null) {
			synchronized (this) {
				if (architecture == null) {
					List<Object> architecture = configuration.getList("architecture.layer");
					if (architecture.isEmpty()) throw new Error("No architecture found");
					transferenceFunctions = new ArrayList<MatrixFunction>(architecture.size() - 1);
					this.architecture = new int[architecture.size()];
					int i = 0;
					for (Object o : architecture) {
						try {
							this.architecture[i] = Integer.parseInt((String)o);
							if (i != 0) {
								Object of = configuration.getProperty("architecture.layer(" + i + ")[@function]");
								if (of == null) throw new Error("No transference function defined for layer " + i);
								String matrixFunctionName = (String)of;
								if (matrixFunctionName.compareToIgnoreCase("tanh") == 0) {
									transferenceFunctions.add(new TanhMatrixFunction());
								} else {
									throw new Error("Uknown transference function");
								}
							}
						} catch (NumberFormatException nfe) {
							throw new Error("Layer " + i + " isn't a valid integer.");
						}
						i++;
					}
				}
			}
		}
		return architecture;
	}

	public  ReplacementAlgorithm getReplacementAlgorithm() {
		if (replacementAlgorithm == null) {
			int replacementId = configuration.getInt("genetics.replacer");
			switch (replacementId) {
				case 1:
					replacementAlgorithm = new ReplacementAlgorithmOne(getSelectionSelector(),
																	getReplacementSelector(),
																	getMutator(),
																	getCrosser(),
																	backpropagator);
					break;
				case 2:
					replacementAlgorithm = new ReplacementAlgorithmTwo(getSelectionSelector(),
																	getReplacementSelector(),
																	getMutator(),
																	getCrosser(),
																	backpropagator);
					break;
				case 3:
					replacementAlgorithm = new ReplacementAlgorithmThree(getSelectionSelector(),
																	getReplacementSelector(),
																	getMutator(),
																	getCrosser(),
																	backpropagator);
			}
		}
		return replacementAlgorithm;
	}

	public  List<Pattern> getPatterns() {
		if (patterns == null) {
			patterns = new LinkedList<Pattern>();
			String patternsFilePath = configuration.getString("patterns");
			if (Strings.isNullOrEmpty(patternsFilePath)) throw new Error("No patterns file found");
			try {
				CSVReader csvReader = new CSVReader(new FileReader(patternsFilePath), ';');
				String[] line;
				while ((line = csvReader.readNext()) != null) {
					double[] patternAr = new double[line.length];
					for (int i = 0; i < line.length; i++) {
						try {
							patternAr[i] = Double.parseDouble(line[i]);
						} catch (NumberFormatException nfe) {
							patternAr[i] = Integer.parseInt(line[i]);
						}
					}
					patterns.add(new Pattern(new DoubleMatrix(patternAr).transpose()));
				}
				csvReader.close();
			} catch (FileNotFoundException e) {
				throw new Error("Patterns file not found");
			} catch (IOException e) {
				throw new Error("An error occurred while reading the patterns");
			} catch (NumberFormatException nfe) {
				throw new Error("Pattern with unknown number format");
			}
		}
		return patterns;
	}

	public  int getTestPatternsQty() {
		if (testPatternsQty == 0) {
			if (configuration.containsKey("patterns[@test]")) {
				testPatternsQty = configuration.getInt("patterns[@test]");
				if (testPatternsQty < 0) throw new Error("Invalid test patterns quantity");
				if (testPatternsQty > patterns.size()) throw new Error("Test patterns quantity exceeds patterns quantity");
			} else {
				testPatternsQty = -1;
			}
		}
		return testPatternsQty;
	}

	public  DeltaCalculator getDeltaCalculator() {
		if (deltaCalculator == null) {
			String deltaCalculatorName = configuration.getString("backpropagation.deltacalculator");
			if (Strings.isNullOrEmpty(deltaCalculatorName)) throw new Error("No delta calculation method defined");
			if (deltaCalculatorName.compareToIgnoreCase("gradient-descent") == 0) {
				double etha = configuration.getDouble("backpropagation.deltacalculator[@etha]");
				String functionName = configuration.getString("backpropagation.deltacalculator[@function]");
				MatrixFunction dFunction;
				if (Strings.isNullOrEmpty(functionName)) throw new Error("No function defined for gradient descent");
				if (functionName.compareToIgnoreCase("tanh") == 0) {
					dFunction = new TanhDMatrixFunction();
				} else {
					throw new Error("Uknown gradient descent function");
				}
				deltaCalculator = new GradientDescentDeltaCalculator(etha, dFunction);
			} else {
				throw new Error("Uknown delta calculation method");
			}
		}
		return deltaCalculator;
	}

	public  Crossover getCrosser() {
		if (crosser == null) {
			String crosserName = configuration.getString("genetics.crossover");
			if (Strings.isNullOrEmpty(crosserName)) throw new Error("No crossover method selected");
			FenotypeBuilder builder = new NeuralNetworkFenotypeBuilder(getArchitecture(),
					getTransferenceFunctions());
			FenotypeSplitter splitter = new NeuralNetworkFenotypeSplitter();
			double probability = configuration.getDouble("genetics.crossover[@probability]");
			if (crosserName.compareToIgnoreCase("anular") == 0) {
				crosser = new AnularCrossover(builder, splitter, probability);
			} else if (crosserName.compareToIgnoreCase("one-point") == 0) {
				crosser = new OnePointCrossover(builder, splitter, probability);
			} else if (crosserName.compareToIgnoreCase("two-point") == 0) {
				crosser = new TwoPointCrossover(builder, splitter, probability);
			} else if (crosserName.compareToIgnoreCase("uniform") == 0) {
				double ocurrenceProbability = configuration.getDouble("genetics.crossvoer[@ocurrence]");
				crosser = new UniformCrossover(builder, splitter, probability, ocurrenceProbability);
			} else {
				throw new Error("Uknown crossover method");
			}
		}
		return crosser;
	}

	public  List<MatrixFunction> getTransferenceFunctions() {
		return transferenceFunctions;
	}

	public  FitnessFunction getFitnessFunction() {
		if (fitnessFunction == null) {
			int fitnessFunctionId = configuration.getInt("genetics.fitnessfunction");
			switch (fitnessFunctionId) {
				case 1:
					fitnessFunction = new PerceptronNetworkFitnessFunction(getPatterns());
					break;
				default:
					throw new Error("Fitness function not found or unknown");
			}
		}
		return fitnessFunction;
	}

	public  Mutator getMutator() {
		if (mutator == null) {
			String selectorName = configuration.getString("genetics.mutator");
			if (Strings.isNullOrEmpty(selectorName)) throw new Error("No mutator name found");
			double probability = configuration.getDouble("genetics.mutator[@probability]");
			if (selectorName.compareToIgnoreCase("classic") == 0) {
				mutator = new ClassicMutator(probability);
			} else if (selectorName.compareToIgnoreCase("modern") == 0) {
				mutator = new ModernMutator(probability);
			} else {
				throw new Error("Unknown mutator");
			}
		}
		return mutator;
	}

	public  FenotypeSelector getReplacementSelector() {
		if (replacementSelector == null) {
			replacementSelector = getSelector("genetics.replacementselector");
		}
		return replacementSelector;
	}

	public  FenotypeSelector getSelectionSelector() {
		if (selectionSelector == null) {
			selectionSelector = getSelector("genetics.selectionselector");
		}
		return selectionSelector;
	}

	private  FenotypeSelector getSelector(String preffix) {
		FenotypeSelector selector = null;
		String selectorPath = preffix + ".selector";
		List<Object> selectorsNames = configuration.getList(selectorPath);
		if (selectorsNames.isEmpty()) throw new Error("No selection method found");
		if (selectorsNames.size() == 1) {
			selector = getSingleSelector(selectorPath, null);
		} else {
			List<FenotypeSelector> selectors = new ArrayList<FenotypeSelector>(selectorsNames.size());
			for (int i = 0; i < selectorsNames.size(); i++) {
				String sp = String.format(selectorPath + "(%d)", i);
				FenotypeSelector s = getSingleSelector(sp, selectors);
				if (s instanceof ChainedFenotypeSelector) {
					selector = s;
				} else {
					selectors.add(s);
				}
			}
		}
		return selector;
	}

	private  FenotypeSelector getSingleSelector(String selectorPath, List<FenotypeSelector> selectors) {
		FenotypeSelector selector;
		int k = configuration.getInt(selectorPath + "[@k]");
		String selectorName = configuration.getString(selectorPath);
		if (selectorName.compareToIgnoreCase("chained") == 0) {
			if (selectors == null) throw new Error("Expected more selectors");
			selector = new ChainedFenotypeSelector(selectors);
		} else if (selectorName.compareToIgnoreCase("elite") == 0) {
			selector = new EliteFenotypeSelector(k, getFitnessFunction(), fenotypeComparator);
		} else if (selectorName.compareToIgnoreCase("boltzman") == 0) {
			selector = new BoltzmanFenotypeSelector(k, getFitnessFunction());
		} else if (selectorName.compareToIgnoreCase("roulette") == 0) {
			selector = new RouletteFenotypeSelector(k, getFitnessFunction());
		} else if (selectorName.compareToIgnoreCase("tournaments-d") == 0) {
			int m = configuration.getInt(selectorPath + "[@m]");
			selector = new TournamentsDeterministicFenotypeSelector(m, k, getFitnessFunction());
		} else if (selectorName.compareToIgnoreCase("tournaments-e") == 0) {
			selector = new TournamentsProbabilisticsFenotypeSelector(k, getFitnessFunction());
		} else if (selectorName.compareToIgnoreCase("universal") == 0) {
			selector = new UniversalFenotypeSelector(k, getFitnessFunction());
		} else {
			throw new Error("Unknown selection selector");
		}
		return selector;  //To change body of created methods use File | Settings | File Templates.
	}

	public  BackpropagationAlgorithm getBackpropagation() {
		if (backpropagation == null) {
			deltaCalculator = getDeltaCalculator();
			backpropagationCutCondition = getBackpropagationCutCondition();
			backpropagation = new BackpropagationAlgorithm(deltaCalculator, backpropagationCutCondition);
		}
		return backpropagation;
	}

	public  ar.edu.itba.sia.perceptrons.backpropagation.CutCondition getBackpropagationCutCondition() {
		if (backpropagationCutCondition == null) {
			List<Object> cutConditionsNames = configuration.getList("backpropagation.cutconditions.cutcondition");
			if (cutConditionsNames.isEmpty()) throw new Error("No cut condition especified for backpropagation");
			if (cutConditionsNames.size() == 1) {
				String cutConditionPath = "backpropagation.cutconditions.cutcondition";
				backpropagationCutCondition = getSingleBackpropagationCutCondition(cutConditionPath, null);
			} else {
				List<ar.edu.itba.sia.perceptrons.backpropagation.CutCondition> cutConditions
						= new ArrayList<ar.edu.itba.sia.perceptrons.backpropagation.CutCondition>(cutConditionsNames.size());
				ar.edu.itba.sia.perceptrons.utils.ChainedCutCondition chainedCutCondition = null;
				for (int i= 0; i < cutConditionsNames.size(); i++) {
					String name = String.format("backpropagation.cutconditions.cutcondition(%d)", i);
					if (Strings.isNullOrEmpty(name)) throw new Error("Empty cut condition found");
					ar.edu.itba.sia.perceptrons.backpropagation.CutCondition cutCondition
							= getSingleBackpropagationCutCondition(name, cutConditions);
					if (cutCondition instanceof ar.edu.itba.sia.perceptrons.utils.ChainedCutCondition) {
						chainedCutCondition = (ar.edu.itba.sia.perceptrons.utils.ChainedCutCondition)cutCondition;
					} else {
						cutConditions.add(cutCondition);
					}
				}
				if (chainedCutCondition == null) throw new Error("Chained cut condition expected");
				backpropagationCutCondition = chainedCutCondition;
			}
		}
		return backpropagationCutCondition;
	}

	private  ar.edu.itba.sia.perceptrons.backpropagation.CutCondition
							getSingleBackpropagationCutCondition(String cutConditionPath,
																 List<ar.edu.itba.sia.perceptrons.backpropagation.CutCondition> chainedConditions) {
		ar.edu.itba.sia.perceptrons.backpropagation.CutCondition cutCondition;
		String cutConditionName = configuration.getString(cutConditionPath);
		if (Strings.isNullOrEmpty(cutConditionName)) throw new Error("No cut condition found");
		if (cutConditionName.compareToIgnoreCase("chained") == 0) {
			if (chainedConditions == null) throw new Error("Expected more cut conditions");
			cutCondition = new ar.edu.itba.sia.perceptrons.utils.ChainedCutCondition(chainedConditions);
		} else if (cutConditionName.compareToIgnoreCase("epochs") == 0) {
			int maxEpochs = configuration.getInt(cutConditionPath + "[@epochs]");
			cutCondition = new EpochsCutCondition(maxEpochs);
		} else if (cutConditionName.compareToIgnoreCase("error") == 0) {
			double epsilon = configuration.getDouble(cutConditionPath + "[@epsilon]");
			cutCondition = new ErrorCutCondition(getTestPatterns(), epsilon);
		} else {
			throw new Error("Unknown cut condition");
		}
		return cutCondition;
	}

	public  List<Pattern> getLearningPatterns() {
		if (testPatternsQty == -1) return getPatterns();
		return patterns.subList(0, patterns.size() - testPatternsQty);
	}

	public  List<Pattern> getTestPatterns() {
		if (testPatternsQty == -1) return getPatterns();
		return patterns.subList(testPatternsQty, patterns.size());
	}

	public  CutCondition getGeneticCutCondition() {
		if (geneticCutCondition == null) {
			List<Object> cutConditionsNames = configuration.getList("genetics.cutconditions.cutcondition");
			if (cutConditionsNames.isEmpty()) throw new Error("No cut condition especified for genetic algorithms");
			if (cutConditionsNames.size() == 1) {
				String cutConditionPath = "genetics.cutconditions.cutcondition";
				geneticCutCondition = getSingleGeneticCutCondition(cutConditionPath, null);
			} else {
				List<CutCondition> cutConditions
						= new ArrayList<CutCondition>(cutConditionsNames.size());
				ChainedCutCondition chainedCutCondition = null;
				for (int i = 0; i < cutConditionsNames.size(); i++) {
					String name = String.format("genetics.cutconditions.cutcondition(%d)", i);
					if (Strings.isNullOrEmpty(name)) throw new Error("Empty cut condition found");
					CutCondition cutCondition
							= getSingleGeneticCutCondition(name, cutConditions);
					if (cutCondition instanceof ChainedCutCondition) {
						chainedCutCondition = (ChainedCutCondition)cutCondition;
					} else {
						cutConditions.add(cutCondition);
					}
				}
				if (chainedCutCondition == null) throw new Error("Chained cut condition expected");
				geneticCutCondition = chainedCutCondition;
			}
		}
		return geneticCutCondition;
	}

	private  CutCondition getSingleGeneticCutCondition(String cutConditionPath, List<CutCondition> cutConditions) {
		CutCondition cutCondition = null;
		String cutConditionName = configuration.getString(cutConditionPath);
		if (Strings.isNullOrEmpty(cutConditionName)) throw new Error("No cut condition found");
		if (cutConditionName.compareToIgnoreCase("chained") == 0) {
			if (cutConditions == null) throw new Error("Expected more cut conditions");
			cutCondition = new ChainedCutCondition(cutConditions);
		} else if (cutConditionName.compareToIgnoreCase("content") == 0) {
			int haltedMaxTime = configuration.getInt(cutConditionPath + "[@haltedMaxTime]");
			double epsilon = configuration.getDouble(cutConditionPath + "[@epsilon]");
			cutCondition = new ContentCutCondition(haltedMaxTime, epsilon, getFitnessFunction(), fenotypeComparator);
		} else if (cutConditionName.compareToIgnoreCase("generations") == 0) {
			int maxGenerations = configuration.getInt(cutConditionPath + "[@maxGenerations]");
			cutCondition = new GenerationsCutCondition(maxGenerations);
		} else if (cutConditionName.compareToIgnoreCase("scope") == 0) {
			double maxScope = configuration.getInt(cutConditionPath + "[@maxScope]");
			cutCondition = new ScopeCutCondition(maxScope, getFitnessFunction(), fenotypeComparator);
		} else if (cutConditionName.compareToIgnoreCase("structure") == 0) {
			double populationPercentage = configuration.getDouble(cutConditionPath + "[@populationPercentage]");
			int maxConsecutiveTimes = configuration.getInt(cutConditionPath + "[@maxConsecutiveTimes]");
			cutCondition = new StructureCutCondition(populationPercentage, maxConsecutiveTimes);
		} else {
			throw new Error("Unknown cut condition");
		}
		return cutCondition;
	}

	public  Backpropagator getBackpropagator() {
		if (backpropagator == null) {
			double probability = configuration.getDouble("genetics.backpropagation");
			backpropagator = new Backpropagator(backpropagation, getTestPatterns(), probability);
		}
		return backpropagator;
	}

	public static ConfigurationService getInstance() {
		if (instance == null) {
			synchronized (LOCKER) {
				if (instance == null) {
					instance = new ConfigurationService();
				}
			}
		}
		return instance;
	}

	public int getPopulation() {
		if (population <= 0) {
			int population = configuration.getInt("genetics.population");
			if (population <= 0) throw new Error("Invalid population");
			this.population = population;
		}
		return population;
	}

	public FenotypeBuilder getFenotypeBuilder() {
		if (fenotypeBuilder == null) {
			fenotypeBuilder = new NeuralNetworkFenotypeBuilder(getArchitecture(),
					getTransferenceFunctions());
		}
		return fenotypeBuilder;
	}

	public Comparator<Fenotype> getFenotypeComparator() {
		if (fenotypeComparator == null) {
			fenotypeComparator = new CachingFenotypeComparator(getFitnessFunction());
		}
		return fenotypeComparator;
	}

	public Path getExportPath() {
		if (exportPath == null) {
			String sExportPath = configuration.getString("exportpath");
			if (Strings.isNullOrEmpty(sExportPath)) throw new Error("No export path found");
			exportPath = Paths.get(sExportPath);
			if (!exportPath.toFile().isDirectory()) throw new Error("The export path is not a directory");
		}
		return exportPath;
	}
}

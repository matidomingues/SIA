package ar.edu.itba.sia.genetics;

import ar.edu.itba.sia.genetics.cutcondition.CutCondition;
import ar.edu.itba.sia.genetics.cutcondition.impl.GenerationsCutCondition;
import ar.edu.itba.sia.genetics.fenotypes.Fenotype;
import ar.edu.itba.sia.genetics.fenotypes.FitnessFunction;
import ar.edu.itba.sia.genetics.fenotypes.impl.NeuralNetworkFenotypeBuilder;
import ar.edu.itba.sia.genetics.fenotypes.impl.NeuralNetworkFenotypeSplitter;
import ar.edu.itba.sia.genetics.fenotypes.impl.PerceptronNetworkFitnessFunction;
import ar.edu.itba.sia.genetics.operators.GeneticOperator;
import ar.edu.itba.sia.genetics.operators.backpropagation.Backpropagator;
import ar.edu.itba.sia.genetics.operators.crossers.impl.AnularCrossover;
import ar.edu.itba.sia.genetics.operators.mutators.impl.ClassicMutator;
import ar.edu.itba.sia.genetics.replacers.GeneticReplacer;
import ar.edu.itba.sia.genetics.replacers.ReplacementAlgorithm;
import ar.edu.itba.sia.genetics.replacers.impl.ReplacementAlgorithmOne;
import ar.edu.itba.sia.genetics.replacers.impl.ReplacementAlgorithmTwo;
import ar.edu.itba.sia.genetics.selectors.FenotypeSelector;
import ar.edu.itba.sia.genetics.selectors.impl.EliteFenotypeSelector;
import ar.edu.itba.sia.genetics.selectors.impl.RouletteFenotypeSelector;
import ar.edu.itba.sia.genetics.utils.CachingFenotypeComparator;
import ar.edu.itba.sia.genetics.utils.FenotypeComparator;
import ar.edu.itba.sia.perceptrons.Layer;
import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.backpropagation.BackpropagationAlgorithm;
import ar.edu.itba.sia.perceptrons.backpropagation.impl.GradientDescentDeltaCalculator;
import ar.edu.itba.sia.perceptrons.backpropagation.impl.TanhDMatrixFunction;
import ar.edu.itba.sia.perceptrons.backpropagation.impl.TanhMatrixFunction;
import ar.edu.itba.sia.perceptrons.utils.ChainedCutCondition;
import ar.edu.itba.sia.perceptrons.utils.EpochsCutCondition;
import ar.edu.itba.sia.perceptrons.utils.ErrorCutCondition;
import ar.edu.itba.sia.utils.MatrixFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.jblas.DoubleMatrix;

public class Genetics {

	private FenotypeSelector selector;
	private GeneticOperator operator;
	private GeneticReplacer replacer;
	private ReplacementAlgorithm replacementAlgorithm;
	private CutCondition cutCondition;
	private int[] arquitecture= {2,75,1};
	private final double[][] learningPatternsArr = {
			{-0.9285,   0.3058,   0.1639},
			{ 0.5755,   1.6415,   0.8146},
			{ 2.0622,  -0.2821,   0.1278},
			{-0.9285,   2.4471,   0.3501},
			{ 3.0094,   1.1991,  -0.9625},
			{-1.5536,   2.4471,   0.0104},
			{-0.6370,  -0.2821,  -0.2276},
			{-1.8210,  -2.3063,   0.1933},
			{-2.4584,   2.7949,  -0.2716},
			{ 1.6545,   0.3058,  -0.0239},
			{-1.9920,  -1.3680,   0.4068},
			{ 1.5835,  -2.3063,   0.0101},
			{ 0.8435,  -1.3680,  -0.6236},
			{-0.6370,   2.4471,   0.4911},
			{ 0.8435,   0.8913,   0.5035},
			{-1.9920,  -0.6306,   0.2240},
			{ 0.8435,  -1.2640,  -0.6821},
			{      0,  -1.2640,  -0.8614},
			{-2.7931,  -1.9257,   0.9086},
			{ 1.2239,  -0.6306,  -0.2012},
			{-2.7931,   0.3058,  -0.2724},
			{-1.9920,   0.3058,  -0.1131},
			{      0,  -2.5350,  -0.5291},
			{-0.9285,  -2.7281,  -0.2256},
			{-2.4584,  -2.3063,   0.5964},
			{-1.1920,  -2.5350,  -0.2013},
			{ 0.5755,   2.2574,   0.6483},
			{-0.3214,   2.2574,   0.7289},
			{      0,  -0.9405,  -0.7999},
			{ 2.8433,   1.1991,  -0.9504},
			{-1.5536,   2.7949,   0.0058},
			{      0,  -1.3680,  -0.9310},
			{ 0.8435,  -2.6679,  -0.3280},
			{-2.4584,  -0.2821,   0.2262},
			{ 1.6545,   0.8913,  -0.0689},
			{-1.5536,  -0.2821,  -0.0050},
			{ 1.2239,        0,        0},
			{ 1.2239,   2.7949,   0.1237},
			{ 1.6545,  -2.7281,   0.0369},
			{-0.6370,  -2.6679,  -0.3576},
			{-1.5536,   2.2574,   0.0137},
			{-2.4584,   0.8913,  -0.5791},
			{-2.7931,  -1.3680,   0.8370},
			{ 2.4630,  -1.3680,   0.7439},
			{ 2.4630,  -2.3063,   0.5583},
			{      0,   0.8913,   0.8132},
			{ 1.5835,   2.4471,  -0.0084},
			{ 1.2239,   2.3015,   0.2550},
			{-2.7931,   0.6573,  -0.6200},
			{ 0.5755,  -1.3680,  -0.7588},
			{-0.6370,   2.2574,   0.6217},
			{ 1.6545,   1.1991,  -0.0804},
			{ 2.8433,  -0.2821,   0.2880},
			{ 0.2858,  -2.7281,  -0.3746},
			{ 2.8433,   0.8913,  -0.7674},
			{-0.3214,   2.3015,   0.6739},
			{ 2.4630,   2.2574,  -0.5644},
			{-2.7931,   0.8913,  -0.7722},
			{ 0.2858,  -1.2640,  -0.9303},
			{-1.8210,   2.2574,  -0.1825},
			{-1.1920,   1.8947,   0.3269},
			{-2.4584,        0,        0},
			{ 3.0094,  -2.7281,   0.3624},
			{ 2.0622,  -0.9405,   0.3896},
			{ 3.0094,  -1.3680,   0.9891},
			{ 2.0622,  -0.6306,   0.2553},
			{ 1.2239,  -1.3680,  -0.3483},
			{ 1.2239,   2.4471,   0.2203},
			{-1.9920,   1.6415,  -0.3700},
			{-1.8210,   2.7949,  -0.0796},
			{      0,   0.3058,   0.2865},
			{ 0.5755,  -1.9257,  -0.8084},
			{-0.6370,  -1.2640,  -0.7419},
			{ 0.2858,   1.1991,   0.9699},
			{-0.3214,  -0.9405,  -0.8322},
			{      0,  -1.9257,  -0.9093},
			{ 2.8433,  -2.6679,   0.4474},
			{-2.7931,   1.6415,  -0.8764},
			{-2.4522,  -1.3680,   0.7023},
			{ 0.5755,   2.3015,   0.6252},
			{-2.4522,   1.1991,  -0.7277},
			{ 0.2858,  -2.5350,  -0.4925},
			{ 1.6545,   2.3015,  -0.0610},
			{-1.8210,   0.3058,  -0.0706},
			{ 2.0622,  -2.3063,   0.3764},
			{ 0.5755,   0.3058,   0.2372},
			{ 2.8433,   1.6415,  -0.9791},
			{ 1.5835,  -1.9257,   0.0120},
			{ 3.0094,  -1.2640,   1.0209},
			{ 1.6545,   0.6573,  -0.0511},
			{-0.3214,  -2.5350,  -0.5318},
			{ 1.5835,  -2.6679,   0.0058},
			{-1.1920,  -2.6679,  -0.1629},
			{ 0.2858,   0.8913,   0.7736},
			{ 1.6545,   1.6415,  -0.0756},
			{-0.9285,  -0.9405,  -0.4391},
			{ 0.5755,  -1.2640,  -0.8334},
			{ 1.5835,        0,        0},
			{-0.6370,   0.3058,   0.2642},
			{ 0.8435,   0.3058,   0.2019}
	};
	private final double[][] testPatternsArr  = {
			{ 0.2858,   2.4471,   0.6018},
			{ 2.4630,   1.1991,  -0.7154},
			{ 1.5835,  -0.9405,   0.0110},
			{ 0.2858,  -2.6679,  -0.4083},
			{      0,  -0.6306,  -0.6373},
			{ 2.8433,   2.7949,  -0.3210},
			{ 2.0622,  -2.5350,   0.2628},
			{-0.6370,   1.6415,   0.8669},
			{ 3.0094,  -2.5350,   0.5933},
			{ 0.5755,  -0.2821,  -0.2497},
			{ 1.2239,   0.6573,   0.2075},
			{-1.9920,   2.7949,  -0.1268},
			{-1.1920,  -0.6306,  -0.2100},
			{-1.1920,  -1.9257,  -0.3264},
			{-0.9285,   2.7949,   0.1863},
			{-1.8210,  -0.2821,   0.0704},
			{ 1.5835,   2.2574,  -0.0096},
			{ 1.2239,  -2.3063,  -0.2525},
			{ 3.0094,  -2.3063,   0.7466},
			{-2.4584,   1.8947,  -0.7337},
			{ 3.0094,   0.6573,  -0.5822},
			{-0.6370,  -2.3063,  -0.6104},
			{-2.4522,   0.6573,  -0.4479},
			{ 3.0094,   0.8913,  -0.6981},
			{ 0.2858,   0.6573,   0.5624},
			{ 0.5755,  -0.9405,  -0.6232},
			{-0.9285,  -0.2821,  -0.1582},
			{-0.3214,  -0.6306,  -0.5541},
			{-0.9285,   2.3015,   0.4317},
			{ 2.8433,  -0.9405,   0.7774},
			{-1.9920,  -2.6679,   0.1710},
			{ 2.4630,  -2.6679,   0.3705},
			{ 2.8433,  -1.9257,   0.8556},
			{ 3.0094,  -1.9257,   0.8987},
			{-2.7931,  -2.5350,   0.5513},
			{ 1.5835,   0.6573,  -0.0077},
			{-1.9920,   0.8913,  -0.3458},
			{      0,        0,        0},
			{-0.6370,  -1.3680,  -0.8087},
			{-2.7931,   2.7949,  -0.2998},
			{ 3.0094,   2.2574,  -0.8357},
			{-0.3214,  -2.7281,  -0.4190},
			{-0.6370,  -0.9405,  -0.6812},
			{-2.4522,  -0.9405,   0.6438},
			{-1.5536,   0.3058,   0.0054},
			{ 2.8433,  -2.3063,   0.7474},
			{-2.4584,   1.6415,  -0.7328},
			{ 2.8433,  -2.7281,   0.4155},
			{-1.1920,  -2.3063,  -0.2917},
			{ 1.2239,   1.1991,   0.3245},
			{-1.1920,  -1.3680,  -0.3735},
			{-1.1920,   2.3015,   0.2557},
			{-1.8210,   1.6415,  -0.2415},
			{-0.9285,   0.8913,   0.4977},
			{-2.7931,  -2.7281,   0.3943},
			{ 2.0622,   0.8913,  -0.3498},
			{ 1.5835,   2.7949,  -0.0043},
			{ 0.2858,  -2.3063,  -0.6981},
			{ 0.2858,  -0.6306,  -0.5907},
			{ 1.2239,  -0.2821,  -0.0880},
			{ 0.2858,  -1.9257,  -0.8600},
			{-0.3214,   1.6415,   0.9981},
			{ 0.5755,   1.8947,   0.7436},
			{ 3.0094,  -0.9405,   0.8557},
			{ 2.4630,   1.6415,  -0.7309},
			{ 0.2858,   2.7949,   0.3506},
			{ 2.0622,  -1.2640,   0.4632},
			{-0.9285,  -2.6679,  -0.2908},
			{ 1.6545,  -2.6679,   0.0350},
			{-0.9285,  -1.3680,  -0.5573},
			{-0.6370,   1.8947,   0.7903},
			{-2.4522,  -2.5350,   0.4537},
			{-1.8210,  -1.2640,   0.2389},
			{      0,  -0.2821,  -0.2871},
			{-1.8210,  -1.9257,   0.2293},
			{-0.6370,   0.6573,   0.5265},
			{-1.1920,  -0.2821,  -0.0957},
			{-1.5536,  -0.9405,  -0.0126},
			{ 1.2239,  -0.9405,  -0.2722},
			{ 1.6545,  -0.9405,   0.0695},
			{ 0.8435,  -2.3063,  -0.4933},
			{      0,   2.3015,   0.7487},
			{-1.5536,  -2.7281,  -0.0063},
			{-1.8210,   0.8913,  -0.1778},
			{-1.9920,   2.2574,  -0.3306},
			{-0.6370,  -1.9257,  -0.7784},
			{-0.9285,        0,        0},
			{      0,   2.2574,   0.7395},
			{-1.1920,   2.4471,   0.2442},
			{ 1.6545,  -0.2821,   0.0221},
			{-1.9920,  -0.9405,   0.3115},
			{-2.4584,   2.2574,  -0.6143},
			{-2.7931,  -0.9405,   0.6990},
			{ 0.8435,   1.6415,   0.6950},
			{ 0.2858,        0,        0},
			{ 0.5755,   2.7949,   0.2891},
			{ 1.2239,   2.2574,   0.2415},
			{ 1.2239,  -2.7281,  -0.1338},
			{ 1.2239,  -1.9257,  -0.3337},
			{ 3.0094,  -2.6679,   0.4962},
			{      0,   1.8947,   0.8895},
			{ 1.6545,  -1.2640,   0.0815},
			{ 1.6545,  -1.3680,   0.0840},
			{ 0.2858,   2.2574,   0.7152},
			{ 2.8433,   0.6573,  -0.5816},
			{-1.1920,  -2.7281,  -0.1578},
			{ 1.2239,  -2.6679,  -0.1555},
			{ 2.0622,   1.1991,  -0.4643},
			{ 0.8435,  -0.9405,  -0.5405},
			{ 0.5755,   0.8913,   0.6953},
			{-2.4522,  -0.2821,   0.2120},
			{-1.1920,   0.3058,   0.1190},
			{-0.3214,  -1.2640,  -0.8756},
			{-1.5536,  -1.2640,  -0.0153},
			{-1.5536,   1.1991,   0.0158},
			{-1.9920,   2.3015,  -0.3096},
			{ 1.6545,   1.8947,  -0.0746},
			{-0.6370,   0.8913,   0.6028},
			{ 2.4630,  -0.2821,   0.2145},
			{-0.3214,   1.8947,   0.8608},
			{ 2.8433,   2.3015,  -0.7221},
			{ 1.5835,  -0.6306,   0.0074},
			{-1.1920,   0.8913,   0.2722},
			{-0.3214,  -1.3680,  -0.9096},
			{ 2.8433,   2.2574,  -0.7380},
			{ 3.0094,   2.7949,  -0.3677},
			{ 1.2239,  -2.5350,  -0.1971},
			{ 1.5835,  -2.7281,   0.0053},
			{ 2.4630,        0,        0},
			{ 2.4630,   2.3015,  -0.6328},
			{ 0.8435,   1.8947,   0.6082},
			{ 1.6545,   2.2574,  -0.0701},
			{ 1.2239,  -1.2640,  -0.3043},
			{-2.7931,   1.8947,  -0.9611},
			{      0,   2.7949,   0.3221},
			{-2.4522,  -2.3063,   0.6233},
			{ 1.5835,  -0.2821,   0.0033},
			{-1.1920,   1.6415,   0.3824},
			{-2.4584,  -2.6679,   0.3263},
			{-1.8210,  -2.7281,   0.1073},
			{-2.4522,   0.8913,  -0.5752},
			{-0.3214,   2.4471,   0.5916},
			{ 2.4630,   0.3058,  -0.2432},
			{-0.9285,   0.6573,   0.3886},
			{ 0.8435,  -2.7281,  -0.2496},
			{ 1.5835,   1.8947,  -0.0125},
			{ 0.8435,        0,        0},
			{-1.5536,  -2.6679,  -0.0084},
			{-2.4584,  -2.7281,   0.3208},
			{ 0.2858,  -1.3680,  -0.8791},
			{-1.5536,  -2.5350,  -0.0091},
			{-1.1920,  -1.2640,  -0.3579},
			{-2.4522,   2.7949,  -0.2737},
			{-0.3214,  -2.3063,  -0.6560},
			{-0.3214,   0.3058,   0.3034},
			{-1.5536,        0,        0},
			{-2.7931,  -0.6306,   0.6037},
			{ 2.4630,   0.8913,  -0.5883},
			{-0.9285,  -2.5350,  -0.3493},
			{-2.4522,   1.8947,  -0.7241},
			{ 0.5755,   0.6573,   0.4651},
			{ 1.6545,  -1.9257,   0.0853},
			{ 1.2239,   0.8913,   0.2518},
			{-1.9920,  -1.2640,   0.3548},
			{-0.9285,   1.8947,   0.5431},
			{ 0.8435,  -2.5350,  -0.3914},
			{-1.5536,   2.3015,   0.0118},
			{ 3.0094,   0.3058,  -0.2928},
			{-0.3214,  -1.9257,  -0.8451},
			{ 0.5755,        0,        0},
			{-1.9920,   1.8947,  -0.3850},
			{      0,  -2.6679,  -0.4338},
			{ 2.4630,  -0.6306,   0.4678},
			{ 1.2239,   0.3058,   0.0955},
			{-2.7931,   2.4471,  -0.5671},
			{ 0.8435,  -1.9257,  -0.6636},
			{ 2.0622,   2.2574,  -0.3647},
			{ 1.6545,  -2.3063,   0.0644},
			{ 1.5835,   1.6415,  -0.0138},
			{-1.5536,   0.8913,   0.0143},
			{-0.6370,        0,        0},
			{ 2.4630,   1.8947,  -0.7446},
			{-0.6370,  -0.6306,  -0.4925},
			{      0,   0.6573,   0.6465},
			{ 2.4630,  -0.9405,   0.6896},
			{ 0.8435,   0.6573,   0.4433},
			{-0.3214,   0.6573,   0.6150},
			{-0.9285,   1.1991,   0.5279},
			{ 2.0622,   1.6415,  -0.4614},
			{ 0.2858,  -0.2821,  -0.2675},
			{-1.8210,  -2.5350,   0.1376},
			{-2.4584,   2.4471,  -0.4713},
			{-2.4522,        0,        0},
			{-2.4584,  -0.9405,   0.6778},
			{-0.3214,   2.7949,   0.3211},
			{ 2.0622,   1.8947,  -0.4819},
			{ 1.6545,   2.4471,  -0.0546},
			{-2.4522,  -0.6306,   0.4559},
			{ 1.6545,        0,        0},
			{-0.3214,   0.8913,   0.6816},
			{-1.8210,   2.4471,  -0.1593},
			{-2.4522,   0.3058,  -0.2482},
			{ 1.5835,   1.1991,  -0.0112},
			{-0.6370,   2.7949,   0.2837},
			{ 2.4630,   0.6573,  -0.4745},
			{ 0.8435,  -0.2821,  -0.1845},
			{-2.7931,   2.3015,  -0.7378},
			{ 0.2858,  -0.9405,  -0.8094},
			{ 3.0094,   1.8947,  -0.8551},
			{-2.7931,  -2.3063,   0.6744},
			{-0.6370,  -2.7281,  -0.3227},
			{-0.6370,  -2.5350,  -0.4506},
			{ 0.8435,   1.1991,   0.5608},
			{-1.9920,   0.6573,  -0.2713},
			{-0.3214,  -0.2821,  -0.2655},
			{ 1.5835,  -2.5350,   0.0075},
			{-2.4584,  -0.6306,   0.4263},
			{ 2.8433,   1.8947,  -0.9617},
			{ 0.5755,  -0.6306,  -0.5030},
			{-1.1920,   2.2574,   0.2957},
			{-1.5536,   1.8947,   0.0150},
			{-2.7931,  -0.2821,   0.2553},
			{-0.9285,  -1.9257,  -0.5798},
			{ 3.0094,        0,        0},
			{      0,   1.1991,   0.9919},
			{      0,  -2.3063,  -0.7068},
			{-1.5536,  -1.9257,  -0.0166},
			{-2.4522,  -2.7281,   0.2996},
			{-1.8210,  -0.6306,   0.1351},
			{ 0.5755,  -2.7281,  -0.3677},
			{ 1.2239,   1.6415,   0.3363},
			{-1.8210,  -0.9405,   0.1898},
			{ 0.8435,   2.2574,   0.5140},
			{-1.9920,        0,        0},
			{-2.4584,   1.1991,  -0.7417},
			{-0.9285,  -2.3063,  -0.4826},
			{ 2.0622,  -2.7281,   0.1839},
			{ 0.2858,   0.3058,   0.2863},
			{ 0.8435,   2.7949,   0.2372},
			{-1.8210,   1.1991,  -0.2483},
			{-1.5536,  -2.3063,  -0.0129},
			{ 3.0094,   2.3015,  -0.7006},
			{ 1.5835,   2.3015,  -0.0098},
			{ 1.2239,   1.8947,   0.3066},
			{-2.4522,  -1.9257,   0.7729},
			{-2.4522,   2.4471,  -0.5251},
			{ 3.0094,   2.4471,  -0.5849},
			{-0.6370,   1.1991,   0.7735},
			{ 2.8433,  -1.2640,   0.8690},
			{-2.7931,   1.1991,  -0.8219},
			{ 2.0622,        0,        0},
			{-2.4584,   0.3058,  -0.2189},
			{ 3.0094,   1.6415,  -1.0567},
			{ 0.8435,  -0.6306,  -0.3601},
			{ 2.8433,   0.3058,  -0.2743},
			{ 2.0622,   0.3058,  -0.1541},
			{ 2.4630,   2.4471,  -0.5251},
			{ 1.5835,  -1.2640,   0.0122},
			{-1.8210,   0.6573,  -0.1621},
			{ 2.4630,   2.7949,  -0.2824},
			{-2.4584,   2.3015,  -0.6074},
			{      0,  -2.7281,  -0.3969},
			{-2.4522,   2.3015,  -0.6165},
			{-2.4584,   0.6573,  -0.4919},
			{ 1.5835,  -1.3680,   0.0135},
			{-1.8210,   2.3015,  -0.1937},
			{ 2.8433,        0,        0},
			{ 2.8433,  -2.5350,   0.5551},
			{ 3.0094,  -0.2821,   0.2541},
			{-2.7931,  -1.2640,   0.9619},
			{ 0.5755,  -2.5350,  -0.4879},
			{ 2.8433,  -1.3680,   0.8442},
			{ 1.6545,   2.7949,  -0.0268},
			{ 2.4630,  -1.9257,   0.7247},
			{-1.9920,  -2.7281,   0.1519},
			{-1.8210,  -1.3680,   0.2434},
			{      0,   2.4471,   0.6283},
			{-1.5536,   1.6415,   0.0158},
			{-2.7931,  -2.6679,   0.4525},
			{-1.9920,   2.4471,  -0.2530},
			{ 0.5755,   1.1991,   0.7079},
			{      0,   1.6415,   1.0845},
			{-0.9285,  -0.6306,  -0.3686},
			{-1.8210,   1.8947,  -0.2253},
			{ 2.4630,  -1.2640,   0.6774},
			{ 1.6545,  -0.6306,   0.0458},
			{ 2.8433,  -0.6306,   0.5326},
			{ 2.0622,  -1.3680,   0.4460},
			{-0.9285,   2.2574,   0.4475},
			{-2.4584,  -2.5350,   0.4427},
			{ 0.5755,  -2.6679,  -0.3447},
			{ 0.5755,   2.4471,   0.5761},
			{-2.4522,  -1.2640,   0.7632},
			{-0.9285,  -1.2640,  -0.5193},
			{-2.4584,  -1.3680,   0.7405},
			{-2.4522,   1.6415,  -0.7892},
			{-0.6370,   2.3015,   0.6584},
			{-1.5536,  -1.3680,  -0.0152},
			{ 0.8435,   2.3015,   0.5115},
			{ 1.5835,   0.3058,  -0.0039},
			{ 2.0622,   0.6573,  -0.2745},
			{-1.9920,  -1.9257,   0.4145},
			{ 2.8433,   2.4471,  -0.6486},
			{ 0.2858,   2.3015,   0.6990},
			{ 2.0622,  -1.9257,   0.4473},
			{-1.8210,        0,        0},
			{-1.1920,   2.7949,   0.1293},
			{-1.9920,  -0.2821,   0.1119},
			{ 2.0622,   2.4471,  -0.2943},
			{ 2.0622,   2.7949,  -0.1565},
			{-0.9285,   1.6415,   0.6271},
			{-2.4584,  -1.2640,   0.7850},
			{-1.9920,  -2.5350,   0.2489},
			{-1.5536,  -0.6306,  -0.0107},
			{-1.5536,   0.6573,   0.0097},
			{ 2.4630,  -2.5350,   0.4642},
			{-1.8210,  -2.6679,   0.1204},
			{ 1.5835,   0.8913,  -0.0101},
			{-0.3214,   1.1991,   0.9049},
			{-2.7931,        0,        0},
			{ 0.8435,   2.4471,   0.4560},
			{-1.9920,   1.1991,  -0.3631},
			{-1.1920,  -0.9405,  -0.3026},
			{ 0.5755,  -2.3063,  -0.6576},
			{ 0.2858,   1.8947,   0.9052},
			{-2.4522,   2.2574,  -0.6510},
			{ 2.0622,   2.3015,  -0.3480},
			{ 1.6545,  -2.5350,   0.0473},
			{-2.4584,  -1.9257,   0.6774},
			{ 2.0622,  -2.6679,   0.2052},
			{-1.1920,   0.6573,   0.2248},
			{ 3.0094,  -0.6306,   0.6275},
			{-1.1920,   1.1991,   0.3640},
			{-1.9920,  -2.3063,   0.2941},
			{-2.7931,   2.2574,  -0.7694},
			{-1.1920,        0,        0},
			{ 0.2858,   1.6415,   0.9172},
			{-0.3214,        0,        0},
			{-0.3214,  -2.6679,  -0.4077},
			{-2.4522,  -2.6679,   0.3252},
			{ 2.4630,  -2.7281,   0.3102}
	};
	private NeuralNetworkFenotypeBuilder fenotypeBuilder;
	
	public static void main(String[] args) {

		Genetics genetics= new Genetics(null,null,null, new GenerationsCutCondition(1000),150);
		List<Fenotype> fenotypes = genetics.initPopulation(150);
		ReplacementAlgorithm loop = genetics.getReplacementAlgorithm();

		while (genetics.cutConditionMet()) {
			System.out.println("size fenotypes "+fenotypes.size());
			loop.evolve(fenotypes);
		}
		FitnessFunction fitnessFunction=genetics.load();
		Collections.sort(fenotypes, new FenotypeComparator(fitnessFunction));
		double value=fitnessFunction.evaluate(fenotypes.get(0));
		System.out.println("best fitness -->"+value);
	}
	
	public Genetics(){
		
	}
	
	public Genetics(FenotypeSelector selector, GeneticOperator operator, ReplacementAlgorithm replacementAlgorithm,CutCondition cutCondition,int N){
		this.cutCondition=cutCondition;
		List<MatrixFunction> transferenceFunctions = new ArrayList<MatrixFunction>(arquitecture.length - 2);
		transferenceFunctions.add(new TanhMatrixFunction());
		transferenceFunctions.add(new TanhMatrixFunction());
		transferenceFunctions.add(new TanhMatrixFunction());
		transferenceFunctions.add(new TanhMatrixFunction());
		this.fenotypeBuilder = new NeuralNetworkFenotypeBuilder(arquitecture, transferenceFunctions);
		FitnessFunction fitnessFunction= load();
		Comparator<Fenotype> fenotypeComparator = new CachingFenotypeComparator(fitnessFunction);
		this.replacementAlgorithm =
				new ReplacementAlgorithmTwo(
						new EliteFenotypeSelector(90, fitnessFunction,fenotypeComparator),
						new EliteFenotypeSelector(150-90, fitnessFunction,fenotypeComparator),
						new ClassicMutator(),
						new AnularCrossover(fenotypeBuilder,
								new NeuralNetworkFenotypeSplitter(), 0.6),
						new Backpropagator(loadBackpropagationAlgoritm(), learningPatterns()));
	}

	private List<Fenotype> initPopulation(int N) {
		List<Fenotype> population= new ArrayList<Fenotype>(N);
		Backpropagator bp=new Backpropagator(loadBackpropagationAlgoritm(), learningPatterns());
		for(int i=0;i<N;i++){
			population.add(bp.backpropagate(fenotypeBuilder.build()));
		}
		return population; 
	}

	private FitnessFunction load(){
		List<Pattern> patterns= new ArrayList<Pattern>(); 
		for (int i = 0; i < learningPatternsArr.length; i++) {
			patterns.add(new Pattern(new DoubleMatrix(learningPatternsArr[i]).transpose()));
		}

		for (int i = 0; i < testPatternsArr.length; i++) {
			patterns.add(new Pattern(new DoubleMatrix(testPatternsArr[i]).transpose()));
		}

		
		FitnessFunction fitnessFunction=new PerceptronNetworkFitnessFunction(patterns);
		return fitnessFunction;
	}
	
	private List<Pattern> learningPatterns(){
		List<Pattern> patterns= new ArrayList<Pattern>(); 
		for (int i = 0; i < learningPatternsArr.length; i++) {
			patterns.add(new Pattern(new DoubleMatrix(learningPatternsArr[i]).transpose()));
		}
		return patterns;
	}
	
	private BackpropagationAlgorithm loadBackpropagationAlgoritm(){
		List<Layer> layers = new ArrayList<Layer>(arquitecture.length - 2);
		List<Pattern> learningPatterns = new ArrayList<Pattern>(learningPatternsArr.length);
		List<Pattern> testPatterns = new ArrayList<Pattern>(testPatternsArr.length);

		for (int i = 1; i < arquitecture.length; i++) {
			layers.add(new Layer(DoubleMatrix.rand(arquitecture[i],arquitecture[i-1] + 1), new TanhMatrixFunction()));
		}

		for (int i = 0; i < learningPatternsArr.length; i++) {
			learningPatterns.add(new Pattern(new DoubleMatrix(learningPatternsArr[i]).transpose()));
		}

		for (int i = 0; i < testPatternsArr.length; i++) {
			testPatterns.add(new Pattern(new DoubleMatrix(testPatternsArr[i]).transpose()));
		}


		List<ar.edu.itba.sia.perceptrons.backpropagation.CutCondition> cutConditions = new LinkedList<ar.edu.itba.sia.perceptrons.backpropagation.CutCondition>();
		cutConditions.add(new EpochsCutCondition(5000));
		cutConditions.add(new ErrorCutCondition(testPatterns, 0.0001));
		ChainedCutCondition cutCondition = new ChainedCutCondition(cutConditions);

		BackpropagationAlgorithm backpropagation = new BackpropagationAlgorithm(new GradientDescentDeltaCalculator(0.1, new TanhDMatrixFunction()), cutCondition);
		return backpropagation;
	}
	
	private boolean cutConditionMet() {
		return cutCondition.conditionMet(null);
	}


	public FenotypeSelector getSelector() {
		return selector;
	}

	public GeneticOperator getOperator() {
		return operator;
	}

	public GeneticReplacer getReplacer() {
		return replacer;
	}
	
	public ReplacementAlgorithm getReplacementAlgorithm(){
		return replacementAlgorithm;
	}
}

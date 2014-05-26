package ar.edu.itba.sia.services;

import ar.edu.itba.sia.perceptrons.Layer;
import ar.edu.itba.sia.perceptrons.PerceptronNetwork;
import au.com.bytecode.opencsv.CSVWriter;
import org.jblas.DoubleMatrix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ExportService {

	private static final Object LOCKER = new Object();
	private static ExportService instance;

	public static ExportService getInstance() {
		if (instance == null) {
			synchronized (LOCKER) {
				if (instance == null) {
					instance = new ExportService();
				}
			}
		}
		return instance;
	}

	public void exportAsCSV(PerceptronNetwork network) {
		try {
			int li = 0;
			long timestamp = System.currentTimeMillis();
			for (Layer l : network.getLayers()) {
				File file = File.createTempFile(String.format("network-%d-layer-%d-", timestamp, li), ".csv",
						ConfigurationService.getInstance().getExportPath().toFile());
				CSVWriter writer = new CSVWriter(new FileWriter(file), ';', ' ');
				DoubleMatrix weights = l.getWeights();
				for (int i = 0; i < weights.rows; i++) {
					String[] nextLine = new String[weights.columns];
					for (int j = 0; j < weights.columns; j++) {
						nextLine[j] = String.valueOf(weights.get(i, j));
					}
					writer.writeNext(nextLine);
				}
				writer.close();
				li++;
			}
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}

	public void exportErrorCSV(List<Double> error) {
		try {
			File file = File.createTempFile("error-" + System.currentTimeMillis() + "-",
					".csv", ConfigurationService.getInstance().getExportPath().toFile());
			CSVWriter writer = new CSVWriter(new FileWriter(file), ';', ' ');
			String[] line = new String[error.size()];
			for (int i = 0; i < error.size(); i++) {
				line[i] = error.get(i).toString();
			}
			writer.writeNext(line);
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void exportFitnessCSV(List<Double> fitness) {
		try {
			File file = File.createTempFile("fitness-" + System.currentTimeMillis() + "-",
					".csv", ConfigurationService.getInstance().getExportPath().toFile());
			CSVWriter writer = new CSVWriter(new FileWriter(file), ';', ' ');
			String[] line = new String[fitness.size()];
			for (int i = 0; i < fitness.size(); i++) {
				line[i] = fitness.get(i).toString();
			}
			writer.writeNext(line);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}

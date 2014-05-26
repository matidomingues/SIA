package ar.edu.itba.sia.services;

import ar.edu.itba.sia.perceptrons.Layer;
import ar.edu.itba.sia.perceptrons.Pattern;
import ar.edu.itba.sia.perceptrons.PerceptronNetwork;
import au.com.bytecode.opencsv.CSVWriter;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.io.data.DataWriter;
import de.erichseifert.gral.io.data.DataWriterFactory;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import org.jblas.DoubleMatrix;

import java.io.File;
import java.io.FileOutputStream;
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
			File file = File.createTempFile("network", ".csv",
					ConfigurationService.getInstance().getExportPath().toFile());
			CSVWriter writer = new CSVWriter(new FileWriter(file));
			for (Layer l : network.getLayers()) {
				DoubleMatrix weights = l.getWeights();
				for (int i = 0; i < weights.rows; i++) {
					String[] nextLine = new String[weights.columns];
					for (int j = 0; j < weights.columns; j++) {
						nextLine[j] = String.valueOf(weights.get(i, j));
					}
					writer.writeNext(nextLine);
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}

	public void exportErrorCSV(List<Double> error) {
		try {
			File file = File.createTempFile("error-" + new Date(System.currentTimeMillis()).toString(),
					".csv", ConfigurationService.getInstance().getExportPath().toFile());
			CSVWriter writer = new CSVWriter(new FileWriter(file));
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

	public void exportErrorImage(List<Double> errors) {
		try {

			File file = File.createTempFile("error-image-", ".png",
					ConfigurationService.getInstance().getExportPath().toFile());
			DataTable data = new DataTable(Integer.class, Double.class);
			for (int i = 0; i < errors.size(); i++) {
				data.add(i, errors.get(i));
			}
			XYPlot plot = new XYPlot(data);
			plot.setLineRenderer(data, new DefaultLineRenderer2D());
			DataWriterFactory dataWriterFactory = DataWriterFactory.getInstance();
			DataWriter dataWriter = dataWriterFactory.get("text/csv");
			dataWriter.write(data, new FileOutputStream(file));
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public void exportComparisonImage(PerceptronNetwork n1, PerceptronNetwork n2, List<Pattern> patterns) {

	}

}

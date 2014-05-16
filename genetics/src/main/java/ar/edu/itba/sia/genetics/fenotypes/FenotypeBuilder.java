package ar.edu.itba.sia.genetics.fenotypes;

import java.util.List;

public interface FenotypeBuilder {

	Fenotype build(List<Allele> childAlleles);
}

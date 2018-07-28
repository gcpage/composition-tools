package evolution.algorithms.select;

import evolution.core.Genome;
import evolution.core.Population;
import evolution.diagnostics.Log;
import evolution.diagnostics.Record;

public class SurvivalThreshold implements Selector {

	Log log;
	double survivalCf; // Fraction of population to survive each generation

	public SurvivalThreshold(double survivalCf) {
		this.survivalCf = survivalCf;
		log = null;
	}

	@Override
	public Population nextGeneration(Population current) {
		int survivorCount = (int) (current.size() * survivalCf);
		Population survivors = new Population();
		Population next = new Population(current.gen + 1);
		for (int i = 0 ; i < survivorCount; i++) {
			survivors.add(current.get(i));
		}
		next.addAll(survivors);
		for (int i = 0; i < current.size() - survivorCount; i++) {
			Genome parent1 = survivors.get((int) (Math.random() * survivorCount));
			Genome parent2 = survivors.get((int) (Math.random() * survivorCount));
			Genome child = parent1.breed(parent2);
			next.add(child);
			if (log != null) {
				log.add(new Record(next.gen, child, parent1, parent2));
			}
		}
		
		return next;
	}

	@Override
	public void enableLogging(Log log) {
		this.log = log;
	}
}

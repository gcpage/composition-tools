package main;

import evolution.core.Genome;
import evolution.core.Splicer;

public class Timbre implements Genome {

	private static final long serialVersionUID = 1008255419750499780L;
	
	public static final int ENVELOPE_DIM = 64;
	public static final int SPEED_DIM = 8;
	public static final float DEFAULT_DUR = 0.2f;

	public float[] pos;
	public float[] att;
	public float[] dec;
	public float[] sus;
	public float[] rel;
	public float[] slev;

	double mRate;
	double score;

	public Timbre() {
		pos = new float[2];
		att = new float[2];
		dec = new float[2];
		sus = new float[2];
		rel = new float[2];
		slev = new float[2];
		mRate = Session.MUTATION_RATE;
		score = 0;
	}

	@Override
	public Genome breed(Genome genome) {
		Timbre other = (Timbre) genome;
		Timbre child = new Timbre();
		child.pos = Splicer.chooseRange(this.pos, other.pos);
		child.att = Splicer.chooseRange(this.att, other.att);
		child.dec = Splicer.chooseRange(this.dec, other.dec);
		child.sus = Splicer.chooseRange(this.sus, other.sus);
		child.rel = Splicer.chooseRange(this.rel, other.rel);
		child.slev = Splicer.chooseRange(this.slev, other.slev);
		child.mutate();
		return child;
	}
	
	@Override
	public void randomize() {
		Splicer.randomizeRange(pos, 1);
		Splicer.randomizeRange(att, 1);
		Splicer.randomizeRange(dec, 1);
		Splicer.randomizeRange(sus, 1);
		Splicer.randomizeRange(rel, 1);
		Splicer.randomizeRange(slev, 1);
		score = 0;
	}

	@Override
	public double getScore() {
		return score;
	}

	@Override
	public int getId() {
		return hashCode();
	}
	
	public void mutate() {
		Splicer.mutateRange(pos, 1, Session.MUTATION_RATE);
		Splicer.mutateRange(att, 1, Session.MUTATION_RATE);
		Splicer.mutateRange(dec, 1, Session.MUTATION_RATE);
		Splicer.mutateRange(sus, 1, Session.MUTATION_RATE);
		Splicer.mutateRange(rel, 1, Session.MUTATION_RATE);
		Splicer.mutateRange(slev, 1, Session.MUTATION_RATE);
	}
}

package main;

import orc.Orchestra;
import orc.Value;
import orc.Variable;
import sco.Note;
import sco.Param;
import sco.Score;
import sco.SineFTable;
import sound.Performable;
import sco.ArrayParam;

import orc.Constant;
import orc.Expression;
import orc.Instrument;
import orc.Opcode;

public class Rhythm implements Performable {

	public String[] samples;
	public Timbre[] timbres;
	public Sequence[] sequences;
	public int voiceCount;

	public Rhythm(String[] samples, Timbre[] timbres, Sequence[] sequences) {
		this.samples = samples;
		this.timbres = timbres;
		this.sequences = sequences;
		voiceCount = samples.length;
	}
	
	public void rate(int rating) {
		for (Timbre timbre : timbres)
			timbre.score += rating;
		for (Sequence sequence : sequences)
			sequence.score += rating;
	}
	
	@Override
	public Score getScore() {
		Score sco = new Score(getOrchestra().mapParams());
		for (int v = 0; v < voiceCount; v++) {
			int ifn = sco.addFTable(new SineFTable());
			float quantLen = sequences[v].getQuantLength();
			for (int s = 0; s < sequences[v].strikeCount; s++) {
				Note note = new Note();
				note.add(new Param<Integer>(Orchestra.INSTRUMENT, 1));
				note.add(new Param<Float>(Orchestra.START, sequences[v].strt[s] * quantLen));
				note.add(new Param<Float>(Orchestra.DURATION, timbres[v].trueDur(sequences[v].dur[s])));
				note.add(new Param<Float>("freqratio", 1.0f));
				note.add(new Param<Integer>("fn", ifn));
				note.add(new Param<Float>("pos", timbres[v].truePos(sequences[v].pos[s])));
				note.add(new ArrayParam<Float>("env", timbres[v].trueEnv(sequences[v].env[s])));
				sco.addNote(note);
			}
		}
		return sco;
	}

	@Override
	public Orchestra getOrchestra() {
			int sr = 44100;
			int ksmps = 32;
			int nchnls = 2;
			float dbfs0 = 1.0f;
			Orchestra orc = new Orchestra(sr, ksmps, nchnls, dbfs0);
			Instrument inst = new Instrument(1);
			Value freqratio = new Variable("k", "freqratio", "=", inst.p());
			Value loop = new Constant<Integer>(0);
			Value end = new Constant<Integer>(0);
			Value ifn = new Variable("i", "fn", "=", inst.p());
			Value pos = new Variable("i", "pos", "=", new Expression("*", inst.p(), Orchestra.SR));
			Value env = new Variable("a", "env", "=", new Constant<Float>(0.5f));
			Opcode oscil = new Opcode("a", "sig", 2, "lposcilsa", env, freqratio, loop, end, ifn, pos);
			inst.setOuts(oscil);
			
			orc.add(inst);
		return orc;
	}
}

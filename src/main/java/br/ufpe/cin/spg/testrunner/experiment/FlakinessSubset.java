package br.ufpe.cin.spg.testrunner.experiment;

import java.util.Arrays;

import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import br.ufpe.cin.spg.testrunner.App;
import br.ufpe.cin.spg.testrunner.TestListener;

public class FlakinessSubset {

	public static void main(String[] args) throws ClassNotFoundException {
		int N = Integer.parseInt(args[0]);
		String modeParam = args[1];
		String[] testClasses = Arrays.copyOfRange(args, 2, args.length);

		JUnitCore runner = new JUnitCore();
		runner.addListener(new TestListener());
		Computer cp = App.configureMode(modeParam);

		Result[] results = new Result[N];
		for (int i = 0; i < N; i++) {
			Result result = runner.run(cp, App.findClasses(testClasses));
			results[i] = result;
		}

		for (Result r : results) {
			App.displayReport(r);
		}
	}
}

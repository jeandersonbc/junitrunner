package br.ufpe.cin.spg.testrunner.experiment;

import java.util.Arrays;

import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import br.ufpe.cin.spg.testrunner.App;
import br.ufpe.cin.spg.testrunner.TestListener;

public class FlakinessSubset {

	public static void main(String[] args) throws ClassNotFoundException {
		String modeParam = args[0];
		String[] testClasses = Arrays.copyOfRange(args, 1, args.length);

		JUnitCore runner = new JUnitCore();
		runner.addListener(new TestListener());
		Computer cp = App.configureMode(modeParam);

		int N = 5;
		Result[] results = new Result[N];
		for (int i = 0; i < N; i++) {
			Result result = runner.run(cp, App.findClasses(testClasses));
			results[i] = result;
		}

		for (Result r : results) {
			String resultCounters = String.format("Total: %d Failures: %d Skip: %d", r.getRunCount(),
					r.getFailureCount(), r.getIgnoreCount());
			String resultElapsedTime = String.format("Elapsed time: %.2f s", r.getRunTime() / 1_000.00);

			StringBuilder sb = new StringBuilder();
			for (Failure f : r.getFailures()) {
				sb.append(f.getTestHeader()).append("\n");
			}
			System.out.println(resultCounters);
			System.out.println(resultElapsedTime);
			System.out.println(sb.toString());
		}
	}
}

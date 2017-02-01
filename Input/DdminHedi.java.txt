package fil.iagl.opl.conan.debug;


import fil.iagl.opl.conan.model.Challenge;

import java.util.*;

public class Ddmin {

	private Challenge challenge;

	private Object input;


	public Ddmin(Object input, Challenge challenge) {
		this.input = input;
		this.challenge = challenge;
	}

	public List<ArrayList<DebugChainElement>> simplifyingDifferences(List<ArrayList<DebugChainElement>> diffsCombinations){
		List<ArrayList<DebugChainElement>> smallestDiffs = new ArrayList<>();
		int smallestDiffsSize = Integer.MAX_VALUE;
		Collections.sort(diffsCombinations, new Comparator<ArrayList>(){
			public int compare(ArrayList a1, ArrayList a2) {
				return a2.size() - a1.size();
			}
		});
		Collections.reverse(diffsCombinations);

		for(ArrayList<DebugChainElement> diffs : diffsCombinations){
			if(diffs.size() > smallestDiffsSize){
				break;
			}else {
				Boolean programFail = !test(diffs);
				if (programFail) {
					smallestDiffsSize = diffs.size();
					smallestDiffs.add(diffs);
				}
			}
		}
		return smallestDiffs;
	}

	public void execDifferences(List<DebugChainElement> diffs) {
		DebugAssistant.capturedVariablesToReplaceValues = new LinkedHashMap<>();
		for (DebugChainElement diff : diffs) {
			CatchedVariable diffToApply = new CatchedVariable(diff.line, diff.value, diff.variableName, diff.iteration);
			DebugAssistant.capturedVariablesToReplaceValues.put(diff.variableName, diffToApply);
		}
	}

	public List<DebugChainElement> process(List<DebugChainElement> diffs) {
		List<DebugChainElement> causes = new ArrayList<>();
		if (diffs.isEmpty()) return causes;

		List<List<DebugChainElement>> diffsByIterationList = new ArrayList<>();

		String previousIteration = diffs.get(0).iteration;
		diffsByIterationList.add(new ArrayList<>());
		int i = 0;
		for (DebugChainElement diff : diffs) {
			String iteration = diff.iteration;
			if (iteration.equals(previousIteration)) {
				diffsByIterationList.get(i).add(diff);
			} else {
				i++;
				previousIteration = iteration;

				List<DebugChainElement> diffsForIt = new ArrayList<>();
				diffsForIt.add(diff);
				diffsByIterationList.add(diffsForIt);
			}
		}

		for (List<DebugChainElement> diffsByIt : diffsByIterationList) {
			causes.addAll(subProcess(diffsByIt));
		}

		return causes;
	}

	public List<DebugChainElement> subProcess(List<DebugChainElement> diffs) {
		List<DebugChainElement> causes = new ArrayList<>();

		List<DebugChainElement> diffsToTest = new ArrayList<>(diffs);
		List<ArrayList<DebugChainElement>> powerSetOfDiffsToTest = powerset(diffsToTest);
		powerSetOfDiffsToTest.remove(0);

		List<ArrayList<DebugChainElement>> crashResponsibleDiffs = simplifyingDifferences(powerSetOfDiffsToTest);

		for(List<DebugChainElement> diffsCrash : crashResponsibleDiffs){
			for(DebugChainElement diff : diffsCrash){
				if(!causes.contains(diff)){
					causes.add(diff);
				}
			}
		}
		return causes;
	}





	public static List<ArrayList<DebugChainElement>> powerset(List<DebugChainElement> list) {
		List<ArrayList<DebugChainElement>> ps = new ArrayList<ArrayList<DebugChainElement>>();
		ps.add(new ArrayList<DebugChainElement>());   // add the empty set

		for (DebugChainElement item : list) {
			List<ArrayList<DebugChainElement>> newPs = new ArrayList<ArrayList<DebugChainElement>>();

			for (ArrayList<DebugChainElement> subset : ps) {
				newPs.add(subset);

				ArrayList<DebugChainElement> newSubset = new ArrayList<DebugChainElement>(subset);
				newSubset.add(item);
				newPs.add(newSubset);
			}

			ps = newPs;
		}
		return ps;
	}


	public Boolean test(List<DebugChainElement> diffs) {
		execDifferences(diffs);
		try {
			challenge.challenge(this.input);
			return true;
		} catch (Exception | AssertionError e) {
				return false;
		}
	}



	public List<DebugChainElement> getDiffs(List<DebugChainElement> goodChain, List<DebugChainElement> badChain) {
		List<DebugChainElement> diffs = new ArrayList<>();
		for (DebugChainElement chainElement : badChain) {
			if (!goodChain.contains(chainElement)) {
				diffs.add(chainElement);
			}
		}
		return diffs;
	}
}
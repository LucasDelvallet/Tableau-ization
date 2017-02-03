package debug;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import fr.univ_lille1.m2iagl.dd.Challenge;
import utils.CapturedVar;
import utils.DebugManipulation;

public class Ddmin {

	private Challenge challenge;

	private Object input;


	public Ddmin(Object input, Challenge challenge) {
		this.input = input;
		this.challenge = challenge;
	}

	public List<DebugChainElement> process(List<DebugChainElement> diffs) {
		List<DebugChainElement> causes = new ArrayList<>();
		if (diffs.isEmpty()) return causes;

		List<List<DebugChainElement>> diffsByIterationList = new ArrayList<>();

		String previousIteration = diffs.get(0).iteration;
		diffsByIterationList.add(new ArrayList<>());
		int i = 0;
		//build the List of diff by iteration
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
			//call subprocess and fill the cause array
			causes.addAll(subProcess(diffsByIt));
		}

		return causes;
	}

	public List<DebugChainElement> subProcess(List<DebugChainElement> diffs) {
		List<DebugChainElement> causes = new ArrayList<>();

		List<DebugChainElement> diffsToTest = new ArrayList<>(diffs);
		List<ArrayList<DebugChainElement>> powerSetOfDiffsToTest = powerset(diffsToTest);
		//remove the empty set
		powerSetOfDiffsToTest.remove(0);

		List<ArrayList<DebugChainElement>> crashResponsibleDiffs = smallestCrashDiffs(powerSetOfDiffsToTest);

		for(List<DebugChainElement> diffsCrash : crashResponsibleDiffs){
			for(DebugChainElement diff : diffsCrash){
				if(!causes.contains(diff)){
					causes.add(diff);
				}
			}
		}
		return causes;
	}

	public List<ArrayList<DebugChainElement>> smallestCrashDiffs(List<ArrayList<DebugChainElement>> diffsCombinations){
		List<ArrayList<DebugChainElement>> smallestDiffs = new ArrayList<>();
		int smallestDiffsSize = Integer.MAX_VALUE;
		Collections.sort(diffsCombinations, new Comparator<ArrayList>(){
			public int compare(ArrayList a1, ArrayList a2) {
				return a2.size() - a1.size(); // assumes you want smallest to biggest
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



	public static List<ArrayList<DebugChainElement>> powerset(List<DebugChainElement> list) {
		List<ArrayList<DebugChainElement>> ps = new ArrayList<ArrayList<DebugChainElement>>();
		ps.add(new ArrayList<DebugChainElement>());   // add the empty set

		// for every item in the original list
		for (DebugChainElement item : list) {
			List<ArrayList<DebugChainElement>> newPs = new ArrayList<ArrayList<DebugChainElement>>();

			for (ArrayList<DebugChainElement> subset : ps) {
				// copy all of the current powerset's subsets
				newPs.add(subset);

				// plus the subsets appended with the current item
				ArrayList<DebugChainElement> newSubset = new ArrayList<DebugChainElement>(subset);
				newSubset.add(item);
				newPs.add(newSubset);
			}

			// powerset is now powerset of list.subList(0, list.indexOf(item)+1)
			ps = newPs;
		}
		return ps;
	}


	public Boolean test(List<DebugChainElement> diffs) {
		//apply diffs here
		applyDiffs(diffs);
		try {
			this.challenge.challenge(this.input);
			return true;
		} catch (Exception e) {
				return false;
		} catch (AssertionError e){
			return false;
		}
	}

	public void applyDiffs(List<DebugChainElement> diffs) {
		//empty the linkedList from potential previous value
		DebugManipulation.capturedVarsToReplaceValues = new LinkedHashMap<>();

		for (DebugChainElement diff : diffs) {
			CapturedVar diffToApply = new CapturedVar(diff.line, diff.value, diff.varName, diff.iteration);
			//add to the linkedList
			DebugManipulation.capturedVarsToReplaceValues.put(diff.varName, diffToApply);
		}
	}


	/**
	 * Get the diffs beetween the good run and the bad run.
	 *
	 * @param goodChain
	 * @param badChain
	 * @return the badChain diffs (element which potentially break the program)
	 */
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
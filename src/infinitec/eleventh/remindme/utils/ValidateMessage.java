package infinitec.eleventh.remindme.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * 
 * @author Abhilasha Singh
 * Class to validate message based on indicator words found.
 */

public class ValidateMessage {

	Map<String, Integer> wordMapExists;
	Map<String, Integer> wordMapMessage;
	static Set<String> wordsExists;
	static Set<String> wordsMessage;
	static Set<String> commonWords;

	public boolean isValid(String message) {
		getExistingWordList();
		extractWordsFromMessage(message);
		boolean valid = checkValidMessage();
		return valid;
	}

	public static float calLimitGot() {
		commonWords = new HashSet<String>(wordsExists);
		commonWords.retainAll(wordsMessage);
		float limitGot = (float)commonWords.size()/AppConstants.WORDS.length;
		return limitGot;
	}
	public void extractWordsFromMessage(String message) {
		message = message.replaceAll("[!?,]", "");
		String[] messageWords = message.split("\\s+");
		List<String> listFromMessage = new ArrayList<String>(Arrays.asList(messageWords));
		wordMapMessage = new HashMap<String, Integer>();
		for (String word : listFromMessage) {
			if (!wordMapMessage.containsKey(word))
				wordMapMessage.put(word, 1);
			else
				wordMapMessage.put(word, wordMapMessage.get(word) + 1);
		}
		wordsMessage = new HashSet<String>(wordMapMessage.keySet());
	}

	public void getExistingWordList() {
		List<String> listExists = new ArrayList<String>(Arrays.asList(AppConstants.WORDS));
		wordMapExists = new HashMap<String, Integer>();
		for (String word : listExists) {
			if (!wordMapExists.containsKey(word))
				wordMapExists.put(word, 1);
			else
				wordMapExists.put(word, wordMapExists.get(word) + 1);
		}
		wordsExists = new HashSet<String>(wordMapExists.keySet());
	}

	public static boolean checkValidMessage() {
		float minLimit = calMinLimit();
		float limitGot = calLimitGot();
		if (limitGot >= minLimit) {
			return true;
		} else {
			return false;
		}

	}

	public static float calMinLimit() {
		float minLimit = (float) 1 / AppConstants.WORDS.length;
		return minLimit;
	}

}

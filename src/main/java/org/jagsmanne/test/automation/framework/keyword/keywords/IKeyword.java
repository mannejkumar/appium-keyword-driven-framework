package org.jagsmanne.test.automation.framework.keyword.keywords;

import org.jagsmanne.test.automation.framework.keyword.reader.IKeywordStore;

/**
 * Base interface for the keyword implementation classes.
 * @author  Jagadeesh Manne
 *
 */
public interface IKeyword {
	
	/**
	 * Executes the specified keyword with specified given arguments.
	 * @param keyword Keyword to be executed.
	 * @param keywordStore Arguments to be used for the keyword execution.
	 */
	public void execute(String keyword, IKeywordStore keywordStore) throws NoSuchMethodException;

}

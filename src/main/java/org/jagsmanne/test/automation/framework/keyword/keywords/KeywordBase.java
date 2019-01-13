package org.jagsmanne.test.automation.framework.keyword.keywords;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jagsmanne.test.automation.framework.keyword.KeywordException;
import org.jagsmanne.test.automation.framework.keyword.reader.IKeywordStore;

public abstract class KeywordBase implements IKeyword {
	
	protected Object executingObject=null;
	protected IKeywordStore keywordStore;

	protected KeywordBase() {
		executingObject = this;
	}
	
	@Override
	public void execute(String keyword, IKeywordStore keywordStore) {
		Method method = null;
		boolean executed = false;
		this.keywordStore = keywordStore;
		try {
			method = executingObject.getClass().getMethod(keyword);
			method.invoke(executingObject);
			executed = true;
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		if(!executed){
			throw new KeywordException("Unable to find any key word with name: \""+keyword+
					this.getClass().getName()+"\".");
		}
	}
}

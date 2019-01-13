package org.jagsmanne.test.automation.framework.keyword.executor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.jagsmanne.test.automation.framework.keyword.keywords.IKeyword;
import org.jagsmanne.test.automation.framework.config.DefaultConfig;
import org.jagsmanne.test.automation.framework.keyword.KeywordException;
import org.jagsmanne.test.automation.framework.keyword.reader.IKeywordStore;
import org.openqa.selenium.WebDriver;


/**
 * Factory class to identify the Keyword implementation classes that implement the <code>IKeyword</code>
 * interface and execute the keywords with respective arguments.
 *
 */
public class KeywordFactory {
	
	DefaultConfig config = DefaultConfig.getDefaultConfig();
	List<IKeyword> keywordClassObjects;
	WebDriver driver;
	
	public KeywordFactory(WebDriver driver){
		this.driver = driver;
		this.keywordClassObjects = getKeywordImplementations(); 	
	}
	
	private List<IKeyword> getKeywordImplementations(){
		String[] keywordClasses = config.getConfigValue("listeners").split(",");
		ClassLoader cloader = this.getClass().getClassLoader();
		List<Class<?>> listenerClasses = new ArrayList<Class<?>>();
		List<IKeyword> keywordClsObjs = new ArrayList<IKeyword>();
		for(String keywordClass:keywordClasses){
			try{
				Class<?> cls = cloader.loadClass(keywordClass);
				listenerClasses.add(cls);
			}catch(ClassNotFoundException e){
				throw new KeywordException("Unable to find class with name as mentione in listner property: "+
						keywordClass+" Make sure the class is present in the classpath.");				
			}
		}		
		
		for (Class<?> cls : listenerClasses) {
			if (IKeyword.class.isAssignableFrom(cls)) {
				IKeyword obj = null;
				try {
					Constructor<?> constructor = cls
							.getConstructor(WebDriver.class);
					obj = (IKeyword) constructor.newInstance(this.driver);
				} catch (IllegalAccessException | InstantiationException
						| SecurityException | NoSuchMethodException
						| IllegalArgumentException | InvocationTargetException e) {
					throw new KeywordException(
							"Unable to find a constructor that accepts the WebDriver object for the keyword class:"
									+ cls.getName());
				}
				if (obj != null) {
					keywordClsObjs.add(obj);
				}

			}
		}
		return keywordClsObjs;
	}
	
	public void executeKeyword(String keyword, IKeywordStore keywordStore){
		boolean executed = false;
		for(IKeyword keywordCls : keywordClassObjects){
			try {
				keywordCls.execute(keyword, keywordStore);
				executed = true;
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		if(!executed){
			throw new KeywordException("Unable to find any keyword class that support keyword: \""
					+keyword);
		}
		
	}
}

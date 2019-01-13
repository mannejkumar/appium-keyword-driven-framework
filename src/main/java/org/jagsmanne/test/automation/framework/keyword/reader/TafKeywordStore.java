package org.jagsmanne.test.automation.framework.keyword.reader;

import java.util.ArrayList;
import java.util.List;

/**
 * TAF implementation of the interface <code>IKeywordStore</code>
 * @author  Jagadeesh Manne
 *
 */
public class TafKeywordStore implements IKeywordStore{
	private String keyword = "";
	private List<Object> arguments = new ArrayList<Object>();
	private String locator = "";
	private String locatorType="";
	
	@Override
	public void setKeyword(String keyword) {
		this.keyword = keyword;		
	}

	@Override
	public String getKeyword() {
		return this.keyword;
	}

	@Override
	public void setLocator(String locator) { this.locator = locator; }

	@Override
	public String getLocator() { return this.locator; }

	@Override
	public void setLocatorType(String locatorType) {this.locatorType = locatorType; }

	@Override
	public String getLocatorType() { return this.locatorType; }

	@Override
	public void setArguments(List<Object> args) {
		this.arguments = args;
	}

	@Override
	public List<Object> getArguments() {
		return this.arguments;
	}


}

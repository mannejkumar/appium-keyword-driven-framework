package org.jagsmanne.test.automation.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Default Config file which loads the base configuraion required for the framework.
 * <p>It looks for the system property <code>"taf.config"</code> for getting the configuration file path.
 * If the property returns <code>null</code>, the file try to load the configuration from the default path 
 * <code>"src/test/resources/taf.properties"</code>.
 * 
 * <p> This is a singleton class so incase you need to create an object of this class 
 * please call the static method <code>getDefaultConfig()</code>.
 * 
 * @author Jagadeesh Manne
 *
 */
public class DefaultConfig {
	private static DefaultConfig defaultConfig = null;
	Properties configData = null;
	
	private DefaultConfig() {
		this.loadConfig();	
	}
	
	/**
	 * Returns the singleton object of the <code>DefaultConfig</code> class.
	 * @return <code>DefaultConfig</code> class object.
	 */
	public static DefaultConfig getDefaultConfig(){		
		if(defaultConfig==null){
			defaultConfig = new DefaultConfig();
		}
		return defaultConfig;
	}
	
	/**
	 * Returns the value of the passed configuration name from the loaded config file.
	 * 
	 * @param configuration - whose value is required from the config file.
	 * @return Configuration value of the said configuration or empty string 
	 * if the value is not found.
	 */
	public String getConfigValue(String configuration){
		return configData.getProperty(configuration,"");		
	}
	
	/**
	 * Sets the configuration and its value to the config value.
	 * 
	 * @param configuration - Configuration name to be stored
	 * @param value - Value to be stored.
	 */
	public void setConfigValue(String configuration,String value){
		configData.setProperty(configuration, value);
	}
	
	private void loadConfig() {
		String platform = System.getProperty("platform");
        InputStream configFile;
        String propertiesFile = "taf.properties";
		configData = new Properties();
        try {
			configFile = this.getClass().getClassLoader().getResourceAsStream(propertiesFile);
            configData.load(configFile);
            if (platform == null) {
            	platform = "Android";
			}
			//configData.setProperty("platform", platform);
        } catch (IOException e) {
            throw new ConfigException(
                    "Unable to load default properties file for: " + platform +
                            ".Please make sure that the propertis"+platform+".properties is deifned under src/test/resources folder of your project.\n " +
                            "Or the path to the properties is set to the property 'taf.config'.", e);
        } catch (Exception e) {
            throw new ConfigException("Unable to load file because of: "
                    + e.getMessage());
		}
	}
	

}

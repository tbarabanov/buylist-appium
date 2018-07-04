package org.noname.core.appium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.io.Resources.getResource;

/**
 * @author Timofei B.
 */
public abstract class CapabilitiesProvider {

    private static final Map<String, Supplier<CapabilitiesProvider>> providers = new HashMap<>();

    public static final String AWS = "aws";
    public static final String CONFIGURATION = "configuration";

    static {
        providers.put(AWS, AWSCapabilitiesProvider::new);
        providers.put(CONFIGURATION, ClassPathPropertiesFileCapabilitiesProvider::new);
    }

    protected CapabilitiesProvider() {
    }

    /**
     * @return
     */
    public abstract Capabilities get();

    /**
     * @return
     */
    public static CapabilitiesProvider getInstance(String provider) {
        return checkNotNull(providers.get(provider),
                String.format("Unknown provider type '%s'. Registered providers '%s'",
                        provider, providers.keySet())).get();
    }

    /**
     * returns empty DesiredCapabilities as required by AWS
     */
    private static class AWSCapabilitiesProvider extends CapabilitiesProvider {

        @Override
        public Capabilities get() {
            return new DesiredCapabilities();
        }
    }

    /**
     * returns desired capabilities backed by classPath configuration
     */
    private static class ClassPathPropertiesFileCapabilitiesProvider extends CapabilitiesProvider {

        final Map<String, String> map;

        ClassPathPropertiesFileCapabilitiesProvider() {
            try {
                map = asMap(loadConfiguration("appium.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Unable to read configuration.", e);
            }
        }

        @Override
        public Capabilities get() {
            return new DesiredCapabilities(map);
        }

        /**
         * @param properties
         * @return
         */
        static Map<String, String> asMap(Properties properties) {
            Map<String, String> map = new HashMap<>();
            for (String name : properties.stringPropertyNames()) {
                map.put(name, properties.getProperty(name));
            }
            return map;
        }

        /**
         * @param name
         * @return
         * @throws IOException
         */
        static Properties loadConfiguration(String name) throws IOException {
            Properties properties = new Properties();
            properties.load(getResource(name).openStream());
            return properties;
        }

    }

}

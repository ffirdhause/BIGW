package utility;

import java.io.File;
import java.io.IOException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class commonFunctions {

    public static RequestSpecification reqspec;

    public static RequestSpecification getConfigPropertyAsString(String strPropertyName) throws IOException {

        if (reqspec == null) {
            PrintStream log = new PrintStream(new FileOutputStream("logDetails.txt"));
            reqspec = new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri(getGlobalValue(strPropertyName))
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log)).build();

            return reqspec;
        }
        return reqspec;
    }

    public static String getGlobalValue(String key) throws IOException {

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(
                "src\\test\\java\\resources\\config.properties");
        prop.load(fis);
        return prop.getProperty(key);

    }

}
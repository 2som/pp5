package pl.mswierczynski.pp5.productcatalog;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static String url = "jdbc:mariadb://localhost:3306/pp5";
    private static String user = "root";
    private static String password = "";

    public static Connection connect() {
//        String url = getConfig("url");
//        String user = getConfig("user");
//        String password = getConfig("password");
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Polączono");
            return connection;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getConfig(String key) {
        File starting = new File(System.getProperty("user.dir"));
        String configFile = new File(starting,"dbConfig.json").getPath();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(configFile));
            JSONObject jsonobj = (JSONObject) obj;

            return (String) jsonobj.get(key);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

package hw3.postgres.controllers;

import hw3.postgres.database.DatabaseService;
import hw3.postgres.dto.HealthDto;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.inject.Inject;
import java.sql.SQLException;

@Controller("/health")
public class HealthController {

//    private DatabaseService databaseService;
//
//    public HealthController() {
//        this.databaseService = new DatabaseService();
//    }

    @Get
    public HealthDto getHealth() {
        HealthDto result = new HealthDto();
        result.setStatus("OK");

        return result;
    }

//    @Get("/db")
//    public String getDb() {
//        try {
////            return System.getenv("JDBC_URL");
//
//            return databaseService.executeSelectQuery("SELECT VERSION() AS version",
//                    resultSet -> {
//                        try {
//                            return resultSet.getString("version");
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                        return "";
//                    }).get(0);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}

package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

public class GetUserSpec {
    public static RequestSpecification getUserRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .basePath("/api/users")
            .queryParam("page",2);

    public static ResponseSpecification getUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(BODY)
            .build();

    public static RequestSpecification getWrongUserRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .basePath("/api2/users")
            .queryParam("page",2);


    public static ResponseSpecification getWrongUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .expectContentType("text/html; charset=utf-8")
            .log(STATUS)
            .log(BODY)
            .build();
}

package petstore;

import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço da entidade Pet

    public String readData(String urlData) throws IOException {
        return new String(Files.readAllBytes(Paths.get(urlData)));
    }

    @Test // Anotation que identifica o método ou função como um teste para o TestNG
    public void createPet() throws IOException {
        String jsonBody = readData("data/pet1.json");

        // Sintaxe Gherkin - Dado | Quando | Então - Given | When | Then
        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
        ;

    }
}

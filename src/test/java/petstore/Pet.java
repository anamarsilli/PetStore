package petstore;

import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço da entidade Pet
    Integer petId;

    public String readData(String urlData) throws IOException {
        return new String(Files.readAllBytes(Paths.get(urlData)));
    }

    @Test // Anotation que identifica o método ou função como um teste para o TestNG
    public void createPet() throws IOException {
        String jsonBody = readData("src/test/resources/pet1.json");
        petId =

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
                .body("name", is("Snoopy"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
                .body("tags.name", contains("lovely", "iterasys"))
        .extract()
                .path("id")
        ;
       System.out.println("O id do pet é " + petId.toString());
    }

    @Test (priority = 1)
    public void getPet(){

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId.toString())
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Snoopy"))
        ;
    }

    @Test(priority = 2)
    public void updatePet() throws IOException{
        String jsonBody = readData("src/test/resources/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Snoopy"))
                .body("status", is("sold"))
        ;
    }

    @Test(priority = 3)
    public void deletePet(){

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + '/' + petId.toString())
        .then()
                .log().all()
                .statusCode(200)
        ;
    }
}


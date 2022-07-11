package code.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class DealMocks {

    public static void setupMockApplicationResponse(WireMockServer mockService) throws IOException {
        final Path conveyorClientResponseOffers = Paths
                .get("src/test/resources/dealClient/LoanOffersDTOList.json")
                .normalize()
                .toAbsolutePath();
        mockService.stubFor(WireMock.post(WireMock.urlEqualTo("/api/deal/application"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(Files.readString(conveyorClientResponseOffers))));

    }

    public static void setupMockOfferResponse(WireMockServer mockService) throws IOException {
        mockService.stubFor(WireMock.put(WireMock.urlEqualTo("/api/deal/offer"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.TEXT_PLAIN_VALUE)));


    }

}

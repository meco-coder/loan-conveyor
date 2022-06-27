package code.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ConveyorMocks {

    public static void setupMockOffersResponse(WireMockServer mockService) throws IOException {
        final Path conveyorClientResponseOffers = Paths
                .get("src/test/resources/conveyorClient/getConveyorClientResponseOffers.json")
                .normalize()
                .toAbsolutePath();
        mockService.stubFor(WireMock.post(WireMock.urlEqualTo("/api/conveyor/offers"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(Files.readString(conveyorClientResponseOffers))));

    }

    public static void setupMockOffersResponseFail(WireMockServer mockService) throws IOException {
        final Path conveyorClientResponseOffersFail = Paths
                .get("src/test/resources/conveyorClient/getConveyorClientResponseOffersFail.txt")
                .normalize()
                .toAbsolutePath();
        mockService.stubFor(WireMock.post(WireMock.urlEqualTo("/api/conveyor/offers"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withHeader("Content-Type", MediaType.TEXT_PLAIN_VALUE)
                        .withBody(Files.readString(conveyorClientResponseOffersFail))));

    }

    public static void setupMockCalculateResponse(WireMockServer mockService) throws IOException {
        final Path conveyorClientResponseOffers = Paths
                .get("src/test/resources/conveyorClient/getConveyorClientResponseCalculate.json")
                .normalize()
                .toAbsolutePath();
        mockService.stubFor(WireMock.post(WireMock.urlEqualTo("/api/conveyor/calculation"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(Files.readString(conveyorClientResponseOffers))));

    }

}

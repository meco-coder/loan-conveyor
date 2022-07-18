package code;

import code.config.SpringConfig;
import code.config.WireMockConfig;
import code.dto.LoanOfferDTO;
import code.mock.DealMocks;
import code.util.TestApplicationUtils;
import code.util.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static code.config.SpringConfig.TEST_PROFILE;
import static code.util.TestUtils.asJson;
import static code.util.TestUtils.fromJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfig.class)
@ContextConfiguration(classes = {WireMockConfig.class})
class ApplicationTests {

    @Autowired
    private WireMockServer mockDealService;
    @Autowired
    private TestApplicationUtils testApplicationUtils;
    @Autowired
    private TestUtils testUtils;

    @Test
    public void testGetLoanOfferDTOList() throws Exception {
        DealMocks.setupMockApplicationResponse(mockDealService);
        final var responseValidLoanApplicationRequestDTO = testUtils
                .perform(post("/application")
                        .contentType(APPLICATION_JSON)
                        .content(asJson(testApplicationUtils.getTestValidLoanApplicationRequestDTO())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final List<LoanOfferDTO> loanOfferDTOListFromResponse = fromJson(responseValidLoanApplicationRequestDTO
                .getContentAsString(), new TypeReference<>() {
        });

        assertThat(loanOfferDTOListFromResponse).hasSize(4);
        assertThat(loanOfferDTOListFromResponse).isEqualTo(testApplicationUtils.getTestLoanOfferDTOList());
        final int hashLoanOfferDTOListFromResponse = loanOfferDTOListFromResponse.hashCode();
        final int hashTestLoanOfferDTOList = testApplicationUtils.getTestLoanOfferDTOList().hashCode();
        assertThat(hashLoanOfferDTOListFromResponse).isEqualTo(hashTestLoanOfferDTOList);

    }

    @Test
    public void testGetLoanOfferDTOListFail() throws Exception {
        final var responseInvalidLoanApplicationRequestDTO = testUtils
                .perform(post("/application")
                        .contentType(APPLICATION_JSON)
                        .content(asJson(testApplicationUtils.getTestInvalidLoanApplicationRequestDTO())))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        assertThat(responseInvalidLoanApplicationRequestDTO.getContentAsString())
                .contains("passportNumber: Need 6 numbers",
                        "lastName: size from 2 to 30 characters",
                        "email: invalid email",
                        "firstName: size from 2 to 30 characters",
                        "middleName: size from 2 to 30 characters",
                        "amount: at least 10000",
                        "term: at least 6",
                        "passportSeries: Need 4 numbers");

    }

    @Test
    public void testGetOffer() throws Exception {
        DealMocks.setupMockOfferResponse(mockDealService);
        testUtils
                .perform(post("/application/offer")
                        .contentType(APPLICATION_JSON)
                        .content(asJson(testApplicationUtils.getTestLoanOfferDTO())))
                .andExpect(status().isOk())
                .andReturn();

    }


}

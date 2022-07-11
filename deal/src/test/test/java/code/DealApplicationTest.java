package code;

import code.config.SpringConfig;
import code.config.WireMockConfig;
import code.dto.LoanOfferDTO;
import code.mock.ConveyorMocks;
import code.model.Application;
import code.model.Client;
import code.model.Credit;
import code.repository.ApplicationRepository;
import code.repository.ClientRepository;
import code.repository.CreditRepository;
import code.utils.TestModelUtils;
import code.utils.TestUtils;
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
import static code.utils.TestUtils.asJson;
import static code.utils.TestUtils.fromJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(TEST_PROFILE)
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringConfig.class)
@ContextConfiguration(classes = {WireMockConfig.class})
class DealApplicationTest {

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private WireMockServer mockConveyorService;

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private TestModelUtils testModelUtils;


    @Test
    public void testGetLoanOfferDTOList() throws Exception {
        ConveyorMocks.setupMockOffersResponse(mockConveyorService);
        final var response = testUtils
                .perform(post("/api/deal/application")
                        .contentType(APPLICATION_JSON)
                        .content(asJson(testUtils.getTestLoanApplicationRequestDTO())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        List<LoanOfferDTO> loanOfferDTOList = fromJson(response.getContentAsString(), new TypeReference<>() {
        });
        List<Client> clients = clientRepository.findAll();
        List<Application> applications = applicationRepository.findAll();
        assertEquals(testUtils.getTestLoanOfferDTOList(), loanOfferDTOList);
        assertEquals(testUtils.getTestLoanOfferDTOList().get(0).hashCode(), loanOfferDTOList.get(0).hashCode());
        assertEquals(2, clients.size());
        assertEquals(2, applications.size());
    }

    @Test
    public void testGetLoanOfferDTOListFail() throws Exception {
        ConveyorMocks.setupMockOffersResponseFail(mockConveyorService);
        final var response = testUtils
                .perform(post("/api/deal/application")
                        .contentType(APPLICATION_JSON)
                        .content(asJson(testUtils.getTestLoanApplicationRequestDTOFail())))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        String failResponse = response.getContentAsString();
        assertEquals(testUtils.getTestLoanOfferDTOListFail(), failResponse);
        List<Client> clients = clientRepository.findAll();
        List<Application> applications = applicationRepository.findAll();
        assertEquals(2, clients.size());
        assertEquals(2, applications.size());
    }

    @Test
    public void testOffer() throws Exception {
        applicationRepository.save(testModelUtils.getApplication());
        List<Application> applications = applicationRepository.findAll();
        final var response = testUtils
                .perform(put("/api/deal/offer")
                        .contentType(APPLICATION_JSON)
                        .content(asJson(testUtils.getTestLoanOfferDTO())))
                .andExpect(status().isOk());
        final Application application = applicationRepository.findById(testUtils.getTestLoanOfferDTO()
                .getApplicationId()).get();
        assertEquals(application.getAppliedOffer().getApplicationId(),
                testUtils.getTestLoanOfferDTO().getApplicationId());
        assertEquals(application.getAppliedOffer().getIsInsuranceEnabled(),
                testUtils.getTestLoanOfferDTO().getIsInsuranceEnabled());
        assertEquals(application.getAppliedOffer().getRate(), testUtils.getTestLoanOfferDTO().getRate());
        assertEquals(application.getAppliedOffer().getTerm(), testUtils.getTestLoanOfferDTO().getTerm());
        assertEquals(application.getAppliedOffer().getMonthlyPayment(),
                testUtils.getTestLoanOfferDTO().getMonthlyPayment());
        assertEquals(application.getAppliedOffer().getIsSalaryClient(),
                testUtils.getTestLoanOfferDTO().getIsSalaryClient());
        assertEquals(application.getAppliedOffer().getTotalAmount(),
                testUtils.getTestLoanOfferDTO().getTotalAmount());
    }

    @Test
    public void testCalculate() throws Exception {
        ConveyorMocks.setupMockCalculateResponse(mockConveyorService);
        List<Application> applications = applicationRepository.findAll();
        final var response = testUtils
                .perform(put("/api/deal/calculate/{id}", applications.get(0).getId())
                        .contentType(APPLICATION_JSON)
                        .content(asJson(testUtils.getFinishRegistrationRequestDTO())))
                .andExpect(status().isOk());

        List<Credit> credits = creditRepository.findAll();
        assertEquals(1, credits.size());
    }

    @Test
    public void testCalculateFail() throws Exception {
        List<Application> applications = applicationRepository.findAll();
        final var response = testUtils
                .perform(put("/api/deal/calculate/{id}", applications.get(0).getId())
                        .contentType(APPLICATION_JSON)
                        .content(asJson(testUtils.getFinishRegistrationRequestDTOFail())))
                .andExpect(status().isBadRequest());
    }


}

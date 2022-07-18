package code;

import code.dto.CreditDTO;
import code.dto.LoanOfferDTO;
import code.util.TestCalculationUtils;
import code.util.TestOfferUtils;
import code.util.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static code.util.TestUtils.asJson;
import static code.util.TestUtils.fromJson;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ConveyorApplicationTests {

    @Autowired
    private TestCalculationUtils testCalculationUtils;

    @Autowired
    private TestOfferUtils testOfferUtils;

    @Autowired
    private TestUtils testUtils;

    @Test
    public void testGetLoanOfferDTOList() throws Exception {
        final var responseValidLoanApplicationRequestDTO = testUtils
                .perform(post("/conveyor/offers")
                        .contentType(APPLICATION_JSON)
                        .content(asJson(testOfferUtils.getTestValidLoanApplicationRequestDTO())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final List<LoanOfferDTO> loanOfferDTOListFromResponse = fromJson(responseValidLoanApplicationRequestDTO
                .getContentAsString(), new TypeReference<>() {
        });

        assertThat(loanOfferDTOListFromResponse).hasSize(4);
        assertThat(loanOfferDTOListFromResponse).isEqualTo(testOfferUtils.getTestLoanOfferDTOList());
        final int hashLoanOfferDTOListFromResponse = loanOfferDTOListFromResponse.hashCode();
        final int hashTestLoanOfferDTOList = testOfferUtils.getTestLoanOfferDTOList().hashCode();
        assertThat(hashLoanOfferDTOListFromResponse).isEqualTo(hashTestLoanOfferDTOList);

    }


    @Test
    public void testGetCreditDTO() throws Exception {
        final var responseValidScoringDataDTO = testUtils
                .perform(post("/conveyor/calculation")
                        .contentType(APPLICATION_JSON)
                        .content(asJson(testCalculationUtils.getTestValidScoringDataDTO())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final CreditDTO responseCreditDTO = fromJson(responseValidScoringDataDTO
                .getContentAsString(), new TypeReference<>() {
        });

        assertThat(responseCreditDTO.getAmount()).isEqualTo(testCalculationUtils.getTestCreditDTO().getAmount());
        assertThat(responseCreditDTO.getPsk()).isEqualTo(testCalculationUtils.getTestCreditDTO().getPsk());
        assertThat(responseCreditDTO.getRate()).isEqualTo(testCalculationUtils.getTestCreditDTO().getRate());
        assertThat(responseCreditDTO.getMonthlyPayment()).isEqualTo(testCalculationUtils.getTestCreditDTO()
                .getMonthlyPayment());
        assertThat(responseCreditDTO.getTerm()).isEqualTo(testCalculationUtils.getTestCreditDTO().getTerm());
        assertThat(responseCreditDTO.getIsInsuranceEnabled()).isEqualTo(testCalculationUtils.getTestCreditDTO()
                .getIsInsuranceEnabled());
        assertThat(responseCreditDTO.getIsSalaryClient()).isEqualTo(testCalculationUtils.getTestCreditDTO()
                .getIsSalaryClient());
        assertThat(responseCreditDTO.getPaymentSchedule().size())
                .isEqualTo(testCalculationUtils.getTestCreditDTO().getPaymentSchedule().size());
    }


    @Test
    public void testGetCreditDTO2() throws Exception {
        final var responseValidScoringDataDTO2 = testUtils
                .perform(post("/conveyor/calculation")
                        .contentType(APPLICATION_JSON)
                        .content(asJson(testCalculationUtils.getTestValidScoringDataDTO2())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final CreditDTO responseCreditDTO2 = fromJson(responseValidScoringDataDTO2
                .getContentAsString(), new TypeReference<>() {
        });

        assertThat(responseCreditDTO2.getAmount()).isEqualTo(testCalculationUtils.getTestCreditDTO2().getAmount());
        assertThat(responseCreditDTO2.getPsk()).isEqualTo(testCalculationUtils.getTestCreditDTO2().getPsk());
        assertThat(responseCreditDTO2.getRate()).isEqualTo(testCalculationUtils.getTestCreditDTO2().getRate());
        assertThat(responseCreditDTO2.getMonthlyPayment()).isEqualTo(testCalculationUtils.getTestCreditDTO2()
                .getMonthlyPayment());
        assertThat(responseCreditDTO2.getTerm()).isEqualTo(testCalculationUtils.getTestCreditDTO2().getTerm());
        assertThat(responseCreditDTO2.getIsInsuranceEnabled()).isEqualTo(testCalculationUtils.getTestCreditDTO2()
                .getIsInsuranceEnabled());
        assertThat(responseCreditDTO2.getIsSalaryClient()).isEqualTo(testCalculationUtils.getTestCreditDTO2()
                .getIsSalaryClient());
        assertThat(responseCreditDTO2.getPaymentSchedule().size())
                .isEqualTo(testCalculationUtils.getTestCreditDTO2().getPaymentSchedule().size());
    }

    @Test
    public void testGetCreditDTOFail() throws Exception {
        final var responseInvalidScoringDataDTO = testUtils
                .perform(post("/conveyor/calculation")
                        .contentType(APPLICATION_JSON)
                        .content(asJson(testCalculationUtils.getTestInvalidScoringDataDTO())))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        assertThat(responseInvalidScoringDataDTO.getContentAsString())
                .contains("birthdate: Denied. Your age is under 20 or over 60",
                        "employment.workExperienceTotal: Denied because your total work experience is less than 12 months",
                        "employment.employmentStatus: Denied because you are unemployed",
                        "employment.workExperienceCurrent: Denied because your current work experience is less than 3 months",
                        "scoringDataDTO: Denied. 20 of your salaries are less than the loan amount");


    }
}

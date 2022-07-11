package code.utils;

import code.dto.FinishRegistrationRequestDTO;
import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;
import code.repository.ApplicationRepository;
import code.repository.ApplicationStatusHistoryRepository;
import code.repository.ClientRepository;
import code.repository.PassportRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationStatusHistoryRepository applicationStatusHistoryRepository;
    @Autowired
    private PassportRepository passportRepository;


    public LoanApplicationRequestDTO getTestLoanApplicationRequestDTO() throws IOException {
        final Path loanApplicationRequestDTO = Paths
                .get("src/test/resources/dto-test/LoanApplicationRequestDTO.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(loanApplicationRequestDTO), new TypeReference<>() {
        });
    }

    public LoanApplicationRequestDTO getTestLoanApplicationRequestDTOFail() throws IOException {
        final Path loanApplicationRequestDTOFail = Paths
                .get("src/test/resources/dto-test/LoanApplicationRequestDTOFail.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(loanApplicationRequestDTOFail), new TypeReference<>() {
        });
    }

    public List<LoanOfferDTO> getTestLoanOfferDTOList() throws IOException {
        final Path conveyorClientResponseOffers = Paths
                .get("src/test/resources/conveyorClient/getConveyorClientResponseOffers.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(conveyorClientResponseOffers), new TypeReference<>() {
        });
    }

    public String getTestLoanOfferDTOListFail() throws IOException {
        final Path conveyorClientResponseOffers = Paths
                .get("src/test/resources/conveyorClient/getConveyorClientResponseOffersFail.txt")
                .normalize()
                .toAbsolutePath();
        return Files.readString(conveyorClientResponseOffers);
    }

    public LoanOfferDTO getTestLoanOfferDTO() throws IOException {
        final Path loanOfferDTO = Paths
                .get("src/test/resources/dto-test/LoanOfferDTO.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(loanOfferDTO), new TypeReference<>() {
        });
    }

    public FinishRegistrationRequestDTO getFinishRegistrationRequestDTO() throws IOException {
        final Path finishRegistrationRequestDTO = Paths
                .get("src/test/resources/dto-test/FinishRegistrationRequestDTO.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(finishRegistrationRequestDTO), new TypeReference<>() {
        });
    }

    public String getFinishRegistrationRequestDTOFail() throws IOException {
        final Path finishRegistrationRequestDTOFail = Paths
                .get("src/test/resources/dto-test/FinishRegistrationRequestDTOFail.json")
                .normalize()
                .toAbsolutePath();
        return Files.readString(finishRegistrationRequestDTOFail);
    }


    public ResultActions perform(final MockHttpServletRequestBuilder request) throws Exception {
        return mockMvc.perform(request);
    }

    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    public static String asJson(final Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T fromJson(final String json, final TypeReference<T> to) throws JsonProcessingException {
        return MAPPER.readValue(json, to);
    }
}

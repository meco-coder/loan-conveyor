package code.util;


import code.dto.LoanApplicationRequestDTO;
import code.dto.LoanOfferDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static code.util.TestUtils.fromJson;


@Component
public class TestApplicationUtils {


    public LoanApplicationRequestDTO getTestValidLoanApplicationRequestDTO() throws IOException {
        final Path validLoanApplicationRequestDTO = Paths
                .get("src/test/resources/dto-test/validLoanApplicationRequestDTO.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(validLoanApplicationRequestDTO), new TypeReference<>() {
        });
    }

    public LoanApplicationRequestDTO getTestInvalidLoanApplicationRequestDTO() throws IOException {
        final Path invalidLoanApplicationRequestDTO = Paths
                .get("src/test/resources/dto-test/invalidLoanApplicationRequestDTO.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(invalidLoanApplicationRequestDTO), new TypeReference<>() {
        });
    }

    public List<LoanOfferDTO> getTestLoanOfferDTOList() throws IOException {
        final Path loanOfferDTOList = Paths
                .get("src/test/resources/dto-test/LoanOffersDTOList.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(loanOfferDTOList), new TypeReference<>() {
        });
    }

    public LoanOfferDTO getTestLoanOfferDTO() throws IOException {
        final Path loanOfferDTO = Paths
                .get("src/test/resources/dto-test/LoanOfferDTO.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(loanOfferDTO), new TypeReference<>() {
        });
    }
}

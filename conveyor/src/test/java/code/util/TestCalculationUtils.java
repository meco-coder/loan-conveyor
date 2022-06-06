package code.util;

import code.dto.CreditDTO;
import code.dto.ScoringDataDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static code.util.TestUtils.fromJson;

@Component
public class TestCalculationUtils {

    public ScoringDataDTO getTestValidScoringDataDTO() throws IOException {
        final Path validScoringDataDTO = Paths
                .get("src/test/resources/dto-test/validScoringDataDTO.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(validScoringDataDTO), new TypeReference<>() {
        });
    }

    public ScoringDataDTO getTestInvalidScoringDataDTO() throws IOException {
        final Path invalidScoringDataDTO = Paths
                .get("src/test/resources/dto-test/invalidScoringDataDTO.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(invalidScoringDataDTO), new TypeReference<>() {
        });
    }

    public CreditDTO getTestCreditDTO() throws IOException {
        final Path CreditDTO = Paths
                .get("src/test/resources/dto-test/CreditDTO.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(CreditDTO), new TypeReference<>() {
        });
    }

    public CreditDTO getTestCreditDTO2() throws IOException {
        final Path CreditDTO2 = Paths
                .get("src/test/resources/dto-test/CreditDTO2.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(CreditDTO2), new TypeReference<>() {
        });
    }


    public ScoringDataDTO getTestValidScoringDataDTO2() throws IOException {
        final Path validScoringDataDTO2 = Paths
                .get("src/test/resources/dto-test/validScoringDataDTO2.json")
                .normalize()
                .toAbsolutePath();
        return fromJson(Files.readString(validScoringDataDTO2), new TypeReference<>() {
        });
    }
}

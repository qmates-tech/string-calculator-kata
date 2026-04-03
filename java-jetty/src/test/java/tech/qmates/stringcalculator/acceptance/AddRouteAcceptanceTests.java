package tech.qmates.stringcalculator.acceptance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tech.qmates.stringcalculator.Application;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddRouteAcceptanceTests {

    private static Thread serverThread;
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    @BeforeAll
    static void startServer() throws Exception {
        serverThread = new Thread(() -> {
            try {
                Application.main(new String[]{});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
        Thread.sleep(2000);
    }

    @AfterAll
    static void stopServer() {
        if (serverThread != null) {
            serverThread.interrupt();
        }
    }

    @Test
    void emptyStringReturnsZero() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString(""))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
        assertEquals(200, response.statusCode());
    }


    @Test
    void singleZeroReturnsZero() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("0"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void singleDigitReturnsItself() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("5", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void twoDigitNumberReturnsItself() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("42"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("42", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void largeIntegerReturnsItself() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("999"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("999", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void veryLargeIntegerReturnsItself() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("123456789"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("123456789", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void singleDecimalReturnsItself() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1.1"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1.1", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void singleDecimalWithZeroIntegerPart() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("0.5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0.5", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void singleDecimalWithMultipleDecimalPlaces() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1.234"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1.234", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void singleDecimalWithManyDecimalPlaces() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("3.14159"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("3.14159", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void twoZerosReturnsZero() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("0,0"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void oneAndZeroReturnsOne() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,0"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void zeroAndOneReturnsOne() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("0,1"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void oneAndTwoReturnsThree() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("3", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void twoAndThreeReturnsFive() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("2,3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("5", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void tenAndTwentyReturnsThirty() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("10,20"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("30", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void largeIntegersSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("100,200"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("300", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void twoDecimalsSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1.1,2.2"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("3.3", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void twoSmallDecimalsSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("0.1,0.2"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0.3", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void integerAndDecimalSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2.5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("3.5", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void decimalAndIntegerSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("2.5,1"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("3.5", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void twoDecimalsWithDifferentPrecision() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1.5,2.25"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("3.75", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void sumResultingInWholeNumber() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1.5,2.5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("4", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void largeDecimalsSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("99.99,0.01"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("100", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void threeIntegersSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2,3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("6", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void fourIntegersSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2,3,4"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("10", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void fiveIntegersSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2,3,4,5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("15", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void sixIntegersSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2,3,4,5,6"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("21", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void tenOnesReturnsTen() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,1,1,1,1,1,1,1,1,1"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("10", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void manyZerosReturnsZero() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("0,0,0,0,0"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void threeDecimalsSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1.1,2.2,3.3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("6.6", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void fourDecimalsSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("0.1,0.2,0.3,0.4"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void manyDecimalsSum() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1.5,2.5,3.5,4.5,5.5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("17.5", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void mixedIntegersAndDecimalsThreeNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2.5,3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("6.5", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void mixedIntegersAndDecimalsFourNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2.5,3,4.5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("11", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void mixedIntegersAndDecimalsFiveNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("10,20.5,30,40.5,50"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("151", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void allZerosWithDifferentFormats() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("0,0.0,0,0.00"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void largeNumberOfArguments() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("120", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void manyLargeNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("100,200,300,400,500"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1500", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void manySmallDecimals() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("0.01,0.02,0.03,0.04,0.05"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0.15", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void sumWithVaryingDecimalPrecision() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1.1,2.22,3.333,4.4444"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("11.0974", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void consecutiveNumbersOneToTwenty() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("210", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void largeRoundNumberFormatting() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1000"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1000", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void veryLargeRoundNumberFormatting() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("10000"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("10000", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void sumResultingInLargeRoundNumber() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("500,500"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1000", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void sumResultingInTenThousand() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("5000,5000"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("10000", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void multipleNumbersSumToLargeRound() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("250,250,250,250"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1000", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void sumOfDecimalsToLargeRound() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("999.5,0.5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1000", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void hundredThousandFormatting() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("100000"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("100000", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void millionFormatting() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1000000"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1000000", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void sumToMillion() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("500000,500000"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1000000", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void decimalSumToThousand() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("333.33,333.33,333.34"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1000", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void manySmallDecimalsToThousand() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("100.1,100.1,100.1,100.1,100.1,100.1,100.1,100.1,100.1,99.1"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1000", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void getRequestShouldReturn405() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .GET()
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals(405, response.statusCode());
    }

    @Test
    void putRequestShouldReturn405() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .PUT(BodyPublishers.ofString("1"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals(405, response.statusCode());
    }

    @Test
    void deleteRequestShouldReturn405() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .DELETE()
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals(405, response.statusCode());
    }

}

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
        serverThread = asyncStartJettyServerThread();
    }

    @AfterAll
    static void stopServer() {
        serverThread.interrupt();
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
    void veryLargeIntegerIsIgnored() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("123456789"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
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
    void numberAboveThousandIsIgnored() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("10000"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
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
    void numbersAboveThousandBothIgnored() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("5000,5000"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
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
    void hundredThousandIsIgnored() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("100000"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void millionIsIgnored() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1000000"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void twoMillionsAreIgnored() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("500000,500000"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
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

    @Test
    void newlineSeparatorTwoNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1\n2"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("3", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void newlineSeparatorThreeNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1\n2\n3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("6", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void newlineSeparatorFiveNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1\n2\n3\n4\n5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("15", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void newlineThenCommaSeparator() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1\n2,3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("6", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void commaThenNewlineSeparator() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2\n3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("6", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void mixedNewlineAndCommaSeparatorsManyNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2\n3,4\n5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("15", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void newlineSeparatorWithZeros() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("0\n0\n0"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void newlineSeparatorWithDecimals() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1.5\n2.5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("4", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void newlineSeparatorWithDecimalsThreeNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1.1\n2.2\n3.3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("6.6", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void mixedSeparatorsWithDecimals() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1.5,2.5\n3.5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("7.5", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void customDelimiterSemicolon() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//;\n1;2"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("3", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void customDelimiterSemicolonThreeNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//;\n1;2;3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("6", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void customDelimiterDash() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//-\n1-2"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("3", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void customDelimiterPipe() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//|\n1|2|3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("6", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void customDelimiterAsterisk() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//*\n2*3*4"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("9", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void customDelimiterDot() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//.\n1.2"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("3", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void customDelimiterWithSingleNumber() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//;\n5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("5", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void customDelimiterWithZero() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//;\n0;0;0"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void customDelimiterManyNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//;\n1;2;3;4;5;6;7;8;9;10"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("55", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void customDelimiterHashSymbol() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//#\n10#20#30"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("60", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void customDelimiterWithLargeNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//;\n100;200;300"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("600", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void singleNegativeReturnsError() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("-1"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("negatives not allowed: -1", response.body());
        assertEquals(400, response.statusCode());
    }

    @Test
    void negativeWithOtherNumbersReturnsError() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,4,-1"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("negatives not allowed: -1", response.body());
        assertEquals(400, response.statusCode());
    }

    @Test
    void negativeAtBeginningReturnsError() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("-1,2,3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("negatives not allowed: -1", response.body());
        assertEquals(400, response.statusCode());
    }

    @Test
    void multipleNegativesAllListedInError() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("-1,-2"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("negatives not allowed: -1, -2", response.body());
        assertEquals(400, response.statusCode());
    }

    @Test
    void multipleNegativesMixedWithPositivesAllListedInError() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("2,-4,-5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("negatives not allowed: -4, -5", response.body());
        assertEquals(400, response.statusCode());
    }

    @Test
    void manyNegativesAllListedInError() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,2,-3,-4,-5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("negatives not allowed: -3, -4, -5", response.body());
        assertEquals(400, response.statusCode());
    }

    @Test
    void negativeDecimalReturnsError() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("-1.5"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("negatives not allowed: -1.5", response.body());
        assertEquals(400, response.statusCode());
    }

    @Test
    void negativeWithNewlineSeparatorReturnsError() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("-1\n2"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("negatives not allowed: -1", response.body());
        assertEquals(400, response.statusCode());
    }

    @Test
    void negativeWithCustomDelimiterReturnsError() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//;\n1;-2;3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("negatives not allowed: -2", response.body());
        assertEquals(400, response.statusCode());
    }

    @Test
    void numberExactlyOneThousandIsNotIgnored() throws Exception {
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
    void numberJustAboveThousandIsIgnored() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1001"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void bigNumberIgnoredKeepsSmallNumber() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("2,1001"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("2", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void thousandKeptAndJustAboveIgnored() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1000,1001"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1000", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void bigNumberIgnoredManyNumbers() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1,1001,2"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("3", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void multipleBigNumbersAllIgnored() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1001,2000,9999"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("0", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void bigNumberIgnoredWithNewlineSeparator() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1001\n2"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("2", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void bigNumberIgnoredWithCustomDelimiter() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("//;\n2;1001;3"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("5", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void specExampleTwoPlusOneThousandOneEqualsTwo() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("2,1001"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("2", response.body());
        assertEquals(200, response.statusCode());
    }

    private static Thread asyncStartJettyServerThread() throws InterruptedException {
        var jettyServerThread = new Thread(() -> {
            try {
                Application.main(new String[]{});
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        jettyServerThread.setDaemon(true);
        jettyServerThread.start();
        Thread.sleep(2000);
        return jettyServerThread;
    }

}

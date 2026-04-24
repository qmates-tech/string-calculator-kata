package tech.qmates.stringcalculator;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddServlet extends HttpServlet {

    private static final BigDecimal MAX_ALLOWED = new BigDecimal("1000");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = new String(req.getInputStream().readAllBytes());
        try {
            String result = "0";
            if (!body.isEmpty()) {
                String delimiter = "[,\n]";
                String numbersPart = body;
                if (body.startsWith("//")) {
                    int newlineIndex = body.indexOf("\n");
                    String customDelimiter = body.substring(2, newlineIndex);
                    delimiter = Pattern.quote(customDelimiter);
                    numbersPart = body.substring(newlineIndex + 1);
                }
                String[] parts = numbersPart.split(delimiter);
                List<BigDecimal> negatives = new ArrayList<>();
                BigDecimal sum = BigDecimal.ZERO;
                for (String part : parts) {
                    BigDecimal number = new BigDecimal(part.trim());
                    if (number.compareTo(BigDecimal.ZERO) < 0) {
                        negatives.add(number);
                    } else if (number.compareTo(MAX_ALLOWED) <= 0) {
                        sum = sum.add(number);
                    }
                }
                if (!negatives.isEmpty()) {
                    String list = negatives.stream()
                        .map(BigDecimal::toPlainString)
                        .collect(Collectors.joining(", "));
                    throw new IllegalArgumentException("negatives not allowed: " + list);
                }
                result = formatResult(sum);
            }

            simulateComplexSlowOperationToDoBeforeResponse();
            resp.getWriter().print(result);
            resp.setContentType("text/plain");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().print(e.getMessage());
        }
    }

    private String formatResult(BigDecimal value) {
        value = value.stripTrailingZeros();
        if (value.scale() <= 0) {
            return value.setScale(0, RoundingMode.FLOOR).toPlainString();
        }
        return value.toPlainString();
    }

    private void simulateComplexSlowOperationToDoBeforeResponse() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

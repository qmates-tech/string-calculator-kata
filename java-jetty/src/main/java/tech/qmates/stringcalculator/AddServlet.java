package tech.qmates.stringcalculator;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

public class AddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = new String(req.getInputStream().readAllBytes());
        String result = add(body);
        resp.setContentType("text/plain");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(result);
    }

    private String add(String numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return "0";
        }

        String delimiter = "[,\n]";
        String numbersPart = numbers;

        if (numbers.startsWith("//")) {
            int newlineIndex = numbers.indexOf("\n");
            String customDelimiter = numbers.substring(2, newlineIndex);
            delimiter = Pattern.quote(customDelimiter);
            numbersPart = numbers.substring(newlineIndex + 1);
        }

        String[] parts = numbersPart.split(delimiter);
        BigDecimal sum = BigDecimal.ZERO;

        for (String part : parts) {
            sum = sum.add(new BigDecimal(part.trim()));
        }

        return formatResult(sum);
    }

    private String formatResult(BigDecimal value) {
        value = value.stripTrailingZeros();
        if (value.scale() <= 0) {
            return value.setScale(0, RoundingMode.FLOOR).toPlainString();
        }
        return value.toPlainString();
    }
}

package com.blumbit.compras_ventas.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * Filter that converts bracket notation parameter names (e.g. documentos[0][detalle])
 * to dot notation (e.g. documentos[0].detalle) so that Spring's @ModelAttribute
 * data binding can correctly resolve nested properties from FormData.
 */
@Component
public class BracketNotationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest httpRequest) {
            chain.doFilter(new BracketToDotRequestWrapper(httpRequest), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private static class BracketToDotRequestWrapper extends HttpServletRequestWrapper {

        private final Map<String, String[]> convertedParams;

        public BracketToDotRequestWrapper(HttpServletRequest request) {
            super(request);
            convertedParams = convertParameterNames(request.getParameterMap());
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return Collections.unmodifiableMap(convertedParams);
        }

        @Override
        public String getParameter(String name) {
            String[] values = convertedParams.get(name);
            return values != null && values.length > 0 ? values[0] : null;
        }

        @Override
        public String[] getParameterValues(String name) {
            return convertedParams.get(name);
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(convertedParams.keySet());
        }

        /**
         * Converts parameter names from bracket notation to dot notation.
         * e.g. "documentos[0][detalle]" -> "documentos[0].detalle"
         * 
         * Only converts the second-level brackets (after an indexed access) to dot notation.
         * First-level brackets like [0] are preserved since Spring understands list indexing.
         */
        private static Map<String, String[]> convertParameterNames(Map<String, String[]> originalParams) {
            Map<String, String[]> result = new LinkedHashMap<>();
            for (Map.Entry<String, String[]> entry : originalParams.entrySet()) {
                String convertedName = convertBracketsToDots(entry.getKey());
                result.put(convertedName, entry.getValue());
            }
            return result;
        }

        /**
         * Converts bracket notation to dot notation for property access.
         * "items[0][name]" -> "items[0].name"
         * "items[0][nested][value]" -> "items[0].nested.value"
         * "simple" -> "simple" (unchanged)
         * "items[0]" -> "items[0]" (unchanged, no property access)
         */
        private static String convertBracketsToDots(String paramName) {
            // Convert ][propertyName] -> .propertyName
            // e.g. documentos[0][detalle] -> documentos[0].detalle
            // e.g. documentos[0][nested][value] -> documentos[0].nested.value
            // Preserves numeric indexes: roles[0] stays as roles[0]
            StringBuilder result = new StringBuilder();
            int i = 0;
            while (i < paramName.length()) {
                if (paramName.charAt(i) == '[') {
                    int close = paramName.indexOf(']', i);
                    if (close == -1) {
                        result.append(paramName.substring(i));
                        break;
                    }
                    String content = paramName.substring(i + 1, close);
                    if (content.matches("\\d+")) {
                        // Numeric index — keep brackets: [0]
                        result.append('[').append(content).append(']');
                    } else {
                        // Property name — convert to dot notation: .propertyName
                        result.append('.').append(content);
                    }
                    i = close + 1;
                } else {
                    result.append(paramName.charAt(i));
                    i++;
                }
            }
            return result.toString();
        }
    }
}

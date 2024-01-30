package util.resp;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseHandler {
    public static void sendResponse(HttpServletResponse resp, int code, String message) throws IOException {
        resp.setContentType("application/json");
        resp.setStatus(code);
        resp.getWriter().println(message);
        resp.getWriter().close();
    }
}
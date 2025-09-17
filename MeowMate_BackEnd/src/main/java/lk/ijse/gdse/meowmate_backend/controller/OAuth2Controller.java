package lk.ijse.gdse.meowmate_backend.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Returns a tiny HTML/JS payload that stores the JWT into localStorage and redirects
 * to the frontend application. This is used as the target of the OAuth2 success redirect.
 */
@RestController
public class OAuth2Controller {

    @GetMapping(path = "/oauth2/success", produces = MediaType.TEXT_HTML_VALUE)
    public String success(@RequestParam("token") String token) {
        // Frontend URL - adjust if your frontend serves from a different path
//        String frontend = "http://localhost:5500/MeowMate_FrontEnd/pages/DashBoard.html";
        String frontend = "http://localhost:5501/MeowMate_FrontEnd/pages/DashBoard.html";

        // The page will store token -> accessToken in localStorage and redirect
        return "<!doctype html>\n" +
                "<html><head><meta charset=\"utf-8\"><title>Login Success</title></head>\n" +
                "<body>\n" +
                "<script>\n" +
                "  try {\n" +
                "    localStorage.setItem('accessToken', '" + token + "');\n" +
                "    // Decode token to get role and userId if needed by frontend\n" +
                "  } catch(e) {}\n" +
                "  window.location.replace('" + frontend + "');\n" +
                "</script>\n" +
                "</body></html>";
    }

    @GetMapping(path = "/oauth2/failure", produces = MediaType.TEXT_HTML_VALUE)
    public String failure() {
        return "<!doctype html><html><body><h3>OAuth2 login failed</h3></body></html>";
    }
}

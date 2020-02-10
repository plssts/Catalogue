/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;
import java.io.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class HomeController implements ErrorController {
    @GetMapping("/")
    public ResponseEntity<String> index(){
        try (Reader file = new FileReader("README.md")){
            StringWriter writer = new StringWriter();
            Markdown mark = new Markdown();
            mark.transform(file, writer);
            return ok(writer.toString());

        } catch (IOException | ParseException ex){
            return new ResponseEntity<>("Issues with README. Please refer to " +
                    "https://github.com/plssts/Java2020/blob/Uzd2/README.md", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/error")
    public ResponseEntity<String> error(){
        return new ResponseEntity<>("Illegal action. Refer to index at '/' for help.", HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

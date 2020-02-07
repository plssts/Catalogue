/**
 * @author Paulius Staisiunas
 */

package com.j2020.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;

import java.io.*;

// FIXME I don't recall this being reviewed on Friday?

@RestController
public class HomeController implements ErrorController {
    @GetMapping("/")
    public String index(){
        try (Reader file = new FileReader("README.md")){
            //Reader file = new FileReader("README.md");
            StringWriter writer = new StringWriter();
            Markdown mark = new Markdown();
            mark.transform(file, writer);
            return writer.toString();

        } catch (IOException | ParseException ex){
            return "Issues with README. Please refer to " +
                    "https://github.com/plssts/Java2020/blob/Uzd2/README.md";
        }
    }

    @GetMapping("/error")
    public String error(){
        return "Unforeseen action has been performed. Refer to index at '/' for help.";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

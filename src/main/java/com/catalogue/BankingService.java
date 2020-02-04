package com.catalogue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.print.attribute.standard.MediaSize;

@Service
public class BankingService {
    @Autowired
    private RevolutService revService;

    @Autowired
    private OtherService otherService;

    public AccountService retrieveAccountService(@PathVariable String bankingService){
        switch (bankingService){
            case "revolut":
                return revService;

            default: // change to something that repreents 'not found'
                return otherService;
        }
    }
}

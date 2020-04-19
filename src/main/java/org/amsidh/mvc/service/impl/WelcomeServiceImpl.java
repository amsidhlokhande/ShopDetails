/**
 *
 */
package org.amsidh.mvc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.amsidh.mvc.service.WelcomeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author amsidhlokhande
 *
 */
@Service
@Slf4j
public class WelcomeServiceImpl implements WelcomeService {

    @Value("${welcome.message}")
    private String message;


    public WelcomeServiceImpl() {
        log.info("WelcomeServiceImpl Loading!!!");
    }


    /* (non-Javadoc)
     * @see org.amsidh.springframework.service.WelcomeService#welcomeMessage()
     */
    @Override
    public String getWelcomeMessage() {
        return this.message;
    }

}

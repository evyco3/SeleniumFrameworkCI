package org.evy.framework.objets;

import org.evy.framework.objets.authentication.NavigateToAuthenticationSection;


/**
 * Represent The homePage ->baseUrl when user first landing
 */

public class HomePage extends BasePage{

    public static HomePage getInstance(){
        return new HomePage();
    }


    public NavigateToAuthenticationSection navigateToAuthenticationSection(){
        return new NavigateToAuthenticationSection();
    }
}

package org.evy.framework.objets.home;

import org.evy.framework.drivers.Driver;
import org.evy.framework.enums.LogType;
import org.evy.framework.objets.BasePage;
import org.evy.framework.objets.products.ProductListingPage;
import org.evy.framework.utils.LoggerUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class NavigateToProductSection extends BasePage {

    public ProductListingPage selectProductFromDropdown(String mainCategory,String subCategory){
        try{
            if(mainCategory.equalsIgnoreCase("Dresses")){
                click(Driver.getInstance().getDriver().findElement(By.xpath("//a[normalize-space()='Dresses']")),mainCategory) ;
            }
            else {

                String mainCategoryStringValue = String.format("//div[@class='ui dropdown item']//*[normalize-space()='%s']", mainCategory);
                WebElement mainCategoryElement = Driver.getInstance().getDriver().findElement(By.xpath(mainCategoryStringValue));
                click(mainCategoryElement, mainCategory);

                String subCategoryStringValue = String.format("//div[@class='ui dropdown item']//*[normalize-space()='Jeans']/parent::div//a[normalize-space()='%s']", subCategory);
                WebElement subCategoryElement = Driver.getInstance().getDriver().findElement(By.xpath(subCategoryStringValue));
                click(subCategoryElement, subCategory);
            }
            
            waitForElementToBeVisible(Driver.getInstance().getDriver().findElement(By.cssSelector("h1.ui.monster.section.dividing.header")));
            LoggerUtils.log(getClass(),LogType.INFO,"Selected main category: "+mainCategory+"& sub category: "+subCategory);
            return new ProductListingPage();

        }catch (Exception e){
            LoggerUtils.log(getClass(), LogType.ERROR,"Failed to select product from dropdown ");
            throw new RuntimeException("Error During select product from dropdown",e);
        }
    }
}

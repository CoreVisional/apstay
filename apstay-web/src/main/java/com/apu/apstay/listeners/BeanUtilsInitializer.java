package com.apu.apstay.listeners;

import com.apu.apstay.utils.EnumAwareConvertUtilsBean;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.beanutils.BeanUtilsBean;

/**
 *
 * @author alexc
 */
@WebListener
public class BeanUtilsInitializer implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BeanUtilsBean.setInstance(new BeanUtilsBean(new EnumAwareConvertUtilsBean()));
        System.out.println("BeanUtilsBean configured with EnumAwareConvertUtilsBean.");
    }
}

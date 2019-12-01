package net.chrisgrollier.cloud.apps.sample.contract.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;

import net.chrisgrollier.cloud.apps.common.exception.handler.support.DefaultWebAppExceptionHandler;
import net.chrisgrollier.cloud.apps.common.i18n.MessageManager;

@ControllerAdvice
public class AppExceptionHandler extends DefaultWebAppExceptionHandler {

	@Autowired
	public AppExceptionHandler(MessageManager messageManager) {
		super(messageManager);
	}
	
}

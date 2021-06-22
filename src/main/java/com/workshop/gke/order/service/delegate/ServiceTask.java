package com.workshop.gke.order.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceTask implements JavaDelegate {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTask.class);

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("Executing {}", execution.getCurrentActivityName());

		if ("subTask1".equalsIgnoreCase(execution.getCurrentActivityName())) {
			Object variable = execution.getVariable("throwError");

			if (variable != null) {
				if ((Boolean) variable == true) {
					LOGGER.info("Throwing exeception at: {}", execution.getCurrentActivityName());
					throw new MyBusinessException();
				}
			}
		}

	}

}

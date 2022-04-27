package uo.ri.ui;

import alb.util.console.Printer;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessFactory;
import uo.ri.cws.infrastructure.persistence.jpa.executor.JpaExecutorFactory;
import uo.ri.cws.infrastructure.persistence.jpa.repository.JpaRepositoryFactory;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;
import uo.ri.ui.manager.MainMenu;

public class ManagerMain {

	public static void main(String[] args) {
		new ManagerMain()
			.configure()
			.run()
			.close();
	}

	private ManagerMain configure() {
		Factory.service = new BusinessFactory();
		Factory.repository = new JpaRepositoryFactory();
		Factory.executor = new JpaExecutorFactory();

		return this;
	}

	private ManagerMain run() {
		try {
			new MainMenu().execute();

		} catch (RuntimeException rte) {
			Printer.printRuntimeException(rte);
		}
		return this;
	}

	private void close() {
		Jpa.close();
	}

}

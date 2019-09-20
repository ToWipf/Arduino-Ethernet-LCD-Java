/*
 * Copyright (c) 2010, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.wipf.elcd.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.wipf.elcd.model.MTime;
import org.wipf.elcd.model.MWipf;
import org.wipf.elcd.model.M_ELcd_Control;

//@XmlRootElement
//@JsonIgnoreProperties(ignoreUnknown = true)

@RequestScoped
@Path("/")
public class Rest {

//	@Inject
//	private MWipf mWipf;

	@GET
	@Path("/t")
	@Produces("text/plain")
	public String testRest() {
		return MWipf.testRest();
	}

	@GET
	@Path("/date")
	@Produces("text/plain")
	public String date() {
		return MTime.date();
	}

	// Start Senden
	@GET
	@Path("s")
	@Produces("text/plain")
	public String startLcd() {
		M_ELcd_Control.startElcd();
		System.out.println("Send to LCD");
		return "RUN";
	}

	@GET
	@Path("r")
	@Produces("text/plain")
	public String zufall() {
		return MWipf.zufall();
	}

}

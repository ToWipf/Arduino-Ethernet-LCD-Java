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
import org.wipf.elcd.model.mElcd;
import org.wipf.elcd.model.mElcd2;

@RequestScoped
@Path("/")
public class Rest {

//	@GET
//	@Path("b")
//	@Produces("text/plain")
//	public String getHello() {
//		return "Hello World!";
//	}

	@GET
	@Path("xxx")
	@Produces("text/plain")
	public String test() {
		return mElcd.test();
	}

	@GET
	@Path("s")
	@Produces("text/plain")
	public void startLcd() {
		mElcd2.start();
	}

}

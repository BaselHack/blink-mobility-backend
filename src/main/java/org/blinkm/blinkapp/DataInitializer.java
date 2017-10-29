/**
 * This file Copyright (c) 2012-2014 Magnolia International
 * Ltd.  (http://www.magnolia-cms.com). All rights reserved.
 *
 *
 * This file is dual-licensed under both the Magnolia
 * Network Agreement and the GNU General Public License.
 * You may elect to use one or the other of these licenses.
 *
 * This file is distributed in the hope that it will be
 * useful, but AS-IS and WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, or NONINFRINGEMENT.
 * Redistribution, except as permitted by whichever of the GPL
 * or MNA you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or
 * modify this file under the terms of the GNU General
 * Public License, Version 3, as published by the Free Software
 * Foundation.  You should have received a copy of the GNU
 * General Public License, Version 3 along with this program;
 * if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * 2. For the Magnolia Network Agreement (MNA), this file
 * and the accompanying materials are made available under the
 * terms of the MNA which accompanies this distribution, and
 * is available at http://www.magnolia-cms.com/mna.html
 *
 * Any modifications to this file must keep this entire header
 * intact.
 */
package org.blinkm.blinkapp;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
class DataInitializer {

    private final RideRepository rideRepository;

    public DataInitializer(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @org.springframework.context.event.EventListener(ApplicationReadyEvent.class)
    public void run(ApplicationReadyEvent evt) {

        this.rideRepository
                .deleteAll()
                .thenMany(
                        Flux
                            .just(
                                    Ride.builder()
                                            .rideId("1")
                                            .contactPhone("061 233 45 89")
                                            .volunteerContactPhone("061 238 85 89")
                                            .fromAddress("St. Alban-Graben 16, 4051 Basel")
                                            .toAddress("Oslo-Strasse 2, 4142 Münchenstein")
                                            .status("accepted")
                                            .time(12323432l)
                                            .volunteer("John")
                                            .requester("Eric")
                                            .build(),

                                    Ride.builder()
                                            .rideId("2")
                                            .contactPhone("061 233 78 96")
                                            .volunteerContactPhone("061 238 44 33")
                                            .fromAddress("Greifengasse 22, 4005 Basel")
                                            .toAddress("Rebgasse 1, 4058 Basel")
                                            .status("accepted")
                                            .time(12323432l)
                                            .volunteer("Georges")
                                            .requester("Tina")
                                            .build(),

                                    Ride.builder()
                                            .rideId("3")
                                            .contactPhone("061 233 44 33")
                                            .volunteerContactPhone("061 238 87 79")
                                            .fromAddress("Centralbahnstrasse 10, 4051 Basel")
                                            .toAddress("Schwarzwaldallee 200, 4058 Basel")
                                            .status("accepted")
                                            .time(12323432l)
                                            .volunteer("Nicolas")
                                            .requester("Ankit")
                                            .build(),

                                    Ride.builder()
                                            .rideId("4")
                                            .contactPhone("061 233 99 00")
                                            .volunteerContactPhone("068 877 85 89")
                                            .fromAddress("Vogesenstrasse 167, 4056 Basel")
                                            .toAddress("Mülhauserstrasse 70-72, 4056 Basel")
                                            .status("submited")
                                            .time(12323432l)
                                            .requester("Eric")
                                            .build(),

                                    Ride.builder()
                                            .rideId("5")
                                            .contactPhone("068 957 34 89")
                                            .volunteerContactPhone("068 877 85 87")
                                            .fromAddress(" Steinwiesstrasse 75, 8032 Zürich")
                                            .toAddress(" Spitalstrasse 21, 4031 Basel")
                                            .status("submited")
                                            .time(12323432l)
                                            .requester("Hector")
                                            .build()
                            )
                            .flatMap(ride -> this.rideRepository.save(ride)))
                .subscribe(null, null,
                        () -> this.rideRepository.findAll().subscribe(System.out::println));


    }


}

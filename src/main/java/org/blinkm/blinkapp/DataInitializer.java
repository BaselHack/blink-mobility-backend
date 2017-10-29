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
                                            .contactPhone("0612334589")
                                            .fromAddress("Basel")
                                            .toAddress("Zurich")
                                            .status("Accepted")
                                            .time(12323432l)
                                            .volunteer("John")
                                            .requester("Eric")
                                            .build(),

                                    Ride.builder()
                                            .rideId("2")
                                            .contactPhone("3489573489")
                                            .fromAddress("Bern")
                                            .toAddress("Lausanne")
                                            .status("Submited")
                                            .time(12323432l)
                                            .requester("Jean")
                                            .build()
                            )
                            .flatMap(ride -> this.rideRepository.save(ride)))
                .subscribe(null, null,
                        () -> this.rideRepository.findAll().subscribe(System.out::println));


    }


}

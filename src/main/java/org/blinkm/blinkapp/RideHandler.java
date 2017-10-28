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

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.blinkm.blinkapp.model.Ride;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mongodb.connection.Server;

import reactor.core.publisher.Mono;


public class RideHandler {

    private final RideRepository rideRepository;

    public RideHandler(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    
    public Mono<ServerResponse> createRide(ServerRequest request) {
        return request.bodyToMono(Ride.class)
                .flatMap(ride -> rideRepository.insert(ride))
                .flatMap(savedRide -> ServerResponse.ok().body(fromObject(savedRide)))
                .switchIfEmpty(ServerResponse.badRequest().build());

    }

    public Mono<ServerResponse> modifyRide(ServerRequest request) {
        return request.bodyToMono(Ride.class)
                .flatMap(ride -> rideRepository.save(ride))
                .flatMap(savedRide -> ServerResponse.ok().body(fromObject(savedRide)))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> listRide(ServerRequest request) {
        String rideId = request.pathVariable("rideId");
        return ok().body(rideRepository.findById(rideId), Ride.class);
    }

    public Mono<ServerResponse> listRides(ServerRequest request) {
        return ok().body(rideRepository.findAll(), Ride.class);
    }
}

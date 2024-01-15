/*
 * Copyright (C) 2014 Atlas of Living Australia
 * All Rights Reserved.
 *
 * The contents of this file are subject to the Mozilla Public
 * License Version 1.1 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 */
package au.org.ala.sds

import au.org.ala.plugins.openapi.Path
import au.org.ala.sds.util.Configuration
import grails.converters.JSON
import groovy.util.logging.Slf4j
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH

/**
 * The Controller to serve up the SDS webapp content.
 *
 * @author Natasha Quimby (natasha.quimby@csiro.au)
 */
@Slf4j
class SDSController {
    def SDSService
    /*
        Renders the SDS front page
     */
    def index() {
        //return the date last generated for the service.
        render(model: [updateDate:SDSService.getLastUpdated()], view:'index')
    }
    /*
        Common web service to use to server up the XML files for the SDS
     */
    def getFile={
        if (params.file){
            //we have a value for the file
            log.debug("Returning " + params.file)
            //set the required response header
            response.setHeader("Cache-Control", "must-revalidate")

            String fileContents = new File('/data/sds/'+params.file.replaceAll("-data","")).getText('UTF-8')
            render(contentType: "text/xml", text:fileContents)
        }
    }
    /*
        Forces the sensitive species data list to be regenerated
     */
    def forceReload={
        def start=System.currentTimeMillis()
        SDSService.forceReload()
        def end = System.currentTimeMillis()
        render "Reload Complete in " + ((end-start).floatValue()/1000) + " seconds"
    }

    /**
     * WS to perform species lookup
     */
    @Operation(
            method = "GET",
            tags = "Species Lookup",
            operationId = "SDS Species Lookup",
            summary = "Lookup Sensitive species data based on species name, date, and location",
            description = "Lookup Sensitive species data based on species name, date, and location ",
            parameters = [
                    @Parameter(name = "scientificName",
                            in = PATH,
                            description = "Scientific name for species lookup",
                            schema = @Schema(implementation = String),
                            required = true),
                    @Parameter(name = "latitude",
                            in = PATH,
                            description = "Latitude",
                            schema = @Schema(implementation = String),
                            required = false),
                    @Parameter(name = "longitude",
                            in = PATH,
                            description = "Longitude",
                            schema = @Schema(implementation = String),
                            required = false),
                    @Parameter(name = "date",
                            in = PATH,
                            description = "Date",
                            schema = @Schema(implementation = String),
                            required = false),
            ]
    )
    @Path("/ws/{scientificName}/location/{latitude}/{longitude}/date/{date}")
    def lookup () {
        log.debug(params.toString())
        def report = SDSService.lookupSpecies(params.scientificName, params.latitude, params.longitude, params.date)
        render report as JSON

    }

    // Additional service for openapi. openapi does not support what is in UrlMappings.groovy
    @Operation(
            method = "GET",
            tags = "Species Lookup",
            operationId = "SDS Species Lookup",
            summary = "Lookup Sensitive species data based on species name, and location",
            description = "Lookup Sensitive species data based on species name, and location ",
            parameters = [
                    @Parameter(name = "scientificName",
                            in = PATH,
                            description = "Scientific name for species lookup",
                            schema = @Schema(implementation = String),
                            required = true),
                    @Parameter(name = "latitude",
                            in = PATH,
                            description = "Latitude",
                            schema = @Schema(implementation = String),
                            required = false),
                    @Parameter(name = "longitude",
                            in = PATH,
                            description = "Longitude",
                            schema = @Schema(implementation = String),
                            required = false)
            ]
    )
    @Path("/ws/{scientificName}/location/{latitude}/{longitude}")
    def lookup1 () {
        lookup()
    }

    // Additional service for openapi. openapi does not support what is in UrlMappings.groovy
    @Operation(
            method = "GET",
            tags = "Species Lookup",
            operationId = "SDS Species Lookup",
            summary = "Lookup Sensitive species data based on species name",
            description = "Lookup Sensitive species data based on species name",
            parameters = [
                    @Parameter(name = "scientificName",
                            in = PATH,
                            description = "Scientific name for species lookup",
                            schema = @Schema(implementation = String),
                            required = true)
            ]
    )
    @Path("/ws/{scientificName}")
    def lookup2 () {
        lookup()
    }

    /**
     * SDS layers
     */
    @Operation(
            method = "GET",
            tags = "Layers Lookup",
            operationId = "Get SDS Layers",
            summary = "Get a list of layers that are required by the SDS",
            description = "Get a list of layers that are required by the SDS"
    )
    @Path("/ws/layers")
    def layers () {
        def layers = Configuration.getInstance().getGeospatialLayers()
        render layers as JSON
    }
}

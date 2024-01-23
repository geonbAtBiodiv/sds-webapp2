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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * The POGO to represents a report on a species and its possible sensitivities
 *
 * @author Natasha Quimby (natasha.quimby@csiro.au)
 */
@JsonIgnoreProperties('metaClass')
class SpeciesReport {
    String scientificName
    String commonName
    String acceptedName
    def instances=[]
    def status =[]
}

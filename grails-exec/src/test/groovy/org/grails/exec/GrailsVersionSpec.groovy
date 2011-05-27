/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.exec

import spock.lang.*

class GrailsVersionSpec extends Specification {
    
    @Delegate GrailsVersion _v
    
    void version(String v) {
        _v = new GrailsVersion(v)
    }

    @Unroll("version - #number")
    def "valid parsing"() {
        given:
        version string
        
        expect:
        major == maj
        minor == min
        patch == p
        tag == t
        
        where:
        string                 | maj | min | p | t
        "1.2"                  | 1   | 2   | 0 | null
        "1.2.3"                | 1   | 2   | 3 | null
        "1.2-SNAPSHOT"         | 1   | 2   | 0 | "SNAPSHOT"
        "1.2.3-SNAPSHOT"       | 1   | 2   | 3 | "SNAPSHOT"
        "1.2.SNAPSHOT"         | 1   | 2   | 0 | "SNAPSHOT"
        "1.2.3.SNAPSHOT"       | 1   | 2   | 3 | "SNAPSHOT"
        "1.2.3.BUILD-SNAPSHOT" | 1   | 2   | 3 | "BUILD-SNAPSHOT"
    }
    
    @Unroll("ivy dependency quirk - #string - #required")
    def "ivy dependency quirk - #string - #required"() {
        when:
        version string
        
        then:
        requiresExplicitIvyDependency == required
        
        where:
        string  | required
        "1.2"   | false
        "1.3.0" | true
        "1.3.1" | true
        "1.3.2" | false
    }
    

}
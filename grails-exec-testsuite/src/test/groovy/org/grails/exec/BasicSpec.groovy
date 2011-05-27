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

class BasicSpec extends GrailsExecSpec {
    
    def "new project"() {
        when:
        newProject "new"
        
        then:
        projectFile.exists()
        projectFile("application.properties").exists()
        projectFile("grails-app").exists()
    }
    
    def "simple test"() {
        given:
        newProject "simple-test"
        
        expect:
        projectFile("test/unit").exists()
        !projectFile("target/test-reports").exists()
        
        when:
        projectFile("test/unit/PassingTests.groovy") << """
            class PassingTests extends grails.test.GrailsUnitTestCase {
                void testIt() {
                    assert true
                }
            }
        """
        
        and:
        exec "test-app"
        
        then:
        stdout.contains("Running test PassingTests...PASSED")
    }
    
}
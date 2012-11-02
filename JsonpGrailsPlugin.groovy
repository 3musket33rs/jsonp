/** Copyright 2012.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import grails.converters.JSON
import org.codehaus.groovy.grails.web.converters.Converter
import grails.plugin.jsonp.JSONP

/**
 * @author <a href='mailto:th33musk3t33rs@gmail.com'>3.musket33rs</a>
 *
 * @since 0.1
 */
class JsonpGrailsPlugin {
	def version = "0.2"
	def grailsVersion = "2.0 > *"

	def loadAfter = ['controllers']
	def observe = ['controllers']

	def title = "Jsonp Plugin"
	def author = '3.musket33rs'
	def authorEmail = 'th33musk3t33rs@gmail.com'
	def organization = [name: '3.musket33rs', url: 'http://3musket33rs.github.com/']
	def developers = [
		[ name: "Aramis alias Sebastien Blanc", email: "scm.blanc@gmail.com"],
		[ name: "Athos alias Corinne Krych", email: "corinnekrych@gmail.com" ],
		[ name: "Porthos alias Fabrice Matrat", email: "fabricematrat@gmail.com" ]
	]
	def description = 'Override render method defined for all controller to add parameter callback function name to provide cross domain JSONP RESTfull controllers'
	def documentation = "http://grails.org/plugin/jsonp"

	def license = "APACHE"
	def issueManagement = [system: 'GitHub', url: 'https://github.com/3musket33rs/jsonp/issues']
	def scm = [url: 'https://github.com/3musket33rs/jsonp']

	def doWithDynamicMethods = { ctx ->

		application.controllerClasses.each { controller ->
            def original = controller.metaClass.pickMethod("render", [Converter] as Class[])
            controller.metaClass.render = {JSON arg ->
                if (params.callback) {
                    arg = new JSONP(response, params.callback, arg.target)
                }
                original.invoke(delegate, arg)
            }
        }
	}
}

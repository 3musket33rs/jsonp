/** Copyright 2012 the original author or authors.
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

/**
*
* @author <a href='mailto:th33musk3t33rs@gmail.com'>3.musket33rs</a>
*
* @since 0.01
*/

import grails.converters.JSON;

import grails.jsonp.JSONP

class JsonpGrailsPlugin {
	// the plugin version
	def version = "0.1"
	// the version or versions of Grails the plugin is designed for
	def grailsVersion = "2.0 > *"

	// resources that are excluded from plugin packaging
	def pluginExcludes = [
		"grails-app/views/error.gsp"
	]

	// the other plugins this plugin depends on
	def dependsOn = [controllers: '1.1 > *']
	def loadAfter = ['controllers']
	def observe = ['controllers']

	// TODO Fill in these fields
	def title = "Jsonp Plugin" // Headline display name of the plugin
	def author = '3.musket33rs'
	def authorEmail = 'th33musk3t33rs@gmail.com'
	def organization = [name: '3.musket33rs', url: 'http://3musket33rs.github.com/']
	def developers = [
		[ name: "Aramis alias Sebastien Blanc", email: "scm.blanc@gmail.com"],
		[ name: "Athos alias Corinne Krych", email: "corinnekrych@gmail.com" ],
		[ name: "Porthos alias Fabrice Matrat", email: "fabricematrat@gmail.com" ]
	]
	def description = '''\
Override render method defined for all controller to add parameter callback 
function name to provide cross domain JSONP RESTfull controllers
'''
	// URL to the plugin's documentation
	def documentation = "http://grails.org/plugin/jsonp"

	// License: one of 'APACHE', 'GPL2', 'GPL3'
	def license = "APACHE"

	// Online location of the plugin's browseable source code.
	def issueManagement = [system: 'GitHub', url: 'https://github.com/3musket33rs/jsonp/issues']
	def scm = [url: 'https://github.com/3musket33rs/jsonp']


	def doWithDynamicMethods = { ctx ->

		application.controllerClasses.each { controller ->
			def original = controller.metaClass.pickMethod("render", [org.codehaus.groovy.grails.web.converters.Converter] as Class[])
			controller.metaClass.render = {JSON arg ->
				def jsonp = new JSONP(response, params.callback, arg.target)			
				original.invoke(delegate, jsonp)
			}

		}
	}

}

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
* @author <a href='mailto:th33musk3t33rs@gmail.com'>3.musketeers</a>
*
* @since 0.01
*/

import grails.converters.JSON;

import grails.jsonp.JSONP

class JsonpGrailsPlugin {
	// the plugin version
	def version = "0.01"
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
	def author = "Your name"
	def authorEmail = ""
	def description = '''\
Override render method defined for all controller to add parameter callback 
function name to provide cross domain JSONP RESTfull controllers
'''

	// URL to the plugin's documentation
	def documentation = "http://grails.org/plugin/jsonp"

	// Extra (optional) plugin metadata

	// License: one of 'APACHE', 'GPL2', 'GPL3'
	def license = "APACHE"

	// Details of company behind the plugin (if there is one)
	//def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

	// Any additional developers beyond the author specified above.
	def developers = [
		[ name: "Corinne Krych", email: "corinnekrych@gmail.com" ],
		[ name: "Fabrice Matrat", email: "fabricematrat@gmail.com" ],
		[ name: "Sebastien Blanc", email: "scm.blanc@gmail.com" ]
	]

	// Location of the plugin's issue tracker.
	//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

	// Online location of the plugin's browseable source code.
	//    def scm = [ url: "http://svn.grails-plugins.codehaus.org/browse/grails-plugins/" ]

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

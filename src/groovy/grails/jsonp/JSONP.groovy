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
* @since 0.1
*/

package grails.jsonp

import grails.converters.JSON;

import java.io.Writer;

import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException;
import org.codehaus.groovy.grails.web.sitemesh.GrailsContentBufferingResponse;


class JSONP extends JSON {
  def response
  def callback
  def target

  JSONP(response, callback, target) {
    this.response = response
    this.callback = callback
    this.target = target
  }

  void render(Writer out) {
    if (target!= null && (target instanceof ArrayList && target.size == 0)) {
      response.getWriter().write(callback + '(')
      response.getWriter().write(')')
    } else {
      response.getWriter().write(callback + '(')
      prepareRender(out)
      try {
        value(target)
        response.getWriter().write(')')
      }
      finally {
        finalizeRender(out)
      }
    }
  }

}

//
// author: Cosmin Basca
//
// Copyright 2010 University of Zurich
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package com.rdftools

import de.l3s.sesame2.tools.RDF2RDF


/**
 * Created by basca on 17/09/14.
 */
object Rdf2RdfConverter extends App {
  override def main(args: Array[String]) = {
    RDF2RDF.main(args)
  }
}

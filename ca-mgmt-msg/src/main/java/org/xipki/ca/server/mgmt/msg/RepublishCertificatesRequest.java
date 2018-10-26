/*
 *
 * Copyright (c) 2013 - 2018 Lijun Liao
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

package org.xipki.ca.server.mgmt.msg;

import java.util.List;

/**
 * TODO.
 * @author Lijun Liao
 */

public class RepublishCertificatesRequest extends CaNameRequest {

  private List<String> publisherNames;

  private int numThreads;

  public List<String> getPublisherNames() {
    return publisherNames;
  }

  public void setPublisherNames(List<String> publisherNames) {
    this.publisherNames = publisherNames;
  }

  public int getNumThreads() {
    return numThreads;
  }

  public void setNumThreads(int numThreads) {
    this.numThreads = numThreads;
  }

}

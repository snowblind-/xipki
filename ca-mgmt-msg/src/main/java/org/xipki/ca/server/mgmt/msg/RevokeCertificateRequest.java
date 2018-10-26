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

import java.math.BigInteger;
import java.util.Date;

import org.xipki.security.CrlReason;

/**
 * TODO.
 * @author Lijun Liao
 */

public class RevokeCertificateRequest extends CaNameRequest {

  private BigInteger serialNumber;

  private CrlReason reason;

  private Date invalidityTime;

  public BigInteger getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(BigInteger serialNumber) {
    this.serialNumber = serialNumber;
  }

  public CrlReason getReason() {
    return reason;
  }

  public void setReason(CrlReason reason) {
    this.reason = reason;
  }

  public Date getInvalidityTime() {
    return invalidityTime;
  }

  public void setInvalidityTime(Date invalidityTime) {
    this.invalidityTime = invalidityTime;
  }

}

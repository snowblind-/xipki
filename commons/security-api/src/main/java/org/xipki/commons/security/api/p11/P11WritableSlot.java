/*
 *
 * This file is part of the XiPKI project.
 * Copyright (c) 2013 - 2016 Lijun Liao
 * Author: Lijun Liao
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3
 * as published by the Free Software Foundation with the addition of the
 * following permission added to Section 15 as permitted in Section 7(a):
 *
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
 * THE AUTHOR LIJUN LIAO. LIJUN LIAO DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
 * OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License.
 *
 * You can be released from the requirements of the license by purchasing
 * a commercial license. Buying such a license is mandatory as soon as you
 * develop commercial activities involving the XiPKI software without
 * disclosing the source code of your own applications.
 *
 * For more information, please contact Lijun Liao at this
 * address: lijun.liao@gmail.com
 */

package org.xipki.commons.security.api.p11;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.xipki.commons.security.api.SecurityException;
import org.xipki.commons.security.api.SecurityFactory;

/**
 * @author Lijun Liao
 * @since 2.0.0
 */

public interface P11WritableSlot extends P11Slot {

    void updateCertificate(
            @Nonnull P11KeyIdentifier keyIdentifier,
            @Nonnull X509Certificate newCert,
            @Nullable Set<X509Certificate> caCerts,
            @Nonnull SecurityFactory securityFactory)
    throws P11TokenException, SecurityException;

    boolean removeKeyAndCerts(
            @Nonnull P11KeyIdentifier keyIdentifier)
    throws P11TokenException, SecurityException;

    boolean removeKey(
            @Nonnull P11KeyIdentifier keyIdentifier)
    throws P11TokenException, SecurityException;

    void removeCerts(
            @Nonnull P11KeyIdentifier keyIdentifier)
    throws P11TokenException, SecurityException;

    P11KeyIdentifier addCert(
            @Nonnull X509Certificate cert)
    throws P11TokenException, SecurityException;

    // CHECKSTYLE:SKIP
    P11KeyIdentifier generateRSAKeypair(
            int keySize,
            @Nonnull BigInteger publicExponent,
            @Nonnull String label)
    throws P11TokenException, SecurityException;

    // CHECKSTYLE:SKIP
    P11KeyIdentifier generateDSAKeypair(
            int plength,
            int qlength,
            @Nonnull String label)
    throws P11TokenException, SecurityException;

    // CHECKSTYLE:SKIP
    P11KeyIdentifier generateECKeypair(
            @Nonnull String curveNameOrOid,
            @Nonnull String label)
    throws P11TokenException, SecurityException;

    X509Certificate exportCert(
            @Nonnull P11KeyIdentifier keyIdentifier)
    throws P11TokenException, SecurityException;

    void showDetails(
            @Nonnull OutputStream stream,
            boolean verbose)
    throws P11TokenException, SecurityException, IOException;

}
// Protocol Buffers - Google's data interchange format
// Copyright 2008 Google Inc.  All rights reserved.
// https://developers.google.com/protocol-buffers/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//     * Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above
// copyright notice, this list of conditions and the following disclaimer
// in the documentation and/or other materials provided with the
// distribution.
//     * Neither the name of Google Inc. nor the names of its
// contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.google.protobuf;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.protobuf.Descriptors.Descriptor;
import protobuf_unittest.UnittestProto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class TypeRegistryTest {

  @Test
  public void getDescriptorForTypeUrl_throwsExceptionForUnknownTypes() throws Exception {
    assertThrows(
        InvalidProtocolBufferException.class,
        () -> TypeRegistry.getEmptyTypeRegistry().getDescriptorForTypeUrl("UnknownType"));
    assertThrows(
        InvalidProtocolBufferException.class,
        () -> TypeRegistry.getEmptyTypeRegistry().getDescriptorForTypeUrl("///"));
  }

  @Test
  public void findDescriptorByFullName() throws Exception {
    Descriptor descriptor = UnittestProto.TestAllTypes.getDescriptor();
    assertThat(TypeRegistry.getEmptyTypeRegistry().find(descriptor.getFullName())).isNull();

    assertThat(TypeRegistry.newBuilder().add(descriptor).build().find(descriptor.getFullName()))
        .isSameInstanceAs(descriptor);
  }

  @Test
  public void findDescriptorByTypeUrl() throws Exception {
    Descriptor descriptor = UnittestProto.TestAllTypes.getDescriptor();
    assertThat(
            TypeRegistry.getEmptyTypeRegistry()
                .getDescriptorForTypeUrl("type.googleapis.com/" + descriptor.getFullName()))
        .isNull();

    assertThat(
            TypeRegistry.newBuilder()
                .add(descriptor)
                .build()
                .getDescriptorForTypeUrl("type.googleapis.com/" + descriptor.getFullName()))
        .isSameInstanceAs(descriptor);
  }
}
